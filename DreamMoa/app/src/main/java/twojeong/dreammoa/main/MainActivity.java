package twojeong.dreammoa.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;

import twojeong.dreammoa.faq.FaqActivity;
import twojeong.dreammoa.util.MoneySet;
import twojeong.dreammoa.item.User;
import twojeong.dreammoa.record.UserInfoActivity;
import twojeong.dreammoa.R;
import twojeong.dreammoa.database.DreamMoaDB;
import twojeong.dreammoa.location.MyLocationSearchActivity;
import twojeong.dreammoa.record.AddUsageRecordActivity;
import twojeong.dreammoa.record.UsageRecordActivity;
import twojeong.dreammoa.search.StoreSearchActivity;

public class MainActivity extends AppCompatActivity {

    private ImageButton addBtn;
    private ImageButton myDreamCardBtn;
    private ImageButton myLocationSearchBtn;
    private ImageButton storeSearchBtn;
    private ImageButton faqBtn;
    private CardView cardView;
    private TextView cardNameTxt;
    private TextView dDayTxt;
    private TextView moneyTxt;
    private ProgressBar progressBar;
    public static DreamMoaDB dreamMoaDB;
    private MoneySet mset;
    private int month;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dreamMoaDB = new DreamMoaDB(getApplicationContext(), "DreamMoa", null, 1);
        init();


    }

    @Override
    protected void onResume() {
        super.onResume();
        user = dreamMoaDB.getUserInfo();
        String temp = user.getName();
        cardNameTxt.setText("'"+temp+"'님의 현재 잔액");
        mset= new MoneySet();
        moneyTxt.setText(mset.getRest()+ "원");
     //   progressBar.setProgress(mset.getmProgress());
    }

    private void init() {
        Calendar cal = Calendar.getInstance();
        addBtn = (ImageButton) findViewById(R.id.addBtn);
        myDreamCardBtn = (ImageButton) findViewById(R.id.myDreamCardBtn);
        myLocationSearchBtn = (ImageButton) findViewById(R.id.myLocationSearchBtn);
        storeSearchBtn = (ImageButton) findViewById(R.id.storeSearchBtn);
        faqBtn = (ImageButton) findViewById(R.id.faqBtn);
        cardView = (CardView) findViewById(R.id.cardView);
        cardNameTxt = (TextView) findViewById(R.id.cardNameTxt);
        dDayTxt = (TextView) findViewById(R.id.dDayTxt);
        moneyTxt = (TextView) findViewById(R.id.moneyTxt);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mset= new MoneySet();
        moneyTxt.setText(mset.getRest()+ "원");
        user = dreamMoaDB.getUserInfo();
        String temp = user.getName();
        cardNameTxt.setText("'"+temp+"'님의 현재 잔액");
    //    progressBar.setProgress(mset.getmProgress());
        dDaySet();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UsageRecordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        myDreamCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        addBtn.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddUsageRecordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        storeSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreSearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        myLocationSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyLocationSearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        faqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FaqActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void dDaySet(){
        try {
        Calendar today = Calendar.getInstance();
        int myear = today.get(today.YEAR);
        int mmonth = today.get(today.MONTH)+1;
        int mday = 1;

            month=mmonth;
        Calendar dday = Calendar.getInstance();
            dday.set(myear,mmonth,mday);

            long day = dday.getTimeInMillis()/86400000;
            // 각각 날의 시간 값을 얻어온 다음
            //( 1일의 값(86400000 = 24시간 * 60분 * 60초 * 1000(1초값) ) )


            long tday = today.getTimeInMillis()/86400000;
            long count = tday - day; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
            dDayTxt.setText("충전 D-"+count);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}