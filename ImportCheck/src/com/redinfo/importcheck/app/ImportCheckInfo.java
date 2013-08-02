package com.redinfo.importcheck.app;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;

import com.redinfo.android.calendar.CalendarActivity;
import com.redinfo.importcheck.R;
import com.redinfo.importcheck.db.OrderDBHelper;

public class ImportCheckInfo extends Activity implements OnClickListener {
    private Button date_btn;
    private Button next_btn;
    private EditText date_et;
    private EditText order_et;
    private EditText send_et;
    private TextView product;
    private TextView producer;
    private TextView Send_date;
    private TextView Send_num;
    // private String order;

    public OrderDBHelper m_db = null;
    SQLiteDatabase db = null;
    public final static String URL = "/data/data/com.redinfo.importcheck/databases";
    public final static String DB_FILE_NAME = "sysorder.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stubw
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importinfo);

        m_db = OrderDBHelper.getInstance(ImportCheckInfo.this);
        File file = new File(URL, DB_FILE_NAME);
        db = SQLiteDatabase.openOrCreateDatabase(file, null);
        date_btn = (Button) findViewById(R.id.date_btn);
        date_btn.setOnClickListener(this);
        date_et = (EditText) findViewById(R.id.date_et);
        order_et = (EditText) findViewById(R.id.order_id);
        send_et = (EditText) findViewById(R.id.send_id);
        next_btn = (Button) findViewById(R.id.next_btn);
        next_btn.setOnClickListener(this);
        product = (TextView) findViewById(R.id.product_tv);
        producer = (TextView) findViewById(R.id.producer_tv);
        Send_date = (TextView) findViewById(R.id.send_date_tv);
        Send_num = (TextView) findViewById(R.id.send_num_tv);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            // order = bundle.getString("SystemOrder");
            producer.setText(bundle.getString("ProductName"));
            product.setText(bundle.getString("ProductVariety"));
            Send_date.setText(bundle.getString("SendDate"));
            Send_num.setText(bundle.getString("SendNum"));
        }
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.date_btn:
                startActivityForResult(
                        new Intent(Intent.ACTION_PICK).setDataAndType(null,
                                CalendarActivity.MIME_TYPE), 100);
                break;
            case R.id.next_btn:
                String date = date_et.getText().toString().trim();
                String orderId = order_et.getText().toString().trim();
                String sendId = send_et.getText().toString().trim();
                if ((date.length() == 0) || (orderId.length() == 0)
                        || (sendId.length() == 0)) {
                    Toast.makeText(ImportCheckInfo.this, "请完善物流信息",
                            Toast.LENGTH_SHORT).show();
                } else {
                    InsertOrder(date, orderId, sendId, producer.getText()
                            .toString().trim(), product.getText().toString().trim());
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("orderId", orderId);
                    bundle.putString("sendId", sendId);
                    bundle.putString("product", product.getText().toString().trim());
                    bundle.putString("producer", producer.getText().toString()
                            .trim());
                    bundle.putString("date", date);
                    intent.putExtras(bundle);
                    intent.setClass(ImportCheckInfo.this, CodeSubmit.class);
                    startActivity(intent);
                    // ImportCheckInfo.this.finish();
                }
            default:
                break;
        }
    }

    private void InsertOrder(String date, String orderId, String sendId,
                             String producer, String product) {
        m_db.insert_order(OrderDBHelper.ORDER_TABLE_NAME, orderId, producer,
                product, date, sendId, 0 + "");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            int year = data.getIntExtra("year", 0); // get number of year
            int month = data.getIntExtra("month", 0); // get number of month
            int day = data.getIntExtra("day", 0); // get number of day 0..31

            // format date and display on screen
            final Calendar dat = Calendar.getInstance();
            dat.set(Calendar.YEAR, year);
            dat.set(Calendar.MONTH, month);
            dat.set(Calendar.DAY_OF_MONTH, day);

            // show result
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(dat.getTime());
            date_et.setText(date);

        }
    }
}
