package com.makienkovs.creditcalc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    private ArrayList<Note> notes;
    private Context c;

    public Adapter(ArrayList<Note> notes, Context c) {
        this.notes = notes;
        this.c = c;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(c).inflate(R.layout.note, null);
        
        fillView(convertView, position);

        return convertView;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void fillView(View v, int position) {
        final Note n = (Note) getItem(position);
        TextView number = v.findViewById(R.id.number);
        number.setText(n.getNumberString());
        TextView date = v.findViewById(R.id.date);
        date.setText(n.getDate());
        TextView amount = v.findViewById(R.id.amount);
        amount.setText(n.getAmountString());
        TextView main = v.findViewById(R.id.main);
        main.setText(n.getMainString());
        TextView percent = v.findViewById(R.id.percent);
        percent.setText(n.getPercentString());
        TextView remains = v.findViewById(R.id.remains);
        remains.setText(n.getRemainsString());
    }
}
