package com.hive_coorporation.cantiquebulu.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.hive_coorporation.cantiquebulu.MainPageFragment;
import com.hive_coorporation.cantiquebulu.R;
import com.hive_coorporation.cantiquebulu.beans.Cantique;
import com.hive_coorporation.cantiquebulu.beans.Favoris;
import com.hive_coorporation.cantiquebulu.sqlDataBase.MyDataBaseHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    List<Favoris> listFavoris;

    MyDataBaseHelper myDB;
    Cantique cantique;



    public MyAdapter(Context ct, List<Favoris> list){
        context = ct;
        listFavoris = list;
        setHasStableIds(true);
        Log.e("sss", String.valueOf(list.size()));
    }


    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_favoris_item,parent,false);


        return new MyViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtNumeroCantique.setText(listFavoris.get(position).getNumero());
        holder.txtTitreCantique.setText(listFavoris.get(position).getNom());



        // button delete
        holder.btnDeleteCantique.setOnClickListener(v -> {
            myDB = new MyDataBaseHelper(context);
            String numeroString ;
            if(holder.txtNumeroCantique.length()<2){
                numeroString ="00"+listFavoris.get(position).getNumero();
            }else if (holder.txtNumeroCantique.length()<3){
                numeroString ="0"+listFavoris.get(position).getNumero();
            }else{
                numeroString = listFavoris.get(position).getNumero();
            }


            myDB.deleteFavorisItem(numeroString);
            listFavoris.remove(position);
            holder.adapter.notifyDataSetChanged();
            //notifyDataSetChanged();
        });


        //Constraint
        holder.constraintLayout.setOnClickListener(v -> {
             Toast.makeText(context, listFavoris.get(position).getNumero(), Toast.LENGTH_SHORT).show();
            openCantique(listFavoris.get(position).getId());

            AppCompatActivity activity = (AppCompatActivity)v.getContext();

            Fragment cantiqueBody = new MainPageFragment();
            Bundle args = new Bundle();
            args.putString("param1",listFavoris.get(position).getNumero());
            args.putInt("param2",0);
            cantiqueBody.setArguments(args);

            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,cantiqueBody).commit();
        });


    }


    public String openCantique(int rawId){
        String corpCantique = null;
        // Read the file :open a InputStream on it
        InputStream inputStream = context.getResources().openRawResource(rawId);
        try {
            if (inputStream != null) {
                // open a reader on the inputStream
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"Cp1252"));

                // String used to store the lines
                String str;
                String titre;
                StringBuilder buf = new StringBuilder();

                // Read the file
                int i =0;
                while ((str = reader.readLine()) != null) {


                    if (i==1)
                        titre = str;
                    i++;

                    buf.append(str).append("\r\n");


                }

                // Close streams
                reader.close();
                inputStream.close();
                corpCantique = buf.toString();
            }
        } catch (IOException e) {
            Toast.makeText(context , "FileNotFoundException", Toast.LENGTH_LONG).show();
            corpCantique = "FileNotFoundException " + e.getMessage();
        }

        return corpCantique;
    }


    @Override
    public int getItemCount() {

        return listFavoris.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNumeroCantique,txtTitreCantique;
        ImageView btnDeleteCantique;
        private MyAdapter adapter;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNumeroCantique = itemView.findViewById(R.id.numeroCantique);
            txtTitreCantique = itemView.findViewById(R.id.titreCantique);
            btnDeleteCantique = itemView.findViewById(R.id.btnDelete);
            constraintLayout = itemView.findViewById(R.id.bodyList);


        }

        public MyViewHolder linkAdapter(MyAdapter adapter){
            this.adapter = adapter;
            return this;
        }
    }
}
