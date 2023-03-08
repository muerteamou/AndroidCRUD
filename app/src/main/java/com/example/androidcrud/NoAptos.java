package com.example.androidcrud;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class NoAptos extends AppCompatActivity {

    AdaptadorNoAptos adaptador;
    static SQLiteDatabase db;
    ArrayList<String> nombreMusicos = new ArrayList<>();
    ArrayList<String> nombreInstrumento = new ArrayList<>();
    private Button btnVolver;
    private Button btnAptos;



    protected void onCreate(Bundle SavedInstaceState) {
        super.onCreate(SavedInstaceState);
        setContentView(R.layout.no_aptos);

        RecyclerView recyclerView = findViewById(R.id.rv_aptos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String result = "";
        db = openOrCreateDatabase("orquesta", Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM musicos WHERE ensayos < 4", null);
        if (c.getCount() == 0) {
            result = "No hay datos en la bd";
        } else {
            while (c.moveToNext()) {
                nombreMusicos.add(c.getString(0));
                nombreInstrumento.add(c.getString(1));
            }

        }
        c.close();

        recyclerView = findViewById(R.id.rv_aptos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new AdaptadorNoAptos(this, nombreMusicos, nombreInstrumento);

        //3. Ponemos una linea de separación entre elementos

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //4. Añadimos el adaptador a la vista
        recyclerView.setAdapter(adaptador);

        Intent volver = new Intent(this, Resultado.class);

        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(volver);
            }
        });

        Intent aptos = new Intent(this, Aptos.class);
        btnAptos = findViewById(R.id.btnAptos);
        btnAptos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(aptos);
            }
        });

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
            case R.id.Musicos:
                Intent i3 = new Intent(this, MainActivity.class);
                startActivity(i3);
                break;

            default:
                break;
        }
        return true;
    }
}