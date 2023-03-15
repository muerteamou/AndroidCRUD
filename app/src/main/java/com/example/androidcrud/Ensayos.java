package com.example.androidcrud;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Esta clase es la pantalla de Ensayos
 * Contiene un menú en la parte superior derecha para acceder a las distintas pantallas de la aplicación.
 *
 * @author Alejandro Piñero Medinilla
 */
public class Ensayos extends AppCompatActivity {

    AdaptadorEnsayos adaptador;
    static SQLiteDatabase db;
    ArrayList<String> nombreMusicos = new ArrayList<>();
    ArrayList<String> nombreInstrumento = new ArrayList<>();
    ArrayList<Integer> numeroEnsayos = new ArrayList<>();

    private Button btnAptos;
    private Button btnNoAptos;
    private Button btnReset;


    /**
     * Este método se llama cuando se crea la actividad EnsayosMusico.
     * Se configura el RecyclerView para mostrar la información de los músicos almacenada en la base de datos.
     * Se crea un adaptador para el RecyclerView con la información obtenida de la base de datos.
     * Se agrega una línea de separación entre cada elemento del RecyclerView.
     * Se configuran los botones para redirigir a las actividades Aptos, NoAptos y para reiniciar los ensayos.
     *
     * @param SavedInstaceState objeto Bundle que contiene datos sobre el estado anterior de la actividad, que se usará para recrearla en caso de ser necesario.
     */
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

        // Se configuran los botones para redirigir a las actividades Aptos, NoAptos y para reiniciar los ensayos.
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
                onClickShowAlert(view);

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

    /**
     * Suma un ensayo al músico correspondiente en la base de datos y actualiza el valor en la vista.
     *
     * @param ensayos El TextView que muestra el número de ensayos del músico.
     * @param musico  El TextView que muestra el nombre del músico.
     * @return El nuevo valor del número de ensayos del músico después de sumar uno.
     */
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

    /**
     * Resta un ensayo al músico correspondiente en la base de datos y actualiza el valor en la interfaz gráfica.
     *
     * @param ensayos TextView que muestra la cantidad de ensayos del músico en la interfaz gráfica.
     * @param musico  TextView que muestra el nombre del músico en la interfaz gráfica.
     * @return La nueva cantidad de ensayos del músico después de la resta.
     */
    public static int restarEnsayo(TextView ensayos, TextView musico) {
        String textoEnsayo = ensayos.getText().toString();
        String textMusico = musico.getText().toString();
        int numEnsayo = 0;
        textMusico = textMusico.substring(0, textMusico.length() - 2);

        Cursor c = db.rawQuery("SELECT ensayos FROM musicos WHERE musico = " + "'" + textMusico + "'", null);
        while (c.moveToNext()) {
            numEnsayo = c.getInt(0);
        }
        if (numEnsayo <= 0) {
            return numEnsayo;
        } else {
            numEnsayo--;
            db.execSQL("UPDATE musicos SET ensayos = " + numEnsayo + " WHERE musico = " + "'" + textMusico + "'");
            return numEnsayo;
        }
    }
    public void onClickShowAlert(View view){
        AlertDialog.Builder myAlertBuild = new AlertDialog.Builder(Ensayos.this);
        myAlertBuild.setTitle("Alerta");
        myAlertBuild.setMessage("Presiona OK para borrar los ensayos, o Cancel para volver");
        myAlertBuild.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Ensayos borrados", Toast.LENGTH_SHORT).show();
                resetEnsayos();
            }
        });
        myAlertBuild.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
            }
        });
        myAlertBuild.show();
    }


    /**
     * Reinicia el contador de ensayos de todos los músicos en la base de datos.
     * Ejecuta una consulta SQL para actualizar el valor de "ensayos" a 0 en la tabla "musicos".
     */
    public static void resetEnsayos() {
        db.execSQL("UPDATE musicos SET ensayos = 0");
    }

}
