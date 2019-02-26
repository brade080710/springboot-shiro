package com.xm.shiro.utils;

import java.io.InputStream;
import java.io.OutputStream;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Json {

    private static Log logger = LogFactory.getLog(Json.class);

    // 输出全部属性
    private static ObjectMapper mapper0;

    // 只输出非Null的属性, 建议在外部接口中使用.
    private static ObjectMapper mapper1;

    static {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        mapper0 = new ObjectMapper();
        mapper0.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper0.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper0.setDateFormat(formatter);

        mapper1 = new ObjectMapper();
        mapper1.setSerializationInclusion(Include.NON_NULL);
        mapper1.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper1.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper1.setDateFormat(formatter);

    }

    public static String dumps(Object obj) throws Exception {
        return dumps(obj, true);
    }

    public static String dumps(Object obj, boolean nonNull) throws Exception {
        if (nonNull) {
            return mapper1.writeValueAsString(obj);
        }
        return mapper0.writeValueAsString(obj);
    }

    public static void dump(Object obj, OutputStream out) throws Exception {
        dump(obj, out, false);
    }

    public static void dump(Object obj, OutputStream out, boolean nonNull) throws Exception {
        if (nonNull) {
            mapper1.writeValue(out, obj);
        } else {
            mapper0.writeValue(out, obj);
        }
    }

    /**
     * 反序列化POJO或简单Collection如List<String>.
     * 
     * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
     * 
     * 如需反序列化复杂Collection如List<MyBean>, 请使用 loads(String,JavaType)
     * 
     * @see #loads(String, Class, Class...)
     */
    public static <T> T loads(String text, Class<T> clazz) {
        try {
            return mapper0.readValue(text, clazz);
        } catch (IOException e) {
            logger.warn("parse json string error:" + text, e);
            return null;
        }
    }

    public static <T> T load(InputStream in, Class<T> clazz) {
        try {
            return mapper0.readValue(in, clazz);
        } catch (IOException e) {
            logger.warn("parse json string error:", e);
            return null;
        }
    }

    /**
     * 反序列化复杂Collection如List<Bean>
     * 
     * @param text
     * @param collectionClass
     *            集合类
     * @param elementClasses
     *            集合里的元素类 例如： ArrayList<MyBean>,
     *            则调用constructCollectionType(ArrayList.class,MyBean.class)
     *            HashMap<String,MyBean>, 则调用(HashMap.class,String.class,
     *            MyBean.class)
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T loads(String text, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = mapper0.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return (T) mapper0.readValue(text, javaType);
        } catch (IOException e) {
            logger.warn("parse json string error:" + text, e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T load(InputStream in, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = mapper0.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return (T) mapper0.readValue(in, javaType);
        } catch (IOException e) {
            logger.warn("parse json string error:", e);
            return null;
        }
    }

    /**
     * 構造泛型的Collection Type如: ArrayList<MyBean>,
     * 则调用constructCollectionType(ArrayList.class,MyBean.class)
     * HashMap<String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
     */
    public static JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper0.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 当json 里只含有 bean 的部分属性时，只覆盖部分属性
     */
    @SuppressWarnings("unchecked")
    public static <T> T update(String text, T object) {
        try {
            return (T) mapper0.readerForUpdating(object).readValue(text);
        } catch (JsonProcessingException e) {
            logger.warn("update json string:" + text + " to object:" + object + " error.", e);
        } catch (IOException e) {
            logger.warn("update json string:" + text + " to object:" + object + " error.", e);
        }
        return null;
    }

    public static String dumpAsArray(Object... vals) throws Exception {
        return dumps(vals);
    }

    /**
     * eg: dumpAsMap("success",true, "message", "OK") == '{"success":true,
     * "message":"OK"}'
     * 
     * @param kvs
     * @return
     * @throws Exception
     */
    public static String dumpAsMap(Object... kvs) throws Exception {
        return dumps(ObjectUtils.map(kvs));
    }

    /**
     * @param keys
     * @param values
     * @return String Json.dumpAsMap(new String[]{"success","message","rows"},
     *         new Object[]{true,"OK",rows}) == '{"success":true,
     *         "message":"OK", "rows":[]}'
     * @throws Exception
     */
    public static String dumpAsMap(String[] keys, Object[] values) throws Exception {
        if (ObjectUtils.isEmpty(keys) || ObjectUtils.isEmpty(values)) {
            return null;
        }

        Map<String, Object> m = new HashMap<String, Object>();
        int i = 0, len = values.length;
        for (String k : keys) {
            if (i >= len)
                break;
            m.put(k, values[i]);
            i++;
        }

        return dumps(m);
    }

}
