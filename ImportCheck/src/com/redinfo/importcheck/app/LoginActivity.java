package com.redinfo.importcheck.app;

import java.util.regex.Pattern;

import com.redinfo.importcheck.R;
import com.redinfo.ui.PwdEditCancel;
import com.redinfo.ui.UserEditCancel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	@SuppressWarnings("unused")
	private CheckBox saveUserInfoCbox = null;
	private Button LoginBtn = null;
	private UserEditCancel UserEC;
	private PwdEditCancel PwdEC;
	public Dialog logindialog = null;
	@SuppressWarnings("unused")
	private String UserID = "";
	@SuppressWarnings("unused")
	private String LoginName = "";
	@SuppressWarnings("unused")
	private String TrueName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		this.LoginBtn = (Button) findViewById(R.id.LoginButton);
		this.UserEC = (UserEditCancel) findViewById(R.id.name);
		this.PwdEC = (PwdEditCancel) findViewById(R.id.pwd);
		this.saveUserInfoCbox = (CheckBox) this
				.findViewById(R.id.remember_user_checkbox);
		String usr = UserEC.getString();
		String pwd = PwdEC.getString();
		if (usr != null && pwd != null) {
			this.LoginBtn.setEnabled(true);
		} else {
			this.LoginBtn.setEnabled(false);
		}
		this.LoginBtn.setOnClickListener(this);
		logindialog = new Dialog(LoginActivity.this, R.style.mmdialog);
		logindialog.setContentView(R.layout.login_dialog);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.LoginButton:

			String pno = this.UserEC.getString().trim();
			String vCode = this.PwdEC.getString().trim();
			if (!Pattern.matches("^[A-Za-z0-9]+$", vCode)) {
				Toast.makeText(this, "您输入的密码有误,密码由0-9,和26位英文数字组成",
						Toast.LENGTH_LONG).show();
				// this.PwdEC.setError("您输入的密码有误,密码由0-9,和26位英文数字组成");
				break;
			}

			if (!Pattern.matches("^[A-Za-z0-9]+$", pno)) {
				Toast.makeText(this, "您输入的用户名有误,用户名由0-9,和26位英文数字组成",
						Toast.LENGTH_LONG).show();
				// this.UserEC.setError("您输入的用户名有误,用户名由0-9,和26位英文数字组成");
				break;
			}
			//
			// RequestParams params = new RequestParams();
			// params.put("loginName", pno);
			// params.put("loginPwd", vCode);
			//
			// AsyncHttpClient client = new AsyncHttpClient();
			// client.post(Helper.LOGIN_URL, params,
			// new AsyncHttpResponseHandler() {
			// @Override
			// public void onStart() {
			// logindialog.show();
			// }
			//
			// @Override
			// public void onFinish() {
			// logindialog.dismiss();
			// Intent intent = new Intent();
			// intent.setClass(LoginActivity.this,
			// ImportCheckList.class);
			// startActivity(intent);
			// LoginActivity.this.finish();
			// }
			//
			// @Override
			// public void onSuccess(String content) {
			// Log.i("onSuccess", content);
			// }
			//
			// @Override
			// public void onFailure(Throwable error) {
			// Toast.makeText(LoginActivity.this,
			// "登陆失败!请确定您输入的信息正确.", Toast.LENGTH_LONG)
			// .show();
			// Log.i("onFailure", "failed");
			// }
			// });
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, ImportCheckList.class);
			startActivity(intent);
			LoginActivity.this.finish();
			break;
		default:
			break;
		}
	}
}