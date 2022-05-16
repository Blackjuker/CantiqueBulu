package com.hive_coorporation.cantiquebulu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hive_coorporation.cantiquebulu.adapter.MyAdapter;
import com.hive_coorporation.cantiquebulu.beans.Favoris;
import com.hive_coorporation.cantiquebulu.sqlDataBase.MyDataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class FavorisFragment extends Fragment {

    Context mContext;

    RecyclerView recyclerView;
    MyDataBaseHelper myDB;
    List<Favoris> listFavoris = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_favoris,container,false);

        recyclerView = view.findViewById(R.id.recycleViewFavoris);


        listFavoris = new ArrayList<>();

        myDB = new MyDataBaseHelper(mContext);
        listFavoris = myDB.getAllFavoris();

        MyAdapter myAdapter = new MyAdapter(mContext,listFavoris);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        return view;
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
