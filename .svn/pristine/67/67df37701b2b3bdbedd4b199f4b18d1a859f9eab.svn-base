package com.pingan.pama.test.invoke.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLContext;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author WUHENG545
 *
 */
public class HttpClientUtilTest implements InitializingBean {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private PoolingHttpClientConnectionManager poolConnManager;
	
	private Map<String, Object> httpConfigMap = new HashMap<String, Object>();

	private CloseableHttpClient client;

	public void init() {
		// 保护原来的client,避免初始化报�?
		CloseableHttpClient oldClient = null;
		if (client != null) {
			oldClient = client;
		}
		try {
			// maxTotalPool:设置整个连接池最大连接数
			// maxConPerRoute:路由的最大连接数
			// socketTimeout:设置获取响应内容的等待时�?
			// connectionRequestTimeout:连接数达到maxConPerRoute，等待连接的超时时间
			// connectTimeout:建立连接等待时间
			logger.info("HttpClientUtil init config:{}",httpConfigMap);
			SSLContext sslcontext = SSLContexts.custom().useTLS().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
			X509HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, hostnameVerifier);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = 
					RegistryBuilder.<ConnectionSocketFactory> create().register(
							"http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
			poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			poolConnManager.setMaxTotal(MapUtils.getIntValue(httpConfigMap, "maxTotalPool", 400));
			poolConnManager.setDefaultMaxPerRoute(MapUtils.getIntValue(httpConfigMap, "maxConPerRoute", 200));
			SocketConfig socketConfig = SocketConfig.custom().
					setSoTimeout(MapUtils.getIntValue(httpConfigMap, "socketTimeout",30 * 1000)).build();
			poolConnManager.setDefaultSocketConfig(socketConfig);

			RequestConfig requestConfig = RequestConfig.custom().
					setConnectionRequestTimeout(MapUtils.getIntValue(httpConfigMap, "connectionRequestTimeout",3 * 1000)).
					setConnectTimeout(MapUtils.getIntValue(httpConfigMap, "connectTimeout",5 * 1000)).
					setSocketTimeout(MapUtils.getIntValue(httpConfigMap, "socketTimeout",30 * 1000)).build();
			client = HttpClients.custom().setConnectionManager(poolConnManager).setDefaultRequestConfig(requestConfig).build();
			logger.info("HttpClientUtil init succeed.");
		} catch (Throwable t) {
			client = oldClient;
			throw new RuntimeException("InterfacePhpUtilManager init Exception", t);
		}
	}

