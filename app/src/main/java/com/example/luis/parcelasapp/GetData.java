package com.example.luis.parcelasapp;

import android.os.AsyncTask;

import com.example.luis.parcelasapp.modelo.DdsBalance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 29/01/18.
 */

public class GetData {


    public List<Mriego> DataRiego() {
        final List<Mriego> ListMriego = new ArrayList<Mriego>();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("http://172.16.1.180/app/api/RiegoChihuahua?est=19&fechaIni=1/04/2017&fechaFin=20/05/2017&opc=1&riego=1&asiento=1");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept", "application/json");

                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream())));

                    String output;
                    //System.out.println("Output from Server .... \n");
                    while ((output = br.readLine()) != null) {

                        JSONArray jsonArr = new JSONArray(output);
                        for (int i = 0; i < jsonArr.length(); i++)
                        {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);
                            System.out.println(jsonArr.getJSONObject(i));
                            //JSONObject jsonObj = jsonArr.getJSONObject(i);
                            String fecha = jsonObj.getString("Fecha");
                            Double Lb = jsonObj.getDouble("Lb");
                            Double Tr =  jsonObj.getDouble("Tr");
                            String Condicion = jsonObj.getString("Condicion");



                            ListMriego.add(new Mriego(fecha,Lb,Tr,Condicion));

                        }

                    }

                    conn.disconnect();

                } catch (MalformedURLException e) {

                    e.printStackTrace();

                } catch (IOException e) {

                    e.printStackTrace();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



        return ListMriego;
    }

    public List<DdsBalance> DataGrafica() {
        final List<DdsBalance> ListDdsBalance = new ArrayList<DdsBalance>();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("http://172.16.1.180/app/api/GraficaRiego?est=19&fechaIni=1/04/2017&fechaFin=10/07/2017&opc=1&riego=1&asiento=2");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept", "application/json");

                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream())));

                    String output;
                    //System.out.println("Output from Server .... \n");
                    while ((output = br.readLine()) != null) {

                        JSONArray jsonArr = new JSONArray(output);
                        for (int i = 0; i < jsonArr.length(); i++)
                        {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);
                            System.out.println(jsonArr.getJSONObject(i));
                            //JSONObject jsonObj = jsonArr.getJSONObject(i);
                            Double balance = jsonObj.getDouble("balance");
                            int dds = jsonObj.getInt("dds");


                            ListDdsBalance.add(new DdsBalance(balance, dds));

                        }

                    }

                    conn.disconnect();

                } catch (MalformedURLException e) {

                    e.printStackTrace();

                } catch (IOException e) {

                    e.printStackTrace();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



        return ListDdsBalance;
    }

}
