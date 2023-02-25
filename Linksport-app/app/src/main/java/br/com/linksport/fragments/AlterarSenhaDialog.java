package br.com.linksport.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.ArrayList;
import java.util.List;

import br.com.linksport.AlterarSenhaActivity;
import br.com.linksport.R;
import br.com.linksport.network.ApiService;
import br.com.linksport.network.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlterarSenhaDialog extends DialogFragment {

    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    private TextInputEditText etSenhaNova;
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    private TextInputEditText etSenhaNovaConfirmar;
    private Button btnCancelarSenha;
    private Button btnConfirmarSenha;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.alterar_senha, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        etSenhaNova = view.findViewById(R.id.etSenhaNova2);
        etSenhaNovaConfirmar = view.findViewById(R.id.etSenhaNovaConfirmar2);
        btnCancelarSenha = view.findViewById(R.id.btnCancelarSenha2);
        btnConfirmarSenha = view.findViewById(R.id.btnConfirmarSenha2);

        Validator validator = new Validator(getActivity());

        btnCancelarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnConfirmarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
    }

    public void onValidationSucceeded() {
        String senhaNova = etSenhaNova.getText().toString();
        String senhaNovaConfirmar = etSenhaNovaConfirmar.getText().toString();

        Bundle bundle = getArguments();
        int usuarioLogado = bundle.getInt("usuarioLogado");

        if(senhaNova.equals(senhaNovaConfirmar)) {
            JsonObject paramObject = new JsonObject ();
            paramObject.addProperty("senha", senhaNova);
            ApiService.getInstance().atualizarUsuario(usuarioLogado, paramObject).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    Toast.makeText(getContext(), "Senha alterada com sucesso", Toast.LENGTH_LONG).show();
                    dismiss();
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(getContext(), "A senhas são diferentes, por favor digite a mesma senha duas vezes.", Toast.LENGTH_LONG).show();
        }
    }

    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
