package br.com.linksport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;

import br.com.linksport.adapters.postAdapter;
import br.com.linksport.network.ApiService;
import br.com.linksport.network.Card;
import br.com.linksport.network.Convite;
import br.com.linksport.network.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardDetalhesActivity extends AppCompatActivity {

    private TextView detalheTextTitulo;
    private TextView detalheTextData;
    private TextView detalheTextHora;
    private TextView detalheTextStatus;
    private TextView detalheTextConcluir;
    private ImageView detalheImgStatus;
    private ImageButton detalheBtnConvite;
    private TextView detalheTextConvite;
    private TextView detalheTextCompartilhar;
    private ImageButton detalheBtnCompartilharDetalhes;
    private ImageButton btnVoltarHome2;
    private ImageButton detalheBtnConcluir;
    private ImageButton detalheBtnApagar;
    private TextView detalheTextApagar;
    private TextView detalheTextEvento2;
    private ConstraintLayout constraintCardDetalhes;
    private String usuarioConvite = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detalhes);

        btnVoltarHome2 = findViewById(R.id.btnVoltarHome2);
        detalheTextTitulo = findViewById(R.id.detalheTextTitulo);
        detalheTextData = findViewById(R.id.detalheTextData);
        detalheTextHora = findViewById(R.id.detalheTextHora);
        detalheTextStatus = findViewById(R.id.detalheTextStatus);
        constraintCardDetalhes = findViewById(R.id.constraintCardDetalhes);
        detalheBtnConcluir = findViewById(R.id.detalheBtnConcluir);
        detalheTextConcluir = findViewById(R.id.detalheTextConcluir);
        detalheBtnApagar = findViewById(R.id.detalheBtnApagar);
        detalheTextApagar = findViewById(R.id.detalheTextApagar);
        detalheImgStatus = findViewById(R.id.detalheImgStatus);
        detalheBtnCompartilharDetalhes = findViewById(R.id.detalheBtnCompartilharDetalhes);
        detalheTextCompartilhar = findViewById(R.id.detalheTextCompartilhar);
        detalheBtnConvite = findViewById(R.id.detalheBtnConvite);
        detalheTextEvento2 = findViewById(R.id.detalheTextEvento2);
        Intent it = getIntent();
        Card currentcard = it.getExtras().getParcelable("currentcard");
        String titulo = it.getStringExtra("titulo");
        String data = it.getStringExtra("data");
        String hora = it.getStringExtra("hora");
        String status = it.getStringExtra("status");
        String evento = it.getStringExtra("evento");
        String eventoStatus = it.getStringExtra("eventoStatus");
        int idcard = Integer.parseInt(it.getStringExtra("idcard"));
        detalheTextTitulo.setText(titulo);
        detalheTextData.setText(data);
        detalheTextHora.setText(hora);
        detalheTextStatus.setText(status);
        detalheTextEvento2.setText(evento);

        if(eventoStatus.contains("Ao vivo")) {
            detalheTextEvento2.setBackgroundResource(R.drawable.custom_status_aovivo);
            detalheTextEvento2.setText("● AO VIVO "+evento);
        } else if(eventoStatus.contains("Encerrado")) {
            detalheTextEvento2.setBackgroundResource(R.drawable.custom_status_encerrado);
            detalheTextEvento2.setText(evento+" ENCERRADO");
        } else {
            detalheTextEvento2.setBackgroundResource(R.drawable.custom_status_hoje);
            detalheTextEvento2.setText("AINDA HOJE "+evento);
        }

        if(evento.contains("Sem evento")){
            detalheTextEvento2.setVisibility(View.INVISIBLE);
        }

        btnVoltarHome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardDetalhesActivity.super.finish();
            }
        });

        detalheBtnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject paramObject = new JsonObject ();
                paramObject.addProperty("status", "Concluido");
                //update status
                //Toast.makeText(CardDetalhesActivity.this, "Concluido", Toast.LENGTH_SHORT).show();
                ApiService.getINSTANCE2().atualizarCard(idcard, paramObject).enqueue(new Callback<Card>() {
                    @Override
                    public void onResponse(Call<Card> call, Response<Card> response) {
                        Toast.makeText(CardDetalhesActivity.this, "Concluido", Toast.LENGTH_SHORT).show();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Card> call, Throwable t) {
                        Toast.makeText(CardDetalhesActivity.this, "Erro ao concluir.", Toast.LENGTH_SHORT).show();
                        CardDetalhesActivity.super.finish();
                    }
                });
            }
        });

        detalheTextConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detalheBtnConcluir.callOnClick();
                //update status
                Toast.makeText(CardDetalhesActivity.this, "Concluido", Toast.LENGTH_SHORT).show();
            }
        });

        detalheBtnApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiService.getINSTANCE2().apagarCard(idcard).enqueue(new Callback<Card>() {
                    @Override
                    public void onResponse(Call<Card> call, Response<Card> response) {
                        Toast.makeText(CardDetalhesActivity.this, "Card apagado!", Toast.LENGTH_SHORT).show();
                        //CardDetalhesActivity.super.finish();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Card> call, Throwable t) {
                        Toast.makeText(CardDetalhesActivity.this, "Erro ao apagar card.", Toast.LENGTH_SHORT).show();
                        CardDetalhesActivity.super.finish();
                    }
                });
            }
        });

        detalheTextApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detalheBtnApagar.callOnClick();
            }
        });

        detalheBtnCompartilharDetalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                detalheBtnApagar.setVisibility(View.GONE);
                detalheTextApagar.setVisibility(View.GONE);

                Intent compartilhar = new Intent(Intent.ACTION_SEND);
                compartilhar.setType("image/png");

                Bitmap bitmap = getBitmapFromView(constraintCardDetalhes);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

                String path = MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"Card",null);
                Uri uri = Uri.parse(path);

                detalheBtnApagar.setVisibility(View.VISIBLE);
                detalheTextApagar.setVisibility(View.VISIBLE);

                compartilhar.putExtra(Intent.EXTRA_STREAM,uri);
                startActivity(Intent.createChooser(compartilhar, "Compartilhar Card"));

            }
        });

        detalheBtnConvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CardDetalhesActivity.this);
                builder.setTitle("Convidar");

                final EditText input = new EditText(CardDetalhesActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setHint("Digite o usuario para convidar");
                builder.setView(input);

                builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        usuarioConvite = input.getText().toString();
                        ApiService.getInstance().buscarUsuario(usuarioConvite).enqueue(new Callback<Usuario>() {
                            @Override
                            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                int idUsuario = response.body().getId();

                                ApiService.getInstance4().enviarConvite(idUsuario, idcard).enqueue(new Callback<Convite>() {
                                    @Override
                                    public void onResponse(Call<Convite> call, Response<Convite> response) {
                                        Toast.makeText(CardDetalhesActivity.this, "Convite enviado", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<Convite> call, Throwable t) {

                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Usuario> call, Throwable t) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

    }

    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
}