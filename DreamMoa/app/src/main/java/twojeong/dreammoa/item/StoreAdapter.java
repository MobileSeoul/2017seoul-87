package twojeong.dreammoa.item;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import twojeong.dreammoa.search.StoreSearchActivity;

/**
 * Created by USER on 2017-10-26.
 */

public class StoreAdapter extends BaseAdapter {

    ArrayList<Store> items =  new ArrayList<>();
    Context context;

    public StoreAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(Store item) {
        items.add(item);
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        StoreItemView view = new StoreItemView(context);
        Store item = items.get(position);
        view.setImageView(item.getType());

        view.setName(item.getName());
        String temp = item.getAddress3();
        if(temp==null) {
            temp = "";
        }
        view.setAddress(item.getAddress1() +" "+ item.getAddress2()+" "+temp);
        view.setTel(item.getTel());
        return view;
    }

    public void addItemList (ArrayList<Store> list) {
        items.clear();
        items=list;
    }
}
