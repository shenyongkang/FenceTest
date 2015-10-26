package com.kangkang.circlefencetest;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.kangkang.connection.JsonRequestProxy;
import com.kangkang.connection.JsonRequestProxy.JsonResponseListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	MapView mMapView = null;
	private RequestQueue mRequestQueue;
	private JsonRequestProxy jsonRequest;
	private  String  url = "http://121.43.233.154:8080/AnyGuide/Point/selectusingBoundary";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);
		// 使用volley 通信
		mRequestQueue = Volley.newRequestQueue(this);
		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
//		testPostRequest();
//		testGetRequest();
//		testJsonRequest();
		getFenceData();

	}

	private void getFenceData() {
		jsonRequest = new JsonRequestProxy(url);
		
		jsonRequest.setJsonResponseListener(new JsonResponseListener() {
			
			@Override
			public void onResponseSuccess(String response) {
				//解析得到围栏信息
				try {
					JSONObject jsonObject = new JSONObject(response);
					JSONArray fenceArray = jsonObject.getJSONArray("data");
					Log.d("TAG", fenceArray.length()+"");
					Log.d("TAG", fenceArray.get(48).toString());
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				Log.d("TAG", response);
			}
			
			@Override
			public void onResponseError(String erroMessage) {
				Log.d("TAG", erroMessage);				
			}
		});
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("spotIdNo", "11");
		map.put("currentStatus", "1");
		jsonRequest.post(map);
		
	}





	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	
	
	
	
	
	
	
	
	private void testGetRequest() {
		StringRequest stringRequest = new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d("TAG", response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("TAG", error.getMessage(), error);
			}
		});
		mRequestQueue.add(stringRequest);

	}
	
	private void testJsonRequest() {
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://m.weather.com.cn/data/101010100.html", null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("TAG", response.toString());
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				});
		mRequestQueue.add(jsonObjectRequest);

	}
	

	private void testPostRequest() {
		StringRequest stringRequest = new StringRequest(Method.POST,
				"http://121.43.233.154:8080/AnyGuide/Point/selectusingBoundary", new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d("TAG", response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("spotIdNo", "11");
				map.put("currentStatus", "1");
				return map;
			}
		};
		mRequestQueue.add(stringRequest);

	}

}
