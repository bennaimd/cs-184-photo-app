package edu.cs.cs184.photoapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FeatureDialog extends DialogFragment {

    TableLayout tl;
    LayoutInflater inflater;
    ViewGroup container;

    ArrayList<String> features;
    ArrayList<Double> certainties;

    DialogFragment dialog;

    public FeatureDialog() {

        // Empty constructor required for DialogFragment

    }



    public static FeatureDialog newInstance(String title) {

        FeatureDialog frag = new FeatureDialog();

        Bundle args = new Bundle();

        args.putString("title", title);

        frag.setArguments(args);

        return frag;

    }





    @Override

    public View onCreateView(LayoutInflater inInflater, ViewGroup inContainer,

                             Bundle savedInstanceState) {

        inflater = inInflater;
        container = inContainer;
        dialog = this;

        return inflater.inflate(R.layout.feature_dialog, container);

    }



    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // Get field from view
        tl = view.findViewById(R.id.featTable);

        buildTable();

        Button button = view.findViewById(R.id.continueButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        // Fetch arguments from bundle and set title

        String title = getArguments().getString("title", "Enter Name");

        getDialog().setTitle(title);



    }


    public void buildTable(){
        if(features != null) {
            for (int i = 0; i < features.size(); i++) {
                addRow(features.get(i), certainties.get(i));
            }
        }
        else{
            dialog.dismiss();
        }

    }

    public void addData(ArrayList<String> inFeatures, ArrayList<Double> inCertainties){
        features = inFeatures;
        certainties = inCertainties;
    }

    public void addRow(String feat, Double cert) {
        TableRow row = (TableRow)inflater.inflate(R.layout.feature_row, container);

        TextView tv1 = row.findViewById(R.id.featTitle);
        tv1.setText(feat);

        TextView tv2 = row.findViewById(R.id.certTitle);
        DecimalFormat df = new DecimalFormat("0.00");
        tv2.setText(df.format(cert)+"%");

        Log.d("feature dialog", "Adding Row: ("+feat+", "+cert+")");

        tl.addView(row);


    }



}
