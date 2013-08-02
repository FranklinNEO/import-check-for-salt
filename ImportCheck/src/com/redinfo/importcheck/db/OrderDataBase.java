package com.redinfo.importcheck.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class OrderDataBase {
	static final String DB_NAME = "code.db";
	static final String TB_NAME = "code_data";

	static void createReaderDataBase(Context mContext) {
		SQLiteDatabase db = mContext.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		db.execSQL(OrderDBHelper.ORDER_TABLE_NAME);
		db.close();
	}

	public static void deleteAll(Context mContext) {
		OrderDBHelper helper = new OrderDBHelper(mContext);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(OrderDBHelper.ORDER_TABLE_NAME);
		db.delete(TB_NAME, null, null);
		db.close();
	}
}
