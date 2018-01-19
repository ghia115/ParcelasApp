package com.example.luis.parcelasapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.luis.parcelasapp.modelo.DdsBalance;
import com.example.luis.parcelasapp.modelo.MresumenRiego;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by mario on 17/01/18.
 */

public class GetApi {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "http://172.16.1.180/app/api/GraficaRiego?est=19&fechaIni=1/04/2017&fechaFin=10/07/2017&opc=1&riego=1&asiento=2";

    public ArrayList<MresumenRiego> apiRiego(Context context){
        final ArrayList<MresumenRiego> riego = new ArrayList<>();
        final RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://172.16.1.180/app/api/GraficaRiego?est=19&fechaIni=1/04/2017&fechaFin=10/07/2017&opc=1&riego=1&asiento=2",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d("JSON", "Respuesta" + response);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                riego.add(resumenRiego(response.getJSONObject(i)));
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
        queue.add(jsonArrayRequest);

        return riego;
    }

    private final MresumenRiego resumenRiego(JSONObject obj) throws JSONException {
        String condicion = obj.getString("Condicion");
        String fecha = obj.getString("Fecha");
        double lb = obj.getDouble("Lb");
        double tr = obj.getDouble("Tr");

        return new MresumenRiego(condicion, fecha, lb, tr);
    }

    public ArrayList<DdsBalance> apiGrfica(Context context){
        final ArrayList<DdsBalance> riego = new ArrayList<>();
        final RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://172.16.1.180/app/api/GraficaRiego?est=19&fechaIni=1/04/2017&fechaFin=10/07/2017&opc=1&riego=1&asiento=2",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d("JSON", "Respuesta" + response);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                riego.add(dataBalance(response.getJSONObject(i)));
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
        queue.add(jsonArrayRequest);

        return riego;
    }

    private final DdsBalance dataBalance(JSONObject obj) throws JSONException {
        double balance = obj.getDouble("Balance");
        int dds = obj.getInt("Dds");

        return new DdsBalance(balance, dds);
    }

    //https://www.journaldev.com/7148/java-httpurlconnection-example-java-http-request-get-post
    public static void sendGET() throws IOException {
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }

    }

}
