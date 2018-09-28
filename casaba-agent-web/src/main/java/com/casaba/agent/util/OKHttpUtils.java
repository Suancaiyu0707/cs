package com.casaba.agent.util;

import okhttp3.*;

import java.io.IOException;

public class OKHttpUtils {
	public static final MediaType JSON
			= MediaType.parse("application/json; charset=utf-8");
	private static OkHttpClient client = new OkHttpClient();

	public static String get(String url) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.build();

		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	public static String post(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.build();
		Response response = client.newCall(request).execute();
		ResponseBody responseBody = response.body();
		return responseBody.string();
	}

}
