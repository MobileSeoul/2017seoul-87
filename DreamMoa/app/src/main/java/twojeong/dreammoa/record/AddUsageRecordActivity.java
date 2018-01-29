package twojeong.dreammoa.record;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static twojeong.dreammoa.main.MainActivity.dreamMoaDB;

import twojeong.dreammoa.R;

public class AddUsageRecordActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private Button saveBtn;
    private TextView useStoreTxt;
    private EditText usePriceTxt;
    private EditText mDay, mYear, mMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addusagerecord);
        init();
    }


    private void init(){

        Calendar today = Calendar.getInstance(); //오늘날자 가져오기
        int myear = today.get(today.YEAR);
        int mmonth = today.get(today.MONTH)+1;
        int mday = today.get(today.DAY_OF_MONTH);



        backBtn = (ImageButton) findViewById(R.id.backbtn);
        saveBtn = (Button)findViewById(R.id.saveBtn);
        mYear = (EditText) findViewById(R.id.date1);
        mMonth = (EditText) findViewById(R.id.date2);
        mDay = (EditText) findViewById(R.id.date3);

        mYear.setText(myear+"");
        mMonth.setText(mmonth+"");
        mDay.setText(mday+"");


        useStoreTxt = (EditText) findViewById(R.id.useStoreTxt);
        usePriceTxt = (EditText) findViewById(R.id.usePriceTxt);




        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = useStoreTxt.getText().toString();
                String price = usePriceTxt.getText().toString();
                dreamMoaDB.insertUsageData(name,mYear.getText().toString(),mMonth.getText().toString(),mDay.getText().toString(),price);
                finish();
            }
        });
    }




}
