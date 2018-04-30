package com.example.kunal.databasewithroom;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterMarks extends RecyclerView.Adapter<AdapterMarks.MyViewHolder> {

    List<Mark> marksList;

    public AdapterMarks(List<Mark> marksList) {
        this.marksList = marksList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_single_item_mark,parent,false);

        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.subject.setText(marksList.get(position).getSubject());
        holder.mark.setText(String.valueOf(marksList.get(position).getPercentage()));
        
    }

    @Override
    public int getItemCount() {
        return marksList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView subject,mark;

        public MyViewHolder(View itemView) {
            super(itemView);

            subject=itemView.findViewById(R.id.subject);
            mark=itemView.findViewById(R.id.mark);

        }
    }


    //livedata
    public void addItems(List<Mark> marksList) {
        this.marksList = marksList;
        notifyDataSetChanged();
    }

}
