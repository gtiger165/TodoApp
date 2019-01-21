package com.hirarki.todoapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hirarki.todoapp.HarianFragment;
import com.hirarki.todoapp.KhususFragment;

/**
 * Created by hp on 06/11/2018.
 */

public class TabTodoAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    //Constructor TabTodoAdapter
    public TabTodoAdapter(FragmentManager fm, int mNumOfTabs){
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    //Pada posisi 0 berada di layout Khusus, 1 berada di layout Harian
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new KhususFragment();
            case 1:
                return new HarianFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
