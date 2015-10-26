package com.kangkang.connection;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;


public class JsonRequestProxy implements ErrorListener, Listener<String> {
	private JsonRequest<String> jsonRequest;
	private int timeout = 5 * 1000;
	private String mUrl = "";
	JsonResponseListener mJsonResponseListener;

	public void setJsonResponseListener(JsonResponseListener listener) {
		mJsonResponseListener = listener;
	}

	public JsonRequestProxy(String url) {
		mUrl = url;
	}

	public void setTimeout(int time) {
		this.timeout = time;
	}

	public void post(final Map<String, String> requestParams) {
		post(null, requestParams);
	}

	public void post(Object tag, final Map<String, String> requestParams) {
		JSONObject jsonObject = new JSONObject(requestParams);
		jsonRequest = new JsonRequest<String>(Method.POST, mUrl,
				jsonObject == null ? "" : jsonObject.toString(), this, this) {

			@Override
			public Map<String, String> getHeaders() {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "application/json; charset=UTF-8");
				return headers;
			}

			@Override
			protected Response parseNetworkResponse(NetworkResponse response) {
				// TODO Auto-generated method stub
				try {
					String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
					return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
				} catch (UnsupportedEncodingException e) {
					return Response.error(new ParseError(e));
				}
			}
		};

		DefaultRetryPolicy policy = new DefaultRetryPolicy(timeout, 0,
				0);
		jsonRequest.setRetryPolicy(policy);

		if (tag != null)
			jsonRequest.setTag(tag);
		BaseApplication.getInstance().getRequestQueue().add(jsonRequest);
	}

	public interface JsonResponseListener {
		void onResponseSuccess(String response);

		void onResponseError(String erroMessage);

	}

	public void cancel() {
		if (jsonRequest != null) {
			jsonRequest.cancel();
		}
	}

	@Override
	public void onResponse(String jsonStr) {
		if (mJsonResponseListener != null)
			mJsonResponseListener.onResponseSuccess(jsonStr);
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		if (mJsonResponseListener != null) {
			if ("com.android.volley.TimeoutError".equals(error.toString().trim())) {
				mJsonResponseListener.onResponseError("请求超时,请稍后再试!");
			} else if (error.getCause() == null) {
				mJsonResponseListener.onResponseError("网络状况不好,请稍后再试!");
			} else if (error.getCause() instanceof com.google.gson.JsonSyntaxException) {
				mJsonResponseListener.onResponseError("暂无数据");
			} else if (error.getCause() instanceof java.net.ConnectException
					|| error.getCause() instanceof java.net.UnknownHostException) {
				mJsonResponseListener.onResponseError("网络状况不好,请稍后再试!");
			} else if (error.getCause() instanceof java.io.IOException) {
				mJsonResponseListener.onResponseError("网络异常,请稍后再试!");
			} else {
				mJsonResponseListener.onResponseError(error.getMessage() + "");
			}
		}
	}
}
