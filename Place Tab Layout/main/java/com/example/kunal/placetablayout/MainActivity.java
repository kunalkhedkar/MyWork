package com.example.kunal.placetablayout;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ViewPagerAdapter adapter;

    ViewPager mViewPager;
    TabLayout tabLayout;
    Toolbar toolbar;

    FloatingActionButton fab;
    int lastPosition = 0, currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fab = (FloatingActionButton) findViewById(R.id.fab);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Profile");


        mViewPager = (ViewPager) findViewById(R.id.container);

        tabLayout = (TabLayout) findViewById(R.id.profiletabs);
        tabLayout.setupWithViewPager(mViewPager);

        setupViewPager(mViewPager);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                lastPosition = tab.getPosition();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {         // fab save
            @Override
            public void onClick(View v) {

                if (currentPosition == 0) {


                } else if (currentPosition == 2) {


                }
            }
        });


    }// onc


    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new PersonalProfileTabFragment(), "Personal");
        adapter.addFrag(new EducationTabFragment(), "Education");
        adapter.addFrag(new ProjectsProfileTabFragment(), "Projects");
        adapter.addFrag(new AchievementsProfileTabFragment(), "Accomplishments");
        adapter.addFrag(new CareerobjProfileTabFragment(), "Career Objectives");
        adapter.addFrag(new PrintProfileTabFragment(), "Print Profile");

        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {


            return mFragmentTitleList.get(position);
        }
    }




//
//    @Override
//    public void onBackPressed() {
//
//        PersonalProfileTabFragment fragment = (PersonalProfileTabFragment) adapter.getItem(0);
//        ProjectsProfileTabFragment projFrag = (ProjectsProfileTabFragment) adapter.getItem(2);
//
//        if (fragment.edittedFlag == 1 || projFrag.edittedFlag == 1) {
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//            alertDialogBuilder
//                    .setMessage("Do you want to save all changes ?")
//                    .setCancelable(false)
//                    .setPositiveButton("save",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//
//                                    int personalflag = 0, projectFlag = 0;
//                                    PersonalProfileTabFragment fragment = (PersonalProfileTabFragment) adapter.getItem(0);
//                                    ProjectsProfileTabFragment projFrag = (ProjectsProfileTabFragment) adapter.getItem(2);
////
//                                    Boolean personal_success = true;
//                                    Boolean project_success = true;
//
//                                    if (fragment.edittedFlag == 1) {
//                                        personal_success = fragment.validate();
//                                        if (!personal_success) {
//                                            mViewPager.setCurrentItem(0);
//                                            fragment.validate();
//                                            personalflag = 1;
//                                        } else {
//                                            personalflag = 0;
//                                            fragment.save();
//                                        }
//                                    }
//                                    if(projFrag.editproj == 1){
//                                        projFrag.save();
//                                        personal_success=true;
//                                    }
//                                    else {
//                                        if (projFrag.edittedFlag == 1) {
//                                            project_success = projFrag.myvalidate();
//                                            if (!project_success) {
//                                                if (personalflag != 1) {
////                                                projFrag.setCount();
//                                                    mViewPager.setCurrentItem(2);
//                                                    projFrag.myvalidate();
//                                                    projectFlag = 1;
//                                                }
//
//                                            } else {
//                                                projFrag.save();
//                                                projectFlag = 0;
//
//                                            }
//                                        }
//                                    }
//
//                                    if (personal_success && project_success) {
//                                        Toast.makeText(getApplicationContext(), "Successfully Saved..!", Toast.LENGTH_SHORT).show();
//                                        setResult(MainActivity.STUDENT_DATA_CHANGE_RESULT_CODE);
//
//                                        EditProfile.super.onBackPressed();
//                                    }
//
//                                }
//                            })
//
//                    .setNegativeButton("Discard", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//
//                            dialog.cancel();
//                            EditProfile.super.onBackPressed();
//                        }
//                    });
//
//            final AlertDialog alertDialog = alertDialogBuilder.create();
//
//            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//                @Override
//                public void onShow(DialogInterface dialogInterface) {
//                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#00bcd4"));
//                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#00bcd4"));
//                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTypeface(Z.getBold(EditProfile.this));
//                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTypeface(Z.getBold(EditProfile.this));
//
//                }
//            });
//
//            alertDialog.show();
//
//        } else
//            EditProfile.super.onBackPressed();
//
//    }

}
