package com.example.murphysl.todo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * MyViewPagerAdapter
 *
 * @author: MurphySL
 * @time: 2016/10/30 13:21
 */

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    private String[] titles = {"计划" , "完成"};

    public MyViewPagerAdapter(FragmentManager manager , List<Fragment> list){
        super(manager);
        this.list = list;
    }
    @Override
    public Fragment getItem(int position) {
        return list.get(position % 2);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
