package com.redinfo.importcheck.adapter;

import com.redinfo.importcheck.R;
import com.redinfo.importcheck.datamodel.DataRow.ItemPar;
import com.redinfo.importcheck.datamodel.DataTable;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class MulitSingleTableAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private DataTable[] data;

	public MulitSingleTableAdapter(Context context, DataTable[] data) {
		this.inflater = LayoutInflater.from(context);
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.length;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	public void setDataTable(DataTable[] datatable) {
		data = datatable;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DataTable currentData = this.data[position];
		convertView = new TableLayout(parent.getContext());
		if (currentData.getRowCount() < 1)
			return convertView;
		// convertView = ((TableLayout)
		// this.inflater.inflate(R.layout.dynamic_list_item,
		// null));
		convertView = new TableLayout(parent.getContext());
		TableLayout tableView = ((TableLayout) convertView);
		tableView.setPadding(12, 0, 12, 0);

		LinearLayout newTitle = (LinearLayout) this.inflater.inflate(
				R.layout.title_item, null);
		LinearLayout TitleName = (LinearLayout) newTitle.getChildAt(0);
		TextView titleTv = (TextView) TitleName.getChildAt(0);
		titleTv.setText("系统单号 "+currentData.getTableName());
		tableView.addView(newTitle);
		for (int j = 0; j < currentData.getRowCount(); j++) {
			int columnCount = currentData.getRowAt(0).getItemCount();

			LinearLayout item = (LinearLayout) this.inflater.inflate(
					R.layout.table_item, null);
			LinearLayout itemContent = (LinearLayout) item.getChildAt(0);
			TextView titleContent = (TextView) itemContent.getChildAt(0);
			Paint paint = titleContent.getPaint();
			float textWidth = 0;
			for (int i = 0; i < columnCount; i++) {
				ItemPar iPar = this.data[position].getRowAt(0).getItemPar(i);
				float t = paint.measureText(iPar.getTitle() + "：");
				textWidth = t > textWidth ? t : textWidth;
			}

			for (int i = 0; i < columnCount; i++) {
				// 创建TableRow
				LinearLayout newRow = (LinearLayout) this.inflater.inflate(
						R.layout.table_item, null);

				// 设置行背景
				if (columnCount == 1) {
					newRow.setBackgroundResource(R.drawable.reader_news_single_bg);
				} else if (i == 0) {
					newRow.setBackgroundResource(R.drawable.reader_news_multi_header);
				} else if (i == columnCount - 1) {
					newRow.setBackgroundResource(R.drawable.reader_news_multi_bottom);
				} else {
					newRow.setBackgroundResource(R.drawable.reader_news_multi_middle);
				}
				LinearLayout rowContent = (LinearLayout) newRow.getChildAt(0);
				// 创建标题
				TextView colTextView = (TextView) rowContent.getChildAt(0);

				// 创建内容
				TextView contentTextView = (TextView) rowContent.getChildAt(1);
				tableView.addView(newRow);

				ItemPar iPar = this.data[position].getRowAt(0).getItemPar(i);

				colTextView.setText(iPar.getTitle() );
				colTextView.setWidth((int) textWidth);
				contentTextView.setText(" "+iPar.getContent());
			}
		}
		return convertView;
	}

}