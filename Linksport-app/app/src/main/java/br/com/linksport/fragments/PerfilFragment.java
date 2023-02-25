package br.com.linksport.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.linksport.AlterarDadosActivity;
import br.com.linksport.AlterarSenhaActivity;
import br.com.linksport.LoginActivity;
import br.com.linksport.MainActivity;
import br.com.linksport.PerfilActivity;
import br.com.linksport.R;
import br.com.linksport.network.ApiService;
import br.com.linksport.network.Card;
import br.com.linksport.network.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilFragment extends Fragment {

    private int CurrentProgress = 0;
    private ProgressBar progressBar;
    private static final String PREF_NAME = "LoginActivityPreferences";
    private Button btnSair;
    private Button btnEditarPerfil;
    private Button btnAlterarSenha;
    private TextView textNomePerfil;
    private TextView textEmailPerfil;
    private TextView textUsuario;
    private TextView textCardsProgresso;
    private TextView textDataPerfil;
    private TextView textEstadoPerfil;
    public static int numConcluidos;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle data = getArguments();
        int usuarioLogado = data.getInt("usuarioLogado");
        int numCards = data.getInt("numCards");
        btnSair = view.findViewById(R.id.btnSair2);
        textNomePerfil = view.findViewById(R.id.textNomePerfil2);
        textEmailPerfil = view.findViewById(R.id.textEmailPerfil2);
        textUsuario = view.findViewById(R.id.textUsuarioPerfil2);
        textDataPerfil = view.findViewById(R.id.textDataPerfil2);
        textEstadoPerfil = view.findViewById(R.id.textEstadoPerfil2);
        textCardsProgresso = view.findViewById(R.id.textCardsProgresso);
        btnEditarPerfil = view.findViewById(R.id.btnEditarPerfil);
        btnAlterarSenha = view.findViewById(R.id.btnAlterarSenha);
        progressBar = view.findViewById(R.id.progressBar2);

        ApiService.getInstance().getCards(usuarioLogado).enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                for(int i = 0; i<response.body().size(); i ++) {
                    if(response.body().get(i).getStatus().indexOf("Concluido") != -1) {
                        numConcluidos = numConcluidos+1;
                    }
                }
                //Toast.makeText(getContext(), String.valueOf(numConcluidos), Toast.LENGTH_SHORT).show();
                int porcentagem;
                if(numCards != 0){
                    porcentagem = (numConcluidos * 100/ numCards);
                } else {
                    porcentagem = 0;
                }
                textCardsProgresso.setText(porcentagem+"%");
                progressBar.setProgress(numConcluidos);
                progressBar.setMax(numCards);
                numConcluidos = 0;
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {

            }
        });

        ApiService.getInstance().buscarUsuarioPorId(usuarioLogado).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                textNomePerfil.setText("Nome: "+response.body().getNomeCompleto());
                textEmailPerfil.setText("Email: "+response.body().getEmail());
                textUsuario.setText("Usuario: "+response.body().getUsuario());
                textDataPerfil.setText("Data de nascimento: "+response.body().getDataNascimento());
                textEstadoPerfil.setText("Estado: "+response.body().getUf());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear().commit();
                Intent sair = new Intent(getContext(), LoginActivity.class);
                startActivity(sair);
                getActivity().finish();
            }
        });

        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditarPerfilDialogFragment dialogEditar = new EditarPerfilDialogFragment();
                Bundle data = new Bundle();
                data.putInt("usuarioLogado", usuarioLogado);
                data.putInt("numCards", numCards);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                dialogEditar.setArguments(data);
                dialogEditar.show(fragmentManager, dialogEditar.getTag());
            }
        });

        btnAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlterarSenhaDialog dialogSenha = new AlterarSenhaDialog();
                Bundle data = new Bundle();
                data.putInt("usuarioLogado", usuarioLogado);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                dialogSenha.setArguments(data);
                dialogSenha.show(fragmentManager, dialogSenha.getTag());
            }
        });
    }
}