	public String post(String url, Map<String, Object> params) throws ClientProtocolException, IOException {
		String returnStr = null;
		// 参数�?�?
		if (url == null || "".equals(url)) {
			return returnStr;
		}
		HttpPost httpPost = new HttpPost(url);
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
			}
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e.getLocalizedMessage(), e);
			}
			logger.info("请求目标地址URL:" + url);
			logger.info("请求参数内容:{}",params);
			CloseableHttpResponse response = client.execute(httpPost);
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				String resopnse = "";
				if (entity != null) {
					resopnse = EntityUtils.toString(entity, "utf-8");
				}
				logger.info("请求返回结果:" + resopnse);
				return resopnse;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		} finally {
			/**
			 * leased ：the number of persistent connections tracked by the
			 * connection manager currently being used to execute requests.
			 * available ：the number idle persistent connections. pending : the
			 * number of connection requests being blocked awaiting a free
			 * connection. max: the maximum number of allowed persistent
			 * connections �?要特别关注pending和leased的�?�，如果leased的�?�特别大，接近max，则�?要修改max�?
			 * 如果pending的�?�也比较�?
			 * ，也�?要调整max，并考虑设置timeout，可以设置两个timeout，一个是获取连接的timeout
			 * ，另外一个是获取socket数据的timeout�?
			 */
			logger.info("now client pool " + poolConnManager.getTotalStats().toString());
			httpPost.abort();
		}
	}
	
	public String getJson(String url) throws ClientProtocolException, IOException {
		String returnStr = null;
		// 参数�?�?
		if (url == null || "".equals(url)) {
			return returnStr;
		}
		HttpGet httpGet = new HttpGet(url);
		/**设置header*/
		httpGet.addHeader("Content-Type", "application/json; charset=utf-8");
		httpGet.addHeader("Accept", "application/json; charset=utf-8");
		try {
			logger.info("请求目标地址URL:" + httpGet.getURI());
			CloseableHttpResponse response = client.execute(httpGet);
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				String resopnse = "";
				if (entity != null) {
					resopnse = EntityUtils.toString(entity, "utf-8");
				}
				logger.info("请求返回结果:" + resopnse);
				return resopnse;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		} finally {
			logger.info("now client pool " + poolConnManager.getTotalStats().toString());
			httpGet.abort();
		}
	}
	
	public String getJson(String url,Map<String, Object> params) throws ClientProtocolException, IOException {
		String returnStr = null;
		// 参数�?�?
		if (url == null || "".equals(url)) {
			return returnStr;
		}
		StringBuilder urlSb = new StringBuilder(url);
		if (MapUtils.isNotEmpty(params)) {
			if (!StringUtils.contains(url, "?")) {
				urlSb.append("?");
			}else if (!"&".equals(StringUtils.substring(url, -1))) {
				urlSb.append("&");
			}
			for (Entry<String, Object> param : params.entrySet()) {
				urlSb.append(param.getKey()).append("=").append(param.getValue().toString()).append("&");
			}
			urlSb.deleteCharAt(urlSb.length()-1);
		}
		HttpGet httpGet = new HttpGet(urlSb.toString());
		/**设置header*/
		httpGet.addHeader("Content-Type", "application/json; charset=utf-8");
		httpGet.addHeader("Accept", "application/json; charset=utf-8");
		try {
			logger.info("请求目标地址URL:" + httpGet.getURI());
			CloseableHttpResponse response = client.execute(httpGet);
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				String resopnse = "";
				if (entity != null) {
					resopnse = EntityUtils.toString(entity, "utf-8");
				}
				logger.info("请求返回结果:" + resopnse);
				return resopnse;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		} finally {
			logger.info("now client pool " + poolConnManager.getTotalStats().toString());
			httpGet.abort();
		}
	}

	/**
	 * 添加报文头，讲报文体以JSON方式传输
	 * Content-Type:必须设置下面的�?�：application/json; charset=utf-8
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String postJSON(String url, Map<String, Object> params) throws ClientProtocolException, IOException {
		return postJSON(url,JSONObject.toJSONString(params));
	}
	public String postForm(String url, Map<String, String> params) throws ClientProtocolException, IOException {
		// 参数�?�?
		if (url == null || "".equals(url)) {
			throw new ConnectException("Invoking url is null!");
		}
		HttpPost httpPost = new HttpPost(url);
		try {
			//装填参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if(params!=null){
				for (Entry<String, String> entry : params.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
			}
			//设置参数到请求对象中
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
//			StringEntity se = new StringEntity(jsonStr, ContentType.APPLICATION_FORM_URLENCODED);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			httpPost.addHeader("Accept", "application/json; charset=utf-8");
			httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//			httpPost.setEntity(se);
			logger.info("请求目标地址URL:" + url);
			logger.info("请求参数内容:" + JSON.toJSONString(params));
			CloseableHttpResponse response = client.execute(httpPost);
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				String resopnse = "";
				if (entity != null) {
					resopnse = EntityUtils.toString(entity, "utf-8");
				}
				logger.info("请求返回结果:" + resopnse);
				return resopnse;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		} finally {
			/**
			 * leased ：the number of persistent connections tracked by the
			 * connection manager currently being used to execute requests.
			 * available ：the number idle persistent connections. pending : the
			 * number of connection requests being blocked awaiting a free
			 * connection. max: the maximum number of allowed persistent
			 * connections �?要特别关注pending和leased的�?�，如果leased的�?�特别大，接近max，则�?要修改max�?
			 * 如果pending的�?�也比较�?
			 * ，也�?要调整max，并考虑设置timeout，可以设置两个timeout，一个是获取连接的timeout
			 * ，另外一个是获取socket数据的timeout�?
			 */
			logger.info("now client pool " + poolConnManager.getTotalStats().toString());
			httpPost.abort();
		}
	}
	
	/**
	 * 添加报文头，讲报文体以JSON方式传输
	 * Content-Type:必须设置下面的�?�：application/json; charset=utf-8
	 * @param url
	 * @param jsonStr
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String postJSON(String url, String jsonStr) throws ClientProtocolException, IOException {
		// 参数�?�?
		if (url == null || "".equals(url)) {
			throw new ConnectException("Invoking url is null!");
		}
		if (jsonStr == null || "".equals(jsonStr)) {
			throw new ConnectException("jsonStr is null!");
		}
		HttpPost httpPost = new HttpPost(url);
		try {
			StringEntity se = new StringEntity(jsonStr, ContentType.APPLICATION_JSON);
			httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
			httpPost.addHeader("Accept", "application/json; charset=utf-8");
			httpPost.setEntity(se);
			logger.info("请求目标地址URL:" + url);
			logger.info("请求参数内容:" + jsonStr);
			CloseableHttpResponse response = client.execute(httpPost);
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				String resopnse = "";
				if (entity != null) {
					resopnse = EntityUtils.toString(entity, "utf-8");
				}
				logger.info("请求返回结果:" + resopnse);
				return resopnse;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		} finally {
			/**
			 * leased ：the number of persistent connections tracked by the
			 * connection manager currently being used to execute requests.
			 * available ：the number idle persistent connections. pending : the
			 * number of connection requests being blocked awaiting a free
			 * connection. max: the maximum number of allowed persistent
			 * connections �?要特别关注pending和leased的�?�，如果leased的�?�特别大，接近max，则�?要修改max�?
			 * 如果pending的�?�也比较�?
			 * ，也�?要调整max，并考虑设置timeout，可以设置两个timeout，一个是获取连接的timeout
			 * ，另外一个是获取socket数据的timeout�?
			 */
			logger.info("now client pool " + poolConnManager.getTotalStats().toString());
			httpPost.abort();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.init();
	}

	public void setHttpConfigMap(Map<String, Object> httpConfigMap) {
		this.httpConfigMap = httpConfigMap;
	}
	
}
