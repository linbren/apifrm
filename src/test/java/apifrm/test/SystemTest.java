package apifrm.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.platform.utils.HttpUtil;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.httpclient.HttpClientUtil;
import com.httpclient.builder.HCB;
import com.httpclient.common.HttpConfig;
import com.httpclient.common.HttpHeader;

public class SystemTest extends BaseTest {
	private final String baseUrl = "http://localhost:8091/";
	public String getAPIUrl(String action, String method) {
		return baseUrl + action + '?' + method;
	}

	@Test
	public void getToken() throws Exception {
		System.out.println("testGetToken................");
		String apiUrl = getAPIUrl("login.do", "getToken");
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("userCode", "admin");
			params.put("userPass", "E10ADC3949BA59ABBE56E057F20F883E");
			String Resp = HttpUtil.doPost(apiUrl, params);
			System.out.println("testGetToken: " + Resp);
		} catch (Exception e) {
			System.out.println("testGetToken: " + e.getMessage());
		}
		System.out.println("................testGetToken");
	}

	@Test
	public void testUrlencodedPost() {
		HttpPost httpPost = new HttpPost(getAPIUrl("login.do", "getToken"));
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userCode", "admin"));
			params.add(new BasicNameValuePair("userPass",
					"E10ADC3949BA59ABBE56E057F20F883E"));
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(httpEntity);
			CloseableHttpResponse response = client.execute(httpPost);
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
		} finally {
			httpPost.releaseConnection();
		}
	}

	@Test
	public void getFuncList() throws Exception {
		System.out.println(getMethodName() + "................................begin");
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			Header[] headers = HttpHeader.custom().userAgent("javacl")
					.contentType("application/json")
					.other("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJhZG1pbiIsInJvbGVzIjoiW3tcImFwcElkXCI6XCIxMDAwXCIsXCJmdW5jdGlvbnNcIjpbXSxcImlkXCI6XCIxXCIsXCJyZW1hcmtcIjpcIueuoeeQhuWRmFwiLFwicm9sZU5hbWVcIjpcIueuoeeQhuWRmFwiLFwic3RhdHVzXCI6XCIxXCIsXCJ1c2Vyc1wiOltdfV0iLCJpYXQiOjE0ODg4NjU3MzQyMjYsImV4dCI6MTQ4ODg3MjkzNDIyNn0.n_diE2obN7N0smDfRLjfCUWz8oa8zsc5c-sOZhmrLpo")
					.other("version", "3")
					.other("timestamp", (new Date()).toString())
					.build();
			params.put("page", 1);
			params.put("rows", 5);

			// 插件式配置请求参数（网址、请求参数、编码、client）
			HttpConfig config = HttpConfig.custom()
					.headers(headers) // 设置headers，不需要时则无需设置
					.url(getAPIUrl("/api/test.do", "getFuncList")) // 设置请求的url
					.map(params) // 设置请求参数，没有则无需设置
					.encoding("utf-8"); // 设置请求和返回编码，默认就是Charset.defaultCharset()
			// 使用方式：
			String resp_get = HttpClientUtil.get(config);
			System.out.println(getMethodName() + resp_get);

			String resp = HttpClientUtil.post(config);
			System.out.println(getMethodName() +  resp);
		} catch (Exception e) {
			System.out.println(getMethodName() + e.getMessage());
		}
		System.out.println(getMethodName() + "..................................end");
	}
}