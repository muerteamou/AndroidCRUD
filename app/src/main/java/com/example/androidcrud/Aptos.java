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
 * Esta clase es la pantalla donde se muestran los músicos que pueden ir a tocar al concierto.
 * Contiene un menú en la parte superior derecha para acceder a las distintas pantallas de la aplicación.
 *
 * @author Alejandro Piñero Medinilla
 */
public class Aptos extends AppCompatActivity {

    AdaptadorAptos adaptador;
    static SQLiteDatabase db;
    ArrayList<String> nombreMusicos = new ArrayList<>();
    ArrayList<String> nombreInstrumento = new ArrayList<>();
    private Button btnVolver;
    private Button btnNoAptos;


    /**
     * El método onCreate() se llama cuando se crea la actividad Aptos.
     * Inicializa una RecyclerView para mostrar la lista de músicos aptos para ensayos.
     *
     * @param SavedInstaceState instancia guardada del estado actual de la actividad
     */
    protected void onCreate(Bundle SavedInstaceState) {
        super.onCreate(SavedInstaceState);
        setContentView(R.layout.aptos);

        RecyclerView recyclerView = findViewById(R.id.rv_aptos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String result = "";
        db = openOrCreateDatabase("orquesta", Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM musicos WHERE ensayos > 4", null);
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
        adaptador = new AdaptadorAptos(this, nombreMusicos, nombreInstrumento);

        //3. Ponemos una linea de separación entre elementos

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //4. Añadimos el adaptador a la vista
        recyclerView.setAdapter(adaptador);

        // Agrega los botones para volver a la actividad anterior o ir a la actividad NoAptos
        Intent volver = new Intent(this, Ensayos.class);

        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(volver);
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