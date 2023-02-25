package br.com.linksport.adapters;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import br.com.linksport.fragments.EventosFragment;
import br.com.linksport.fragments.NoticiasFragment;
import br.com.linksport.network.Evento;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private ArrayList<Evento> eventos;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Evento> eventos) {
        super(fragmentActivity);
        this.eventos = eventos;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new NoticiasFragment();
            case 0:
            default:
                Bundle data = new Bundle();
                data.putSerializable("eventosArray", eventos);
                EventosFragment eventosFragment = new EventosFragment();
                eventosFragment.setArguments(data);
                return eventosFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
