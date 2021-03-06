package com.sbs.java.blog.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Util {
	public static String gmailId;
	public static String gmailPw;
	public static String extra_code;
	
	public static boolean empty(HttpServletRequest req, String paramName) {
		String paramValue = req.getParameter(paramName);
		
		return empty(paramValue);
	}

	private static boolean empty(Object obj) {
		if(obj==null) {
			return true;
		}
		if(obj instanceof String) {
			return ((String) obj).trim().length()==0;
		}
		return true;
	}
	
	public static boolean isNum(HttpServletRequest req, Object obj) {
		if(obj == null) {
			return false;
		}
		if(obj instanceof Long) {
			return true;
		} else if(obj instanceof String) {
			try {
				Integer.parseInt((String) obj);
				return true;
			}
			catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}
	
	public static String getUriEncoded(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}
	
	public static int getInt(HttpServletRequest req, String paramName) {
		return Integer.parseInt(req.getParameter(paramName));
	}
	
	public static String getString(HttpServletRequest req, String paramName) {
		return req.getParameter(paramName);
	}
	
	public static String getString(HttpServletRequest req, String paramName, String elseValue) {
		if (req.getParameter(paramName) == null) {
			return elseValue;
		}

		if (req.getParameter(paramName).trim().length() == 0) {
			return elseValue;
		}

		return getString(req, paramName);
	}
		
	public static void printEx(String errName, HttpServletResponse resp, Exception e) {
		try {
			resp.getWriter().append("<h1 style='color:red; font-weight:bold; text-align: left;'>[에러 : "+errName+"]</h1>");
			resp.getWriter().append("<pre style='text-align:left; font-weight:bold; font-size:1.3rem;'>");
			e.printStackTrace(resp.getWriter());
			resp.getWriter().append("</pre>");
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}