package com.example.luis.parcelasapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.luis.parcelasapp.adapter.AdapterTabla;
import com.example.luis.parcelasapp.modelo.DdsBalance;
import com.example.luis.parcelasapp.petitions.dataDdsBalance;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultaParcelasFragment extends Fragment {

    ListView listView;
    ArrayList<DdsBalance> arrayDatos;
    AdapterTabla adapter;


    public ConsultaParcelasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_consulta_parcelas, container, false);

        listView = (ListView) v.findViewById(R.id.leads_list);

        dataDdsBalance db = new dataDdsBalance(getContext());
        arrayDatos = db.data();

        adapter = new AdapterTabla(getActivity().getBaseContext(),arrayDatos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(),arrayDatos.get(position).getDds(),Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

}
