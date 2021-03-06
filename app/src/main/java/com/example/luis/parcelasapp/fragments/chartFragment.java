package com.example.luis.parcelasapp.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.luis.parcelasapp.GetApi;
import com.example.luis.parcelasapp.Mriego;
import com.example.luis.parcelasapp.NetworkManager;
import com.example.luis.parcelasapp.R;
import com.example.luis.parcelasapp.modelo.DdsBalance;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.luis.parcelasapp.GetData;
/**
 * A simple {@link Fragment} subclass.
 */
public class chartFragment extends Fragment {

    ArrayList<DdsBalance> result = new ArrayList<>();

    GetApi api = new GetApi();
    //GetData obj = new GetData();


    public chartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chart, container, false);

        final RequestQueue queue = Volley.newRequestQueue(getActivity());

        final GraphView graph = (GraphView) v.findViewById(R.id.graphic);

        //List<Mriego> result = obj.DataRiego();
        //List<DdsBalance> result = obj.DataGrafica();
        result = api.apiGrfica(getActivity().getApplicationContext());
        /*JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://172.16.1.180/app/api/GraficaRiego?est=19&fechaIni=1/04/2017&fechaFin=10/07/2017&opc=1&riego=1&asiento=2",
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

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "Respuesta en JSON" + error.getMessage());
                    }
                }
        );

        NetworkManager.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsonArrayRequest);*/

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

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getActivity(), "Dds y Balance: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

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
