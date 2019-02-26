package com.xm.shiro.utils;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectUtils {
	public static String[] array(String... vals){
		return vals;
	}
	public static Object[] array(Object... vals){
		return vals;
	}
	/**
	 * 
	 * @param kvs 
	 * @return
	 * call: map("success",true,"message","hello")
	 * get : Map{"success"=>true,"message"=>"hello"}
	 */
	public static Map<Object,Object> map(Object... kvs){
		Map<Object,Object> m=new HashMap<Object,Object>();
		if(kvs==null || kvs.length==0)return m;
		
		for(int i=0,len=kvs.length;(i+1)<len;i+=2){
			if(isEmpty(kvs[i]))continue;
			m.put(kvs[i], kvs[i+1]);	
		}
		return m;
	}
	
	/**
	 * 
	 * @param kvs 
	 * @return
	 * call: dict("success",true,1,"hello")
	 * get : Map{"success"=>true,"1"=>"hello"}
	 */
	public static Map<String,Object> dict(Object... kvs){
		Map<String,Object> m=new HashMap<String,Object>();
		if(kvs==null || kvs.length==0)return m;
		
		for(int i=0,len=kvs.length;(i+1)<len;i+=2){
			if(isEmpty(kvs[i]))continue;
			
			m.put((String)kvs[i], kvs[i+1]);	
		}
		return m;
	}

	public static boolean isNull(Object o) {
         return (o==null)?true:false;
	}
	public static boolean notNull(Object o) {
        return (o==null)?false:true;
	}

	public static boolean isNullOrEmptyString(Object o) {
		if(o == null)
			return true;
		if(o instanceof String) {
			String str = (String)o;
			if(str.length() == 0)
				return true;
		}
		return false;
	}
	
	/**
	 * 可以用于判断 Map,Collection,String,Array是否为空
	 * @param o
	 * @return
	 */
	@SuppressWarnings("all")
    public static boolean isEmpty(Object o)  {
        if(o == null) return true;

        if(o instanceof String) {
            if(((String)o).length() == 0){
                return true;
            }
        } else if(o instanceof Collection) {
            if(((Collection)o).isEmpty()){
                return true;
            }
        } else if(o.getClass().isArray()) {
            if(Array.getLength(o) == 0){
                return true;
            }
        } else if(o instanceof Map) {
            if(((Map)o).isEmpty()){
                return true;
            }
        }

        return false;
    }
	/**
	 * 可以用于判断 Map,Collection,String,Array,number,boolean是否为空
	 * @param o
	 * @return
	 */
	@SuppressWarnings("all")
    public static boolean isEmpty(Object o,boolean strict)  {
        if(o == null) return true;

        if(o instanceof String) {
            if(((String)o).length() == 0){
                return true;
            }
        } else if(o instanceof Collection) {
            if(((Collection)o).isEmpty()){
                return true;
            }
        } else if(o.getClass().isArray()) {
            if(Array.getLength(o) == 0){
                return true;
            }
        } else if(o instanceof Map) {
            if(((Map)o).isEmpty()){
                return true;
            }
        }
        
        if(strict){
        	if(o instanceof Boolean){
            	if(o.equals(Boolean.FALSE)){
            		return true;
            	}
            }else if(o.getClass().isPrimitive()){
            	if(Double.valueOf(o.toString())==0d){
            		return true;
            	}
            }
        }

        return false;
    }
	
	public static boolean empty(Object obj){
		return isEmpty(obj);
	}
	public static boolean empty(Object obj,boolean strict){
		return isEmpty(obj,strict);
	}

	/**
	 * 可以用于判断 Map,Collection,String,Array是否不为空
	 * @param c
	 * @return
	 */
	public static boolean isNotEmpty(Object c) throws IllegalArgumentException{
		return !isEmpty(c);
	}
	
	/**
	 * 可以用于判断 Map,Collection,String,Array,number,boolean是否不为空
	 * @param c
	 * @return
	 */
	public static boolean isNotEmpty(Object c,boolean strict) throws IllegalArgumentException{
		return !isEmpty(c,strict);
	}
	
	 /** 
     * 数据库Clob对象转换为String
     * @param clob
     */  
    public static String clobToString(Clob clob) {  
        if(clob == null)
            return null;
        
        try {  
            Reader inStreamDoc = clob.getCharacterStream();  
            char[] tempDoc = new char[(int) clob.length()];  
            inStreamDoc.read(tempDoc);  
            inStreamDoc.close();  
            return new String(tempDoc);  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (SQLException es) {  
            es.printStackTrace();  
        } 
        return null;  
    }
	public static Boolean asBool(String value) {
		if(value==null || value.equals(""))return false;
		if(value.equals("true")||value.equals("1")||value.equals("yes"))return true;
		return false;
	}  
	
	public static String asString(Object obj){
		if(obj==null)return "";
		return obj.toString();
	}
	
	public static Integer asInteger(Object obj){
		if(obj==null)return null;
		return Integer.parseInt(obj.toString());
	}
	
	
	public static void setValues(Object target, Object... kvs) throws Exception{
        if(target==null || kvs==null || kvs.length==0)return;
        
        String name;
        Class<?> cls=target.getClass();
        Method[] methods=cls.getMethods();
        
        for(int i=0,len=kvs.length;(i+1)<len;i+=2){
            
            name=kvs[i].toString();
            if(name.equals(""))continue;
            
            for(Method m:methods){
                if(!Modifier.isPublic(m.getModifiers()))continue;
                if(m.getName().equals("set"+StringUtils.ucfirst(name))){
                    m.invoke(target, kvs[i+1]);
                    break;
                }
            }
        }
    }
    
    public static void updateValues(Object target, Map<String,Object> kvs) throws Exception{
        if(target==null || kvs==null)return;
        
        Class<?> cls=target.getClass();
        Method[] methods=cls.getMethods();
        
        for(String name:kvs.keySet()){
            
            if(name.equals(""))continue;
            
            for(Method m:methods){
                if(!Modifier.isPublic(m.getModifiers()))continue;
                if(m.getName().equals("set"+StringUtils.ucfirst(name))){
                    m.invoke(target, kvs.get(name));
                    break;
                }
            }
        }
    }
    
    /**
     * 从 values 对象读取值然后更新到target对象. 最好在同类型的对象间操作
     * @param target
     * @param values
     * @param updateNull
     * @throws Exception
     */
    public static <T> void updateValues(T target, T values, boolean updateNull) throws Exception{
        if(target==null || values==null)return;
        
        Method[] setters=target.getClass().getMethods();
        Method[] getters=values.getClass().getMethods();
        
        for(Method m:setters){
            if(!Modifier.isPublic(m.getModifiers()))continue;
            
            if(!m.getName().startsWith("set") || m.getParameterCount()!=1){
                continue;
            }

            for(Method g:getters){
                if(!Modifier.isPublic(g.getModifiers()))continue;
                if(!g.getName().startsWith("get") || g.getParameterCount() > 0)continue;

                if(g.getName().substring(3).equals(m.getName().substring(3))){
                    if(updateNull){
                        m.invoke(target, g.invoke(values));
                    }else{
                        Object v = g.invoke(values);
                        if(v==null){break;}
                        m.invoke(target, v);
                    }
                }
            }
        }
    }
	
}
