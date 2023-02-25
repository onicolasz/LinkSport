package br.com.linksport.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import br.com.linksport.R;
import br.com.linksport.adapters.ViewPagerAdapter;
import br.com.linksport.network.Evento;
import eightbitlab.com.blurview.BlurView;

public class AovivoFragment extends Fragment {

    private TabLayout tabLayout;
    private NoticiasFragment noticiasFragment;
    private EventosFragment eventosFragment;
    private ArrayList<Evento> eventos = new ArrayList<>();
    private TextView textEventos;
    private TextView textFragmentAtivo;
    private BlurView blurView;
    private ViewPager2 viewPager2;

    public AovivoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aovivo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.tabAovivo);
        blurView = view.findViewById(R.id.blurView);
        viewPager2 = view.findViewById(R.id.viewPager2);

        Bundle bundle = getArguments();
        eventos = (ArrayList<Evento>) bundle.getSerializable("eventosArray");

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity(), eventos);
        viewPager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        /*
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
        }).attach();

         */

    }
}