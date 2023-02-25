package br.com.linksport.fragments;

import static java.lang.String.valueOf;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.JsonObject;
import com.santalu.maskara.widget.MaskEditText;

import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.linksport.AddCardActivity;
import br.com.linksport.LoginActivity;
import br.com.linksport.MainActivity;
import br.com.linksport.R;
import br.com.linksport.adapters.postAdapter;
import br.com.linksport.network.ApiService;
import br.com.linksport.network.Card;
import br.com.linksport.network.Evento;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCardDialogFragment extends DialogFragment {

    private Button btnAddCard2;
    private Spinner spinnerEventos2;
    private EditText etTitulo2;
    private MaskEditText etData2;
    private MaskEditText etHora2;
    private EditText etLocalizacao2;
    private CardsFragment cardsFragment;
    private ArrayList<String> eventosHoje2 = new ArrayList<>();
    private String eventoSelecionado;
    private int usuarioLogado;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_card, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnAddCard2 = view.findViewById(R.id.btnAddCard2);
        spinnerEventos2 = view.findViewById(R.id.spinnerEventos2);
        etTitulo2 = view.findViewById(R.id.etTitulo2);
        etData2 = view.findViewById(R.id.etData2);
        etHora2 = view.findViewById(R.id.etHora2);
        etLocalizacao2 = view.findViewById(R.id.etLocalizacao2);

        Bundle bundle = getArguments();
        eventosHoje2 = (ArrayList<String>) bundle.getSerializable("eventosHoje");
        usuarioLogado = bundle.getInt("usuarioLogado");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.color_spinner_layout, eventosHoje2);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerEventos2.setAdapter(spinnerAdapter);

        spinnerEventos2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelecionado = spinnerEventos2.getSelectedItem().toString();
                String horaSelecionada = itemSelecionado.substring(itemSelecionado.indexOf("HOJE")+5);
                SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
                Date data = new Date();
                String dataFormatada = formataData.format(data);

                if(itemSelecionado.indexOf("Clique") == -1){
                    etHora2.setText(horaSelecionada);
                    etData2.setText(dataFormatada);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAddCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titulo = etTitulo2.getText().toString();
                String item = spinnerEventos2.getSelectedItem().toString();
                String evento;
                if(item.indexOf("HOJE") != -1) {
                    evento = item.substring(0, item.indexOf("HOJE"));
                } else {
                    evento = "Sem evento";
                }
                String data = etData2.getText().toString();
                String hora = etHora2.getText().toString();
                String localizacao = etLocalizacao2.getText().toString();
                String status = "Em aberto";

                if(titulo.isEmpty() == false){
                    if(evento.isEmpty() == false){
                        if(etData2.isDone() == true){
                            if(etHora2.isDone() == true){
                                if(localizacao.isEmpty() == false){

                                    JsonObject paramObject = new JsonObject ();
                                    paramObject.addProperty("titulo", titulo);
                                    paramObject.addProperty("data", data);
                                    paramObject.addProperty("status", status);
                                    paramObject.addProperty("evento", evento);
                                    paramObject.addProperty("hora", hora);
                                    paramObject.addProperty("localizacao", localizacao);

                                    ApiService.getINSTANCE2().addCard(usuarioLogado, paramObject).enqueue(new Callback<Card>() {
                                        @Override
                                        public void onResponse(Call<Card> call, Response<Card> response) {
                                            Toast.makeText(getActivity(), "Card criado!", Toast.LENGTH_SHORT).show();
                                            Bundle data = new Bundle();
                                            data.putInt("usuarioLogado", usuarioLogado);
                                            cardsFragment = new CardsFragment();
                                            cardsFragment.setArguments(data);
                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.frameHome, cardsFragment);
                                            fragmentTransaction.commit();
                                            dismiss();
                                        }

                                        @Override
                                        public void onFailure(Call<Card> call, Throwable t) {
                                            Toast.makeText(getActivity(), "Erro ao criar card.", Toast.LENGTH_SHORT).show();
                                            dismiss();
                                        }
                                    });

                                } else {
                                    Toast.makeText(getActivity(), "O campo localizacao não pode ser vazio.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "O campo hora não foi preenchido corretamente.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "O campo data não foi preenchido corretamente.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "O campo evento não pode ser vazio.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "O campo titulo não pode ser vazio.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
