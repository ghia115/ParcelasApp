package com.example.luis.parcelasapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.luis.parcelasapp.R;
import com.example.luis.parcelasapp.adapter.AdapterTabla;
import com.example.luis.parcelasapp.modelo.DdsBalance;
import com.example.luis.parcelasapp.modelo.MresumenRiego;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultaParcelasFragment extends Fragment {

    ListView listView;
    //ArrayList<DdsBalance> arrayDatos;
    AdapterTabla adapter;


    public ConsultaParcelasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_consulta_parcelas, container, false);

        final ArrayList<DdsBalance> result = new ArrayList<>();

        final RequestQueue queue = Volley.newRequestQueue(getActivity());

        listView = (ListView) v.findViewById(R.id.leads_list);

        ArrayList<MresumenRiego> myList = (ArrayList<MresumenRiego>) getActivity().getIntent().getSerializableExtra("mylist");

        //dataDdsBalance db = new dataDdsBalance(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://172.16.1.11/app/api/GraficaRiego",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d("JSON", "Respuesta" + response);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                result.add(dataBalance(response.getJSONObject(i)));
                            }
                            catch (JSONException e) {
                            }
                        }
                        adapter = new AdapterTabla(getActivity().getBaseContext(),result);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(getActivity().getApplicationContext(),result.get(position).getDds(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "Respuesta en JSON" + error.getMessage());
                    }
                }
        );
        queue.add(jsonArrayRequest);

        /*arrayDatos = db.data();

        adapter = new AdapterTabla(getActivity().getBaseContext(),arrayDatos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(),arrayDatos.get(position).getDds(),Toast.LENGTH_SHORT).show();
            }
        });*/

        return v;
    }

    public void getData(ArrayList<MresumenRiego> result) {
        ArrayList<MresumenRiego> resultNew = result;
    }

    private final DdsBalance dataBalance(JSONObject obj) throws JSONException {
        double balance = obj.getDouble("Balance");
        int dds = obj.getInt("Dds");

        return new DdsBalance(balance, dds);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

}
