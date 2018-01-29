package twojeong.dreammoa.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import twojeong.dreammoa.R;
import twojeong.dreammoa.database.DreamMoaDB;
import twojeong.dreammoa.item.Store;
import twojeong.dreammoa.item.StoreAdapter;

import static twojeong.dreammoa.main.MainActivity.dreamMoaDB;

public class StoreSearchActivity extends AppCompatActivity {


    private ImageButton backBtn;
    private Spinner addressSpinner;
    private Spinner typeSpinner;
    private Button searchBtn;
    private ListView listView;
    ArrayList<Store> dataList;
    public StoreAdapter storeAdapter;
    private int addressNum=0, typeNum=0;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storesearch);
        init();
    }

    private void init() {
        backBtn = (ImageButton) findViewById(R.id.backbtn);
        addressSpinner = (Spinner) findViewById(R.id.addressSpinner);
        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        listView = (ListView) findViewById(R.id.listview1);
        layout = (LinearLayout) findViewById(R.id.layout);

        //스피너 어댑터 설정
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.address,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.type,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //어뎁터 연결
        addressSpinner.setAdapter(adapter1);
        typeSpinner.setAdapter(adapter2);

        //리스트뷰 어댑터 연결
        storeAdapter = new StoreAdapter(getApplication());
        listView.setAdapter(storeAdapter);   // listView 객체에 Adapter를 붙인다

        //뒤로가기 버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //스피너 이벤트
        addressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addressNum = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {            }
        });
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeNum = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.VISIBLE);
                dataList = dreamMoaDB.storeListSelect(addressNum,typeNum);
                storeAdapter.addItemList(dataList);
                storeAdapter.notifyDataSetChanged();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), detailSearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                Store st = (Store)storeAdapter.getItem(position);
                intent.putExtra("name",st.getName());
                intent.putExtra("address",st.getAddress1()+" "+st.getAddress2());
                intent.putExtra("etcAddress",st.getAddress3());
                intent.putExtra("tel",st.getTel());
                startActivity(intent);
            }
        });


    }
}
