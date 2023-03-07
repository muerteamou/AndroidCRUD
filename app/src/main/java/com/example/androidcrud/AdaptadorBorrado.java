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

public class AdaptadorBorrado extends RecyclerView.Adapter<AdaptadorBorrado.ViewHolder> {

    private List<String> aMusico;
    private List<String> aInstrumento;
    private LayoutInflater aInflater;


    //Los datos nos llegan a través del constructor
    public AdaptadorBorrado(Context context, List<String> musico, List<String> instrumento) {
        this.aMusico = musico;
        this.aInstrumento = instrumento;
        this.aInflater = LayoutInflater.from(context);
    }

    // Infla la fila del XML cuando sea necesario
    @NonNull
    @Override
    public AdaptadorBorrado.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = aInflater.inflate(R.layout.rv_del_musicos, parent, false);

        return new ViewHolder(view);
    }

    //Enlaza los datos a los textviews de cada fila
    @Override
    public void onBindViewHolder(@NonNull AdaptadorBorrado.ViewHolder holder, int position) {
        String aux = aMusico.get(position);
        holder.setMusico(aux);
        aux = aInstrumento.get(position);
        holder.setInstrumento(aux);
        int positionFixed = holder.getAdapterPosition();
        holder.boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String musico = aMusico.get(positionFixed);
                aMusico.remove(positionFixed);
                Borrar.borrarMusico(musico);
                notifyItemRemoved(positionFixed);
            }
        });
    }

    //Número total de filas que vamos a tener
    @Override
    public int getItemCount() {
        return aMusico.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView musico;
        private TextView instrumento;
        private Button boton;

        public ViewHolder(View itemView) {
            super(itemView);
            musico = itemView.findViewById(R.id.tvNombre);
            instrumento = itemView.findViewById(R.id.tvInstrumento);
            boton = itemView.findViewById(R.id.botonDel);
        }

        public String getMusico() {
            return musico.getText().toString();
        }

        public void setMusico(String musico) {
            this.musico.setText(musico);
        }

        public String getInstrumento() {
            return instrumento.getText().toString();
        }

        public void setInstrumento(String instrumento) {
            this.instrumento.setText(instrumento);
        }

        @Override
        public void onClick(View view) {
        }
    }

}
