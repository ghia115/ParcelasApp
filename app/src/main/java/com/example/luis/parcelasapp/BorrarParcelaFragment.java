package com.example.luis.parcelasapp;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
public class BorrarParcelaFragment extends Fragment {

    public BorrarParcelaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_borrar_parcela, container, false);

        final EditText edt1 = (EditText) v.findViewById(R.id.buscarEliminar);
        final TextView textoParcela = (TextView) v.findViewById(R.id.TextViewTitle);
        final TextView textoLocalidad = (TextView) v.findViewById(R.id.TextViewTitle2);

        Button buttn = (Button) v.findViewById(R.id.botonEliminar);
        ImageButton buttn2 = (ImageButton) v.findViewById(R.id.buscarCampo);

        final BBDD_Helper helper = new BBDD_Helper(this.getContext());

        buttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = helper.getWritableDatabase();
                // Define 'where' part of query.
                String selection = Estructura_BBDD.NOMBRE_COLUMNA1 + " LIKE ?";
                // Specify arguments in placeholder order.
                String[] selectionArgs = { edt1.getText().toString() };
                // Issue SQL statement.
                db.delete(Estructura_BBDD.TABLE_NAME, selection, selectionArgs);

                Toast.makeText(getActivity().getApplicationContext(), "Se borro el registro numero: " +
                        edt1.getText().toString(), Toast.LENGTH_LONG).show();

                edt1.setText("");
                textoParcela.setText("");
                textoLocalidad.setText("");

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
                String[] selectionArgs = { edt1.getText().toString() };

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

                    textoParcela.setText(c.getString(0));
                    textoLocalidad.setText(c.getString(1));

                }
                catch (Exception e){
                    Toast.makeText(getActivity().getApplicationContext(), "No se encontro registro.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }

}
