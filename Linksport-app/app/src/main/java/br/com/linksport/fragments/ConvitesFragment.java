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

import java.util.List;

import br.com.linksport.R;
import br.com.linksport.adapters.conviteAdapter;
import br.com.linksport.network.ApiService;
import br.com.linksport.network.Convite;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ConvitesFragment extends Fragment {

    private RecyclerView recyclerView;

    public ConvitesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_convites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle data = getArguments();
        int usuarioLogado = data.getInt("usuarioLogado");
        recyclerView = view.findViewById(R.id.recyclerViewConvites);

        ApiService.getInstance4().listarConvites(usuarioLogado).enqueue(new Callback<List<Convite>>() {
            @Override
            public void onResponse(Call<List<Convite>> call, Response<List<Convite>> response) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                conviteAdapter conviteAdapter = new conviteAdapter(response.body(), getContext(), getActivity());
                recyclerView.setAdapter(conviteAdapter);
            }

            @Override
            public void onFailure(Call<List<Convite>> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}