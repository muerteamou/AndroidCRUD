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

/**
 * Esta clase es la pantalla donde se muestran los músicos que no pueden ir a tocar al concierto.
 * Contiene un menú en la parte superior derecha para acceder a las distintas pantallas de la aplicación.
 *
 * @author Alejandro Piñero Medinilla
 */
public class NoAptos extends AppCompatActivity {

    AdaptadorNoAptos adaptador;
    static SQLiteDatabase db;
    ArrayList<String> nombreMusicos = new ArrayList<>();
    ArrayList<String> nombreInstrumento = new ArrayList<>();
    private Button btnVolver;
    private Button btnAptos;

    /**
     * Método para crear la actividad de borrado de músicos
     * Se obtienen los datos de la tabla de la base de datos "musicos" y se agregan a los arreglos de los nombres de los músicos y sus instrumentos y número de ensayos.
     * Luego se crea un RecyclerView para mostrar los datos en una lista con una línea de separación entre cada elemento.
     * Finalmente se agrega un adaptador al RecyclerView para mostrar los datos en la lista.
     *
     * @param SavedInstaceState objeto Bundle que contiene datos sobre el estado anterior de la actividad, que se usará para recrearla en caso de ser necesario.
     */
    protected void onCreate(Bundle SavedInstaceState) {
        super.onCreate(SavedInstaceState);
        setContentView(R.layout.no_aptos);

        RecyclerView recyclerView = findViewById(R.id.rv_aptos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String result = "";
        db = openOrCreateDatabase("orquesta", Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM musicos WHERE ensayos < 5", null);
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

        Intent volver = new Intent(this, Ensayos.class);

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

    /**
     * Método que crea el menú de opciones de la aplicación.
     * Se infla el menú "menu" en el objeto Menu pasado como parámetro.
     * Se devuelve "true" para indicar que se ha creado el menú correctamente.
     *
     * @param menu el objeto Menu en el que se va a inflar el menú de opciones
     * @return true si se ha creado el menú correctamente, false en caso contrario
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Método que se ejecuta cuando se selecciona un elemento del menú de opciones de la actividad.
     * Se utiliza un switch para determinar la opción seleccionada y realizar la acción correspondiente.
     * Se devuelve "true" para indicar que se ha manejado correctamente la selección del elemento del menú.
     *
     * @param item el MenuItem seleccionado del menú de opciones
     * @return true si se ha manejado correctamente la selección del elemento del menú, false en caso contrario
     */
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