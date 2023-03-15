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

/**
 * Esta clase es el adaptadorBorrado de la pantalla de borrado de músicos.
 * Contiene un menú en la parte superior derecha para acceder a las distintas pantallas de la aplicación.
 *
 * @author Alejandro Piñero Medinilla
 */
public class AdaptadorBorrado extends RecyclerView.Adapter<AdaptadorBorrado.ViewHolder> {

    private List<String> aMusico;
    private List<String> aInstrumento;
    private LayoutInflater aInflater;


    /**
     * Constructor de la clase AdaptadorBorrado.
     * Recibe los datos a mostrar en el adaptador y el contexto de la aplicación.
     * Los datos se almacenan en los atributos correspondientes de la clase.
     * El LayoutInflater se inicializa a partir del contexto recibido.
     *
     * @param context     el contexto de la aplicación
     * @param musico      la lista de nombres de los músicos a mostrar en el adaptador
     * @param instrumento la lista de nombres de los instrumentos a mostrar en el adaptador
     */
    public AdaptadorBorrado(Context context, List<String> musico, List<String> instrumento) {
        this.aMusico = musico;
        this.aInstrumento = instrumento;
        this.aInflater = LayoutInflater.from(context);
    }

    /**
     * Método que se llama cuando se crea un nuevo ViewHolder en el RecyclerView.
     * Se infla la vista correspondiente a cada elemento del adaptador a partir del layout "rv_del_musicos".
     * Se devuelve un nuevo ViewHolder creado a partir de la vista inflada.
     *
     * @param parent   el ViewGroup padre en el que se creará la vista
     * @param viewType el tipo de vista a crear
     * @return un nuevo ViewHolder creado a partir de la vista inflada
     */
    @NonNull
    @Override
    public AdaptadorBorrado.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = aInflater.inflate(R.layout.rv_del_musicos, parent, false);

        return new ViewHolder(view);
    }

    /**
     * Método que se llama cuando se vincula un ViewHolder a los datos del adaptador.
     * Se asigna el valor correspondiente a cada elemento visual del ViewHolder a partir de los datos de la lista.
     *
     * @param holder   el ViewHolder a vincular a los datos del adaptador
     * @param position la posición del elemento en la lista de datos del adaptador
     */
    @Override
    public void onBindViewHolder(@NonNull AdaptadorBorrado.ViewHolder holder, int position) {
        String aux = aMusico.get(position);
        holder.setMusico(aux);
        aux = aInstrumento.get(position);
        holder.setInstrumento(aux);
        holder.boton.setOnClickListener(new View.OnClickListener() {
            /**
             * Método onClick que se llama cuando el usuario hace clic en el botón de eliminar músico en el adaptador.
             * Obtiene la posición del elemento que se va a eliminar y lo elimina tanto del ArrayList como de la base de datos.
             * También notifica al adaptador que se ha eliminado un elemento para que actualice la vista.
             *
             * @param view La vista del botón que se hizo clic
             */
            @Override
            public void onClick(View view) {

                String musico = aMusico.get(holder.getAdapterPosition());
                aMusico.remove(holder.getAdapterPosition());
                Borrar.borrarMusico(musico);
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    /**
     * Método que devuelve el número de elementos en la lista de datos del adaptador.
     *
     * @return el número de elementos en la lista de datos del adaptador
     */
    @Override
    public int getItemCount() {
        return aMusico.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView musico;
        private TextView instrumento;
        private Button boton;

        /**
         * Constructor para la clase ViewHolder.
         * Inicializa los TextViews con sus correspondientes objetos View en el layout del itemView.
         *
         * @param itemView El objeto View que representa el layout de un solo elemento en el RecyclerView.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            musico = itemView.findViewById(R.id.tvNombre);
            instrumento = itemView.findViewById(R.id.tvInstrumento);
            boton = itemView.findViewById(R.id.botonDel);
        }

        /**
         * Método que devuelve el nombre del músico del ViewHolder.
         *
         * @return el nombre del músico del ViewHolder
         */
        public String getMusico() {
            return musico.getText().toString();
        }

        /**
         * Método que establece el nombre del músico del ViewHolder.
         *
         * @param musico el nombre del músico a establecer
         */
        public void setMusico(String musico) {
            this.musico.setText(musico);
        }

        /**
         * Método que devuelve el nombre del instrumento del ViewHolder.
         *
         * @return el nombre del instrumento del ViewHolder
         */
        public String getInstrumento() {
            return instrumento.getText().toString();
        }

        /**
         * Método que establece el nombre del instrumento del ViewHolder.
         *
         * @param instrumento el nombre del instrumento a establecer
         */
        public void setInstrumento(String instrumento) {
            this.instrumento.setText(instrumento);
        }

        @Override
        public void onClick(View view) {
        }
    }

}
