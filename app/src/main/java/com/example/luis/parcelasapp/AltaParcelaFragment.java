package com.example.luis.parcelasapp;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AltaParcelaFragment extends Fragment {

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

        Button buttn = (Button) rootView.findViewById(R.id.altaParcela);

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

                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(Estructura_BBDD.TABLE_NAME, null, values);

                Toast.makeText(getActivity().getBaseContext(), "Se guardo el registro con clave: " +
                        newRowId, Toast.LENGTH_LONG).show();

                edt1.setText("");
                edt2.setText("");
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

}
