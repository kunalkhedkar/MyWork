package com.example.kunal.dialogwithalertdialog_builder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by KunaL on 06-Jul-17.
 */
public class Mydialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

/*
        builder.setTitle("Warning..");
        builder.setMessage("turn off all application , phone battery running low");
*/


        builder.setItems(R.array.days, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "item selected "+i , Toast.LENGTH_SHORT).show();
            }
        });
        Dialog dialog=builder.create();
        return dialog;


    }
}
