package com.ibero.tutorialyoutube;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

public class DataList extends AppCompatActivity {

    private TableLayout tabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);

        setTitle("Datos Registrados");
        tabla=(TableLayout) findViewById(R.id.tblayout);
        cargarDatos();
    }

    public void cargarDatos(){
        /*instanciamos la variables de dbhelper y
        le pasamos el contexto , nombre de base de datos*/
        DBHelper admin = new DBHelper(this, "DBIBERO", null, 1);
        /*abrimos la base de datos pora escritura*/
        SQLiteDatabase db = admin.getWritableDatabase();
        /*iniciamos la query en el cursor y indicamos el codigo digitado*/
        Cursor fila = db.rawQuery("select codigo,descripcion,precio  from articulos", null);
        fila.moveToFirst();
        /*hacemos un bucle while para la busqueda de datos */
        while(!fila.isAfterLast()){
           View layout = LayoutInflater.from(this).inflate(R.layout.item_data,null,false);
           TextView codigo= (TextView)layout.findViewById(R.id.tvcodigo);
           TextView descripcion= (TextView)layout.findViewById(R.id.tvdescripcion);
           TextView precio= (TextView)layout.findViewById(R.id.tvprecio);

           codigo.setText(fila.getString(0));
           descripcion.setText(fila.getString(1));
           precio.setText(fila.getString(2));

            tabla.addView(layout);
            /*movemos el cursor a la siguiente linea*/
            fila.moveToNext();
        }
    }
}