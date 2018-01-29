package twojeong.dreammoa.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import twojeong.dreammoa.R;

/**
 * Created by USER on 2017-10-26.
 */

public class StoreItemView extends LinearLayout {
    ImageView imageView;
    TextView textView1;
    TextView textView2;
    TextView textView3;


    public StoreItemView(Context context) {
        super(context);
        init(context);
    }

    public StoreItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item, this, true);
        imageView = (ImageView) findViewById(R.id.itemImg);
        textView1 = (TextView) findViewById(R.id.itemName);
        textView2 = (TextView) findViewById(R.id.itemAddress);
        textView3 = (TextView) findViewById(R.id.itemTel);

    }
// String[] typeList={"업종","도시락","떡집","반찬집","분식","양식","제과점","죽집","중식","치킨","카페","토스트","한식","기타","급식소"};
    public void setImageView(String type) {
        switch (type) {
            case "도시락": imageView.setImageResource(R.drawable.ehtlfkr_icon);break;
            case "떡집": imageView.setImageResource(R.drawable.ejr_icon);break;
            case "반찬집": imageView.setImageResource(R.drawable.qkscks_icon);break;
            case "분식": imageView.setImageResource(R.drawable.qnstlr_icon);break;
            case "양식": imageView.setImageResource(R.drawable.yangsic_icon);break;
            case "제과점": imageView.setImageResource(R.drawable.bakery_icon);break;
            case "죽집": imageView.setImageResource(R.drawable.center_icon);break;
            case "중식": imageView.setImageResource(R.drawable.jungsik_icon);break;
            case "치킨": imageView.setImageResource(R.drawable.chicken_icon);break;
            case "카페": imageView.setImageResource(R.drawable.cafe_icon);break;
            case "토스트": imageView.setImageResource(R.drawable.toast_icon);break;
            case "한식": imageView.setImageResource(R.drawable.hansik_icon);break;
            case "기타": imageView.setImageResource(R.drawable.kita_icon);break;
            case "급식소": imageView.setImageResource(R.drawable.rmqtlrth_icon);break;
        }
    }
    public void setName(String name) {
        textView1.setText(name);
    }

    public void setAddress(String address) {
        textView2.setText(address);
    }

    public void setTel(String tel) {
        textView3.setText(tel);
    }
}
