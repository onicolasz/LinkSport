package br.com.linksport.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.linksport.CardConcluidoActivity;
import br.com.linksport.CardDetalhesActivity;
import br.com.linksport.R;
import br.com.linksport.network.Card;

public class postAdapter extends RecyclerView.Adapter<postAdapter.MyViewHolder> {
    //click
    private Context context;
    //click
    private List<Card> cards;

    public postAdapter(List<Card> listaCards, Context context) {
        this.context = context;
        this.cards = listaCards;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_detalhe, parent, false);
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
        private ImageView imagemStatus;
        private ImageView imagemCompartilhar;
        private String eventoStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //click
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                    if(currentCard.getStatus().indexOf("Concluido") != -1) {
                        Intent itemClicado = new Intent(context, CardConcluidoActivity.class);
                        itemClicado.putExtra("titulo", currentCard.getTitulo());
                        itemClicado.putExtra("data", currentCard.getData());
                        itemClicado.putExtra("hora", currentCard.getHora());
                        itemClicado.putExtra("status", currentCard.getStatus());
                        itemClicado.putExtra("evento", currentCard.getEvento());
                        itemClicado.putExtra("idcard", String.valueOf(currentCard.getId()));
                        itemClicado.putExtra("eventoStatus", eventoStatus);
                        Activity activity = (Activity) context;
                        activity.startActivityForResult(itemClicado, 1);
                    } else {
                        Intent itemClicado = new Intent(context, CardDetalhesActivity.class);
                        itemClicado.putExtra("titulo", currentCard.getTitulo());
                        itemClicado.putExtra("data", currentCard.getData());
                        itemClicado.putExtra("hora", currentCard.getHora());
                        itemClicado.putExtra("status", currentCard.getStatus());
                        itemClicado.putExtra("evento", currentCard.getEvento());
                        itemClicado.putExtra("eventoStatus", eventoStatus);
                        itemClicado.putExtra("idcard", String.valueOf(currentCard.getId()));
                        //context.startActivity(itemClicado);
                        Activity activity = (Activity) context;
                        activity.startActivityForResult(itemClicado, 1);
                    }
                    //

                    /*
                    Intent itemClicado = new Intent(context, CardDetalhesActivity.class);
                    itemClicado.putExtra("titulo", currentCard.getTitulo());
                    itemClicado.putExtra("data", currentCard.getData());
                    itemClicado.putExtra("hora", currentCard.getHora());
                    itemClicado.putExtra("status", currentCard.getStatus());
                    itemClicado.putExtra("idcard", String.valueOf(currentCard.getId()));
                    context.startActivity(itemClicado);
                     */
                }
            });
            //click
            textTitulo = itemView.findViewById(R.id.detalheTextTitulo);
            textData = itemView.findViewById(R.id.detalheTextData);
            textHora = itemView.findViewById(R.id.detalheTextHora);
            textEvento = itemView.findViewById(R.id.detalheTextEvento);
            textStatus = itemView.findViewById(R.id.detalheTextStatus2);
            imagemStatus = itemView.findViewById(R.id.detalheImgStatus);
            imagemCompartilhar = itemView.findViewById(R.id.detalheBtnCompartilharDetalhes);
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

        if(card.getStatus().indexOf("Em aberto") != -1) {
            holder.imagemStatus.setImageResource(R.drawable.ic_aberto_foreground);
        } else {
            holder.imagemStatus.setImageResource(R.drawable.ic_concluir_foreground);
        }
        holder.imagemCompartilhar.setImageResource(R.mipmap.ic_compartilhar_foreground);

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
