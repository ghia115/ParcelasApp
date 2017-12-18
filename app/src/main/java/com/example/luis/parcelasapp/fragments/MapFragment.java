package com.example.luis.parcelasapp.fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.luis.parcelasapp.MainActivity;
import com.example.luis.parcelasapp.R;
import com.example.luis.parcelasapp.SendData;
import com.example.luis.parcelasapp.modelo.DdsBalance;
import com.example.luis.parcelasapp.modelo.MresumenRiego;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DatePickerDialog.OnDateSetListener dateSetListenerFinal;
    SendData sendData;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        final TextView fechaInicio = (TextView) v.findViewById(R.id.dateInicio);
        final TextView fechaFinal = (TextView) v.findViewById(R.id.dateFinal);
        ImageButton consultar = (ImageButton) v.findViewById(R.id.consultar);

        fechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        fechaFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListenerFinal,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                fechaInicio.setText(date);
            }
        };

        dateSetListenerFinal = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                fechaFinal.setText(date);
            }
        };

        final ArrayList<MresumenRiego> result = new ArrayList<>();

        final RequestQueue queue = Volley.newRequestQueue(getActivity());

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        Request.Method.GET,
                        "http://172.16.1.11/app/api/RiegoChihuahua?est=19&fechaIni=" + fechaInicio.getText() + "&fechaFin=" + fechaFinal.getText() + "&opc=1&riego=1&asiento=1",
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                //Log.d("JSON", "Respuesta" + response);

                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        result.add(resumenRiego(response.getJSONObject(i)));
                                    }
                                    catch (JSONException e) {
                                    }
                                }
                                Toast.makeText(getContext(), result.get(1).getCondicion(), Toast.LENGTH_LONG).show();


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

                //sendData.send(result);
            }
        });


        return v;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            sendData = (SendData) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Need implement");
        }
    }


    private final MresumenRiego resumenRiego(JSONObject obj) throws JSONException {
        String condicion = obj.getString("Condicion");
        String fecha = obj.getString("Fecha");
        double lb = obj.getDouble("Lb");
        double tr = obj.getDouble("Tr");

        return new MresumenRiego(condicion, fecha, lb, tr);
    }

}
