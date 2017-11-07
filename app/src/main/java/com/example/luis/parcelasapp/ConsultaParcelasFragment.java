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
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultaParcelasFragment extends Fragment {

    //ListView mLeadsList;
    //ArrayAdapter<String> mLeadsAdapter;
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
        arrayDatos = new ArrayList<DdsBalance>();

        arrayDatos = new ArrayList<DdsBalance>();
        DdsBalance articulo = new DdsBalance(12.3, 1);
        arrayDatos.add(articulo);
        articulo = new DdsBalance(13, 2);
        arrayDatos.add(articulo);
        articulo = new DdsBalance(14.4, 3);
        arrayDatos.add(articulo);
        articulo = new DdsBalance(15.12, 4);
        arrayDatos.add(articulo);
        articulo = new DdsBalance(9.45, 5);
        arrayDatos.add(articulo);

        adapter = new AdapterTabla(getActivity().getBaseContext(),arrayDatos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getBaseContext(),arrayDatos.get(position).getDds(),Toast.LENGTH_SHORT).show();
            }
        });

        /*mLeadsList = (ListView) v.findViewById(R.id.leads_list);

        String[] leadsNames = {
                "Alexander Pierrot",
                "Carlos Lopez",
                "Sara Bonz",
                "Liliana Clarence",
                "Benito Peralta",
                "Juan Jaramillo",
                "Christian Steps",
                "Alexa Giraldo",
                "Linda Murillo",
                "Lizeth Astrada"
        };

        mLeadsAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                leadsNames);

        mLeadsList.setAdapter(mLeadsAdapter);*/

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

}
