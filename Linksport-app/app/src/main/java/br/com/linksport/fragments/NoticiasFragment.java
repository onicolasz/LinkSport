package br.com.linksport.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.linksport.R;
import br.com.linksport.adapters.noticiaAdapter;
import br.com.linksport.network.ApiService;
import br.com.linksport.network.NewsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticiasFragment extends Fragment {

    private RecyclerView recyclerView;

    public NoticiasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_noticias, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewNoticias);

        ApiService.getNoticiaService().listarNoticias().enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                noticiaAdapter noticiaAdapter = new noticiaAdapter(response.body().getArticles(), getContext());
                recyclerView.setAdapter(noticiaAdapter);
            }
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(view.getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}