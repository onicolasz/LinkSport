package br.com.linksport.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.JsonObject;
import com.santalu.maskara.widget.MaskEditText;

import br.com.linksport.R;
import br.com.linksport.network.ApiService;
import br.com.linksport.network.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarPerfilDialogFragment extends DialogFragment {

    private EditText etNomeAlterar;
    private EditText etUsuarioAlterar;
    private MaskEditText etDataNascimentoAlterar;
    private Button btnCancelarAlterar;
    private Button btnConfirmarAlterar;
    private PerfilFragment perfilFragment;
    private int usuarioLogado;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.editar_perfil, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        etNomeAlterar = view.findViewById(R.id.etNomeAlterar2);
        etUsuarioAlterar =  view.findViewById(R.id.etUsuarioAlterar2);
        etDataNascimentoAlterar =  view.findViewById(R.id.etDataNascimentoAlterar2);
        btnCancelarAlterar =  view.findViewById(R.id.btnCancelarAlterar2);
        btnConfirmarAlterar =  view.findViewById(R.id.btnConfirmarAlterar2);

        JsonObject paramObject = new JsonObject ();

        Bundle bundle = getArguments();
        usuarioLogado = bundle.getInt("usuarioLogado");
        int numCards = bundle.getInt("numCards");

        btnCancelarAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
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
                        Bundle data = new Bundle();
                        data.putInt("usuarioLogado", usuarioLogado);
                        data.putInt("numCards", numCards);
                        perfilFragment = new PerfilFragment();
                        perfilFragment.setArguments(data);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.fade_in, 0, 0,
                                R.anim.fade_out);
                        fragmentTransaction.replace(R.id.frameHome, perfilFragment, "TAGPERFIL");
                        fragmentTransaction.commit();

                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {

                    }
                });
            }
        });
    }
}
