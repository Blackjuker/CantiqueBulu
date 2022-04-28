package com.juker_enterprise.cantiquebulu;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.juker_enterprise.cantiquebulu.beans.Cantique;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context mContext;
    Cantique cantique ;
    String titre=null;
    TextView body;

    public MainPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainPageFragment newInstance(String param1, String param2) {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);



        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_page,container,false);
         body = view.findViewById(R.id.bodyCantique);
        if (mParam1==null)
            mParam1="";
        String param = mParam1;

        body.setText(openNextCantique(param).getCorps());
     //   Toast.makeText(mContext, mParam1, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), mParam1, Toast.LENGTH_SHORT).show();

        // Inflate the layout for this fragment
        return view;
    }


    private Cantique openNextCantique(String nom){
        String name;
        cantique = new Cantique();
        int resId ;
        try {
        switch (nom.length()){
            case 1:
                resId = getResources().getIdentifier("raw/b00"+nom,"raw",mContext.getPackageName());
               cantique.setNumeroTitre("00"+nom);
                break;
            case 2:
                resId = getResources().getIdentifier("raw/b00"+nom,"raw",mContext.getPackageName());
                cantique.setNumeroTitre("0"+nom);
                break;
            case 3:
                resId = getResources().getIdentifier("raw/b00"+nom,"raw",mContext.getPackageName());
                cantique.setNumeroTitre(nom);
                break;
            default:
                resId =0;
                break;
        }


            cantique.setNumero(resId);

            cantique.setCorps(getCorps(resId));
            cantique.setTitre(titre);

        }catch (Exception e){
            Log.d("erreur2",e.getMessage());
        }



        return cantique;

    }


    //get Body

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
}