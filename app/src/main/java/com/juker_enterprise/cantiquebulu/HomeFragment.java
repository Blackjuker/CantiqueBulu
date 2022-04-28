package com.juker_enterprise.cantiquebulu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.juker_enterprise.cantiquebulu.beans.Cantique;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener  {

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

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_home,container,false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int resId ;
        listCantique = new ArrayList();

        try {
            for (int i=1;i<=296;i++){
                cantique = new Cantique();
                if(i<10){
                    resId = getResources().getIdentifier("raw/b00"+String.valueOf(i),"raw",mContext.getPackageName());
                    cantique.setNumeroTitre("00"+i);
                }else if(i<100){
                    resId = getResources().getIdentifier("raw/b0"+i,"raw",mContext.getPackageName());
                    cantique.setNumeroTitre("0"+i);
                }else{
                    resId = getResources().getIdentifier("raw/b"+i,"raw",mContext.getPackageName());
                    cantique.setNumeroTitre(String.valueOf(i));
                }
                // context.getResources().openRawResource(resId);

                cantique.setNumero(resId);
                cantique.setCorps(getCorps(resId));
               // String t = ;
              //  byte[] caractereSpecia = titre.getBytes("Cp1252");
               // String TitreCantique = new String(caractereSpecia);
               // Toast.makeText(mContext, TitreCantique, Toast.LENGTH_SHORT).show();
                cantique.setTitre(titre);


                listCantique.add(cantique);
                titre = null;

            }
        }catch (Exception e){
            Log.d("erreur2",e.getMessage());
        }

        String[] titreCantique = new String[296];
        int i=0;
        for(Cantique can : listCantique) {
            titreCantique[i] = can.getNumeroTitre() + " : " + can.getTitre();
            i++;
        }
        ListView listView =(ListView) view.findViewById(R.id.listCantique);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,android.R.layout.simple_list_item_1,titreCantique);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    private  String getCorps(int rawRessource){
        String corpCantique = null;
        // Read the file :open a InputStream on it
        InputStream inputStream = getResources().openRawResource(rawRessource);
        try {
            if (inputStream != null) {
                // open a reader on the inputStream
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"Cp1252"));

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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        Fragment cantiqueBody = new MainPageFragment();
        Bundle args = new Bundle();
        args.putString("param1",String.valueOf(position+1));
        args.putString("param2","");
        cantiqueBody.setArguments(args);

        fragmentTransaction.replace(R.id.fragment_container,cantiqueBody);
        fragmentTransaction.commit();

    }
}
