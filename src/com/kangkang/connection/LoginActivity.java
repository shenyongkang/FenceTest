//package com.kangkang.connection;
//
//import java.util.HashMap;
//
//import org.json.JSONObject;
//
//import com.hk1949.gson.base.request.JsonRequestProxy;
//import com.hk1949.gson.base.request.JsonRequestProxy.JsonResponseListener;
//import com.hk1949.smartguide.eventbus.event.LoginEvent;
//import com.hk1949.smartguide.http.URLs;
//import com.hk1949.smartguide.util.ActivityUtil;
//import com.hk1949.smartguide.util.DialogUtils;
//import com.hk1949.smartguide.util.Logger;
//import com.hk1949.smartguide.util.NetUtil;
//import com.hk1949.smartguide.util.StringUtil;
//import com.hk1949.smartguide.util.ToastUtil;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.os.Bundle;
//import android.provider.ContactsContract.CommonDataKinds.Event;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnFocusChangeListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//import de.greenrobot.event.EventBus;
//
//public class LoginActivity extends BaseActivity implements OnClickListener{
//
//	TextView tvRegister;
//	TextView tvFindPassword;
//	EditText etPhone;
//	EditText etPassword;
//	Button btnLogin;
//
//	private String mobilePhone = "", password = "";
//	private SharedPreferences sp;
//
//	private String uri_log = URLs.getLoginUrl();
//	String next = "";
//
//	JsonRequestProxy rq_login;
//	boolean firstLaunch;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		firstLaunch = getIntent().getBooleanExtra("first_launch", false);
//		EventBus.getDefault().register(this);
//		// next = getIntent().getStringExtra("next");
//		setContentView(R.layout.activity_login);
//		setTitle("登录");
//		// setBackBtnVisibility(View.GONE);
//		findView();
//		initRQs();
//		// sp=getSharedPreferences("loginconfig",Context.MODE_PRIVATE);
//		// ActivityUtil.addActivitiesExceptHome(this);
//	}
//
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		EventBus.getDefault().unregister(this);
//	}
//
//	LoginEvent mEvent;
//
//	public void onEventMainThread(LoginEvent event) {
//		mEvent = event;
//		if (event.action == 2) {
//			DialogUtils.showProgressDialog(getActivity());
//			mobilePhone = event.phone;
//			final String password = event.password;
//			etPhone.setText(mobilePhone);
//			etPassword.setText(password);
//			mHandler.postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					if (rq_login != null)
//						rq_login.cancel();
//					HashMap<String, String> params1 = new HashMap<>();
//					params1.put("mobilePhone", mobilePhone + "");
//					params1.put("password", password + "");
//					rq_login.post(params1);
//				}
//			}, 1000);
//		}else{
//			onBackPressed();
//		}
//
//	}
//
//	private void initRQs() {
//		// TODO Auto-generated method stub
//		rq_login = new JsonRequestProxy(URLs.getLoginUrl());
//		rq_login.setJsonResponseListener(new JsonResponseListener() {
//
//			@Override
//			public void onResponseSuccess(String response) {
//				DialogUtils.hideProgressDialog(getActivity());
//				login_success(response);
//			}
//
//			@Override
//			public void onResponseLocal(String response) {
//				// TODO Auto-generated method stub
//				Logger.e(LoginActivity.class.getSimpleName(), response);
//				DialogUtils.hideProgressDialog(getActivity());
//			}
//
//			@Override
//			public void onResponseError(String erroMessage) {
//				// TODO Auto-generated method stub
//				ToastUtil.toast(getActivity(), erroMessage);
//				DialogUtils.hideProgressDialog(getActivity());
//			}
//		});
//	}
//
//	private void findView() {
//		// TODO Auto-generated method stub
//		initView();
//		setClickListener();
//	}
//
//	private void setClickListener() {
//		// TODO Auto-generated method stub
//		tvRegister.setOnClickListener(this);
//		tvFindPassword.setOnClickListener(this);
//		btnLogin.setOnClickListener(this);
////		etPhone.setOnFocusChangeListener(new OnFocusChangeListener() {
////			
////			@Override
////			public void onFocusChange(View v, boolean hasFocus) {
////				if(hasFocus){
////					etPhone.setHint("");
////				}else{
////					etPhone.setHint("请输入用户名");
////				}
////			}
////		});
////		etPassword.setOnFocusChangeListener(new OnFocusChangeListener() {
////			
////			@Override
////			public void onFocusChange(View v, boolean hasFocus) {
////				if(hasFocus){
////					etPassword.setHint("");
////				}else{
////					etPassword.setHint("请输入密码");
////				}
////			}
////		});
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		Intent intent;
//		switch (v.getId()) {
//		case R.id.btnLogin:
//			mobilePhone = etPhone.getText().toString().trim();
//			password = etPassword.getText().toString().trim();
//			if (!TextUtils.isEmpty(mobilePhone) && !TextUtils.isEmpty(password)) {
//				if(mobilePhone.length()!=11){
//					ToastUtil.toast(getActivity(), "用户名请输入11位手机号");
//					return;
//				}
//				// new LoginAsyncTask().execute(mobilePhone, password);
//				// HashMap<String, String> params1 = new HashMap<>();
//				// params1.put("mobilePhone", mobilePhone+"");
//				// params1.put("password", password+"");
//				// rq_login.cancel();
//				// rq_login.post(params1);
//
//				DialogUtils.showProgressDialog(getActivity());
//
//				if (rq_login != null)
//					rq_login.cancel();
//
//				HashMap<String, String> params1 = new HashMap<>();
//				params1.put("mobilePhone", mobilePhone + "");
//				params1.put("password", password + "");
//				rq_login.post(params1);
//			} else {
//				Toast.makeText(LoginActivity.this, "账号或密码不能为空！", Toast.LENGTH_LONG).show();
//				etPhone.requestFocus();
//			}
//			// intent = new Intent(LoginActivity.this,
//			// MapActivity.class);
//			// startActivity(intent);
//			// ActivityUtil.removeExceptHomeAc(this);
//			break;
//		case R.id.linkedRegister:
//			// intent = new Intent(LoginActivity.this,
//			// RegisterActivity.class);
//			startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//
//			// ActivityUtil.removeExceptHomeAc(this);
//			break;
//		case R.id.findPassword:
//			// intent = new Intent(LoginActivity.this,
//			// FindPasswordActivity.class);
//			startActivity(new Intent(LoginActivity.this, FindPasswordActivity.class));
//
//			// ActivityUtil.removeExceptHomeAc(this);
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	private void initView() {
//		tvRegister = (TextView) findViewById(R.id.linkedRegister);
//		tvFindPassword = (TextView) findViewById(R.id.findPassword);
//		etPhone = (EditText) findViewById(R.id.username);
//		etPassword = (EditText) findViewById(R.id.password);
//		btnLogin = (Button) findViewById(R.id.btnLogin);
//
//	}
//
//	private void login_success(String resultStr) {
//		try {
//			JSONObject jo = new JSONObject(resultStr);
//
//			String result = jo.optString("result");
//			if (TextUtils.isEmpty(result)) {
//				ToastUtil.toast(getActivity(), "网络异常");
//				return;
//			} else if ("error".equals(result)) {
//				if (!TextUtils.isEmpty(jo.optString("message")))
//					ToastUtil.toast(getActivity(), jo.optString("message"));
//				else
//					ToastUtil.toast(getActivity(), "网络异常");
//				return;
//			} else if ("success".equals(result)) {
//
//				JSONObject jj = jo.getJSONObject("data");
//				// 将用户名读取并保存到SharedPreferences
//				sp = getSharedPreferences("user_data", MODE_PRIVATE);
//				Editor ed = sp.edit();
//				ed.putString("mobilePhone", jj.optString("mobilePhone"));
//				ed.putString("personIdNo", jj.optString("personIdNo"));
//				ed.putString("nickName", jj.optString("personName"));
//				ed.putString("sex", jj.optString("sex"));
//				ed.putString("dateOfBirth", jj.optString("dateOfBirth"));
//				ed.putString("token", jo.optString("token"));
//				ed.commit();
//				Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_LONG).show();
//				setResult(RESULT_OK);
//				onBackPressed();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	@Override
//	public void onBackPressed() {
//		super.onBackPressed();
//		ActivityUtil.removeExceptHomeAc(this);
//		finish();
//		if (firstLaunch) {
//			startActivity(new Intent(getApplication(), MainActivity.class));
//		}
//	}
//
//}
