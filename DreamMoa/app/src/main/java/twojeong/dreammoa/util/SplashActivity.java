package twojeong.dreammoa.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import twojeong.dreammoa.R;
import twojeong.dreammoa.main.MainActivity;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e  ){
            e.printStackTrace();
        }

       startActivity(new Intent(this, MainActivity.class));
        Toast.makeText(this,"인터넷과 GPS를 연결해주세요.",Toast.LENGTH_LONG).show();
        finish();
    }


}
