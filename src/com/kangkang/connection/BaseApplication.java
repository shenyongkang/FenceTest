package com.kangkang.connection;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

import android.app.Application;
import android.graphics.Typeface;
import android.text.TextUtils;

public class BaseApplication extends Application {

	
	private static BaseApplication mApplication;

	public static RequestQueue mRequestQueue = null;
	public static final String TAG = "VolleyPatterns";
	public static Typeface TYPE_ROBOTO ;

	public RequestQueue getRequestQueue() {
		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public void addToRequestQueue(Request req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		VolleyLog.d("Adding request to queue: %s", req.getUrl());

		getRequestQueue().add(req);
	}
	
	public void addToRequestQueue(Request req) {
        // set the default tag if tag is empty
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
	
	 public void cancelPendingRequests(Object tag) {
	        if (mRequestQueue != null) {
	            mRequestQueue.cancelAll(tag);
	        }
	    }

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	
	}
	
	public BaseApplication() {
		mApplication = this;
	}

	public static BaseApplication getInstance() {
		return mApplication;
	}



}
