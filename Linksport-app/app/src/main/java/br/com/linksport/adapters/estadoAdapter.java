package br.com.linksport.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.linksport.network.Card;
import br.com.linksport.network.Estado;

public class estadoAdapter extends RecyclerView.Adapter<postAdapter.MyViewHolder>{
    //click
    private Context context;
    //click
    private List<Estado> estados;

    public estadoAdapter(List<Estado> listaEstados, Context context) {
        this.context = context;
        this.estados = listaEstados;
    }

    @NonNull
    @Override
    public postAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull postAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
