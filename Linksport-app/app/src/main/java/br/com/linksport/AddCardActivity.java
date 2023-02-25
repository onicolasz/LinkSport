package br.com.linksport;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.santalu.maskara.widget.MaskEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.linksport.network.ApiService;
import br.com.linksport.network.Card;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCardActivity extends AppCompatActivity {

    private ImageButton btnVoltarHome;
    private Button btnAddCard;
    private EditText etTitulo;
    private MaskEditText etData;
    private MaskEditText etHora;
    private EditText etLocalizacao;
    private Spinner spinnerEventos;
    private ArrayList<String> eventosHoje = new ArrayList<>();
    private String eventoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        spinnerEventos = findViewById(R.id.spinnerEventos);
        Intent it = getIntent();
        int usuarioLogado = Integer.parseInt(it.getStringExtra("usuarioLogado"));
        eventosHoje = it.getStringArrayListExtra("eventosHojeArray");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.color_spinner_layout, eventosHoje);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerEventos.setAdapter(spinnerAdapter);

        btnVoltarHome = (ImageButton) findViewById(R.id.btnVoltarHome);

        btnAddCard = (Button) findViewById(R.id.btnAddCard);
        etTitulo = findViewById(R.id.etTitulo);
        etData = findViewById(R.id.etData);
        etHora = findViewById(R.id.etHora);
        etLocalizacao = findViewById(R.id.etLocalizacao);

        spinnerEventos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelecionado = spinnerEventos.getSelectedItem().toString();
                String horaSelecionada = itemSelecionado.substring(itemSelecionado.indexOf("HOJE")+5);
                SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
                Date data = new Date();
                String dataFormatada = formataData.format(data);

                if(itemSelecionado.indexOf("Clique") == -1){
                    etHora.setText(horaSelecionada);
                    etData.setText(dataFormatada);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnVoltarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCardActivity.super.finish();
                Intent home = new Intent(AddCardActivity.this, MainActivity.class);
                home.putExtra("usuarioLogado", valueOf(usuarioLogado));
                finish();
                startActivity(home);
            }
        });

        btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titulo = etTitulo.getText().toString();
                String item = spinnerEventos.getSelectedItem().toString();
                String evento;
                if(item.indexOf("HOJE") != -1) {
                    evento = item.substring(0, item.indexOf("HOJE"));
                } else {
                    evento = "Sem evento";
                }
                String data = etData.getText().toString();
                String hora = etHora.getText().toString();
                String localizacao = etLocalizacao.getText().toString();
                String status = "Em aberto";

                if(titulo.isEmpty() == false){
                    if(evento.isEmpty() == false){
                        if(etData.isDone() == true){
                            if(etHora.isDone() == true){
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
                                            Toast.makeText(AddCardActivity.this, "Card criado!", Toast.LENGTH_SHORT).show();
                                            AddCardActivity.super.finish();
                                            Intent home = new Intent(AddCardActivity.this, MainActivity.class);
                                            home.putExtra("usuarioLogado", valueOf(usuarioLogado));
                                            startActivity(home);
                                        }

                                        @Override
                                        public void onFailure(Call<Card> call, Throwable t) {
                                            Toast.makeText(AddCardActivity.this, "Erro ao criar card.", Toast.LENGTH_SHORT).show();
                                            AddCardActivity.super.finish();
                                        }
                                    });

                                } else {
                                    Toast.makeText(AddCardActivity.this, "O campo localizacao não pode ser vazio.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AddCardActivity.this, "O campo hora não foi preenchido corretamente.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AddCardActivity.this, "O campo data não foi preenchido corretamente.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddCardActivity.this, "O campo evento não pode ser vazio.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddCardActivity.this, "O campo titulo não pode ser vazio.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}