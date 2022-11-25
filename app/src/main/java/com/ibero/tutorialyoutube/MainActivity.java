package com.ibero.tutorialyoutube;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText edt1,edt2,edt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt1=(EditText)findViewById(R.id.edtcodigo);
        edt2=(EditText)findViewById(R.id.edtdescripcion);
        edt3=(EditText)findViewById(R.id.edtprecio);

    }

    //metodo guardar los datos
    public void Guardar(View view) {
        /*instanciamos la variables de dbhelper y
        le pasamos el contexto , nombre de base de datos*/
        DBHelper admin = new DBHelper(this, "DBIBERO",null, 1);
        /*abrimos la base de datos pora escritura*/
        SQLiteDatabase db = admin.getWritableDatabase();
        /*capturamos los datos de los edittext*/
        String codigo = edt1.getText().toString();
        String desc   = edt2.getText().toString();
        String preci  = edt3.getText().toString();

        //insertamos en la db los datos capturados
        ContentValues datos = new ContentValues();
        datos.put("codigo", codigo);
        datos.put("descripcion", desc);
        datos.put("precio", preci);
        /*realizamos el insert en el resgitro*/
        db.insert("articulos", null, datos);
        /*cerramos la base de datos*/
        db.close();
        /*limpiamos las cajas de textos*/
        edt1.setText("");
        edt2.setText("");
        edt3.setText("");
        /*lanzamos una notificacion toast para saber que se cargaron los datos*/
        Toast.makeText(this, "se cargaron los datos ", Toast.LENGTH_SHORT).show();
    }

    //metodo consultar la base de datos
    public void BuscarCodigo(View v) {
        /*instanciamos la variables de dbhelper y
        le pasamos el contexto , nombre de base de datos*/
        DBHelper admin = new DBHelper(this, "DBIBERO", null, 1);
        /*abrimos la base de datos pora escritura*/
        SQLiteDatabase db = admin.getWritableDatabase();
        /*creamos una variables string y lo
        inicializamos con los datos ingresados en edittext*/
        String codigo = edt1.getText().toString();
        /*iniciamos la query en el cursor y indicamos el codigo digitado*/
        Cursor fila = db.rawQuery("select codigo,descripcion,precio  from articulos where codigo='"+codigo+"'", null);
        /*hacemos un if si hay datos que lea los datos del primer codigo*/
        if (fila.moveToFirst()) {
            edt2.setText(fila.getString(1));
            edt3.setText(fila.getString(2));
        }
        else
            /*si no existe entonces lanzara un toast*/
            Toast.makeText(this, "no existe un registro con dicho codigo",
                    Toast.LENGTH_SHORT).show();
        /*cerramos la base de datos*/
        db.close();
    }

    //metodo eliminar
    public void eliminarCodigo(View v) {
        /*instanciamos la variables de dbhelper y
        le pasamos el contexto , nombre de base de datos*/
        DBHelper admin = new DBHelper(this, "DBIBERO", null, 1);
        /*abrimos la base de datos pora escritura*/
        SQLiteDatabase db = admin.getWritableDatabase();
        /*creamos una variables string y lo
        inicializamos con los datos ingresados en edittext*/
        String codigo = edt1.getText().toString();
        /*variable int pasamos el metodo delete de la tabla registro el codigo que edittext*/
        int cant = db.delete("articulos", "codigo=" + codigo, null);
        /*cerramos la DB*/
        db.close();
        /*limpiamos las cajas de texto*/
        edt1.setText("");
        edt2.setText("");
        edt3.setText("");
        /*si cante es igual que unos entonces*/
        if (cant == 1)
            /*lanzamos un toast que notifique se elimino*/
            Toast.makeText(this, "se borró el registro con dicho codigo",
                    Toast.LENGTH_SHORT).show();
        else
            /*de lo contrario lanzara que esos datos no existen*/
            Toast.makeText(this, "no existe un registro con dicho codigo",
                    Toast.LENGTH_SHORT).show();
    }

    //metodo consultar la base de datos
    public void BuscarDescr(View v) {
        /*instanciamos la variables de dbhelper y
        le pasamos el contexto , nombre de base de datos*/
        DBHelper admin = new DBHelper(this, "DBIBERO", null, 1);
        /*abrimos la base de datos pora escritura*/
        SQLiteDatabase db = admin.getWritableDatabase();
        /*creamos una variables string y lo
        inicializamos con los datos ingresados en edittext*/
        String descr = edt2.getText().toString();
        /*iniciamos la query en el cursor y indicamos el codigo digitado*/
        Cursor fila = db.rawQuery("select codigo,precio  from articulos where descripcion='"+descr+"'", null);
        /*hacemos un if si hay datos que lea los datos del primer codigo*/
        if (fila.moveToFirst()) {
            edt1.setText(fila.getString(0));
            edt3.setText(fila.getString(1));
        }
        else
            /*si no existe entonces lanzara un toast*/
            Toast.makeText(this, "no existe un registro con la descripcion",
                    Toast.LENGTH_SHORT).show();
        /*cerramos la base de datos*/
        db.close();
    }

    //metodo modificar ````
    public void ActualizarCodigo(View v) {
        /*instanciamos la variables de dbhelper y
        le pasamos el contexto , nombre de base de datos*/
        DBHelper admin = new DBHelper(this, "DBIBERO", null, 1);
        /*abrimos la base de datos pora escritura*/
        SQLiteDatabase db = admin.getWritableDatabase();
        /*capturamos los datos de los edittext*/
        String codigo       = edt1.getText().toString();
        String descripcion  = edt2.getText().toString();
        String precio       = edt3.getText().toString();

        ContentValues datos = new ContentValues();
        datos.put("codigo", codigo);
        datos.put("descripcion", descripcion);
        datos.put("precio", precio);
        /*lanzamos el metodo update con el query para que actualice segun el id o codigo*/
        int cant = db.update("articulos", datos, "codigo=" + codigo, null);
        /*cerramos la BD*/
        db.close();
        /*limpiamos la caja de texto*/
        edt1.setText("");
        edt2.setText("");
        edt3.setText("");
        /*si cante es igual que unos entonces*/
        if (cant == 1)
            /*si se cumple lka sentencia entonces muestra el toast*/
            Toast.makeText(this,"se modificaron los datos",Toast.LENGTH_SHORT)
                    .show();
        else
            /*de lo contrario muestra el siguiente toast*/
            Toast.makeText(this,"no existe un registro con dicho documento",
                    Toast.LENGTH_SHORT).show();
    }
    
    public void VerDatos(View v){
        Intent intentact=new Intent(this,DataList.class);
        startActivity(intentact);
    }


}