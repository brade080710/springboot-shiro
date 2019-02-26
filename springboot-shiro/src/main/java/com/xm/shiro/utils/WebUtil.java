package com.xm.shiro.utils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class WebUtil {

	/**
	 * 返回 json 格式的错误信息
	 * @param response
	 * @param err
	 * @throws Exception
	 * @returns write json output: {"success":false,"message":"error message"}
	 */
	public static void writeError(HttpServletResponse response, BaseError err) throws Exception {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("success", false);
		m.put("message", err.toString());
		Json.dump(m, response.getOutputStream());
	}
	
	/**
     * 返回 json 格式的错误信息
     * @param response
     * @param err
     * @throws Exception
     */
    public static void writeError(HttpServletResponse response, String forField, BaseError err) throws Exception {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("success", false);
        m.put("field", forField);
        m.put("message", err.toString());
        Json.dump(m, response.getOutputStream());
    }
	
	/**
	 * 返回 datagrid 需要的数据
	 * @param response
	 * @param total
	 * @param rows
	 * @throws Exception
	 */
	public static <L,T> void writeDatagrid(HttpServletResponse response, L total, List<T> rows) throws Exception {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("total", total);
		m.put("rows", rows);
		Json.dump(m, response.getOutputStream());
	}

	public static <T> void writeOK(HttpServletResponse response, T data) throws Exception {
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("success", true);
		if (data instanceof BaseError){
			m.put("data", ((BaseError) data).getMessage());
		}else{
			m.put("data", data);
		}
		Json.dump(m, response.getOutputStream());
	}

	/**
	 * 
	 * @param response
	 * @param data
	 * @param nonNull 是否忽略值为 null的字段
	 * @throws Exception
	 */
	public static <T> void writeOK(HttpServletResponse response, T data,boolean nonNull) throws Exception {
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("success", true);
		if (data instanceof BaseError){
			m.put("data", ((BaseError) data).getMessage());
		}else{
			m.put("data", data);
		}
		Json.dump(m, response.getOutputStream(),nonNull);
	}
	
	
    public static <T> void writeOK(HttpServletResponse response, String name, T value) throws Exception {
        
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("success", true);
        if (value instanceof BaseError){
            m.put(name, ((BaseError) value).getMessage());
        }else{
            m.put(name, value);
        }
        Json.dump(m, response.getOutputStream());
    }
    
    public static <T> void writeOK(HttpServletResponse response, Object...kv) throws Exception {
    	Map<String,Object> m = ObjectUtils.dict(kv);
        m.put("success", true);
        Json.dump(m, response.getOutputStream());
    }

	public static void writeOK(HttpServletResponse response) throws Exception {
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("success", true);
		Json.dump(m, response.getOutputStream());
	}

	public static <T> void writeObject(HttpServletResponse response, T object) throws Exception {
		Json.dump(object, response.getOutputStream());
	}
	
	public static String generateOKResponse(String message) throws Exception {
		Map<String, Object> responseJson  = new HashMap<String, Object>();
        responseJson.put("success", true);
        responseJson.put("message", message);
        return Json.dumps(responseJson);
    }
	
	public static <T> String generateErrorResponse(Throwable error) throws Exception {
		Map<String, Object> responseJson  = new HashMap<String, Object>();
        responseJson.put("success", false);
        responseJson.put("message", error.getMessage());
        return Json.dumps(responseJson);
    }
	
	public static <T> String generateDatagridResponse(List<T> data) throws Exception {
		Map<String, Object> responseJson  = new HashMap<String, Object>();
        responseJson.put("total", data.size());
        responseJson.put("rows", data);
        return Json.dumps(responseJson);
    }
	
	
	public static String getIpAddr(HttpServletRequest request) {
		 String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
	 }

    public static void redirect(HttpServletResponse response, String url, Object... args) throws Exception {
        if (args.length>0){
            response.sendRedirect(String.format(url,urlEncode(args)));
        }else{
            response.sendRedirect(url);
        }
    }

    public static Object[] urlEncode(Object[] args) throws Exception {
        String[] ret = new String[args.length];
        for (int i=0;i<args.length;i++){
            if(args[i]==null){
                ret[i]=null;
                continue;
            }
            ret[i] = URLEncoder.encode(String.valueOf(args[i]), "utf-8");
        }
        return ret;
    }
    
    public static String urlEncode(String val) throws Exception {
        if(val==null)return "";
        return URLEncoder.encode(val, "utf-8");
    }
    
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cs=request.getCookies();
        if(cs==null || cs.length==0)return null;
        
        for(Cookie c:cs){
            if(c.getName().equals(name)){
                return c;
            }
        }
        
        return null;
    }

	public static String getRequestUrlRoot(StringBuffer requestURL, String requestURI) {
		return requestURL.substring(0,requestURL.indexOf(requestURI)+1);
	}

}
