package com.example.silicon.founder_name;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.silicon.founder_name.Info_Modal;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Info_Modal> infoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView lang_name, fname;

        public MyViewHolder(View view) {
            super(view);
            lang_name = (TextView) view.findViewById(R.id.lang_name);
            fname = (TextView) view.findViewById(R.id.fname);
        }
    }


    public RecyclerAdapter(List<Info_Modal> infoList, Context context)
    {
        this.infoList = infoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.details_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Info_Modal info_modal = infoList.get(position);
        holder.lang_name.setText(info_modal.getLang_Name());
        holder.fname.setText(info_modal.getFounder_Name());


    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }
}