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

public class AdaptadorEnsayos extends RecyclerView.Adapter<AdaptadorEnsayos.ViewHolder> {

    private static List<String> aMusico;
    private static List<String> aInstrumento;
    private static List<Integer> aEnsayos;

    private static LayoutInflater aInflater;


//Los datos nos llegan a través del constructor

    public AdaptadorEnsayos(Context context, List<String> musico, List<String> instrumento, List<Integer> ensayos) {
        this.aMusico = musico;
        this.aInstrumento = instrumento;
        this.aEnsayos = ensayos;
        this.aInflater = LayoutInflater.from(context);
    }

    // Infla la fila del XML cuando sea necesario
    @NonNull
    @Override
    public AdaptadorEnsayos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = aInflater.inflate(R.layout.rv_del_ensayos, parent, false);

        return new ViewHolder(view);
    }

    //Enlaza los datos a los textviews de cada fila
    @Override
    public void onBindViewHolder(@NonNull AdaptadorEnsayos.ViewHolder holder, int position) {
        String aux = aMusico.get(position);
        holder.setMusico(aux);
        aux = aInstrumento.get(position);
        holder.setInstrumento(aux);
        aux = String.valueOf(aEnsayos.get(position));
        holder.setEnsayos(aux);

    }

    //Número total de filas que vamos a tener
    @Override
    public int getItemCount() {
        return aMusico.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView musico;
        private TextView instrumento;
        private TextView ensayos;
        private Button botonMas;
        private Button botonMenos;

        public ViewHolder(View itemView) {
            super(itemView);
            musico = itemView.findViewById(R.id.tvNombre);
            instrumento = itemView.findViewById(R.id.tvInstrumento);
            ensayos = itemView.findViewById(R.id.tvEnsayo);

            botonMas = itemView.findViewById(R.id.botonSum);
            botonMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                sumarEnsayo();
                }
            });

            botonMenos = itemView.findViewById(R.id.botonRes);
            botonMenos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    restarEnsayo();
                }
            });

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
            this.instrumento.setText(instrumento);
        }

        public void setEnsayos(String ensayos) {
            this.ensayos.setText(ensayos);
        }

        public void sumarEnsayo() {

            int cantidadEnsayos = Resultado.sumarEnsayo(ensayos, musico);
            setEnsayos(String.valueOf(cantidadEnsayos));
        }
        public void restarEnsayo() {

            int cantidadEnsayos = Resultado.restarEnsayo(ensayos, musico);
            setEnsayos(String.valueOf(cantidadEnsayos));
        }

        @Override
        public void onClick(View view) {

        }
    }
}


