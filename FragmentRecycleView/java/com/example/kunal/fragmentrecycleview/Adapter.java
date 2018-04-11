package com.example.kunal.fragmentrecycleview;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KunaL on 11-Apr-18.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    ArrayList<String> list=new ArrayList<>();

    public Adapter(ArrayList<String> list) {
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

        Log.d("TAG", "onBindViewHolder: "+position);


//        String uri = "@mipmap/ic_launcher";  // where myresource (without the extension) is the file
//
//        int imageResource = holder.imageView.getContext().getResources().getIdentifier(uri, null, holder.imageView.getContext().getPackageName());
//        Drawable res = holder.imageView.getContext().getResources().getDrawable(imageResource);
//
//        holder.imageView.setImageDrawable(res);
        holder.textView.setText(list.get(position));

    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView=(ImageView) itemView.findViewById(R.id.img);
            textView=(TextView) itemView.findViewById(R.id.name);

        }
    }


}
