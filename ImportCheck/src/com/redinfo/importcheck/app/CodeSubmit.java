package com.redinfo.importcheck.app;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.redinfo.importcheck.R;
import com.redinfo.importcheck.barcode.CaptureActivity;
import com.redinfo.importcheck.db.OrderDBHelper;
import com.redinfo.ui.CustomDialog;

public class CodeSubmit extends Activity implements OnClickListener,
		OnItemLongClickListener {
	private static final String BARCODE_SCANER_INTENT = "com.redinfo.importcheck.barcode.SCAN";
	private static final int BARCODE_INTENT_REQ_CODE = 0x965;
	private ArrayList<String> BarcodeResult = null;
	private ArrayList<String> BarcodeDetail = null;
	private Button scan_btn;
	private Button back_btn;
	private Button submmit_btn;
	private Button save_btn;
	private ImageButton plus_btn;
	private LinearLayout func_bar;
	private CustomAdapter adapter = null;
	private EditText code_et = null;
	private ListView code_list;
	private CheckBox unclear_code;
	private CheckBox no_code;
	public int pos;
	private boolean plus = false;

	private String orderId;
	private String sendId;
	private String product;
	private String producer;
	private String date;

	public OrderDBHelper m_db = null;
	SQLiteDatabase db = null;
	public final static String URL = "/data/data/com.redinfo.importcheck/databases";
	public final static String DB_FILE_NAME = "sysorder.db";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submmit);

		m_db = OrderDBHelper.getInstance(CodeSubmit.this);
		File file = new File(URL, DB_FILE_NAME);
		db = SQLiteDatabase.openOrCreateDatabase(file, null);

		BarcodeResult = new ArrayList<String>();
		BarcodeDetail = new ArrayList<String>();
		func_bar = (LinearLayout) findViewById(R.id.func_bar);
		scan_btn = (Button) findViewById(R.id.scan_btn);
		scan_btn.setOnClickListener(this);
		back_btn = (Button) findViewById(R.id.back_btn);
		back_btn.setOnClickListener(this);
		submmit_btn = (Button) findViewById(R.id.submmit_btn);
		submmit_btn.setOnClickListener(this);
		plus_btn = (ImageButton) findViewById(R.id.plus_btn);
		plus_btn.setOnClickListener(this);
		save_btn = (Button) findViewById(R.id.save_btn);
		save_btn.setOnClickListener(this);
		code_et = (EditText) findViewById(R.id.code);
		code_list = (ListView) findViewById(R.id.check_code_list);
		unclear_code = (CheckBox) findViewById(R.id.unclear_code);
		no_code = (CheckBox) findViewById(R.id.no_code);
		code_list.setOnItemLongClickListener(this);
		unclear_code
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							no_code.setChecked(false);
						}
					}
				});
		no_code.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					unclear_code.setChecked(false);
				}
			}
		});
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			orderId = bundle.getString("orderId");
			sendId = bundle.getString("sendId");
			date = bundle.getString("date");
			producer = bundle.getString("producer");
			product = bundle.getString("product");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.scan_btn:
			this.openScanner();
			break;
		case R.id.back_btn:
			CodeSubmit.this.finish();
			break;
		case R.id.submmit_btn:
			String code = code_et.getText().toString();
			BarcodeResult.add(code);
			adapter = new CustomAdapter(CodeSubmit.this);
			code_list.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			getInfo(code);
			code_list.setSelection(adapter.getCount());
			InsertCode(code);
			break;
		case R.id.plus_btn:
			if (!plus) {
				plus = true;
				func_bar.setVisibility(View.VISIBLE);
			} else {
				plus = false;
				func_bar.setVisibility(View.GONE);
			}
			break;
		case R.id.save_btn:
			submmit();
			UpdateOrder();
			Toast.makeText(CodeSubmit.this, "已提交", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.setClass(CodeSubmit.this, ImportCheckList.class);
			startActivity(intent);
			this.finish();
			break;
		default:
			break;
		}
	}

	private void InsertCode(String code) {
		String unclear = null;
		if (unclear_code.isChecked()) {
			unclear = "1";
		} else {
			unclear = "0";
		}
		String nocode = null;
		if (no_code.isChecked()) {
			nocode = "1";
		} else {
			nocode = "0";
		}
		String result = "无流向错误";
		m_db.insert_code(OrderDBHelper.CODE_TABLE_NAME, code, unclear, nocode,
				result);
	}

	private void UpdateOrder() {
		m_db.update_order(OrderDBHelper.ORDER_TABLE_NAME, orderId, producer,
				product, date, sendId, 1 + "");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		db.close();
		super.onDestroy();
	}

	private void submmit() {
	}

	private void getInfo(String code) {
		// TODO Auto-generated method stub
		String information = "";
		if (unclear_code.isChecked()) {
			information += "喷码不清、";
		}
		if (no_code.isChecked()) {
			information += "无喷码、";
		}
		BarcodeDetail.add(information + "无流向错误");
	}

	private void openScanner() {
		Intent intent = new Intent(BARCODE_SCANER_INTENT);
		intent.setClass(CodeSubmit.this, CaptureActivity.class);
		this.startActivityForResult(intent, BARCODE_INTENT_REQ_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		if (requestCode == BARCODE_INTENT_REQ_CODE) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				code_et.setText(contents);
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "未找到条形码", Toast.LENGTH_LONG).show();
			}
		}
	}

	public final class ViewHolder {
		public TextView BarcodeTv;
		public TextView BarcodeInfo;
		public TextView CodeNum;
	}

	// 自定义条码数据容纳器
	private class CustomAdapter extends BaseAdapter {
		private LayoutInflater inflater;

		public CustomAdapter(Context context) {
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return BarcodeResult.size();
		}

		@Override
		public Object getItem(int position) {
			return BarcodeResult.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.code_item, null);
				holder = new ViewHolder();
				holder.BarcodeTv = (TextView) convertView
						.findViewById(R.id.reBarcode);
				holder.BarcodeInfo = (TextView) convertView
						.findViewById(R.id.BarcodeInfo);
				holder.CodeNum = (TextView) convertView
						.findViewById(R.id.code_num);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.BarcodeTv.setText("物流码:" + BarcodeResult.get(position));

			holder.BarcodeInfo.setText("抽检结果:" + BarcodeDetail.get(position));
			if (position < 9) {
				holder.CodeNum.setText("0" + (position + 1));
			} else {
				holder.CodeNum.setText("" + (position + 1));
			}
			return convertView;
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		pos = arg2;
		Dialog dialog = null;
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(
				CodeSubmit.this);
		customBuilder.setMessage("确定要删除此记录？")
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						BarcodeResult.remove(pos);
						BarcodeDetail.remove(pos);
						code_list.setAdapter(adapter);
						m_db.delete_code(OrderDBHelper.CODE_TABLE_NAME,
								BarcodeResult.get(pos));
						dialog.dismiss();
					}
				});
		dialog = customBuilder.create();
		dialog.show();

		return false;
	}

}
