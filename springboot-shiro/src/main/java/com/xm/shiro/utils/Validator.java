package com.xm.shiro.utils;

import java.util.List;
import java.util.regex.Pattern;

public class Validator {

	public static boolean matches(String value, String pattern) {
		if (value == null || "".equals(value))
			return false;
		return Pattern.matches(pattern, value);
	}

	public static boolean validatePhone(String value) {
		if(value==null||"".equals(value)) return false;
		return Pattern.matches("^((13[0-9])|(14[5,7,9])|(15[^4,\\D])|(17[0,1,3,5-8])|(18[0-9]))\\d{8}$", value);
	}
	
	//校验座机号码
	public static boolean validateFestnetznummer(String value) {
	    if(value==null||"".equals(value)) return false;
	    return Pattern.matches("^((0\\d{2,3}-\\d{7,8})|(1[3584]\\d{9}))$", value);
	}

	/**
	 * 检测字符串是否不为空(null,"","null")
	 * 
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s) {
		return s != null && !"".equals(s) && !"null".equals(s);
	}
	
	public static boolean notEmpty(List<?> list) {
        return list != null && list.size() > 0;
    }

	/**
	 * 检测字符串是否为空(null,"","null")
	 * 
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s) || "null".equals(s);
	}
	
	public static boolean isEmpty(List<?> list) {
        return list == null || list.size()==0;
    }

	public static boolean validateEmail(String value) {
		if(value==null||"".equals(value)) return false;
		return Pattern.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", value);
	}

	public static boolean validateSubUsername(String value) {
		if(value==null||"".equals(value)) return false;
		return Pattern.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\\#\\w+([-_]\\w+)*$", value);
	}

	public static boolean validateUsername(String username) {
		return notEmpty(username) && !username.contains("'");
	}

	public static boolean validatePassword(String value) {
		if (value == null || "".equals(value))
			return false;
		return Pattern.matches("^(?![a-zA-Z]+$)(?![0-9]+$)(?!\\W+$)[\\w\\W]{6,12}$", value);
	}

	public static boolean validateIdCard(String value) {
		if (value == null || "".equals(value))
			return false;
		return Pattern.matches("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$", value);
	}

	public static boolean validateQQNumber(String value){
	    if (null == value || "".equals(value))
	        return false;
	    return Pattern.matches("[1-9][0-9]{4,14}", value);
	}
	
	public static boolean validateBankCard(String value){
	    if (null == value || "".equals(value))
	        return false;
	    return Pattern.matches("^\\d{19}$|^\\d{16}$", value);
	}

	public static boolean validateFolderName(String value){
	    if (null == value || "".equals(value))
	        return false;
	    return Pattern.matches("^\\w+$", value);
	}
}
