package com.juker_enterprise.cantiquebulu;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
    ArrayList <Cantique> listCantique = null;
    Context mContext  ;
    String titre = null;
    String name=null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          int resId ;
        listCantique = new ArrayList();

       try {
           for (int i=1;i<=296;i++){
               if(i<10){
                   resId = getResources().getIdentifier("raw/b00"+String.valueOf(i),"raw",mContext.getPackageName());
               }else if(i<100){
                   resId = getResources().getIdentifier("raw/b0"+i,"raw",mContext.getPackageName());
               }else{
                   resId = getResources().getIdentifier("raw/b"+i,"raw",mContext.getPackageName());
               }
              // context.getResources().openRawResource(resId);
               cantique = new Cantique();
               cantique.setNumero(resId);
               cantique.setCorps(getCorps(resId));
               cantique.setTitre(titre);


               listCantique.add(cantique);
               titre = null;

           }
       }catch (Exception e){
           Log.d("erreur2",e.getMessage());
       }

        Log.d("valeur2",String .valueOf(listCantique.get(295).getTitre()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


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
            Toast.makeText(mContext , "FileNotFoundException", Toast.LENGTH_LONG).show();
            corpCantique = "FileNotFoundException " + e.getMessage();
        }

        return corpCantique;
    }


}
