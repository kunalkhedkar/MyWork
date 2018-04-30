package com.example.kunal.databasewithroom;

/**
 * Created by KunaL on 28-Apr-18.
 */

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KunaL on 11-Apr-18.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    List<ModelUserContact> list=new ArrayList<>();

    public Adapter(List<ModelUserContact> list) {
        this.list = list;
        Log.d("TAG", "Adapter: list size "+list.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_single_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "getItemCount: list size "+list.size());
        return list.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ModelUserContact modelUserContact=list.get(position);
//
//        Log.d("TAG", "onBindViewHolder: name "+user.getName());
//        Log.d("TAG", "onBindViewHolder: phone "+user.getPhone());
//        Log.d("TAG", "onBindViewHolder: id "+user.getId());

//        String id=String.valueOf(user.getId());

        holder.name.setText(modelUserContact.getName());
        holder.phone.setText(modelUserContact.getPhone());
        holder.mobile.setText(modelUserContact.getMobile());
        holder.landline.setText(modelUserContact.getLandLine());
        holder.parentMobile.setText(modelUserContact.getParentMobile());

    }





    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,phone,mobile,landline,parentMobile;

        public MyViewHolder(View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            phone=itemView.findViewById(R.id.phone);
            mobile=itemView.findViewById(R.id.mobile);
            landline=itemView.findViewById(R.id.landline);
            parentMobile=itemView.findViewById(R.id.parentMobile);

        }
    }


}