package com.example.luis.parcelasapp;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by mario on 23/01/18.
 */

public class NetworkManager {
    public static NetworkManager networkManager;
    public RequestQueue requestQueue;
    public static Context context;

    public NetworkManager(Context context){
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue = new Volley().newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }

    public static synchronized NetworkManager getInstance(Context context){
        if(networkManager==null){
            networkManager =new NetworkManager(context);
        }

        return networkManager;
    }

    public<T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }
}
