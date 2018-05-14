package com.example.silicon.founder_name;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class Founder_Name extends Fragment {
     RecyclerView recyclerView;
    View rootView;
    ArrayList<Info_Modal> itemlist = new ArrayList<>();
    RecyclerAdapter recyclerAdapter;
    public static int position;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_founder__name, container, false);

        recyclerView = rootView.findViewById(R.id.my_recycler_view);



        recyclerAdapter = new RecyclerAdapter(itemlist,this.getActivity());
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManagerPlacement = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManagerPlacement);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Founder_Image fragment = (Founder_Image)getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container2);

                fragment.showImage(itemlist.get(position).getLang_Name());

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        //get from db and populate

        TaskOperation t=new TaskOperation(getActivity());
        t.open();
        itemlist.addAll(t.GetTaskList());
        Log.d("TAG", "refresh size: "+itemlist.size());
        recyclerAdapter.notifyDataSetChanged();


        return rootView;
    }


    public void refresh(ArrayList<Info_Modal> itemlistparam) {

        itemlist.clear();
        itemlist.addAll(itemlistparam);

        Log.d("TAG", "refresh size: "+itemlist.size());

        recyclerAdapter.notifyDataSetChanged();

    }
}
