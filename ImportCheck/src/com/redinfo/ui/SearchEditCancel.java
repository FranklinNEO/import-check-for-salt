package com.redinfo.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.redinfo.importcheck.R;

public class SearchEditCancel extends LinearLayout implements EdtInterface {

	LinearLayout clear;
	EditText et;

	public SearchEditCancel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public SearchEditCancel(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.search_bar, this, true);
		init();

	}

	public String getString() {
		et = (EditText) findViewById(R.id.search_bar_et);
		return et.getText().toString();

	}

	private void init() {
		clear = (LinearLayout) findViewById(R.id.search_clear_bt);
		et = (EditText) findViewById(R.id.search_bar_et);

		et.addTextChangedListener(tw);// 为输入框绑定一个监听文字变化的监听器
		// 添加按钮点击事件
		clear.setOnClickListener(new OnClickListener() {

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
		if (clear.isShown())
			clear.setVisibility(View.GONE);

	}


	public void showBtn() {
		// 设置按钮可见
		if (!clear.isShown())
			clear.setVisibility(View.VISIBLE);

	}

}
