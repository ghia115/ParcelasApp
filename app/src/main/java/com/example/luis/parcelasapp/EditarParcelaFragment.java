package com.example.luis.parcelasapp;


import android.content.ContentValues;
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
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditarParcelaFragment extends Fragment {


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

}
