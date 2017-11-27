package com.example.luis.parcelasapp;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.content.Context;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AltaParcelaFragment extends Fragment implements OnClickListener ,Validator.ValidationListener {

    @NotEmpty
    private TextView edt1, edt2, editarLatitud, editarLongitud, arena, arcilla, materiaSeca;

    FloatingActionButton location;
    LocationManager mlocManager;
    AlertDialog alert = null;
    Spinner cultivos;

    public AltaParcelaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_alta_parcela, container, false);
        View rootView = inflater.inflate(R.layout.fragment_alta_parcela, container, false);

        final Validator validator = new Validator(this);
        validator.setValidationListener(this);

        ArrayList<String> comboCultivo = new ArrayList<>();
        comboCultivo.add("Cultivos");
        comboCultivo.add("Maíz");
        comboCultivo.add("Frijol");
        comboCultivo.add("Manzano");


        edt1 = (TextView) rootView.findViewById(R.id.campoParcela);
        edt2 = (TextView) rootView.findViewById(R.id.campoLocalidad);

        ImageButton buttn = (ImageButton) rootView.findViewById(R.id.altaParcela);

        cultivos  = (Spinner) rootView.findViewById(R.id.cultivos);
        ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter(
                getContext(),
                android.R.layout.simple_spinner_item,
                comboCultivo){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cultivos.setAdapter(arrayAdapter);

        editarLatitud = (TextView) rootView.findViewById(R.id.editarLatitud);
        editarLongitud = (TextView) rootView.findViewById(R.id.editarLongitud);
        //cultivo = (TextView) rootView.findViewById(R.id.cultivo);
        arena = (TextView) rootView.findViewById(R.id.arena);
        arcilla = (TextView) rootView.findViewById(R.id.arcilla);
        materiaSeca = (TextView) rootView.findViewById(R.id.materiaSeca);
        location = (FloatingActionButton) rootView.findViewById(R.id.location);
        location.setOnClickListener(this);

        buttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validator.validate();
                // Gets the data repository in write mode

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

    @Override
    public void onValidationSucceeded() {

        final BBDD_Helper helper = new BBDD_Helper(this.getContext());

        SQLiteDatabase db = helper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        //values.put(Estructura_BBDD.ID, textoId.getText().toString());
        values.put(Estructura_BBDD.NOMBRE_COLUMNA1, edt1.getText().toString());
        values.put(Estructura_BBDD.NOMBRE_COLUMNA2, edt2.getText().toString());
        values.put(Estructura_BBDD.LATITUD, editarLatitud.getText().toString());
        values.put(Estructura_BBDD.LONGITUD, editarLongitud.getText().toString());
        values.put(Estructura_BBDD.CULTIVO, cultivos.getSelectedItem().toString());
        values.put(Estructura_BBDD.ARENA, arena.getText().toString());
        values.put(Estructura_BBDD.ARCILLA, arcilla.getText().toString());
        values.put(Estructura_BBDD.MATERIA, materiaSeca.getText().toString());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Estructura_BBDD.TABLE_NAME, null, values);

        Toast.makeText(getActivity().getBaseContext(), "Se guardo el registro con clave: " +
                newRowId, Toast.LENGTH_LONG).show();

        edt1.setText("");
        edt2.setText("");
        editarLatitud.setText("");
        editarLongitud.setText("");
        cultivos.setSelection(0);
        arena.setText("");
        arcilla.setText("");
        materiaSeca.setText("");
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = "Todos los campos son obligatorios";

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
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
