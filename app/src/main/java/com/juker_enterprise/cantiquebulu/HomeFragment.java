package com.juker_enterprise.cantiquebulu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.juker_enterprise.cantiquebulu.beans.Cantique;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    Cantique cantique ;
    ArrayList listCantique = null;
    Context context = getContext();
    String titre = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        String name=null;
        int resId ;
        listCantique = new ArrayList();
        for (int i=1;i<296;i++){
            if(i<10){
                resId = getResources().getIdentifier("raw/b00"+i,null,context.getPackageName());
            }else if(i<100){
                resId = getResources().getIdentifier("raw/b0"+i,null,context.getPackageName());
            }else{
                resId = getResources().getIdentifier("raw/b"+i,null,context.getPackageName());
            }
            cantique = new Cantique();

            cantique.setNumero(resId);
            cantique.setCorps(getCorps(resId));
            cantique.setTitre(titre);


            listCantique.add(cantique);
            titre = null;

        }

        return inflater.inflate(R.layout.fragment_home,container,false);


    }


    private String getCorps(int rawRessource){
        String corpCantique = null;
        // Read the file :open a InputStream on it
        InputStream inputStream = getResources().openRawResource(rawRessource);
        try {
            if (inputStream != null) {
                // open a reader on the inputStream
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                // String used to store the lines
                String str;

                StringBuilder buf = new StringBuilder();

                // Read the file
                int i =0;
                while ((str = reader.readLine()) != null) {
                    buf.append(str).append("\r\n");
                    if (i==1)
                        titre = str;
                    i++;
                }
                // Close streams
                reader.close();
                inputStream.close();
                corpCantique = buf.toString();
            }
        } catch (IOException e) {
            Toast.makeText(context, "FileNotFoundException", Toast.LENGTH_LONG).show();
            corpCantique = "FileNotFoundException " + e.getMessage();
        }

        return corpCantique;
    }


}
