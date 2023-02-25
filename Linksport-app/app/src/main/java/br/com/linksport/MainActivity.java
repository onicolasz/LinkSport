package br.com.linksport;

import static java.lang.String.valueOf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import br.com.linksport.fragments.AovivoFragment;
import br.com.linksport.fragments.CardsFragment;
import br.com.linksport.fragments.ConvitesFragment;
import br.com.linksport.fragments.ExplorarFragment;
import br.com.linksport.fragments.PerfilFragment;
import br.com.linksport.network.ApiService;
import br.com.linksport.network.Convite;
import br.com.linksport.network.Evento;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.BlurViewFacade;
import eightbitlab.com.blurview.RenderEffectBlur;
import eightbitlab.com.blurview.RenderScriptBlur;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnHome;
    private ImageButton btnPerfil;
    private ImageButton btnConvites;
    private ImageButton btnEventos;
    private ImageButton btnBuscar;
    private CardsFragment cardsFragment;
    private ExplorarFragment explorarFragment;
    private ConvitesFragment convitesFragment;
    private PerfilFragment perfilFragment;
    private AovivoFragment aovivoFragment;
    private ArrayList<Evento> eventos = new ArrayList<>();
    private int usuarioLogado;
    public static int numCards;
    private BlurView blurView;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        btnHome = findViewById(R.id.btnHome);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnConvites = findViewById(R.id.btnConvites);
        btnEventos = findViewById(R.id.btnEventos);
        btnBuscar = findViewById(R.id.btnBuscar);
         */
        blurView = findViewById(R.id.blurView);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        //bottomNavigation.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Intent it = getIntent();
        usuarioLogado = Integer.parseInt(it.getStringExtra("usuarioLogado"));

        Bundle data = new Bundle();
        data.putInt("usuarioLogado", usuarioLogado);
        cardsFragment = new CardsFragment();
        cardsFragment.setArguments(data);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameHome, cardsFragment);
        fragmentTransaction.commit();

        blurBackground();

        ApiService.getInstance4().listarConvites(usuarioLogado).enqueue(new Callback<List<Convite>>() {
            @Override
            public void onResponse(Call<List<Convite>> call, Response<List<Convite>> response) {
                if(response.body().toString() != "[]") {

                    BadgeDrawable badgeDrawable = bottomNavigation.getOrCreateBadge(R.id.page_2);
                    badgeDrawable.setVisible(true);
                    badgeDrawable.setVerticalOffset(dpToPx(MainActivity.this, 10));
                    badgeDrawable.setHorizontalOffset(dpToPx(MainActivity.this, 7));
                    badgeDrawable.setNumber(response.body().size());
                    badgeDrawable.setBackgroundColor(getResources().getColor(R.color.red));
                    badgeDrawable.setBadgeTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void onFailure(Call<List<Convite>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.page_1:
                        fragmentTransaction.setCustomAnimations(R.anim.fade_in, 0, 0,
                                R.anim.fade_out);
                        fragmentTransaction.replace(R.id.frameHome, cardsFragment);
                        fragmentTransaction.commit();
                        break;
                        case R.id.page_2:
                            BadgeDrawable badgeDrawable = bottomNavigation.getOrCreateBadge(R.id.page_2);
                            badgeDrawable.setVisible(false);

                            Bundle data = new Bundle();
                            data.putInt("usuarioLogado", usuarioLogado);
                            convitesFragment = new ConvitesFragment();
                            convitesFragment.setArguments(data);
                            fragmentTransaction.setCustomAnimations(R.anim.fade_in, 0, 0,
                                    R.anim.fade_out);
                            fragmentTransaction.replace(R.id.frameHome, convitesFragment, "TAGCONVITES");
                            fragmentTransaction.commit();
                            break;
                    case R.id.page_3:
                        Content content = new Content();
                        content.execute();
                        break;
                    case R.id.page_4:
                        explorarFragment = new ExplorarFragment();
                        fragmentTransaction.setCustomAnimations(R.anim.fade_in, 0, 0,
                                R.anim.fade_out);
                        fragmentTransaction.replace(R.id.frameHome, explorarFragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.page_5:
                        Bundle data2 = new Bundle();
                        data2.putInt("usuarioLogado", usuarioLogado);
                        data2.putInt("numCards", numCards);
                        perfilFragment = new PerfilFragment();
                        perfilFragment.setArguments(data2);
                        fragmentTransaction.setCustomAnimations(R.anim.fade_in, 0, 0,
                                R.anim.fade_out);
                        fragmentTransaction.replace(R.id.frameHome, perfilFragment, "TAGPERFIL");
                        fragmentTransaction.commit();
                        break;
                }
                return true; // return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Bundle data2 = new Bundle();
            data2.putInt("usuarioLogado", usuarioLogado);
            cardsFragment = new CardsFragment();
            cardsFragment.setArguments(data2);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameHome, cardsFragment);
            fragmentTransaction.commit();
        }
    }

    private class Content extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }

        @Override
        protected void onCancelled(Void unused) {
            super.onCancelled(unused);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //ao vivo
            try {
                eventos.clear();

                String url = "https://www.placardefutebol.com.br/";
                Document doc = Jsoup.connect(url).get();

                Elements trending = doc.getElementsByClass("container content trending-box").select("a[href]");
                Elements data2 = doc.getElementById("livescore").select("a[href]");
                int size = trending.size();
                int size2 = data2.size();

                for (int i = size; i < size2; i++) {

                    String link = data2.eq(i).attr("href");
                    if(link.indexOf("2023") != -1) {
                        String str = data2.eq(i).toString();
                        String campeonatoString = link.substring(link.indexOf("/")+1, link.indexOf("/", 2)).replace("-", " ");
                        String campeonatoUpper = campeonatoString.substring(0,1).toUpperCase()+campeonatoString.substring(1, campeonatoString.indexOf(" "));
                        String campeonatoUpper2 = campeonatoString.substring(campeonatoString.indexOf(" ")).substring(1,2).toUpperCase()+campeonatoString.substring(campeonatoString.indexOf(" ")+2);
                        String campeonato2 = campeonatoUpper+" "+campeonatoUpper2;

                        Elements times2 = data2.eq(i).select("div.content").select("div.team-name");
                        String timeCasa2 = times2.eq(0).text();
                        String timeFora2 = times2.eq(1).text();

                        Elements gols2 = data2.eq(i).select("div.content").select("div.match-score");
                        String placarCasa2 = gols2.eq(0).text();
                        String placarFora2 = gols2.eq(1).text();

                        String status = data2.eq(i).select("div.content").select("div.status").text();
                        String statusOrder;

                        if(status.indexOf("MIN") != -1){
                            statusOrder = "1. "+status;
                        } else if(status.indexOf("ENCERRADO") != -1) {
                            statusOrder = "4. "+status;
                        } else if(status.indexOf("HOJE") != -1) {
                            statusOrder = "3. "+status;
                        } else if(status.indexOf("INTERVALO") != -1) {
                            statusOrder = "2. "+status;
                        } else {
                            statusOrder = "5. "+status;
                        }

                        eventos.add(new Evento(timeCasa2,timeFora2, statusOrder, campeonato2, placarCasa2, placarFora2));
                    }

                    Collections.sort(eventos, new Comparator<Evento>(){
                        public int compare(Evento obj1, Evento obj2) {
                            return obj1.getTempo().compareToIgnoreCase(obj2.getTempo());
                        }
                    });
                    Bundle data = new Bundle();
                    data.putSerializable("eventosArray", eventos);
                    aovivoFragment = new AovivoFragment();
                    aovivoFragment.setArguments(data);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in, 0, 0,
                            R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frameHome, aovivoFragment, "TAGEVENTOS");
                    fragmentTransaction.commit();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private void blurBackground() {
        float radius = 25f;

        View decorView = getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius)
                .setBlurAutoUpdate(true);
    }

    public static int dpToPx(Context context, int dp){
        Resources resources = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,resources.getDisplayMetrics()));
    }
}