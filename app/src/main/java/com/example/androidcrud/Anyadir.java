package com.example.androidcrud;

import static com.example.androidcrud.MainActivity.mostrar;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Esta clase es la pantalla donde se pueden añadir músicos de la lista.
 * Contiene un menú en la parte superior derecha para acceder a las distintas pantallas de la aplicación.
 *
 * @author Alejandro Piñero Medinilla
 */

public class Anyadir extends AppCompatActivity {
    SQLiteDatabase db;
    EditText etNombre;
    EditText etInstrumento;

    /**
     * Método que se ejecuta cuando se crea la actividad AddMusico.
     * Establece el contenido de la vista con el archivo de diseño add_musico.xml,
     * crea o abre la base de datos "orquesta" en modo privado,
     * y obtiene referencias a los elementos de la interfaz de usuario, etNombre y etInstrumento.
     *
     * @param SavedInstaceState objeto Bundle que contiene datos sobre el estado anterior de la actividad, que se usará para recrearla en caso de ser necesario.
     */
    protected void onCreate(Bundle SavedInstaceState) {
        super.onCreate(SavedInstaceState);
        setContentView(R.layout.add_musico);
        db = openOrCreateDatabase("orquesta", Context.MODE_PRIVATE, null);
        etNombre = findViewById(R.id.etNombre);
        etInstrumento = findViewById(R.id.etInstrumento);
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

            case R.id.Musicos:
                Intent i1 = new Intent(this, MainActivity.class);
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
     * Añade un nuevo músico a la base de datos con los valores de nombre e instrumento obtenidos de
     * los campos de texto en la interfaz de usuario y establece su número de ensayos en cero.
     * Muestra un mensaje de éxito si añade el músico o un mensaje de error en caso contrario.
     * Vacía los TextView correspondientes al nombre e instrumento.
     *
     * @param view La vista del botón que activa la acción de añadir.
     */
    public void anyadir(View view) {
        try {
            db.execSQL("INSERT INTO musicos values ('" + etNombre.getText().toString() + "' , '" + etInstrumento.getText().toString() + "' , '" + "0" + "');");
            Toast.makeText(getApplicationContext(), etNombre.getText().toString() + " añadido con éxito", Toast.LENGTH_SHORT).show();
            etInstrumento.setText("");
            etNombre.setText("");
            mostrar();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }

    }


}

