package twojeong.dreammoa.util;

import java.util.ArrayList;
import java.util.Calendar;

import twojeong.dreammoa.item.Usage;
import twojeong.dreammoa.item.User;

import static twojeong.dreammoa.main.MainActivity.dreamMoaDB;

/**
 * Created by USER on 2017-10-28.
 */

public class MoneySet {
    private int mMoney;
    private int mmonth;
    private int dday;
    private int count;
    private int unitCost = 5000;
    private String name="";
    private int[] month= {16,15,31};
    private int[] month10= {16,15,31};
    private int[] month11= {22,8,30};
    private int[] month12= {20,11,31};
    private int[] month1= {20,11,31};
    private int[] month2= {20,8,28};
    private int[] month3= {22,9,31};
    private int[] month4= {20,10,30};
    private int[] month5= {20,11,31};
    private int[] month6= {21,9,30};
    private int[] month7= {21,10,31};
    private int[] month8= {22,9,31};
    private int[] month9= {21,9,30};

    User user;

    public MoneySet() {
        user = dreamMoaDB.getUserInfo();
        Calendar todady = Calendar.getInstance();
        mmonth =  todady.get(Calendar.MONTH)+1;
        switch (mmonth) {
            case 10 : month =month10; break;
            case 11 : month =month11;  break;
            case 12 : month =month12;  break;
            case 1 : month =month1;  break;
            case 2 : month =month2;  break;
            case 3 : month =month3;  break;
            case 4 : month =month4;  break;
            case 5 : month =month5;  break;
            case 6 : month =month6;  break;
            case 7 : month =month7;  break;
            case 8 : month =month8;  break;
            case 9 : month =month9;  break;
        }
        switch (user.getType()) {
            case "0" : dday = month[0]; break;
            case "1" : dday = month[1]; break;
            case "2" : dday = month[2]; break;
        }
        count= Integer.parseInt(user.getCount())+1;
        if(user.getAddress().equals("1"))
            unitCost = 5500;

        getmMoney();
    }

    public int getmMoney() {
        mMoney = unitCost*dday*count;
        return mMoney;
    }

    public int getmProgress() {
        int temp = getRest()*100/mMoney;
        return temp;
    }

    public User getUser() {
        return user;
    }

    public int getRest() {
        ArrayList<Usage> dataList = dreamMoaDB.usageListSelect(mmonth);
        Usage usage;
        int rest=0;
        for(int i = 0; i<dataList.size();i++ ) {
            usage =  dataList.get(i);
            rest = rest +Integer.parseInt(usage.getPrice());
        }
        return mMoney-rest;
    }

}
