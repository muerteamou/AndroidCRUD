package com.example.androidcrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static SQLiteDatabase db;
    Adaptador adaptador;

    public static ArrayList<String> nombreMusicos = new ArrayList<>();
    public static ArrayList<String> nombreInstrumento = new ArrayList<>();
    public static ArrayList<Integer> numeroEnsayos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = openOrCreateDatabase("orquesta", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS musicos(musico VARCHAR, instrumento VARCHAR, ensayos int)");


        if (nombreMusicos.isEmpty() || nombreInstrumento.isEmpty()) {
            mostrar();
        }
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new Adaptador(this, nombreMusicos, nombreInstrumento);

        //3. Ponemos una linea de separación entre elementos

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //4. Añadimos el adaptador a la vista
        recyclerView.setAdapter(adaptador);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.Anyadir:
                Intent i1 = new Intent(this, Anyadir.class);
                startActivity(i1);
                break;
            case R.id.Borrar:
                Intent i2 = new Intent(this, Borrar.class);
                startActivity(i2);
                break;
            case R.id.Resultado:
                Intent i3 = new Intent(this, Resultado.class);
                startActivity(i3);
                break;
            default:
                break;
        }
        return true;
    }

    public static void mostrar() {
        String result = "";
        Cursor c = db.rawQuery("SELECT * FROM musicos", null);
        if (c.getCount() == 0) {
            result = "No hay datos en la bd";

        } else {
            while (c.moveToNext()) {
                nombreMusicos.add(c.getString(0));
                nombreInstrumento.add(c.getString(1));
                numeroEnsayos.add(c.getInt(2));
            }

        }
        c.close();
    }
}