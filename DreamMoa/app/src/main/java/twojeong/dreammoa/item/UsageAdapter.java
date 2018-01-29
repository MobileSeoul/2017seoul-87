package twojeong.dreammoa.item;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by USER on 2017-10-29.
 */

public class UsageAdapter extends BaseAdapter {

    ArrayList<Usage> items =  new ArrayList<>();
    Context context;

    public UsageAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(Usage item) {
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
        UsageView view = new UsageView(context);
        Usage item = items.get(position);
        view.setName(item.getStoreName());
        view.setDate(item.getDate());
        view.setPrice("-"+item.getPrice());
        return view;
    }

    public void addItemList (ArrayList<Usage> list) {
            items.clear();
            items=list;
    }
}
