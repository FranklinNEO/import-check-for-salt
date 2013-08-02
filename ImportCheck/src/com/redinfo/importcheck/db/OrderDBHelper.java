package com.redinfo.importcheck.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderDBHelper extends SQLiteOpenHelper {

    public static OrderDBHelper mInstance = null;

    /**
     * 数据库名称 *
     */
    public static final String DATABASE_NAME = "sysorder.db";

    /**
     * 数据库版本号 *
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * DB对象 *
     */
    SQLiteDatabase mDb = null;

    Context mContext = null;

    public final static String ORDER_TABLE_NAME = "order_data";
    public final static String INFO_TABLE_NAME = "info_data";
    public final static String CODE_TABLE_NAME = "code_data";

    /**
     * 数据库SQL语句 创建表 *
     */

    public static final String ORDER_TABLE_CREATE = "create table order_data("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "orderid TEXT NOT NULL," + "produce TEXT NOT NULL,"
            + "variety TEXT NOT NULL," + "send_date TEXT NOT NULL,"
            + "send_num TEXT NOT NULL," + "checkable TEXT NOT NULL);";

    public static final String INFO_TABLE_CREATE = "create table info_data("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "order_num TEXT NOT NULL," + "send_id TEXT NOT NULL,"
            + "arrive_date TEXT NOT NULL);";

    public static final String CODE_TABLE_CREATE = "create table code_data("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "code20 TEXT NOT NULL," + "unclear TEXT NOT NULL,"
            + "nocode TEXT NOT NULL," + "result TEXT NOT NULL);";

    /**
     * 单例模式 *
     */
    public static synchronized OrderDBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new OrderDBHelper(context);
        }
        return mInstance;
    }

    public OrderDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // 得到数据库对象
        mDb = getReadableDatabase();
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建数据库
        db.execSQL(ORDER_TABLE_CREATE);
        db.execSQL(INFO_TABLE_CREATE);
        db.execSQL(CODE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * @param tablename
     * @param orderid
     * @param produce
     * @param variety
     * @param send_date
     * @param send_num
     * @param checkable
     */
    public void insert_order(String tablename, String orderid, String produce,
                             String variety, String send_date, String send_num, String checkable) {
        ContentValues values = new ContentValues();
        values.put("orderid", orderid);
        values.put("produce", produce);
        values.put("variety", variety);
        values.put("send_date", send_date);
        values.put("send_num", send_num);
        values.put("checkable", checkable);
        mDb.insert(tablename, null, values);
        values.clear();
    }

    /**
     * @param tablename
     * @param orderid
     * @param produce
     * @param variety
     * @param send_date
     * @param send_num
     * @param checkable
     */
    public void update_order(String tablename, String orderid, String produce,
                             String variety, String send_date, String send_num, String checkable) {
        ContentValues values = new ContentValues();
        values.put("orderid", orderid);
        values.put("produce", produce);
        values.put("variety", variety);
        values.put("send_date", send_date);
        values.put("send_num", send_num);
        values.put("checkable", checkable);
        mDb.update(tablename, values, "orderid='" + orderid + "' AND produce='"
                + produce + "' AND variety='" + variety + "' AND send_date='"
                + send_date + "'AND send_num='" + send_num + "'AND checkable='"
                + checkable + "';", null);
        values.clear();
    }

    /**
     * @param tablename
     * @param order_num
     * @param send_id
     * @param arrive_date
     */
    public void insert_info(String tablename, String order_num, String send_id,
                            String arrive_date) {
        ContentValues values = new ContentValues();
        values.put("order_num", order_num);
        values.put("send_id", send_id);
        values.put("arrive_date", arrive_date);
        mDb.insert(tablename, null, values);
        values.clear();
    }

    /**
     * @param tablename
     * @param order_num
     * @param send_id
     * @param arrive_date
     */
    public void update_info(String tablename, String order_num, String send_id,
                            String arrive_date) {
        ContentValues values = new ContentValues();
        values.put("order_num", order_num);
        values.put("send_id", send_id);
        values.put("arrive_date", arrive_date);
        mDb.update(tablename, values, "order_num='" + order_num
                + "' AND send_id='" + send_id + "' AND arrive='" + arrive_date
                + "';", null);
        values.clear();
    }

    /**
     * @param tablename
     * @param code20
     * @param unclear
     * @param nocode
     * @param result
     */
    public void insert_code(String tablename, String code20, String unclear,
                            String nocode, String result) {
        ContentValues values = new ContentValues();
        values.put("code20", code20);
        values.put("unclear", unclear);
        values.put("nocode", nocode);
        values.put("result", result);
        mDb.insert(tablename, null, values);
        values.clear();
    }

    /**
     * @param tablename
     * @param code20
     * @param unclear
     * @param nocode
     * @param result
     */
    public void update_code(String tablename, String code20, String unclear,
                            String nocode, String result) {
        ContentValues values = new ContentValues();
        values.put("code20", code20);
        values.put("unclear", unclear);
        values.put("nocode", nocode);
        values.put("result", result);
        mDb.update(tablename, values, "code20='" + code20
                + "' AND unclear='" + unclear + "' AND nocode='" + nocode + "'AND result='" + result
                + "';", null);
        values.clear();
    }

    /**
     * @param tablename
     * @param code20
     */
    public void delete_code(String tablename, String code20) {
        mDb.delete(tablename, "code20='" + code20 + "';", null);
    }
}
