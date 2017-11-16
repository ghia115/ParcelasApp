package com.example.luis.parcelasapp.petitions;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.luis.parcelasapp.modelo.DdsBalance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Luis on 14/11/2017.
 */

public class dataDdsBalance extends AppCompatActivity {
    public Context context;
    private RequestQueue queue ;

    public dataDdsBalance (Context context){
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public ArrayList<DdsBalance> data (){
        final ArrayList<DdsBalance> result = new ArrayList<>();


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://172.16.1.11/app/api/GraficaRiego",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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
        queue.add(jsonArrayRequest);

        return result;
    }

    private final DdsBalance dataBalance(JSONObject obj) throws JSONException {
        double balance = obj.getDouble("Balance");
        int dds = obj.getInt("Dds");

        return new DdsBalance(balance, dds);
    }
}
