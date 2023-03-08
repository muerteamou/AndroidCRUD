package com.example.androidcrud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorNoAptos extends RecyclerView.Adapter<AdaptadorNoAptos.ViewHolder> {
    private static List<String> aMusico;
    private static List<String> aInstrumento;
    private static LayoutInflater aInflater;


//Los datos nos llegan a través del constructor

    public AdaptadorNoAptos(Context context, List<String> musico, List<String> instrumento) {
        this.aMusico = musico;
        this.aInstrumento = instrumento;
        this.aInflater = LayoutInflater.from(context);
    }

    // Infla la fila del XML cuando sea necesario
    @NonNull
    @Override
    public AdaptadorNoAptos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = aInflater.inflate(R.layout.rv_aptos, parent, false);

        return new AdaptadorNoAptos.ViewHolder(view);
    }


    //Enlaza los datos a los textviews de cada fila
    @Override
    public void onBindViewHolder(@NonNull AdaptadorNoAptos.ViewHolder holder, int position) {
        String aux = aMusico.get(position);
        holder.setMusico(aux);
        aux = aInstrumento.get(position);
        holder.setInstrumento(aux);

    }

    //Número total de filas que vamos a tener
    @Override
    public int getItemCount() {
        return aMusico.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView musico;
        private TextView instrumento;


        public ViewHolder(View itemView) {
            super(itemView);
            musico = itemView.findViewById(R.id.tvNombre);
            instrumento = itemView.findViewById(R.id.tvInstrumento);
        }

        public String getMusico() {
            return musico.getText().toString();
        }

        public void setMusico(String musico) {
            this.musico.setText(musico + ": ");
        }

        public String getInstrumento() {
            return instrumento.getText().toString();
        }

        public void setInstrumento(String instrumento) {
            this.instrumento.setText("Instrumento: " + instrumento);
        }


        @Override
        public void onClick(View view) {

        }
    }
}
