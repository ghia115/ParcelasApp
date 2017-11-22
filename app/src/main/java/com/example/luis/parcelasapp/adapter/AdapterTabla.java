package com.example.luis.parcelasapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.luis.parcelasapp.R;
import com.example.luis.parcelasapp.modelo.DdsBalance;

import java.util.ArrayList;

/**
 * Created by Luis on 07/11/2017.
 */

public class AdapterTabla extends BaseAdapter{
    ArrayList<DdsBalance> itemList = new ArrayList<DdsBalance>();
    Context context;

    public AdapterTabla(Context c, ArrayList<DdsBalance> arrayDatos) {
        context = c;
        itemList = arrayDatos;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.items_table, parent, false);
        TextView dds = (TextView) itemView.findViewById(R.id.dds);
        TextView balance = (TextView) itemView.findViewById(R.id.balance);

        dds.setText("" + itemList.get(position).getDds());
        balance.setText("" + itemList.get(position).getBalance());

        if (position%2==0){
            itemView.setBackgroundColor(Color.argb(255,248,248,248));
        }
        else {
            itemView.setBackgroundColor(Color.argb(255,255,255,255));
        }


        return itemView;
    }
}
