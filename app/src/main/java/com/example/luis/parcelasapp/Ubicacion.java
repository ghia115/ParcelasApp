package com.example.luis.parcelasapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Luis on 27/09/2017.
 */

public class Ubicacion implements LocationListener {
    private Context ctx;
    LocationManager locationManager;
    String proveedor;
    private boolean networkOp;

    public Ubicacion(Context ctx) {
        this.ctx = ctx;
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        proveedor = LocationManager.NETWORK_PROVIDER;
        networkOp = locationManager.isProviderEnabled(proveedor);
        locationManager.requestLocationUpdates(proveedor,1000,1,this);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
