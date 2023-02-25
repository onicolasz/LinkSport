package br.com.linksport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.santalu.maskara.widget.MaskEditText;

import java.util.List;
import java.util.regex.Pattern;

import br.com.linksport.network.ApiService;
import br.com.linksport.network.Estado;
import br.com.linksport.network.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity implements Validator.ValidationListener {

    private ImageButton btnVoltarHome3;
    private TextInputEditText etNomeCadastro;
    @Email
    private TextInputEditText etEmailCadastro;
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    private TextInputEditText etSenhaCadastro;
    private MaskEditText etDataNascimento;
    private Spinner spEstados;
    private ImageButton btnAddFoto;
    private Button btnCadastrar;
    private EditText etUsuarioCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Validator validator = new Validator(this);
        validator.setValidationListener(this);

        etNomeCadastro = findViewById(R.id.etNomeCadastro);
        etEmailCadastro = findViewById(R.id.etEmailCadastro);
        etUsuarioCadastro = findViewById(R.id.etUsuarioCadastro);
        etSenhaCadastro = findViewById(R.id.etSenhaCadastro);
        etDataNascimento = findViewById(R.id.etDataNascimento);
        spEstados = findViewById(R.id.spEstados);
        btnVoltarHome3 = findViewById(R.id.btnVoltarHome3);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        ApiService.getINSTANCE3().getEstados().enqueue(new Callback<List<Estado>>() {
            @Override
            public void onResponse(Call<List<Estado>> call, Response<List<Estado>> response) {
                if (response.body() != null) {
                    List<Estado> estadoList = response.body();
                    String[] items = new String[estadoList.size()];
                    for (int i = 0; i < estadoList.size(); i++) {
                        items[i] = estadoList.get(i).getNome();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CadastroActivity.this, R.layout.color_spinner_layout, items);
                    spEstados.setAdapter(adapter);
                    adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                }
            }
            @Override
            public void onFailure(Call<List<Estado>> call, Throwable t) {

            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

        btnVoltarHome3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CadastroActivity.super.finish();
                Intent login = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });

    }

    @Override
    public void onValidationSucceeded() {
        String nomeCompleto = etNomeCadastro.getText().toString();
        String usuario = etUsuarioCadastro.getText().toString();
        String email = etEmailCadastro.getText().toString();
        String senha = etSenhaCadastro.getText().toString();
        String dataNascimento = etDataNascimento.getText().toString();
        String uf = spEstados.getSelectedItem().toString();

        ApiService.getInstance().buscarEmail(email).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if(response.body() == null) {

                    ApiService.getInstance().buscarUsuario(usuario).enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                            if(usuario.isEmpty() == false) {

                                if(response.body() == null) {

                                    if (nomeCompleto.isEmpty() == false) {
                                        if (etDataNascimento.isDone() == true) {
                                            JsonObject paramObject = new JsonObject ();
                                            paramObject.addProperty("nomeCompleto", nomeCompleto);
                                            paramObject.addProperty("usuario", usuario);
                                            paramObject.addProperty("email", email);
                                            paramObject.addProperty("senha", senha);
                                            paramObject.addProperty("dataNascimento", dataNascimento);
                                            paramObject.addProperty("uf", uf);

                                            ApiService.getInstance().cadastrarUsuario(paramObject).enqueue(new Callback<Usuario>() {
                                                @Override
                                                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                                    Toast.makeText(CadastroActivity.this, "Usuário criado!", Toast.LENGTH_SHORT).show();
                                                    CadastroActivity.super.finish();
                                                    Intent login = new Intent(CadastroActivity.this, LoginActivity.class);
                                                    startActivity(login);
                                                }

                                                @Override
                                                public void onFailure(Call<Usuario> call, Throwable t) {
                                                    Toast.makeText(CadastroActivity.this, "Erro ao criar usuário.", Toast.LENGTH_SHORT).show();
                                                    CadastroActivity.super.finish();
                                                    Intent login = new Intent(CadastroActivity.this, LoginActivity.class);
                                                    startActivity(login);

                                                }
                                            });
                                        } else {
                                            Toast.makeText(CadastroActivity.this, "O campo data de nascimento não foi preenchido corretamente.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(CadastroActivity.this, "O campo nome completo não pode ser vazio.", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(CadastroActivity.this, "Este usuário já esta em uso por outra conta.", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(CadastroActivity.this, "O campo usuário não pode ser vazio.", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {

                        }
                    });

                } else {
                    Toast.makeText(CadastroActivity.this, "Este email já esta cadastrado em outra conta.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

            }
        });
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