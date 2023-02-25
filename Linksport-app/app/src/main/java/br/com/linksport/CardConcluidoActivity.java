package br.com.linksport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import br.com.linksport.network.ApiService;
import br.com.linksport.network.Card;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardConcluidoActivity extends AppCompatActivity {

    private TextView detalheTextTitulo;
    private TextView detalheTextData;
    private TextView detalheTextHora;
    private TextView detalheTextStatus;
    private ImageView detalheImgStatus;
    private ImageButton detalheBtnCompartilhar;
    private ImageButton btnVoltarHome2;
    private ImageButton detalheBtnApagar;
    private TextView detalheTextApagar;
    private ConstraintLayout constraintCard;
    private TextView DetalheTextEventoConcluido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_concluido);

        constraintCard = findViewById(R.id.constraintCard);
        btnVoltarHome2 = findViewById(R.id.btnVoltarHome2);
        detalheBtnCompartilhar = findViewById(R.id.detalheBtnCompartilharDetalhes);
        detalheTextTitulo = findViewById(R.id.detalheTextTitulo);
        detalheTextData = findViewById(R.id.detalheTextData);
        detalheTextHora = findViewById(R.id.detalheTextHora);
        detalheTextStatus = findViewById(R.id.detalheTextStatus);
        detalheBtnApagar = findViewById(R.id.detalheBtnApagar);
        detalheTextApagar = findViewById(R.id.detalheTextApagar);
        detalheImgStatus = findViewById(R.id.detalheImgStatus);
        DetalheTextEventoConcluido = findViewById(R.id.detalheTextEventoConcluido);
        Intent it = getIntent();
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
        DetalheTextEventoConcluido.setText(evento);

        if(eventoStatus.contains("Ao vivo")) {
            DetalheTextEventoConcluido.setBackgroundResource(R.drawable.custom_status_aovivo);
            DetalheTextEventoConcluido.setText("● AO VIVO "+evento);
        } else if(eventoStatus.contains("Encerrado")) {
            DetalheTextEventoConcluido.setBackgroundResource(R.drawable.custom_status_encerrado);
            DetalheTextEventoConcluido.setText(evento+" ENCERRADO");
        } else {
            DetalheTextEventoConcluido.setBackgroundResource(R.drawable.custom_status_hoje);
            DetalheTextEventoConcluido.setText("AINDA HOJE "+evento);
        }

        if(evento.contains("Sem evento")){
            DetalheTextEventoConcluido.setVisibility(View.GONE);
        }

        btnVoltarHome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardConcluidoActivity.super.finish();
            }
        });

        detalheBtnApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiService.getINSTANCE2().apagarCard(idcard).enqueue(new Callback<Card>() {
                    @Override
                    public void onResponse(Call<Card> call, Response<Card> response) {
                        Toast.makeText(CardConcluidoActivity.this, "Card apagado!", Toast.LENGTH_SHORT).show();
                        CardConcluidoActivity.super.finish();
                    }

                    @Override
                    public void onFailure(Call<Card> call, Throwable t) {
                        Toast.makeText(CardConcluidoActivity.this, "Erro ao apagar card.", Toast.LENGTH_SHORT).show();
                        CardConcluidoActivity.super.finish();
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

        detalheBtnCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                detalheBtnApagar.setVisibility(View.GONE);
                detalheTextApagar.setVisibility(View.GONE);

                Intent compartilhar = new Intent(Intent.ACTION_SEND);
                compartilhar.setType("image/png");

                Bitmap bitmap = getBitmapFromView(constraintCard);

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