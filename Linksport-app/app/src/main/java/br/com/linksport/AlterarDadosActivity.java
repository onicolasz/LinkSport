package br.com.linksport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.santalu.maskara.widget.MaskEditText;

import br.com.linksport.network.ApiService;
import br.com.linksport.network.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlterarDadosActivity extends AppCompatActivity {

    private EditText etNomeAlterar;
    private EditText etUsuarioAlterar;
    private MaskEditText etDataNascimentoAlterar;
    private Button btnCancelarAlterar;
    private Button btnConfirmarAlterar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_dados);
        etNomeAlterar = findViewById(R.id.etNomeAlterar);
        etUsuarioAlterar = findViewById(R.id.etUsuarioAlterar);
        etDataNascimentoAlterar = findViewById(R.id.etDataNascimentoAlterar);
        btnCancelarAlterar = findViewById(R.id.btnCancelarAlterar);
        btnConfirmarAlterar = findViewById(R.id.btnConfirmarAlterar);

        JsonObject paramObject = new JsonObject ();

        Intent it = getIntent();
        int usuarioLogado = Integer.parseInt(it.getStringExtra("usuarioLogado"));

        btnCancelarAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlterarDadosActivity.super.finish();
            }
        });

        btnConfirmarAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeCompleto = etNomeAlterar.getText().toString();
                String usuario = etUsuarioAlterar.getText().toString();
                String dataNascimento = etDataNascimentoAlterar.getText().toString();

                if(!nomeCompleto.isEmpty()){
                    paramObject.addProperty("nomeCompleto", nomeCompleto);
                }

                if(!usuario.isEmpty()) {
                    ApiService.getInstance().buscarUsuario(usuario).enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            if(response.body() == null) {
                                paramObject.addProperty("usuario", usuario);
                                //Toast.makeText(AlterarDadosActivity.this, "usuario: "+usuario, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {

                        }
                    });
                }

                if(etDataNascimentoAlterar.isDone()){
                    paramObject.addProperty("dataNascimento", dataNascimento);
                }

                //Toast.makeText(AlterarDadosActivity.this, paramObject.toString(), Toast.LENGTH_LONG).show();
                ApiService.getInstance().atualizarUsuario(usuarioLogado, paramObject).enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        AlterarDadosActivity.super.finish();
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {

                    }
                });
            }
        });

    }
}