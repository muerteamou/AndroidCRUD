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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Anyadir extends AppCompatActivity {
    SQLiteDatabase db;
    EditText etNombre ;
    EditText etInstrumento;


    protected void onCreate(Bundle SavedInstaceState) {
        super.onCreate(SavedInstaceState);
        setContentView(R.layout.add_musico);
        db = openOrCreateDatabase("orquesta", Context.MODE_PRIVATE, null);
        etNombre = findViewById(R.id.etNombre);
        etInstrumento = findViewById(R.id.etInstrumento);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

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
                Intent i3 = new Intent(this, Resultado.class);
                startActivity(i3);
                break;
            default:
                break;
        }
        return true;
    }
    public void anyadir(View view){
        db.execSQL("INSERT INTO musicos values ('" + etNombre.getText().toString() + "' , '" + etInstrumento.getText().toString() + "' , '" + "0" + "');");
        mostrar();
    }



}
