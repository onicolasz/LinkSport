package br.com.linksport.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import br.com.linksport.MainActivity;
import br.com.linksport.R;
import br.com.linksport.adapters.eventoAdapter;
import br.com.linksport.network.Evento;

public class EventosFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Evento> eventos = new ArrayList<>();

    public EventosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eventos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewEventos);

        Bundle bundle = getArguments();
        eventos = (ArrayList<Evento>) bundle.getSerializable("eventosArray");


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        eventoAdapter eventoAdapter = new eventoAdapter(eventos, getContext());
        recyclerView.setAdapter(eventoAdapter);
    }
}