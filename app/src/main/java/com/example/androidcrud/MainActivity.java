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

/**
 * Esta clase es la pantalla principal de la aplicación, donde se muestran todos los músicos almacenados en la base de datos.
 * Contiene un menú en la parte superior derecha para acceder a las distintas pantallas de la aplicación.
 *
 * @author Alejandro Piñero Medinilla
 */

public class MainActivity extends AppCompatActivity {

    public static SQLiteDatabase db;
    Adaptador adaptador;

    public static ArrayList<String> nombreMusicos = new ArrayList<>();
    public static ArrayList<String> nombreInstrumento = new ArrayList<>();
    public static ArrayList<Integer> numeroEnsayos = new ArrayList<>();

    /**
     * Método para inicializar la pantalla principal de la aplicación.
     * Se crea o abre la base de datos "orquesta" en modo privado.
     * Se crea la tabla "musicos" si no existe en la base de datos.
     * Si las listas "nombreMusicos" y "nombreInstrumento" están vacías, se llama al método "mostrar" para obtener los registros de la tabla "musicos".
     * Se obtiene una referencia al RecyclerView de la actividad y se establece su layout manager como un LinearLayoutManager.
     * Se crea un nuevo adaptador con las listas "nombreMusicos" y "nombreInstrumento".
     * Se agrega una línea de separación entre los elementos del RecyclerView.
     * Se establece el adaptador creado en el RecyclerView.
     * @param savedInstanceState objeto Bundle que contiene datos sobre el estado anterior de la actividad, que se usará para recrearla en caso de ser necesario.
     */
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

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(adaptador);
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
            case R.id.Resultado:
                Intent i3 = new Intent(this, Ensayos.class);
                startActivity(i3);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * Este método se encarga de mostrar los datos almacenados en una base de datos de música.
     * Limpia las listas de nombreMusicos, nombreInstrumento y numeroEnsayos.
     * Realiza una consulta a la base de datos y recorre el resultado para añadir los datos a las listas correspondientes.
     * Si la consulta no devuelve resultados, el método guarda el mensaje "No hay datos en la bd" en la variable result.
     */
    public static void mostrar() {
        nombreMusicos.clear();
        nombreInstrumento.clear();
        numeroEnsayos.clear();

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