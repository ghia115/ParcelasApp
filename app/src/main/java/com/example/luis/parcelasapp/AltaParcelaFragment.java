package com.example.luis.parcelasapp;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.content.Context;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class AltaParcelaFragment extends Fragment implements OnClickListener {

    TextView editarLatitud, editarLongitud;
    FloatingActionButton location;
    LocationManager mlocManager;
    AlertDialog alert = null;

    public AltaParcelaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_alta_parcela, container, false);
        View rootView = inflater.inflate(R.layout.fragment_alta_parcela, container, false);

        //Hacer un cast de un objeto de la vista en el controlador.
        final EditText edt1 = (EditText) rootView.findViewById(R.id.campoParcela);
        final EditText edt2 = (EditText) rootView.findViewById(R.id.campoLocalidad);

        ImageButton buttn = (ImageButton) rootView.findViewById(R.id.altaParcela);

        editarLatitud = (TextView) rootView.findViewById(R.id.editarLatitud);
        editarLongitud = (TextView) rootView.findViewById(R.id.editarLongitud);
        location = (FloatingActionButton) rootView.findViewById(R.id.location);
        location.setOnClickListener(this);

        final BBDD_Helper helper = new BBDD_Helper(this.getContext());

        buttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gets the data repository in write mode
                SQLiteDatabase db = helper.getWritableDatabase();

                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                //values.put(Estructura_BBDD.ID, textoId.getText().toString());
                values.put(Estructura_BBDD.NOMBRE_COLUMNA1, edt1.getText().toString());
                values.put(Estructura_BBDD.NOMBRE_COLUMNA2, edt2.getText().toString());
                values.put(Estructura_BBDD.LATITUD, editarLatitud.getText().toString());
                values.put(Estructura_BBDD.LONGITUD, editarLongitud.getText().toString());

                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(Estructura_BBDD.TABLE_NAME, null, values);

                Toast.makeText(getActivity().getBaseContext(), "Se guardo el registro con clave: " +
                        newRowId, Toast.LENGTH_LONG).show();

                edt1.setText("");
                edt2.setText("");
                editarLatitud.setText("");
                editarLongitud.setText("");
            }
        });
        /*buttn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Recoger el texto de un EditText
                String text1 = edt1.getText().toString();
                String text2 = edt2.getText().toString();

                //Mostrar texto en el log de la consola
                //Log.d("com.duhnnae.test2",text1 + text2);

                //Muestra un toast en pantalla
                Toast.makeText(getActivity().getBaseContext(),
                        text1 + text2, Toast.LENGTH_LONG).show();

            }
        });*/


        return rootView;
    }

    @Override
    public void onClick(View v) {
        mlocManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener mlocListener = new MyLocationListener();
        mlocManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener);

        Location lastLocation = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (lastLocation == null) {
            //Toast.makeText(getApplicationContext(), "Enciende el GPS", Toast.LENGTH_LONG).show();
            if (!mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                AlertNoGps();
            }
        }else {
            String latitudeInfo = "" + lastLocation.getLatitude();
            String longitudeInfo = "" + lastLocation.getLongitude();

            editarLatitud.setText(latitudeInfo);
            editarLongitud.setText(longitudeInfo);

        }
    }

    public class MyLocationListener implements LocationListener{
        @Override

        public void onLocationChanged(Location loc){

            String latitudeInfo = "" + loc.getLatitude();
            String longitudeInfo = "" + loc.getLongitude();

            editarLatitud.setText(latitudeInfo);
            editarLongitud.setText(longitudeInfo);

            /*String Text = "location: " +
                    "Latitud = " + loc.getLatitude() +
                    "Longitud = " + loc.getLongitude();

            tv.setText(Text);*/

        }
        public void onProviderDisabled(String provider){

            //nothin
        }


        public void onProviderEnabled(String provider){

            //nothin
        }

        public void onStatusChanged(String provider, int status, Bundle extras){
            //nothin
        }
    }/* End of Class MyLocationListener */

    private void AlertNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }

}
