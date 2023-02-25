package br.com.linksport;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.sql.Array;
import java.util.List;

import br.com.linksport.network.ApiService;
import br.com.linksport.network.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String PREF_NAME = "LoginActivityPreferences";
    private EditText etUsuario, etSenha;
    private Button btnLogin;
    private Button btnCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String verificaUsuario = sp.getString("usuario", "");
        String verificaSenha = sp.getString("senha", "");

        JsonObject paramObject1 = new JsonObject ();
        paramObject1.addProperty("usuario", verificaUsuario);
        paramObject1.addProperty("senha", verificaSenha);

        ApiService.getInstance().login(paramObject1).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.body() != null) {
                    //Toast.makeText(LoginActivity.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, new Gson().toJson(response.body()));
                    String usuarioLogado = String.valueOf(response.body().getId());
                    Intent in = new Intent(LoginActivity.this, MainActivity.class);
                    in.putExtra("usuarioLogado", usuarioLogado);
                    startActivity(in);
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
            }
        });

        etUsuario = findViewById(R.id.etUsuario);
        etSenha = findViewById(R.id.etSenha);
        btnLogin = findViewById(R.id.btnLogin);
        btnCadastro = findViewById(R.id.btnCadastro);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String et_usuario = etUsuario.getText().toString();
                String et_senha = etSenha.getText().toString();

                JsonObject paramObject = new JsonObject ();
                paramObject.addProperty("usuario", et_usuario);
                paramObject.addProperty("senha", et_senha);

                ApiService.getInstance().login(paramObject).enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if(response.body() != null) {
                            //Toast.makeText(LoginActivity.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, new Gson().toJson(response.body()));
                            String usuarioLogado = String.valueOf(response.body().getId());

                            SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("usuario", et_usuario);
                            editor.putString("senha", et_senha);
                            editor.commit();

                            Intent in = new Intent(LoginActivity.this, MainActivity.class);
                            in.putExtra("usuarioLogado", usuarioLogado);
                            startActivity(in);
                        } else {
                            Toast.makeText(LoginActivity.this, "Usuario ou Senha incorretos.", Toast.LENGTH_SHORT).show();
                            limparCampos();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Erro!", Toast.LENGTH_SHORT).show();
                        limparCampos();
                    }
                });
            }
        });


        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadastrar = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(cadastrar);
            }
        });

    }

    private void limparCampos(){
        etUsuario.setText("");
        etSenha.setText("");
    }
}