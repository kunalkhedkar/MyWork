package com.example.kunal.swipetabs;

import android.app.FragmentTransaction;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

public class MainActivity extends ActionBarActivity implements TabLayout.OnTabSelectedListener {

    ActionBar actionBar;

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager= (ViewPager) findViewById(R.id.viewpager);
        tabLayout= (TabLayout) findViewById(R.id.tablayout);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(new myadapter(getSupportFragmentManager()));

        tabLayout.addOnTabSelectedListener(this);


  /*      actionBar=getSupportActionBar();

        //actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_STANDARD);



      ActionBar.Tab tab1=actionBar.newTab();
        tab1.setText("TAB 1");

        actionBar.addTab(tab1);


        actionBar.addTab(actionBar.newTab().setText("TAB 1").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("TAB 2").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("TAB 3").setTabListener(this));

*/


    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }


    class myadapter extends FragmentPagerAdapter{

        public myadapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new FragmentA();

                case 1:return new FragmentB();

                default:return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0: return "Home";

                case 1:return "Contact us";

                default:return null;
            }
        }
    }

}
