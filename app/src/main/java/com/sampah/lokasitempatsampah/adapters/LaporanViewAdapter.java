package com.sampah.lokasitempatsampah.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.sampah.lokasitempatsampah.R;
import com.sampah.lokasitempatsampah.models.Laporan;
import com.sampah.lokasitempatsampah.utils.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class LaporanViewAdapter extends RecyclerView.Adapter<LaporanViewAdapter.DataObjectHolder> {
    private Context context;
    private ArrayList<Laporan> mList;


    public LaporanViewAdapter(Context context, ArrayList<Laporan> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_laporan, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.lblIsi.setText(mList.get(position).getLaporan());
        holder.lblTanggal.setText(mList.get(position).getTanggal());
        if (mList.get(position).getGambar().equals("")){
            holder.img.setVisibility(View.GONE);
            holder.rldefauld.setVisibility(View.GONE);
        }else {
            Picasso.with(context)
                    .load(Utilities.getURLImage() + mList.get(position).getGambar())
                    .fit()
                    .centerCrop()
                    .into(holder.img);
        }

        switch (mList.get(position).getStatus()) {
            case "0":
                holder.ll.setBackgroundResource(R.color.colorYellow);
                break;
            case "1":
                holder.ll.setBackgroundResource(R.color.colorGreen);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class DataObjectHolder extends RecyclerView.ViewHolder {
        CircularImageView img;
        RelativeLayout rldefauld;
        LinearLayout ll;
        TextView lblJudul, lblIsi, lblTanggal;

        DataObjectHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            rldefauld = itemView.findViewById(R.id.rldefauld);
            lblIsi = itemView.findViewById(R.id.tvIsi);
            lblTanggal = itemView.findViewById(R.id.tvTgl);
            ll = itemView.findViewById(R.id.ll);
        }
    }
}
