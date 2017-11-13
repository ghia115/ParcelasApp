package com.example.luis.parcelasapp.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.luis.parcelasapp.R;
import com.example.luis.parcelasapp.modelo.DdsBalance;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class chartFragment extends Fragment {


    public chartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chart, container, false);

        final ArrayList<DdsBalance> result = new ArrayList<>();

        final RequestQueue queue = Volley.newRequestQueue(getActivity());

        final GraphView graph = (GraphView) v.findViewById(R.id.graphic);

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
                        System.out.println(result);
                        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                        for (int i=0; i<result.size(); i++) {
                            DataPoint point = new DataPoint(result.get(i).getDds(), result.get(i).getBalance());
                            series.appendData(point, true, result.size());
                        }

                        graph.addSeries(series);
                        graph.setBackgroundColor(Color.argb(50, 160, 160, 160));
                        series.setColor(Color.CYAN);
                        series.setAnimated(true);
                        series.setDrawDataPoints(true);
                        series.setDataPointsRadius(5);
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

        //GraphView graph = (GraphView) v.findViewById(R.id.graphic);
        /*LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });*/

        /*LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for (int i=0; i<result.size(); i++) {
            DataPoint point = new DataPoint(result.get(i).getDds(), result.get(i).getBalance());
            series.appendData(point, true, result.size());
        }

        graph.addSeries(series);*/

        return v;
    }

    private final DdsBalance dataBalance(JSONObject obj) throws JSONException {
        double balance = obj.getDouble("Balance");
        int dds = obj.getInt("Dds");

        return new DdsBalance(balance, dds);
    }
}
