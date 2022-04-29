package com.juker_enterprise.cantiquebulu.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.juker_enterprise.cantiquebulu.R;
import com.juker_enterprise.cantiquebulu.beans.Cantique;
import com.juker_enterprise.cantiquebulu.beans.Favoris;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    List<Favoris> listFavoris;



    public MyAdapter(Context ct, List<Favoris> list){
        context = ct;
        listFavoris = list;
        Log.e("sss", String.valueOf(list.size()));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_favoris_item,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtNumeroCantique.setText(listFavoris.get(position).getNumero());
        holder.txtTitreCantique.setText(listFavoris.get(position).getNom());
    }

    @Override
    public int getItemCount() {

        return listFavoris.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNumeroCantique,txtTitreCantique;
        ImageView btnDeleteCantique;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNumeroCantique = itemView.findViewById(R.id.numeroCantique);
            txtTitreCantique = itemView.findViewById(R.id.titreCantique);
            btnDeleteCantique = itemView.findViewById(R.id.btnDelete);
        }
    }
}
