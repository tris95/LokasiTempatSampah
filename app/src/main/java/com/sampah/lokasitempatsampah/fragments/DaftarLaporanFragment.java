package com.sampah.lokasitempatsampah.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sampah.lokasitempatsampah.R;
import com.sampah.lokasitempatsampah.adapters.LaporanViewAdapter;
import com.sampah.lokasitempatsampah.models.Laporan;

import java.util.ArrayList;
import java.util.Arrays;

public class DaftarLaporanFragment extends Fragment {
    RecyclerView rView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Laporan> mList;
    Laporan[] data;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout rl_none, rl_conn;
    TextView tv_cobalagi;

    public DaftarLaporanFragment() {
    }
    public static DaftarLaporanFragment newInstance() {
        return new DaftarLaporanFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_daftar_laporan, container, false);

        rView = v.findViewById(R.id.rView);
        swipeRefreshLayout = v.findViewById(R.id.swipeRefresh);
        rl_none = v.findViewById(R.id.rl_none);
        rl_conn = v.findViewById(R.id.rl);
        tv_cobalagi = v.findViewById(R.id.tv_cobalagi);
        rView.addItemDecoration(new DividerItemDecoration(rView.getContext(), DividerItemDecoration.VERTICAL));

        data = new Laporan[]{
                new Laporan("1", "Jomblo", "Cari kan saya jodoh dong min", "Palembang", "085832639919", "23-09-1995", "1", ""),
                new Laporan("1", "Jomblo", "Cari kan saya jodoh dong min", "Palembang", "085832639919", "23-09-1995", "0", ""),
                new Laporan("1", "Jomblo", "Cari kan saya jodoh dong min", "Palembang", "085832639919", "23-09-1995", "2", ""),
        };

        mList = new ArrayList<>(Arrays.asList(data));

        linearLayoutManager = new LinearLayoutManager(getContext());
        rView.setLayoutManager(linearLayoutManager);
        LaporanViewAdapter laporanViewAdapter = new LaporanViewAdapter(getContext(), mList);
        rView.setAdapter(laporanViewAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //getLaporan();
            }
        });

        tv_cobalagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getLaporan();
            }
        });

        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
