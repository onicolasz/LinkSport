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
import br.com.linksport.network.Article;

public class noticiaAdapter extends RecyclerView.Adapter<noticiaAdapter.MyViewHolder>{
    //click
    private Context context;
    //click
    private List<Article> noticias;

    public noticiaAdapter(List<Article> listaNoticias, Context context) {
        this.context = context;
        this.noticias = listaNoticias;
    }

    @NonNull
    @Override
    public noticiaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.noticia, parent, false);
        return new noticiaAdapter.MyViewHolder(itemLista);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        //click
        public View view;
        public Article currentNoticia;
        //click
        private TextView textNoticia;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //click
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                }
            });
            //click
            textNoticia = itemView.findViewById(R.id.textNoticia);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull noticiaAdapter.MyViewHolder holder, int position) {
        Article article = noticias.get(position);
        //click
        holder.currentNoticia = article;
        //click
        holder.textNoticia.setText(article.getTitle());
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }
}
