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
 * Esta clase es el adaptador de Ensayos
 * Contiene un menú en la parte superior derecha para acceder a las distintas pantallas de la aplicación.
 *
 * @author Alejandro Piñero Medinilla
 */

public class AdaptadorEnsayos extends RecyclerView.Adapter<AdaptadorEnsayos.ViewHolder> {

    private static List<String> aMusico;
    private static List<String> aInstrumento;
    private static List<Integer> aEnsayos;

    private static LayoutInflater aInflater;

    /**
     * Constructor de la clase AdaptadorEnsayos.
     * Recibe los datos a mostrar en el adaptador y el contexto de la aplicación.
     * Los datos se almacenan en los atributos correspondientes de la clase.
     * El LayoutInflater se inicializa a partir del contexto recibido.
     *
     * @param context     el contexto de la aplicación
     * @param musico      la lista de nombres de los músicos a mostrar en el adaptador
     * @param instrumento la lista de nombres de los instrumentos a mostrar en el adaptador
     * @param ensayos     la lista de número de ensayos a mostrar en el adaptador
     */
    public AdaptadorEnsayos(Context context, List<String> musico, List<String> instrumento, List<Integer> ensayos) {
        this.aMusico = musico;
        this.aInstrumento = instrumento;
        this.aEnsayos = ensayos;
        this.aInflater = LayoutInflater.from(context);
    }

    /**
     * Método que se llama cuando se crea un nuevo ViewHolder en el RecyclerView.
     * Se infla la vista correspondiente a cada elemento del adaptador a partir del layout "rv_del_ensayos".
     * Se devuelve un nuevo ViewHolder creado a partir de la vista inflada.
     *
     * @param parent   el ViewGroup padre en el que se creará la vista
     * @param viewType el tipo de vista a crear
     * @return un nuevo ViewHolder creado a partir de la vista inflada
     */
    @NonNull
    @Override
    public AdaptadorEnsayos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = aInflater.inflate(R.layout.rv_del_ensayos, parent, false);

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
    public void onBindViewHolder(@NonNull AdaptadorEnsayos.ViewHolder holder, int position) {
        String aux = aMusico.get(position);
        holder.setMusico(aux);
        aux = aInstrumento.get(position);
        holder.setInstrumento(aux);
        aux = String.valueOf(aEnsayos.get(position));
        holder.setEnsayos(aux);
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
        private TextView ensayos;
        private Button botonMas;
        private Button botonMenos;

        /**
         * Constructor para la clase ViewHolder.
         * Inicializa los TextViews y botones con sus correspondientes objetos View en el layout del itemView.
         *
         * @param itemView El objeto View que representa el layout de un solo elemento en el RecyclerView.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            musico = itemView.findViewById(R.id.tvNombre);
            instrumento = itemView.findViewById(R.id.tvInstrumento);
            ensayos = itemView.findViewById(R.id.tvEnsayo);
            botonMas = itemView.findViewById(R.id.botonSum);
            botonMas.setOnClickListener(new View.OnClickListener() {
                /**
                 Este método incrementa el número de ensayos para el músico que se muestra en este objeto ViewHolder.
                 */
                @Override
                public void onClick(View view) {
                    sumarEnsayo();
                }
            });

            botonMenos = itemView.findViewById(R.id.botonRes);
            botonMenos.setOnClickListener(new View.OnClickListener() {
                /**
                 Este método decrementa el número de ensayos para el músico que se muestra en este objeto ViewHolder.
                 */
                @Override
                public void onClick(View view) {
                    restarEnsayo();
                }
            });

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
            this.musico.setText(musico + ": ");
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

        /**
         * Método que establece el número de ensayos del ViewHolder.
         *
         * @param ensayos el número de ensayos a establecer
         */
        public void setEnsayos(String ensayos) {
            this.ensayos.setText(ensayos);
        }

        /**
         * Método que aumenta el número de ensayos del músico asociado al ViewHolder.
         * Obtiene el número actual de ensayos a través de la clase Ensayos,
         * lo incrementa en 1 y actualiza el valor en la vista del ViewHolder.
         */
        public void sumarEnsayo() {
            int cantidadEnsayos = Ensayos.sumarEnsayo(ensayos, musico);
            setEnsayos(String.valueOf(cantidadEnsayos));
        }

        /**
         * Método que reduce el número de ensayos del músico asociado al ViewHolder.
         * Obtiene el número actual de ensayos a través de la clase Ensayos,
         * lo decrementa en 1 y actualiza el valor en la vista del ViewHolder.
         */
        public void restarEnsayo() {

            int cantidadEnsayos = Ensayos.restarEnsayo(ensayos, musico);
            setEnsayos(String.valueOf(cantidadEnsayos));
        }

        @Override
        public void onClick(View view) {

        }
    }
}


