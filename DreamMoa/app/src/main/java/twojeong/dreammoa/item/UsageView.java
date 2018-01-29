package twojeong.dreammoa.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import twojeong.dreammoa.R;

/**
 * Created by USER on 2017-10-29.
 */

public class UsageView  extends LinearLayout {
    TextView textView1;
    TextView textView2;
    TextView textView3;

    public UsageView(Context context) {
        super(context);
        init(context);
    }

    public UsageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.record_item, this, true);
        textView1 = (TextView) findViewById(R.id.useNameTxt);
        textView2 = (TextView) findViewById(R.id.useDateTxt);
        textView3 = (TextView) findViewById(R.id.usePriceTxt);

    }
    public void setName(String name) {
        textView1.setText(name);
    }

    public void setDate(String date) {
        textView2.setText(date);
    }

    public void setPrice(String price) {
        textView3.setText(price);
    }
}
