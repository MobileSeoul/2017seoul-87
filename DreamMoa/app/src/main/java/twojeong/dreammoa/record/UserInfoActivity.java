package twojeong.dreammoa.record;

import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import twojeong.dreammoa.R;
import twojeong.dreammoa.database.DreamMoaDB;
import twojeong.dreammoa.item.User;

import static twojeong.dreammoa.main.MainActivity.dreamMoaDB;

public class UserInfoActivity extends AppCompatActivity {

    private EditText userName;
    private Spinner addressSpinner;
    private Spinner typeSpinner;
    private Spinner countSpinner;
    private Button saveBtn;
    private ImageButton homeBtn;
    private TextView uintCostTxt;

    String name="";
    String text="";
    String countTxt="";
    String typeTxt="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        init();
    }

    private void init(){
        userName = (EditText) findViewById(R.id.userName);
        addressSpinner = (Spinner) findViewById(R.id.addressSpinner);
        countSpinner = (Spinner) findViewById(R.id.countSpinner);
        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        uintCostTxt = (TextView) findViewById(R.id.uintCostTxt);
        settingUserInfo();

        //스피너 연결
        //스피너 어댑터 설정
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.address,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.type2,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this,R.array.count,android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if(addressSpinner.getSelectedItemPosition()!=1) {
            uintCostTxt.setText("5000원");
        }
        else {
            uintCostTxt.setText("5500원");

        }

        addressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if(position!=1) {
                    uintCostTxt.setText("5000원");
                }
                else uintCostTxt.setText("5500원");
                onResume();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });




        //어뎁터 연결
        addressSpinner.setAdapter(adapter1);
        typeSpinner.setAdapter(adapter2);
        countSpinner.setAdapter(adapter3);
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                name = userName.getText().toString();
                text = addressSpinner.getSelectedItemPosition()+"";
                typeTxt=typeSpinner.getSelectedItemPosition()+"";;
                countTxt=countSpinner.getSelectedItemPosition()+"";;
                dreamMoaDB.setUserData(name,text,typeTxt,countTxt);
                finish();
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        settingUserInfo();
    }

    private  void settingUserInfo() {
        User user = dreamMoaDB.getUserInfo();
        userName.setText(user.getName());
        int n1 = Integer.parseInt(user.getAddress());
        addressSpinner.setSelection(n1);
        int n2 = Integer.parseInt(user.getType());
        typeSpinner.setSelection(n2);
        int n3 = Integer.parseInt(user.getCount());
        countSpinner.setSelection(n3);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
