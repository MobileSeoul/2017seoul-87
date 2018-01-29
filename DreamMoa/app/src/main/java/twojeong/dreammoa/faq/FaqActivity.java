package twojeong.dreammoa.faq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import twojeong.dreammoa.R;

import static twojeong.dreammoa.main.MainActivity.dreamMoaDB;

public class FaqActivity extends AppCompatActivity {

    private ImageButton homeBtn;
    private LinearLayout q1,q2,q3,q4,q5,q6,q7,q8,q9,q10;
    private LinearLayout a1,a2,a3,a4,a5,a6,a7,a8,a9,a10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        init();

    }

    private void init(){

        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        q1 = (LinearLayout) findViewById(R.id.q1);
        q2 = (LinearLayout) findViewById(R.id.q2);
        q3 = (LinearLayout) findViewById(R.id.q3);
        q4 = (LinearLayout) findViewById(R.id.q4);
        q5 = (LinearLayout) findViewById(R.id.q5);
        q6 = (LinearLayout) findViewById(R.id.q6);
        q7 = (LinearLayout) findViewById(R.id.q7);
        q8 = (LinearLayout) findViewById(R.id.q8);
        q9 = (LinearLayout) findViewById(R.id.q9);
        q10 = (LinearLayout) findViewById(R.id.q10);

        a1 = (LinearLayout) findViewById(R.id.a1);
        a2 = (LinearLayout) findViewById(R.id.a2);
        a3 = (LinearLayout) findViewById(R.id.a3);
        a4 = (LinearLayout) findViewById(R.id.a4);
        a5 = (LinearLayout) findViewById(R.id.a5);
        a6 = (LinearLayout) findViewById(R.id.a6);
        a7 = (LinearLayout) findViewById(R.id.a7);
        a8 = (LinearLayout) findViewById(R.id.a8);
        a9 = (LinearLayout) findViewById(R.id.a9);
        a10 = (LinearLayout) findViewById(R.id.a10);

        q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a1.getVisibility()==View.VISIBLE) a1.setVisibility(View.GONE);
                else                    a1.setVisibility(View.VISIBLE);
            }
        });
        q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a2.getVisibility()==View.VISIBLE) a2.setVisibility(View.GONE);
                else                    a2.setVisibility(View.VISIBLE);
            }
        });
        q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a3.getVisibility()==View.VISIBLE) a3.setVisibility(View.GONE);
                else                    a3.setVisibility(View.VISIBLE);
            }
        });
        q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a4.getVisibility()==View.VISIBLE) a4.setVisibility(View.GONE);
                else                    a4.setVisibility(View.VISIBLE);
            }
        });
        q5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a5.getVisibility()==View.VISIBLE) a5.setVisibility(View.GONE);
                else                    a5.setVisibility(View.VISIBLE);
            }
        });

        q6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a6.getVisibility()==View.VISIBLE) a6.setVisibility(View.GONE);
                else                    a6.setVisibility(View.VISIBLE);
            }
        });
        q7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a7.getVisibility()==View.VISIBLE) a7.setVisibility(View.GONE);
                else                    a7.setVisibility(View.VISIBLE);
            }
        });
        q8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a8.getVisibility()==View.VISIBLE) a8.setVisibility(View.GONE);
                else                    a8.setVisibility(View.VISIBLE);
            }
        });
        q9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a9.getVisibility()==View.VISIBLE) a9.setVisibility(View.GONE);
                else                    a9.setVisibility(View.VISIBLE);
            }
        });
        q10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a10.getVisibility()==View.VISIBLE) a10.setVisibility(View.GONE);
                else                    a10.setVisibility(View.VISIBLE);
            }
        });



    }
}
