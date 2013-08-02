package com.redinfo.ui;

import com.redinfo.importcheck.R;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class UserEditCancel extends LinearLayout implements EdtInterface {
	ImageButton ib;
	EditText et;

	public UserEditCancel(Context context) {
		super(context);

	}

	public UserEditCancel(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.uid_input, this, true);
		init();

	}

	public String getString() {
		et = (EditText) findViewById(R.id.username_et);
		return et.getText().toString();

	}

	public void setError(String error) {
		et = (EditText) findViewById(R.id.username_et);
		et.setError(error);
		ib = (ImageButton) findViewById(R.id.user_ib);
		hideBtn();// 隐藏按钮
		et.setText("");// 设置输入框内容为空
	}

	private void init() {
		ib = (ImageButton) findViewById(R.id.user_ib);
		et = (EditText) findViewById(R.id.username_et);
		et.addTextChangedListener(tw);// 为输入框绑定一个监听文字变化的监听器
		et.setHint("用户名");
		// 添加按钮点击事件
		ib.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				hideBtn();// 隐藏按钮
				et.setText("");// 设置输入框内容为空
			}
		});

	}

	// 当输入框状态改变时，会调用相应的方法
	TextWatcher tw = new TextWatcher() {

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		// 在文字改变后调用
		public void afterTextChanged(Editable s) {
			if (s.length() == 0) {
				hideBtn();// 隐藏按钮
			} else {
				showBtn();// 显示按钮
			}

		}

	};

	public void hideBtn() {
		// 设置按钮不可见
		if (ib.isShown())
			ib.setVisibility(View.GONE);

	}

	public void showBtn() {
		// 设置按钮可见
		if (!ib.isShown())
			ib.setVisibility(View.VISIBLE);

	}

}

