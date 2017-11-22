package com.example.luis.parcelasapp;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditarParcelaFragment extends Fragment implements View.OnClickListener {

    TextView editarLatitud, editarLongitud;
    FloatingActionButton location;
    LocationManager mlocManager;
    AlertDialog alert = null;

    public EditarParcelaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_editar_parcela, container, false);

        final EditText edt1 = (EditText) v.findViewById(R.id.editarParcela);
        final EditText edt2 = (EditText) v.findViewById(R.id.editarLocalidad);
        final EditText edt3 = (EditText) v.findViewById(R.id.buscar);

        Button buttn = (Button) v.findViewById(R.id.botonEditar);

        editarLatitud = (TextView) v.findViewById(R.id.editLatitud);
        editarLongitud = (TextView) v.findViewById(R.id.editLongitud);
        location = (FloatingActionButton) v.findViewById(R.id.editLocation);
        location.setOnClickListener(this);

        ImageButton buttn2 = (ImageButton) v.findViewById(R.id.buscarCampo);

        final BBDD_Helper helper = new BBDD_Helper(this.getContext());

        buttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getReadableDatabase();

                // New value for one column
                ContentValues values = new ContentValues();
                values.put(Estructura_BBDD.NOMBRE_COLUMNA1, edt1.getText().toString());
                values.put(Estructura_BBDD.NOMBRE_COLUMNA2, edt2.getText().toString());
                values.put(Estructura_BBDD.LATITUD, editarLatitud.getText().toString());
                values.put(Estructura_BBDD.LONGITUD, editarLongitud.getText().toString());

                // Which row to update, based on the title
                String selection = Estructura_BBDD.NOMBRE_COLUMNA1 + " LIKE ?";
                String[] selectionArgs = { edt3.getText().toString() };

                int count = db.update(
                        Estructura_BBDD.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);

                Toast.makeText(getActivity().getApplicationContext(), "Se actualizo el registro.", Toast.LENGTH_LONG).show();

                edt1.setText("");
                edt2.setText("");
                editarLatitud.setText("");
                editarLongitud.setText("");
            }
        });

        buttn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getReadableDatabase();

                // Define a projection that specifies which columns from the database
                // you will actually use after this query.
                String[] projection = {
                        Estructura_BBDD.NOMBRE_COLUMNA1,
                        Estructura_BBDD.NOMBRE_COLUMNA2
                };

                // Filter results WHERE "title" = 'My Title'
                String selection = Estructura_BBDD.NOMBRE_COLUMNA1 + " = ?";
                String[] selectionArgs = { edt3.getText().toString() };

                // How you want the results sorted in the resulting Cursor
                /*String sortOrder =
                        Estructura_BBDD.ID + " DESC";*/
                try {
                    Cursor c = db.query(
                            Estructura_BBDD.TABLE_NAME,                     // The table to query
                            projection,                               // The columns to return
                            selection,                                // The columns for the WHERE clause
                            selectionArgs,                            // The values for the WHERE clause
                            null,                                     // don't group the rows
                            null,                                     // don't filter by row groups
                            null                                 // The sort order
                    );
                    c.moveToFirst();

                    edt1.setText(c.getString(0));
                    edt2.setText(c.getString(1));

                }
                catch (Exception e){
                    Toast.makeText(getActivity().getApplicationContext(), "No se encontro registro.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        mlocManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener mlocListener = new EditarParcelaFragment.MyLocationListener();
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
