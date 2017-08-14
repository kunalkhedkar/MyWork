package com.example.kunal.scrolltabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       pager= (ViewPager) findViewById(R.id.viewpager);

        pager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
    }


    class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;

            if (position == 0) {
                fragment=new FragmentA();
            }
            else if(position==1){
                fragment=new FragmentB();
            }
            else if(position==2){
                fragment=new FragmentC();
            }else if(position==3){
                fragment=new FragmentD();
            }
            else
                fragment=new FragmentE();


            return fragment;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            String titleName = null;
            switch (position){
                case 0: titleName="TAB 1";
                    break;
                case 1: titleName="TAB 2";
                break;
                case 2: titleName="TAB 3";
                break;
                default: titleName="new Tab";
            }
            return titleName;
        }
    }
}
