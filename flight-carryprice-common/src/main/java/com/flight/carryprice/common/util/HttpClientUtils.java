package com.flight.carryprice.common.util;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.*;
import java.net.ProtocolException;
import java.util.*;

/**
 * 
 * @Title: HttpClientUtils.java
 * @Description: HttpClient工具类
 * @version V1.0
 */
public class HttpClientUtils {

	private final static Logger LOGGER = Logger.getLogger(HttpClientUtils.class);

	public static final String METHOD_GET = "GET";

	public static final String METHOD_POST = "POST";

	public static final String DEFAULT_CHARSET = "UTF-8";

	public static final int defaultConnectionTimeout = 30 * 1000;// 连接超时时间

	public static final int defaultSoTimeout = 30 * 1000;// 回应超时时间

	/**
	 * post请求
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String sendPostRequest(String url, Map<String, String> paramMap, String charset) throws IOException {
		LOGGER.info("进行查询参数为：");
		LOGGER.info("url：" + url);
		StringBuffer sb = new StringBuffer();
		Iterator iter = paramMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = entry.getKey().toString();
			String val = paramMap.get(key);
			if (!"data".equals(key)) {
				sb.append("名称 : " + key + " , 值 ：" + val + "; ");
			}
		}
		LOGGER.info(sb.toString());
		return sendHttpRequest(url, paramMap, METHOD_POST, charset, null, null);
	}

	/**
	 * post请求 booking使用http请求
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String sendPostJDSpeRequest(String url, Map<String, String> paramMap, String charset) {
		HttpURLConnection httpURLConnection = null;
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		StringBuffer responseResult = new StringBuffer();
		StringBuffer params = new StringBuffer();
		// 组织请求参数
		Iterator it = paramMap.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry element = (Map.Entry) it.next();
			params.append(element.getKey());
			params.append("=");
			params.append(element.getValue());
			params.append("&");
		}
		if (params.length() > 0) {
			params.deleteCharAt(params.length() - 1);
		}

		LOGGER.info("params = " + params);

		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			httpURLConnection = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			// httpURLConnection.setRequestProperty("accept", "*/*");
			httpURLConnection.setRequestProperty("accept", "application/json");
			httpURLConnection.setRequestProperty("connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Content-Length", String.valueOf(params.length()));
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			printWriter = new PrintWriter(httpURLConnection.getOutputStream());
			// 发送请求参数
			printWriter.write(params.toString());
			// flush输出流的缓冲
			printWriter.flush();
			// 根据ResponseCode判断连接是否成功
			int responseCode = httpURLConnection.getResponseCode();
			if (responseCode != 200) {
				LOGGER.info(" Error===" + responseCode);
			} else {
				LOGGER.info("Post Success!");
			}
			// 定义BufferedReader输入流来读取URL的ResponseData
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				// responseResult.append("/n").append(line);
				responseResult.append(line);
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		} finally {
			httpURLConnection.disconnect();
			try {
				if (printWriter != null) {
					printWriter.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ex) {
				LOGGER.error(ex);
			}

		}

		return responseResult.toString();
	}

	/**
	 * @Title: sendSOAP12Request @Description: 发送soap协议 @param @param
	 *         url @param @param xml @param @param
	 *         charset @param @return @return String @throws
	 */
	public static String sendSOAP12Request(String url, String xml, String charset) {
		charset = charset == null ? DEFAULT_CHARSET : charset;
		try {
			HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(defaultConnectionTimeout);
			conn.setReadTimeout(defaultSoTimeout);
			conn.addRequestProperty("Content-Length", String.valueOf(xml.length()));
			conn.addRequestProperty("Content-Type", "application/soap+xml; charset=" + charset);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream out = conn.getOutputStream();
			out.write(xml.getBytes());
			out.close();
			int code = conn.getResponseCode();
			if (code != 200) {
				LOGGER.info(String.format("SOAP12 request failed: %d for %s", code, url));
				return null;
			}
			InputStream in = conn.getInputStream();
			ByteArrayOutputStream respStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				respStream.write(buffer, 0, len);
			}
			in.close();
			String resp = respStream.toString();
			respStream.close();
			return resp;
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return null;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @param charset
	 * @return
	 */
	public static String sendGetRequest(String url, String charset) throws Exception {
		return sendHttpRequest(url, null, METHOD_GET, charset, null, null);
	}

	/**
	 * http请求
	 * 
	 * @param url
	 * @param paramMap
	 * @param http_method
	 * @param charset
	 * @return
	 */
	public static String sendHttpRequest(String url, Map<String, String> paramMap, String http_method, String charset,
			Integer connectionTimeout, Integer soTimeout) throws HttpException, IOException {

		HttpMethod method = null;
		String responseString = "";

		HttpClient httpclient = new HttpClient();
		// 设置代理服务器的ip地址和端口
		// httpclient.getHostConfiguration().setProxy("192.168.63.158", 54691);
		// 使用抢先认证
		// httpclient.getParams().setAuthenticationPreemptive(true);
		// 编码
		charset = charset == null ? DEFAULT_CHARSET : charset;
		connectionTimeout = connectionTimeout == null ? defaultConnectionTimeout : connectionTimeout;
		soTimeout = soTimeout == null ? defaultSoTimeout : soTimeout;
		// 设置连接超时时间
		httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
		// 设置回应超时
		httpclient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

		// get模式
		if (HttpClientUtils.METHOD_GET.equalsIgnoreCase(http_method)) {
			// 将url后面的参数分离出来，设置到queryString
			Map<String, String> urlMap = splitUrlAndQueryString(url);
			method = new GetMethod(urlMap.get("url"));
			method.getParams().setCredentialCharset(charset);
			if (urlMap.size() > 1) {
				if (null == paramMap) {
					paramMap = new HashMap<String, String>();
				}
				for (Map.Entry<String, String> entry : urlMap.entrySet()) {
					if (!"url".equals(entry.getKey())) {// 过滤掉url
						paramMap.put(entry.getKey(), entry.getValue());
					}
				}
			}
			if (null != paramMap) {
				String queryString = createLinkString(paramMap);
				method.setQueryString(queryString);
			}

		} else {
			// post
			method = new PostMethod(url);
			if (null != paramMap) {
				NameValuePair[] parameters = generatNameValuePair(paramMap);
				((PostMethod) method).addParameters(parameters);
			}
			// text/html;
			method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + charset);
		}
		method.addRequestHeader("User-Agent", "Mozilla/4.0");
		try {
			int executeMethod = httpclient.executeMethod(method);
			// responseString = method.getResponseBodyAsString();
			LOGGER.info("状态码：" + executeMethod);
			if (executeMethod == 500) {
				throw new IOException("接口访问返回500，服务端接口服务异常");
			}
			String responseBodyAsString = method.getResponseBodyAsString();
			// System.out.println(responseBodyAsString);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(method.getResponseBodyAsStream(), charset));
			StringBuffer stringBuffer = new StringBuffer();
			String str = null;
			while ((str = reader.readLine()) != null) {
				stringBuffer.append(str);
			}
			responseString = stringBuffer.toString();
		} catch (HttpException e) {
			LOGGER.error("", e);
			e.printStackTrace();
			throw new HttpException(e.toString());
		} catch (IOException e) {
			LOGGER.error("", e);
			e.printStackTrace();
			throw new IOException(e.toString());
		} finally {
			method.releaseConnection();
		}
		// logger.info("访问远程服务器<" + url + ">返回：" + responseString);
		return responseString;
	}

	/**
	 * HTTP POST 请求
	 *
	 * @param serverUrl
	 *            请求地址
	 * @param requestMap
	 *            请求信息（MAP）
	 * @param charset
	 *            编码
	 * @param connectionTimeout
	 *            连接超时时间
	 * @param soTimeout
	 *            读取超时时间
	 * @return 请求结果
	 */
	public static String sendPostRequest(String serverUrl, Map requestMap, String charset, Integer connectionTimeout,
			Integer soTimeout) throws Exception {
		// 返回信息
		String responseXml = "";
		// 构造HttpClient的实例；使用完后关闭连接（有待测试）
		HttpClient httpClient = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));

		charset = charset == null ? DEFAULT_CHARSET : charset;
		connectionTimeout = connectionTimeout == null ? defaultConnectionTimeout : connectionTimeout;
		soTimeout = soTimeout == null ? defaultSoTimeout : soTimeout;

		// 设置连接超时时间
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
		// 设置回应超时
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

		// 创建POST方法的实例
		PostMethod postMethod = new PostMethod(serverUrl);
		// 设置失败不重试
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(0, false));

		// 返回结果的数据解压
		InputStream inputStream = null;
		try {
			if (null != requestMap) {
				NameValuePair[] parameters = generatNameValuePair(requestMap);
				postMethod.addParameters(parameters);
			}
			postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + charset);

			// 执行Method
			int statusCode = httpClient.executeMethod(postMethod);
			// 失败抛出去,IBE+内部标识：用户名、密码、IP错误或无权限
			if (statusCode != HttpStatus.SC_OK) {
				LOGGER.error("HTTP POST 请求，执行操作失败，返回状态---->" + statusCode);
			} else {
				inputStream = postMethod.getResponseBodyAsStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset));

				StringBuilder stringBuilder = new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line);
				}
				responseXml = stringBuilder.toString();
			}

		} catch (Exception e) {
			LOGGER.error("HTTP POST 请求报错", e);
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception ex) {
				LOGGER.error("HTTP POST 请求，关闭返回流信息报错", ex);
				throw new Exception(ex.getMessage());
			}
			// httpClient释放连接
			postMethod.releaseConnection();
		}
		LOGGER.info("返回信息==========" + responseXml);
		return responseXml;
	}

	public static String sendHttpRequestByProxy(String url, Map<String, String> paramMap, String http_method,
			String charset, Integer connectionTimeout, Integer soTimeout, String ip, Integer port) {

		HttpMethod method = null;
		String responseString = "";

		HttpClient httpclient = new HttpClient();
		// 设置代理服务器的ip地址和端口
		// httpclient.getHostConfiguration().setProxy("192.168.63.158", 54691);
		// 使用抢先认证
		httpclient.getParams().setAuthenticationPreemptive(true);
		// 编码
		charset = charset == null ? DEFAULT_CHARSET : charset;
		connectionTimeout = connectionTimeout == null ? defaultConnectionTimeout : connectionTimeout;
		soTimeout = soTimeout == null ? defaultSoTimeout : soTimeout;
		// 设置连接超时时间
		httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
		// 设置回应超时
		httpclient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

		// 此部分上线须删掉
		// HttpHost proxy = new HttpHost(ip,port);
		// httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
		// proxy);

		httpclient.getHostConfiguration().setProxy(ip, port);

		httpclient.getParams().setAuthenticationPreemptive(true);

		// get模式
		if (HttpClientUtils.METHOD_GET.equalsIgnoreCase(http_method)) {
			// 将url后面的参数分离出来，设置到queryString
			Map<String, String> urlMap = splitUrlAndQueryString(url);
			method = new GetMethod(urlMap.get("url"));
			method.getParams().setCredentialCharset(charset);
			if (urlMap.size() > 1) {
				if (null == paramMap) {
					paramMap = new HashMap<String, String>();
				}
				for (Map.Entry<String, String> entry : urlMap.entrySet()) {
					if (!"url".equals(entry.getKey())) {// 过滤掉url
						paramMap.put(entry.getKey(), entry.getValue());
					}
				}
			}
			if (null != paramMap) {
				String queryString = createLinkString(paramMap);
				method.setQueryString(queryString);
			}

		} else {
			// post
			method = new PostMethod(url);
			if (null != paramMap) {
				NameValuePair[] parameters = generatNameValuePair(paramMap);
				((PostMethod) method).addParameters(parameters);
			}
			method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=" + charset);
		}
		method.addRequestHeader("User-Agent", "Mozilla/4.0");

		try {
			httpclient.executeMethod(method);
			// responseString = method.getResponseBodyAsString();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(method.getResponseBodyAsStream(), charset));
			StringBuffer stringBuffer = new StringBuffer();
			String str = null;
			while ((str = reader.readLine()) != null) {
				stringBuffer.append(str);
			}
			responseString = stringBuffer.toString();
		} catch (HttpException e) {
			LOGGER.error("", e);
			responseString = null;
		} catch (IOException e) {
			LOGGER.error("", e);
			responseString = null;
		} finally {
			method.releaseConnection();
		}
		// logger.info("访问远程服务器<" + url + ">返回：" + responseString);
		return responseString;
	}

	/**
	 * JD传文件http-post请求
	 * 
	 * @param url
	 * @param paramMap
	 * @param fileMap
	 * @return
	 */
	public static String sendPostFileRequest(String url, LinkedHashMap<String, String> paramMap,
			Map<String, List<String>> fileMap) {
		return sendPostFileRequest(url, paramMap, fileMap, null, null);
	}

	/**
	 * JD传文件http-post请求
	 * 
	 * @param url
	 * @param paramMap
	 * @param fileMap
	 * @param connectionTimeout
	 * @param soTimeout
	 * @return
	 */
	public static String sendPostFileRequest(String url, LinkedHashMap<String, String> paramMap,
			Map<String, List<String>> fileMap, Integer connectionTimeout, Integer soTimeout) {
		String res = "";
		connectionTimeout = connectionTimeout == null ? defaultConnectionTimeout : connectionTimeout;
		soTimeout = soTimeout == null ? defaultSoTimeout : soTimeout;
		HttpURLConnection conn = null;
		String BOUNDARY = "---------------------------123821742118716"; // boundary就是request头和上传文件内容的分隔符
		try {
			URL realUrl = new URL(url);
			conn = (HttpURLConnection) realUrl.openConnection();
			conn.setConnectTimeout(connectionTimeout);
			conn.setReadTimeout(soTimeout);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			OutputStream out = new DataOutputStream(conn.getOutputStream());
			// text
			if (paramMap != null) {
				StringBuffer strBuf = new StringBuffer();
				Iterator iter = paramMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes());
			}

			// file
			if (fileMap != null) {
				Iterator iter = fileMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String inputName = (String) entry.getKey();
					List<String> inputValues = (List<String>) entry.getValue();
					if (inputValues == null) {
						continue;
					}

					for (String inputValue : inputValues) {

						File file = new File(inputValue);
						String filename = file.getName();
						String contentType = new MimetypesFileTypeMap().getContentType(file);
						if (filename.endsWith(".png")) {
							contentType = "image/png";
						}
						if (contentType == null || contentType.equals("")) {
							contentType = "application/octet-stream";
						}

						StringBuffer strBuf = new StringBuffer();
						strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
						strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\""
								+ filename + "\"\r\n");
						strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

						out.write(strBuf.toString().getBytes());

						DataInputStream in = new DataInputStream(new FileInputStream(file));
						int bytes = 0;
						byte[] bufferOut = new byte[1024];
						while ((bytes = in.read(bufferOut)) != -1) {
							out.write(bufferOut, 0, bytes);
						}
						in.close();
					}
				}
			}

			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();

			// 读取返回数据
			StringBuffer strBuf = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			res = strBuf.toString();
			reader.close();
			reader = null;
		} catch (Exception e) {
			LOGGER.error("", e);
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return res;
	}

	/**
	 * 洗ip专用方法
	 * 
	 * @param url
	 * @param connectionTimeout
	 * @param soTimeout
	 * @param ip
	 * @param port
	 * @return
	 */
	public static Integer cleanIP(String url, Integer connectionTimeout, Integer soTimeout, String ip, Integer port) {

		HttpMethod method = null;

		HttpClient httpclient = new HttpClient();
		// 设置代理服务器的ip地址和端口
		// httpclient.getHostConfiguration().setProxy("192.168.63.158", 54691);
		// 使用抢先认证
		httpclient.getParams().setAuthenticationPreemptive(true);
		connectionTimeout = connectionTimeout == null ? defaultConnectionTimeout : connectionTimeout;
		soTimeout = soTimeout == null ? defaultSoTimeout : soTimeout;
		// 设置连接超时时间
		httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
		// 设置回应超时
		httpclient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

		// 此部分上线须删掉
		// HttpHost proxy = new HttpHost(ip,port);
		// httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
		// proxy);

		httpclient.getHostConfiguration().setProxy(ip, port);

		httpclient.getParams().setAuthenticationPreemptive(true);

		// get模式
		// 将url后面的参数分离出来，设置到queryString
		Map<String, String> urlMap = splitUrlAndQueryString(url);
		method = new GetMethod(urlMap.get("url"));
		method.getParams().setCredentialCharset("utf-8");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (urlMap.size() > 1) {
			for (Map.Entry<String, String> entry : urlMap.entrySet()) {
				if (!"url".equals(entry.getKey())) {// 过滤掉url
					paramMap.put(entry.getKey(), entry.getValue());
				}
			}
		}
		if (null != paramMap) {
			String queryString = createLinkString(paramMap);
			method.setQueryString(queryString);
		}

		method.addRequestHeader("User-Agent", "Mozilla/4.0");

		try {
			return httpclient.executeMethod(method);
		} catch (Exception e) {
			LOGGER.error("", e);
			throw new RuntimeException(e.getMessage());
		} finally {
			method.releaseConnection();
		}
	}

	/**
	 * 分割URL
	 * 
	 * @param url
	 * @return
	 */
	public static Map<String, String> splitUrlAndQueryString(String url) {
		Map<String, String> map = new HashMap<String, String>();

		if (url.indexOf("?") != -1) {
			map.put("url", url.split("\\?")[0]);
			String queryString = url.split("\\?")[1];
			String[] aParam = queryString.split("&");
			for (String param : aParam) {
				String[] e = param.split("=");
				if (e != null && e.length > 1) {
					map.put(e[0], e[1]);
				}
			}
		} else {
			map.put("url", url);
		}

		return map;
	}

	/**
	 * MAP类型数组转换成NameValuePair类型
	 * 
	 * @param paramMap
	 *            MAP类型数组
	 * @return NameValuePair类型数组
	 */
	public static NameValuePair[] generatNameValuePair(Map<String, String> paramMap) {
		NameValuePair[] nameValuePair = new NameValuePair[paramMap.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
		}
		return nameValuePair;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = urlEncode(params.get(key), DEFAULT_CHARSET);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	/**
	 * @Title: createNoLinkString @Description: @param @param
	 *         params @param @return @return String @throws
	 */
	public static String createNoLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;

	}

	public static String urlEncode(String str, String charset) {
		try {
			charset = null == charset ? DEFAULT_CHARSET : charset;
			return URLEncoder.encode(str, charset);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("", e);
			return null;
		}
	}

	public static String urlDecode(String str, String charset) {
		try {
			charset = null == charset ? DEFAULT_CHARSET : charset;
			return URLDecoder.decode(str, charset);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("", e);
			return null;
		}
	}

	/**
	 * httpclient 传输文件
	 * 
	 * @param url
	 * @param requestParamsMap
	 * @param fileInputName
	 * @param fileName
	 * @param is
	 * @return
	 */
	public static String reqestWithFile(String url, Map<String, String> requestParamsMap, String fileInputName,
			String fileName, InputStream is) {

		OutputStream out = null;
		DataInputStream in = null;
		BufferedReader reader = null;

		String result = "";

		try {
			HttpURLConnection conn = null;
			final String BOUNDARY = "---------------------------123821742118716"; // boundary就是request头和上传文件内容的分隔符

			URL realUrl = new URL(url);
			conn = (HttpURLConnection) realUrl.openConnection();
			conn.setConnectTimeout(HttpClientUtils.defaultConnectionTimeout);
			conn.setReadTimeout(HttpClientUtils.defaultSoTimeout);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			out = new DataOutputStream(conn.getOutputStream());
			// text
			if (requestParamsMap != null) {
				StringBuffer strBuf = new StringBuffer();
				Iterator iter = requestParamsMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes());
			}

			String contentType = "application/octet-stream";
			StringBuffer strBuf = new StringBuffer();
			strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
			strBuf.append("Content-Disposition: form-data; name=\"" + fileInputName + "\"; filename=\"" + fileName
					+ "\"\r\n");
			strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

			out.write(strBuf.toString().getBytes());

			in = new DataInputStream(is);
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}

			// file
			/*
			 * if (fileMap != null) { Iterator iter =
			 * fileMap.entrySet().iterator(); while (iter.hasNext()) { Map.Entry
			 * entry = (Map.Entry) iter.next(); String inputName = (String)
			 * entry.getKey(); String inputValue = (String) entry.getValue(); if
			 * (inputValue == null) { continue; } File file = new
			 * File(inputValue); String filename = file.getName(); String
			 * contentType = new MimetypesFileTypeMap().getContentType(file); if
			 * (filename.endsWith(".png")) { contentType = "image/png"; } if
			 * (contentType == null || contentType.equals("")) { contentType =
			 * "application/octet-stream"; }
			 * 
			 * StringBuffer strBuf = new StringBuffer();
			 * strBuf.append("\r\n").append
			 * ("--").append(BOUNDARY).append("\r\n"); strBuf.append(
			 * "Content-Disposition: form-data; name=\"" + inputName +
			 * "\"; filename=\"" + filename + "\"\r\n");
			 * strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
			 * 
			 * out.write(strBuf.toString().getBytes());
			 * 
			 * DataInputStream in = new DataInputStream(new
			 * FileInputStream(file)); int bytes = 0; byte[] bufferOut = new
			 * byte[1024]; while ((bytes = in.read(bufferOut)) != -1) {
			 * out.write(bufferOut, 0, bytes); } in.close(); } }
			 */

			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();

			// 读取返回数据
			StringBuffer resuleBuf = new StringBuffer();
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				resuleBuf.append(line).append("\n");
			}
			result = resuleBuf.toString();

		} catch (MalformedURLException e) {
			LOGGER.error("", e);
		} catch (ProtocolException e) {
			LOGGER.error("", e);
		} catch (IOException e) {
			LOGGER.error("", e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				LOGGER.error("", e);
			}
			try {
				in.close();
			} catch (IOException e) {
				LOGGER.error("", e);
			}
			try {
				out.close();
			} catch (IOException e) {
				LOGGER.error("", e);
			}
		}

		return result;
	}


	/**
	 * 获取退票附件文件流
	 * @param url
	 * @return
	 */
	public static InputStream getFileInputStreamByGet(String url) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url)
					.openConnection();
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			//防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = conn.getInputStream();
				return inputStream;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
