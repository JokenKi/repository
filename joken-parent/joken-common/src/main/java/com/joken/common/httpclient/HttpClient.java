/**
 * 
 */
package com.joken.common.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.joken.common.properties.SystemGlobal;
import com.joken.common.utils.MapUtils;
import com.joken.common.utils.StringUtils;

/***
 * 类描述：Http请求工具类
 * 
 * @version V1.0.0
 * 
 * @author 欧阳增高
 */
public class HttpClient {

	// private HttpClient(){
	// }

	/**
	 * GET方式访问HTTP
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            Map请求参数
	 * @param charset
	 *            编码方式
	 * @return 请求响应值
	 */
	public static String get(String url, Map<String, Object> params,
			String charset) {
		return get(url, MapUtils.mapToUrlParams(params), charset);
	}

	/**
	 * GET方式访问HTTP
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param charset
	 *            编码方式
	 * @return 请求响应值
	 */
	public static String get(String url, String params, String charset) {
		if (StringUtils.isEmpty(url)) {
			return null;
		}
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();
		try {
			StringBuilder uri = new StringBuilder(url);
			if (params != null && params.length() > 0) {
				uri.append(uri.indexOf("?") == -1 ? "?" : "&");
				uri.append(params);
			}

			URL u = new URL(uri.toString());
			HttpURLConnection con = (HttpURLConnection) u.openConnection();
			con.setUseCaches(false);
			HttpURLConnection.setFollowRedirects(true);
			con.setConnectTimeout(6000);
			in = new BufferedReader(new InputStreamReader(con.getInputStream(),
					charset));
			while (true) {
				String line = in.readLine();
				if (line == null) {
					break;
				} else {
					result.append(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace(System.out);
				}
			}
		}
		return result.toString();
	}

	/**
	 * 发送POST请求
	 * 
	 * @param urlPath
	 *            请求路径
	 * @param params
	 *            Map请求参数
	 * @param charset
	 *            编码格式
	 * @return 请求响应值
	 */
	public static String post(String urlPath, Map<String, Object> params,
			String charset) {
		return post(urlPath, MapUtils.mapToUrlParams(params), charset);
	}
	
	/**
	 * post请求，默认字符格式utf-8
	 * @param urlPath
	 * @param params
	 * @return
	 */
	public static String post(String urlPath, Map<String, Object> params) {
		return post(urlPath, MapUtils.mapToUrlParams(params), "UTF-8");
	}

	/**
	 * 发送POST请求
	 * 
	 * @param urlPath
	 *            请求路径
	 * @param params
	 *            请求参数
	 * @param charset
	 *            编码格式
	 * @return 请求响应值
	 */
	public static String post(String urlPath, String params, String charset) {
		return post(urlPath, params, charset,
				"application/x-www-form-urlencoded");
	}

	/**
	 * 发送POST请求
	 * 
	 * @param urlPath
	 *            请求路径
	 * @param params
	 *            请求参数
	 * @param charset
	 *            编码格式
	 * @return 请求响应值
	 */
	public static String postJson(String urlPath, String params, String charset) {
		return post(urlPath, params, charset, "application/json");
	}

	/**
	 * 发送POST请求
	 * 
	 * @param urlPath
	 *            请求路径
	 * @param params
	 *            请求参数
	 * @param charset
	 *            编码格式
	 * @param contentType
	 *            请求内容格式
	 * @return 请求响应值
	 */
	private static String post(String urlPath, String params, String charset,
			String contentType) {
		if (StringUtils.isEmpty(urlPath)) {
			return null;
		}
		BufferedReader in = null;
		try {
			// 发送POST请求
			URL url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", StringUtils.getValue(
					contentType, "application/x-www-form-urlencoded"));
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			Integer timeout = SystemGlobal.getInteger("http.client.timeout");
			if (timeout > -1) {
				conn.setConnectTimeout(timeout);
			}

			conn.setRequestProperty("Content-Length", "" + params.length());
			OutputStreamWriter out = new OutputStreamWriter(
					conn.getOutputStream(), charset);
			out.write(params);
			out.flush();
			out.close();

			// 获取响应状态
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.out.println("HttpClient连接错误!状态码："
						+ conn.getResponseCode() + ",url:" + urlPath + ",参数:"
						+ params);
				return null;
			}
			// 获取响应内容体
			String line;
			StringBuilder result = new StringBuilder();
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), charset));
			while ((line = in.readLine()) != null) {
				if (result.length() > 0) {
					result.append("\n");
				}
				result.append(line);
			}
			in.close();
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace(System.out);
				}
			}
		}
		return null;
	}

}
