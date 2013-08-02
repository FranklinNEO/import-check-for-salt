package com.redinfo.importcheck.app;

import java.util.ArrayList;

import com.redinfo.importcheck.R;
import com.redinfo.importcheck.adapter.MulitSingleTableAdapter;
import com.redinfo.importcheck.datamodel.DataRow;
import com.redinfo.importcheck.datamodel.DataTable;
import com.redinfo.importcheck.util.SoundPlayer;
import com.redinfo.importcheck.util.SoundPlayer.State;
import com.redinfo.ui.CustomListView;
import com.redinfo.ui.CustomListView.OnRefreshListener;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class ImportCheckList extends Activity implements OnItemClickListener {
	private CustomListView importlv = null;
	private MulitSingleTableAdapter madapter = null;
	private TextView numtv = null;
	private Dialog LoadingDialog = null;
	private String ReturnStr = null;
	private DataTable[] Result = null;
	private EditText search_et = null;
	public ArrayList<String> orderlist = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_importlist);
		importlv = (CustomListView) findViewById(R.id.import_list);
		// adapter = new MyAdapter(ImportCheckList.this);
		// importlv.setAdapter(adapter);
		importlv.setOnItemClickListener(this);
		SoundPlayer sp = new SoundPlayer();
		sp.addSound(ImportCheckList.this, R.raw.refresh_loading, State.refreshed);
		sp.addSound(ImportCheckList.this, R.raw.refresh_pulling, State.pull);
		importlv.setSoundPlayer(sp);
		numtv = (TextView) findViewById(R.id.num_tv);
		// numtv.setText(TestList.length + "");
		search_et = (EditText) findViewById(R.id.search_bar_et);
		LoadingDialog = new Dialog(ImportCheckList.this, R.style.mmdialog);
		LoadingDialog.setContentView(R.layout.loading_dialog);
		new AsyncTask<Integer, Integer, String[]>() {

			protected void onPreExecute() {
				LoadingDialog.show();
				super.onPreExecute();
			}

			@Override
			protected void onCancelled() {
				super.onCancelled();
			}

			protected String[] doInBackground(Integer... params) {
				return null;
			}

			protected void onPostExecute(String[] result) {
				LoadingDialog.dismiss();
				ShowInfo(null);
				orderlist = new ArrayList<String>();
				for (int i = 0; i < Result.length; i++) {
					orderlist.add(((Result)[i].getTableName()));
				}
				init();
				super.onPostExecute(result);
			}
		}.execute(0);

		importlv.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				new AsyncTask<Integer, Integer, Void>() {
					protected void onPreExecute() {
						super.onPreExecute();
					}

					@Override
					protected void onCancelled() {
						super.onCancelled();
					}

					protected Void doInBackground(Integer... params) {
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						freshOrderList();
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						madapter.notifyDataSetChanged();
						importlv.onRefreshComplete();

						ImportCheckList.this.FillDynamicList(Result);
						((ImportApplication) ImportCheckList.this
								.getApplication()).setResult(Result);
					}

				}.execute(0);
			}
		});
	}

	private void freshOrderList() {
		int OrderSize = Result.length + 1;
		DataTable[] order = new DataTable[OrderSize];
		for (int i = 0; i < Result.length; i++) {
			order[i] = Result[i];
		}
		DataTable t = new DataTable();
		String num;
        num = Result[Result.length - 1].getTableName();
        t.setTableName((Integer.parseInt(num) + 1) + "");
		DataRow tR = new DataRow();
		tR.addNewData("生产企业", "南通");
		tR.addNewData("产品名称", "250g绿标碘盐");
		tR.addNewData("发货日期", "2013-05-03");
		tR.addNewData("发货箱数", "920");
		t.addNewRow(tR);
		order[OrderSize - 1] = t;
		Result = new DataTable[OrderSize];
		Result = order;
		orderlist.add((Result)[OrderSize - 1].getTableName());
	}

	protected void init() {
		// TODO Auto-generated method stub
		search_et.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View arg0, int keyCode, KeyEvent KeyEvent) {
				if (keyCode == android.view.KeyEvent.KEYCODE_DEL) {
					EditText edit = (EditText) arg0;
					resetList(edit.getText().toString().trim());
				}
				return false;
			}

		});

		search_et.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				String str = s.toString();
				resetList(str);
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		position--;
		DataTable SelectedOrder = Result[position];
		String SystemOrder = SelectedOrder.getTableName();
		String ProductName = (Result[position].getRowAt(0).getItemPar(0))
				.getContent();
		String ProductVariety = (Result[position].getRowAt(0).getItemPar(1))
				.getContent();
		String SendDate = (Result[position].getRowAt(0).getItemPar(2))
				.getContent();
		String SendNum = (Result[position].getRowAt(0).getItemPar(3))
				.getContent();
		Log.d("ProductName", ProductName);
		Log.d("ProductVariety", ProductVariety);
		Log.d("SendDate", SendDate);
		Log.d("SendNum", SendNum);
		bundle.putString("SystemOrder", SystemOrder);
		bundle.putString("ProductName", ProductName);
		bundle.putString("ProductVariety", ProductVariety);
		bundle.putString("SendDate", SendDate);
		bundle.putString("SendNum", SendNum);
		intent.putExtras(bundle);
		intent.setClass(ImportCheckList.this, ImportCheckInfo.class);
		startActivity(intent);
	}

	protected void ShowInfo(DataTable[] dataTables) {
		// TODO Auto-generated method stub
		if (dataTables == null) {
			Result = GetDataFromServer.getDatas(ReturnStr);
			((ImportApplication) ImportCheckList.this.getApplication())
					.setResult(Result);
			this.FillDynamicList(Result);
			return;
		}
		this.FillDynamicList(dataTables);
	}

	private void FillDynamicList(DataTable[] data) {
		// TODO Auto-generated method stub
		if (importlv == null || data == null) {
			Log.i("list", "null");
			return;
		}

		if (importlv != null) {
			Log.i("list", "not null");
		}
		madapter = new MulitSingleTableAdapter(this, data);
		this.importlv.setAdapter(madapter);
		ImportCheckList.this.madapter.setDataTable(data);
		ImportCheckList.this.madapter.notifyDataSetChanged();
		if (data.length < 10) {
			numtv.setText("0" + data.length);
		} else {

			numtv.setText(data.length + "");
		}
	}

	public void resetList(String words) {
		// TODO Auto-generated method stub
		Result = ((ImportApplication) ImportCheckList.this.getApplication())
				.getResult();
		ArrayList<DataTable> select = new ArrayList<DataTable>();
		for (int i = 0; i < orderlist.size(); i++) {
			if (orderlist.get(i).contains(words)) {
				select.add(Result[i]);
			}
		}
		int size = select.size();
		Result = new DataTable[size];
		select.toArray(Result);
		if (Result == null) {
			this.FillDynamicList(Result);
			return;
		}
		this.FillDynamicList(Result);
	}
}
