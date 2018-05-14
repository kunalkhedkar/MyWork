package com.example.admin.clientvisit.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.clientvisit.R;
import com.example.admin.clientvisit.model.ClientData;

import java.util.ArrayList;
import java.util.List;

import static com.example.admin.clientvisit.database.DbUtil.buildOwnerNameStringFromList;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.MyViewHolder> {


    List<ClientData> list = new ArrayList<>();

    public ClientAdapter(List<ClientData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_single_client_item_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.business_name.setText(list.get(position).getBusinessName());
        holder.address_area.setText(list.get(position).getAddressArea()+" "+list.get(position).getAddressPincode());
        holder.client_name.setText(buildOwnerNameStringFromList(list.get(position)));

        byte[] imageByte = list.get(position).getImage();
        if (imageByte != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            holder.image.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItems(List<ClientData> clientData) {
        this.list = clientData;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView business_name,address_area, client_name;

        public MyViewHolder(View itemView) {
            super(itemView);

            business_name = itemView.findViewById(R.id.business_name);
            address_area = itemView.findViewById(R.id.address_area);
            client_name = itemView.findViewById(R.id.client_name);
            image = itemView.findViewById(R.id.image);

        }
    }
}
