package com.example.androidcrud;

import static com.example.androidcrud.MainActivity.mostrar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Resultado extends AppCompatActivity {

    AdaptadorEnsayos adaptador;
    static SQLiteDatabase db;
    ArrayList<String> nombreMusicos = new ArrayList<>();
    ArrayList<String> nombreInstrumento = new ArrayList<>();
    ArrayList<Integer> numeroEnsayos = new ArrayList<>();

    private Button btnAptos;
    private Button btnNoAptos;
    private Button btnReset;


    protected void onCreate(Bundle SavedInstaceState) {
        super.onCreate(SavedInstaceState);
        setContentView(R.layout.ensayos_musico);

        RecyclerView recyclerView = findViewById(R.id.rv_del_ensayos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String result = "";
        db = openOrCreateDatabase("orquesta", Context.MODE_PRIVATE, null);
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

        recyclerView = findViewById(R.id.rv_del_ensayos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new AdaptadorEnsayos(this, nombreMusicos, nombreInstrumento, numeroEnsayos);

        //3. Ponemos una linea de separación entre elementos

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //4. Añadimos el adaptador a la vista
        recyclerView.setAdapter(adaptador);

        Intent aptos = new Intent(this, Aptos.class);

        btnAptos = findViewById(R.id.btnAptos);
        btnAptos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(aptos);
            }
        });

        Intent noAptos = new Intent(this, NoAptos.class);
        btnNoAptos = findViewById(R.id.btnNoAptos);
        btnNoAptos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(noAptos);
            }
        });

        btnReset = findViewById(R.id.btnReset);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetEnsayos();
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

    public static int sumarEnsayo(TextView ensayos, TextView musico) {
        String textoEnsayo = ensayos.getText().toString();
        String textMusico = musico.getText().toString();
        int numEnsayo = 0;
        textMusico = textMusico.substring(0, textMusico.length() - 2);

        Cursor c = db.rawQuery("SELECT ensayos FROM musicos WHERE musico = " + "'" + textMusico + "'", null);
        while (c.moveToNext()) {
            numEnsayo = c.getInt(0);
        }
        numEnsayo++;
        db.execSQL("UPDATE musicos SET ensayos = " + numEnsayo + " WHERE musico = " + "'" + textMusico + "'");
        return numEnsayo;
    }

    public static int restarEnsayo(TextView ensayos, TextView musico) {
        String textoEnsayo = ensayos.getText().toString();
        String textMusico = musico.getText().toString();
        int numEnsayo = 0;
        textMusico = textMusico.substring(0, textMusico.length() - 2);

        Cursor c = db.rawQuery("SELECT ensayos FROM musicos WHERE musico = " + "'" + textMusico + "'", null);
        while (c.moveToNext()) {
            numEnsayo = c.getInt(0);
        }
        numEnsayo--;
        db.execSQL("UPDATE musicos SET ensayos = " + numEnsayo + " WHERE musico = " + "'" + textMusico + "'");
        return numEnsayo;
    }

    private void resetEnsayos() {
        db.execSQL("UPDATE musicos SET ensayos = 0");

    }

}
