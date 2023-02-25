package br.com.linksport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import br.com.linksport.network.ApiService;
import br.com.linksport.network.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlterarSenhaActivity extends AppCompatActivity implements Validator.ValidationListener {

    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    private EditText etSenhaNova;
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    private EditText etSenhaNovaConfirmar;
    private Button btnCancelarSenha;
    private Button btnConfirmarSenha;
    private CheckBox cbMostrarSenhaAlterar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_senha);
        etSenhaNova = findViewById(R.id.etSenhaNova);
        etSenhaNovaConfirmar = findViewById(R.id.etSenhaNovaConfirmar);
        btnCancelarSenha = findViewById(R.id.btnCancelarSenha);
        btnConfirmarSenha = findViewById(R.id.btnConfirmarSenha);
        cbMostrarSenhaAlterar = findViewById(R.id.cbMostrarSenhaAlterar);

        Validator validator = new Validator(this);
        validator.setValidationListener(this);

        btnCancelarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlterarSenhaActivity.super.finish();
            }
        });

        btnConfirmarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

        cbMostrarSenhaAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbMostrarSenhaAlterar.isChecked()) {
                    etSenhaNova.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    etSenhaNova.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        String senhaNova = etSenhaNova.getText().toString();
        String senhaNovaConfirmar = etSenhaNovaConfirmar.getText().toString();

        Intent it = getIntent();
        int usuariologado = Integer.parseInt(it.getStringExtra("usuarioLogado"));

        if(senhaNova.equals(senhaNovaConfirmar)) {
            JsonObject paramObject = new JsonObject ();
            paramObject.addProperty("senha", senhaNova);
            ApiService.getInstance().atualizarUsuario(usuariologado, paramObject).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    Toast.makeText(AlterarSenhaActivity.this, "Senha alterada com sucesso", Toast.LENGTH_LONG).show();
                    AlterarSenhaActivity.super.finish();
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(AlterarSenhaActivity.this, "A senhas são diferentes, por favor digite a mesma senha duas vezes.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}