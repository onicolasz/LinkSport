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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import br.com.linksport.R;
import br.com.linksport.adapters.postOnlyViewAdapter;
import br.com.linksport.network.ApiService;
import br.com.linksport.network.Card;
import br.com.linksport.network.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExplorarFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextInputEditText et_buscar;
    private Button bt_buscar;
    private TextView buscarNome;
    private MaterialCardView cardPerfil;
    private TextView textBuscarUsuario;
    private TextView textBuscarUf;
    private TextView textBuscarData;

    public ExplorarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explorar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewExplorar);
        et_buscar = view.findViewById(R.id.et_buscar);
        bt_buscar = view.findViewById(R.id.bt_buscar);
        buscarNome = view.findViewById(R.id.textBuscarNome);
        cardPerfil = view.findViewById(R.id.cardPerfil);
        textBuscarUsuario = view.findViewById(R.id.textBuscarUsuario);
        textBuscarUf = view.findViewById(R.id.textEstadoPerfil);
        textBuscarData = view.findViewById(R.id.textDataPerfil);

        cardPerfil.setVisibility(View.GONE);
        et_buscar.setImeOptions(EditorInfo.IME_ACTION_NONE);

        bt_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuarioBusca = et_buscar.getText().toString();

                ApiService.getInstance().buscarUsuario(usuarioBusca).enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        int id = response.body().getId();
                        buscarNome.setText(response.body().getNomeCompleto());
                        textBuscarUsuario.setText(response.body().getUsuario());
                        textBuscarUf.setText(response.body().getUf());
                        textBuscarData.setText(response.body().getDataNascimento());
                        ApiService.getInstance().getCards(id).enqueue(new Callback<List<Card>>() {
                            @Override
                            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(layoutManager);
                                postOnlyViewAdapter postOnlyViewAdapter = new postOnlyViewAdapter(response.body(), getContext());
                                recyclerView.setAdapter(postOnlyViewAdapter);
                                cardPerfil.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFailure(Call<List<Card>> call, Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {

                    }
                });
            }
        });

    }
}