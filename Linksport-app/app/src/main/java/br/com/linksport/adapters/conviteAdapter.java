package br.com.linksport.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import java.util.List;

import br.com.linksport.R;
import br.com.linksport.network.ApiService;
import br.com.linksport.network.Card;
import br.com.linksport.network.Convite;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class conviteAdapter extends RecyclerView.Adapter<conviteAdapter.MyViewHolder>{

    private List<Convite> convites;
    private Context context;
    private Activity activity;

    public conviteAdapter(List<Convite> listaConvites, Context context, Activity activity) {
        this.convites = listaConvites;
        this.context = context;
        this.activity = activity;
    }


    @NonNull
    @Override
    public conviteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.convite, parent, false);
        return new conviteAdapter.MyViewHolder(itemLista);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        //click
        public View view;
        public Convite currentConvite;
        //click
        private TextView textConvidado;
        private TextView textTitulo;
        private TextView textData;
        private TextView textEvento;
        private TextView textHora;
        private ImageView imagemAceitar;
        private ImageView imagemRecusar;
        private String eventoStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textConvidado = itemView.findViewById(R.id.conviteTextConvidado);
            textTitulo = itemView.findViewById(R.id.conviteTextTitulo);
            textData = itemView.findViewById(R.id.conviteTextData);
            textHora = itemView.findViewById(R.id.conviteTextHora);
            textEvento = itemView.findViewById(R.id.detalheTextEvento3);
            imagemAceitar = itemView.findViewById(R.id.btnConviteAceitar);
            imagemRecusar = itemView.findViewById(R.id.btnConviteRecusar);

            //click
            imagemAceitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    JsonObject paramObject = new JsonObject ();
                    paramObject.addProperty("resposta", "Aceitou");

                    ApiService.getInstance4().atualizarConvite(currentConvite.getId(), paramObject).enqueue(new Callback<Convite>() {
                        @Override
                        public void onResponse(Call<Convite> call, Response<Convite> response) {
                            JsonObject paramObject2 = new JsonObject ();
                            paramObject2.addProperty("titulo", currentConvite.getCard().getTitulo());
                            paramObject2.addProperty("data", currentConvite.getCard().getData());
                            paramObject2.addProperty("status", currentConvite.getCard().getStatus());
                            paramObject2.addProperty("evento", currentConvite.getCard().getEvento());
                            paramObject2.addProperty("hora", currentConvite.getCard().getHora());
                            paramObject2.addProperty("localizacao", currentConvite.getCard().getLocalizacao());

                            ApiService.getINSTANCE2().addCard(currentConvite.getUsuario().getId(), paramObject2).enqueue(new Callback<Card>() {
                                @Override
                                public void onResponse(Call<Card> call, Response<Card> response) {
                                    Toast.makeText(context, "Você aceitou o convite", Toast.LENGTH_SHORT).show();
                                    convites.remove(currentConvite);
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Call<Card> call, Throwable t) {

                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<Convite> call, Throwable t) {

                        }
                    });
                }
            });

            imagemRecusar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    JsonObject paramObject = new JsonObject ();
                    paramObject.addProperty("resposta", "Recusou");

                    ApiService.getInstance4().atualizarConvite(currentConvite.getId(), paramObject).enqueue(new Callback<Convite>() {
                        @Override
                        public void onResponse(Call<Convite> call, Response<Convite> response) {
                            Toast.makeText(context, "Você recusou o convite", Toast.LENGTH_SHORT).show();
                            convites.remove(currentConvite);
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<Convite> call, Throwable t) {

                        }
                    });

                }
            });
            //click
        }
    }

    @Override
    public void onBindViewHolder(@NonNull conviteAdapter.MyViewHolder holder, int position) {
        Convite convite = convites.get(position);
        //click
        holder.currentConvite = convite;
        //click
        holder.textConvidado.setText("Convidado por "+ convite.getCard().getUsuario().getUsuario());
        holder.textTitulo.setText(convite.getCard().getTitulo());
        holder.textData.setText(convite.getCard().getData());
        holder.textHora.setText(convite.getCard().getHora());
        holder.imagemAceitar.setImageResource(R.drawable.ic_aceitar_foreground);
        holder.imagemRecusar.setImageResource(R.drawable.ic_recusar_foreground);
        holder.textEvento.setText(convite.getCard().getEvento());

        if(convite.getCard().getEvento().indexOf("Sem evento") != -1) {
            holder.textEvento.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return convites.size();
    }
}
