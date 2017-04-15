package com.example.asztalos.szakdolgozat;

import android.app.Activity;
import android.graphics.LightingColorFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> content;

    public CustomList(Activity context, List<String> cont) {
        super(context, R.layout.list_item,cont);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.content = cont;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null, true);


        RelativeLayout row = (RelativeLayout) rowView.findViewById(R.id.row);
        TextView modelTitle = (TextView) rowView.findViewById(R.id.item);
        TextView licence_plate = (TextView) rowView.findViewById(R.id.textView1);
        ImageView img = (ImageView)rowView.findViewById(R.id.check);

        String[] splitt = content.get(position).trim().split("\\|");//adott sor
        modelTitle.setText(splitt[0]+" "+splitt[1]);
        licence_plate.setText(splitt[2]);

        return rowView;

    }
}


