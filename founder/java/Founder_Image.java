package com.example.silicon.founder_name;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Founder_Image extends Fragment {

    ImageView image1;
    View rootView;
    public static Bitmap map;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_founder__image, container, false);

        image1=rootView.findViewById(R.id.image1);
        return rootView;
    }

    public void showImage(String lang_name) {
        TaskOperation t=new TaskOperation(getActivity());
        t.open();
        map=t.getImageBitmapFromDB(lang_name);
        image1.setImageBitmap(map);
    }
}
