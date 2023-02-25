package br.com.linksport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.linksport.network.ApiService;
import br.com.linksport.network.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {

    private int CurrentProgress = 0;
    private ProgressBar progressBar;
    private static final String PREF_NAME = "LoginActivityPreferences";
    private ImageButton btnVoltarHome3;
    private Button btnSair;
    private TextView textNomePerfil;
    private TextView textEmailPerfil;
    private TextView textUsuario;
    private TextView textDataPerfil;
    private TextView textEstadoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        btnVoltarHome3 = findViewById(R.id.btnVoltarHome3);
        btnSair = findViewById(R.id.btnSair);
        textNomePerfil = findViewById(R.id.textNomePerfil);
        textEmailPerfil = findViewById(R.id.textEmailPerfil);
        textUsuario = findViewById(R.id.textUsuarioPerfil);
        textDataPerfil = findViewById(R.id.textDataPerfil);
        textEstadoPerfil = findViewById(R.id.textEstadoPerfil);
        progressBar = findViewById(R.id.progressBar);


        Intent it = getIntent();
        int usuarioLogado = Integer.parseInt(it.getStringExtra("usuarioLogado"));
        int numCards = Integer.parseInt(it.getStringExtra("numCards"));

        CurrentProgress = numCards;
        progressBar.setProgress(CurrentProgress);
        progressBar.setMax(4);

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

        btnVoltarHome3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sairHome = new Intent(PerfilActivity.this, MainActivity.class);
                sairHome.putExtra("usuarioLogado", String.valueOf(usuarioLogado));
                startActivity(sairHome);
                finish();
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear().commit();
                Intent sair = new Intent(PerfilActivity.this, LoginActivity.class);
                startActivity(sair);
                finish();
            }
        });

        /*
        btnApagarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService.getInstance().apagarUsuario(usuarioLogado).enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        Toast.makeText(PerfilActivity.this, "Usuário apagado.", Toast.LENGTH_SHORT).show();
                        Intent sair = new Intent(PerfilActivity.this, LoginActivity.class);
                        startActivity(sair);
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {

                    }
                });
            }
        });
         */
    }

}