package br.com.linksport.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.linksport.CardConcluidoActivity;
import br.com.linksport.CardDetalhesActivity;
import br.com.linksport.MainActivity;
import br.com.linksport.R;
import br.com.linksport.network.Card;

public class postOnlyViewAdapter extends RecyclerView.Adapter<postOnlyViewAdapter.MyViewHolder> {
    //click
    private Context context;
    //click
    private List<Card> cards;

    public postOnlyViewAdapter(List<Card> listaCards, Context context) {
        this.context = context;
        this.cards = listaCards;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_buscar, parent, false);
        return new MyViewHolder(itemLista);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        //click
        public View view;
        public Card currentCard;
        //click
        private TextView textTitulo;
        private TextView textData;
        private TextView textHora;
        private TextView textEvento;
        private TextView textStatus;
        private TextView textDono;
        private ImageView imagemStatus;
        private ImageView imagemDono;
        private String eventoStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //click
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Este card pertence a outra pessoa.", Toast.LENGTH_SHORT).show();
                }
            });
            //click
            textTitulo = itemView.findViewById(R.id.textTituloBusca);
            textData = itemView.findViewById(R.id.textDataBusca);
            textHora = itemView.findViewById(R.id.textHoraBusca);
            textEvento = itemView.findViewById(R.id.textEventoBusca);
            textStatus = itemView.findViewById(R.id.textStatusBusca);
            textDono = itemView.findViewById(R.id.textDono);
            imagemStatus = itemView.findViewById(R.id.imgStatusBusca);
            imagemDono = itemView.findViewById(R.id.imgDono);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Card card = cards.get(position);
        //click
        holder.currentCard = card;
        //click
        holder.textEvento.setText(card.getEvento());
        holder.textTitulo.setText(card.getTitulo());
        holder.textData.setText(card.getData());
        holder.textHora.setText(card.getHora());
        holder.textStatus.setText(card.getStatus());
        holder.textDono.setText(card.getUsuario().getUsuario());

        if(card.getStatus().indexOf("Em aberto") != -1) {
            holder.imagemStatus.setImageResource(R.drawable.ic_aberto_foreground);
        } else {
            holder.imagemStatus.setImageResource(R.drawable.ic_concluir_foreground);
        }
        holder.imagemDono.setImageResource(R.drawable.ic_person_foreground);

        if(card.getEvento().indexOf("Sem evento") != -1) {
            holder.textEvento.setVisibility(View.INVISIBLE);
        }

        try {
            String d = card.getData()+" "+card.getHora();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date =sdf.parse(d);
            Date dataHoraAtual = new Date();
            long diff = date.getTime() - dataHoraAtual.getTime();
            //int myInt = (int) diff;

            if(diff < -7277639) {
                holder.textEvento.setBackgroundResource(R.drawable.custom_status_encerrado);
                holder.textEvento.setText(card.getEvento());
                holder.eventoStatus = "Encerrado";
            } else if(diff < 0){
                holder.textEvento.setBackgroundResource(R.drawable.custom_status_aovivo);
                holder.textEvento.setText("● AO VIVO "+card.getEvento());
                holder.eventoStatus = "Ao vivo";
            } else {
                holder.textEvento.setBackgroundResource(R.drawable.custom_status_hoje);
                holder.eventoStatus = "Hoje";
            }
        } catch(ParseException e) {
            e.printStackTrace();
            System.out.print("you get the ParseException");
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }
}
