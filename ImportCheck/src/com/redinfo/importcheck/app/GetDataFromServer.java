package com.redinfo.importcheck.app;

import java.util.ArrayList;

import com.redinfo.importcheck.datamodel.DataRow;
import com.redinfo.importcheck.datamodel.DataTable;

public class GetDataFromServer {
	public static int size = 0;

	public static DataTable[] getDatas(String rec) {
		// DataTable t1 = new DataTable();
		// t1.setTableName("没有查询到相关数据");
		// DataRow t1R = new DataRow();
		// rec = rec.replaceAll("\"", "");
		// t1R.addNewData("注意", rec);
		// t1.addNewRow(t1R);
		// ArrayList<DataTable> r = new ArrayList<DataTable>();
		// r.add(t1);
		// final int size = r.size();
		// DataTable[] result = new DataTable[size];
		// r.toArray(result);
		// return result;

		DataTable t1 = new DataTable();
		t1.setTableName("100001");
		DataRow t1R = new DataRow();
		t1R.addNewData("生产企业", "井神");
		t1R.addNewData("产品名称", "500g精制绿标碘盐");
		t1R.addNewData("发货日期", "2013-05-01");
		t1R.addNewData("发货箱数", "120");
		t1.addNewRow(t1R);

		DataTable t2 = new DataTable();
		t2.setTableName("100002");
		DataRow t2R = new DataRow();
		t2R.addNewData("生产企业", "瑞丰 ");
		t2R.addNewData("产品名称", "400g海藻碘盐");
		t2R.addNewData("发货日期", "2013-05-01");
		t2R.addNewData("发货箱数", "1910");
		t2.addNewRow(t2R);

		DataTable t3 = new DataTable();
		t3.setTableName("100003");
		DataRow t3R = new DataRow();
		t3R.addNewData("生产企业", "井神");
		t3R.addNewData("产品名称", "250g绿色食品食用盐");
		t3R.addNewData("发货日期", "2013-05-02");
		t3R.addNewData("发货箱数", "1746");
		t3.addNewRow(t3R);

		DataTable t4 = new DataTable();
		t4.setTableName("100004");
		DataRow t4R = new DataRow();
		t4R.addNewData("生产企业", "金桥盐化");
		t4R.addNewData("产品名称", "400g海藻碘盐");
		t4R.addNewData("发货日期", "2013-05-02");
		t4R.addNewData("发货箱数", "1850");
		t4.addNewRow(t4R);

		DataTable t5 = new DataTable();
		t5.setTableName("100005");
		DataRow t5R = new DataRow();
		t5R.addNewData("生产企业", "银宝盐业");
		t5R.addNewData("产品名称", "400g海藻碘盐");
		t5R.addNewData("发货日期", "2013-05-02");
		t5R.addNewData("发货箱数", "2000");
		t5.addNewRow(t5R);

		DataTable t6 = new DataTable();
		t6.setTableName("100006");
		DataRow t6R = new DataRow();
		t6R.addNewData("生产企业", "井神盐化第二分公司");
		t6R.addNewData("产品名称", "400g海藻碘盐");
		t6R.addNewData("发货日期", "2013-05-02");
		t6R.addNewData("发货箱数", "1400");
		t6.addNewRow(t6R);

		DataTable t7 = new DataTable();
		t7.setTableName("100007");
		DataRow t7R = new DataRow();
		t7R.addNewData("生产企业", "南通");
		t7R.addNewData("产品名称", "5KG加碘海盐");
		t7R.addNewData("发货日期", "2013-05-02");
		t7R.addNewData("发货箱数", "20");
		t7.addNewRow(t7R);

		DataTable t8 = new DataTable();
		t8.setTableName("100008");
		DataRow t8R = new DataRow();
		t8R.addNewData("生产企业", "泰州");
		t8R.addNewData("产品名称", "500g淮牌绿标特制食盐(海盐)");
		t8R.addNewData("发货日期", "2013-05-02");
		t8R.addNewData("发货箱数", "265");
		t8.addNewRow(t8R);

		DataTable t9 = new DataTable();
		t9.setTableName("100009");
		DataRow t9R = new DataRow();
		t9R.addNewData("生产企业", "张虞");
		t9R.addNewData("产品名称", "250g绿标碘盐");
		t9R.addNewData("发货日期", "2013-05-02");
		t9R.addNewData("发货箱数", "200");
		t9.addNewRow(t9R);

		DataTable t10 = new DataTable();
		t10.setTableName("100010");
		DataRow t10R = new DataRow();
		t10R.addNewData("生产企业", "南通");
		t10R.addNewData("产品名称", "250g绿标碘盐");
		t10R.addNewData("发货日期", "2013-05-03");
		t10R.addNewData("发货箱数", "920");
		t10.addNewRow(t10R);

		ArrayList<DataTable> r = new ArrayList<DataTable>();
		r.add(t1);
		r.add(t2);
		r.add(t3);
		r.add(t4);
		r.add(t5);
		r.add(t6);
		r.add(t7);
		r.add(t8);
		r.add(t9);
		r.add(t10);

		size = r.size();
		DataTable[] result = new DataTable[size];
		r.toArray(result);
		return result;
	}

	public DataTable[] getNoDatas() {
		DataTable t1 = new DataTable();
		t1.setTableName("没有查询到相关数据");
		DataRow t1R = new DataRow();
		t1R.addNewData("注意", "获取服务器数据失败!");
		t1.addNewRow(t1R);
		ArrayList<DataTable> r = new ArrayList<DataTable>();
		r.add(t1);
		final int size = r.size();
		DataTable[] result = new DataTable[size];
		r.toArray(result);
		return result;
	}

}
