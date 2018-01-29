package twojeong.dreammoa.record;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


import twojeong.dreammoa.util.MoneySet;
import twojeong.dreammoa.R;
import twojeong.dreammoa.item.Usage;
import twojeong.dreammoa.item.UsageAdapter;
import twojeong.dreammoa.item.User;

import static twojeong.dreammoa.main.MainActivity.dreamMoaDB;


public class UsageRecordActivity extends AppCompatActivity {

    private int mmonth;
    private ImageButton backBtn;
    private ImageButton addBtn;
    private Spinner monthSpinner;
    private ListView usgeListView;
    private UsageAdapter usageAdapter;
    private ArrayList<Usage> dataList;
    private  User user;
    private TextView tmoneyTxt, tnameTxt, taddressTxt, ttypeTxt,tcountTxt;
    String[] addressList={"자치구","강남구", "강동구","강북구","강서구","관악구","광진구","구로구","금천구","노원구","도봉구","동대문구","동작구","마포구","서대문구","서초구","성동구","성북구","송파구","양천구","영등포구","용산구","은평구","종로구","중구", "중랑구"};
    String[] typeList = {"평일","주말/공휴일","평일+주말/공휴일"};
    String[] countList = {"1일 1식","1일 2식"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usagerecord);
        init();
    }

    private void init(){

        Calendar cal = Calendar.getInstance();
         mmonth = cal.get(cal.MONTH);
        tnameTxt= (TextView) findViewById(R.id.tnameTxt);
        taddressTxt= (TextView) findViewById(R.id.taddressTxt);
        ttypeTxt= (TextView) findViewById(R.id.ttypeTxt);
        tcountTxt= (TextView) findViewById(R.id.tcountTxt);
        addBtn = (ImageButton) findViewById(R.id.listAddBtn);
        backBtn = (ImageButton) findViewById(R.id.backbtn);
        MoneySet m = new MoneySet();
        user =m.getUser();

        tnameTxt.setText(user.getName());
        taddressTxt.setText(addressList[Integer.parseInt(user.getAddress())]+" / ");
        ttypeTxt.setText(typeList[Integer.parseInt(user.getType())]+" / ");
        tcountTxt.setText(countList[Integer.parseInt(user.getCount())]);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddUsageRecordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        usgeListView = (ListView) findViewById(R.id.usgeListView);
        String[] month = {"1", "2", "3", "4", "5","6","7","8","9","10","11","12"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, month);
        monthSpinner.setAdapter(adapter);
        monthSpinner.setSelection(mmonth);
        usageAdapter = new UsageAdapter(getApplication());
        usgeListView.setAdapter(usageAdapter);   // listView 객체에 Adapter를 붙인다
        dataList = dreamMoaDB.usageListSelect(mmonth+1);
        usageAdapter.addItemList(dataList);
        usageAdapter.notifyDataSetChanged();


        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                dataList = dreamMoaDB.usageListSelect(position+1);
                usageAdapter.addItemList(dataList);
                usageAdapter.notifyDataSetChanged();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        dataList = dreamMoaDB.usageListSelect(mmonth+1);
        usageAdapter.addItemList(dataList);
        usageAdapter.notifyDataSetChanged();
    }
}
