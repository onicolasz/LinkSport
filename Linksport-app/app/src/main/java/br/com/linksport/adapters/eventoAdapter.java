package br.com.linksport.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.linksport.R;
import br.com.linksport.network.Evento;

public class eventoAdapter extends RecyclerView.Adapter<eventoAdapter.MyViewHolder>{

    private List<Evento> eventos;
    private Context context;

    public eventoAdapter(List<Evento> listaEventos, Context context) {
        this.eventos = listaEventos;
        this.context = context;
    }

    @NonNull
    @Override
    public eventoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.evento, parent, false);
        return new eventoAdapter.MyViewHolder(itemLista);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        //click
        public View view;
        public Evento currentEvento;
        //click
        private TextView timeCasa;
        private TextView timeFora;
        private TextView tempo;
        private TextView campeonato;
        private TextView placarCasa;
        private TextView placarFora;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            timeCasa = itemView.findViewById(R.id.textCasa);
            timeFora = itemView.findViewById(R.id.textFora);
            tempo = itemView.findViewById(R.id.textTempo);
            campeonato = itemView.findViewById(R.id.textCampeonato);
            placarCasa = itemView.findViewById(R.id.textPlacarCasa);
            placarFora = itemView.findViewById(R.id.textPlacarFora);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull eventoAdapter.MyViewHolder holder, int position) {
        Evento evento = eventos.get(position);
        //click
        holder.currentEvento = evento;
        //click
        holder.tempo.setText(evento.getTempo());
        holder.timeCasa.setText(evento.getTimeCasa());
        holder.timeFora.setText(evento.getTimeFora());
        holder.campeonato.setText(evento.getCampeonato());
        holder.placarCasa.setText(evento.getPlacarCasa());
        holder.placarFora.setText(evento.getPlacarFora());

        if(evento.getTempo().indexOf("MIN") != -1){
            holder.tempo.setBackgroundResource(R.drawable.custom_status_aovivo);
        } else if(evento.getTempo().indexOf("ENCERRADO") != -1) {
            holder.tempo.setBackgroundResource(R.drawable.custom_status_encerrado);
            holder.tempo.setTextSize(10);
        } else if(evento.getTempo().indexOf("HOJE") != -1) {
            holder.tempo.setBackgroundResource(R.drawable.custom_status_hoje);
        } else if(evento.getTempo().indexOf("INTERVALO") != -1) {
            holder.tempo.setBackgroundResource(R.drawable.custom_status_intervalo);
            holder.tempo.setTextSize(10);
        } else {
            holder.tempo.setBackgroundResource(R.drawable.custom_status_intervalo);
            holder.tempo.setTextSize(10);
        }
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }
}
