package com.example.androidcrud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    private List<String> aMusico;
    private List<String> aInstrumento;
    private List<Integer> aEnsayo;
    private LayoutInflater aInflater;

    //Los datos nos llegan a través del constructor


    public Adaptador(Context context, List<String> musico, List<String> instrumento, List<Integer> ensayo) {
        this.aMusico = musico;
        this.aInstrumento = instrumento;
        this.aEnsayo = ensayo;
        this.aInflater = LayoutInflater.from(context);
    }

    public Adaptador(Context context, List<String> musico, List<String> instrumento) {
        this.aMusico = musico;
        this.aInstrumento = instrumento;
        this.aInflater = LayoutInflater.from(context);
    }

    // Infla la fila del XML cuando sea necesario
    @NonNull
    @Override
    public Adaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = aInflater.inflate(R.layout.rv_musicos, parent, false);

        return new ViewHolder(view);
    }

    //Enlaza los datos a los textviews de cada fila
    @Override
    public void onBindViewHolder(@NonNull Adaptador.ViewHolder holder, int position) {
        String aux = aMusico.get(position);
        holder.setmusico(aux);
        aux = aInstrumento.get(position);
        holder.setinstrumento(aux);
    }

    //Número total de filas que vamos a tener
    @Override
    public int getItemCount() {
        return aMusico.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView musico;
        private TextView instrumento;

        public ViewHolder(View itemView) {
            super(itemView);
            musico = itemView.findViewById(R.id.tvNombre);
            instrumento = itemView.findViewById(R.id.tvInstrumento);
        }

        public String getmusico() {
            return musico.getText().toString();
        }

        public void setmusico(String musico) {
            this.musico.setText("Nombre: " + musico);
        }

        public String getinstrumento() {
            return instrumento.getText().toString();
        }

        public void setinstrumento(String instrumento) {
            this.instrumento.setText("Instrumento: " +instrumento);
        }

    }
}
