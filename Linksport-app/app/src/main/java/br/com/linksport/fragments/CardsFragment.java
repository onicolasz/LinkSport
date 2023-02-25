package br.com.linksport.fragments;

import static java.lang.String.valueOf;

import static br.com.linksport.MainActivity.numCards;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.linksport.AddCardActivity;
import br.com.linksport.LoginActivity;
import br.com.linksport.MainActivity;
import br.com.linksport.R;
import br.com.linksport.adapters.postAdapter;
import br.com.linksport.network.ApiService;
import br.com.linksport.network.Card;
import br.com.linksport.network.CardService;
import eightbitlab.com.blurview.BlurView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CardsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton floatingBtnAddCard;
    private CardsFragment reloadFragment;
    private ArrayList<String> eventosHoje = new ArrayList<>();
    private int usuarioLogado;

    public CardsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cards, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle data = getArguments();
        usuarioLogado = data.getInt("usuarioLogado");
        recyclerView = view.findViewById(R.id.recyclerViewCards);
        floatingBtnAddCard = view.findViewById(R.id.floatingBtnAddCard);

        Content2 content2 = new Content2();
        content2.execute();

        floatingBtnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCardDialogFragment dialog = new AddCardDialogFragment();
                Bundle data = new Bundle();
                data.putSerializable("eventosHoje", eventosHoje);
                data.putInt("usuarioLogado", usuarioLogado);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                dialog.setArguments(data);
                dialog.show(fragmentManager, dialog.getTag());
            }
        });

        ApiService.getInstance().getCards(usuarioLogado).enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                postAdapter postAdapter = new postAdapter(response.body(), getContext());
                recyclerView.setAdapter(postAdapter);

                numCards = response.body().size();
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class Content2 extends AsyncTask<Void, Void, Void> {

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
                eventosHoje.clear();
                eventosHoje.add("Clique para selecionar");

                String url = "https://www.placardefutebol.com.br/";
                Document doc = Jsoup.connect(url).get();

                Elements trending = doc.getElementsByClass("container content trending-box").select("a[href]");
                Elements data2 = doc.getElementById("livescore").select("a[href]");
                int size = trending.size();
                int size2 = data2.size();

                for (int i = size; i < size2; i++) {

                    String link = data2.eq(i).attr("href");
                    if(link.indexOf("2023") != -1) {
                        Elements times2 = data2.eq(i).select("div.content").select("div.team-name");
                        String timeCasa2 = times2.eq(0).text();
                        String timeFora2 = times2.eq(1).text();

                        String status = data2.eq(i).select("div.content").select("div.status").text();
                        if(status.indexOf("HOJE") != -1){
                            eventosHoje.add(timeCasa2+" x "+timeFora2+" "+status);
                        }
                    }
                    /*
                    Intent addCard = new Intent(getContext(), AddCardActivity.class);
                    addCard.putExtra("usuarioLogado", valueOf(usuarioLogado));
                    addCard.putStringArrayListExtra("eventosHojeArray", eventosHoje);
                    startActivity(addCard);

                     */
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
    /*
    public void ShowDialog(View view){
        AddCardDialogFragment dialog = new AddCardDialogFragment();
        Bundle data = new Bundle();
        data.putSerializable("eventosArray", eventosHoje);
        dialog.setArguments(data);
        dialog.show(getActivity().getSupportFragmentManager(), dialog.getTag());
    }
     */
}