package com.juker_enterprise.cantiquebulu.sqlDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.juker_enterprise.cantiquebulu.beans.Cantique;
import com.juker_enterprise.cantiquebulu.beans.Favoris;

import java.util.ArrayList;
import java.util.List;

public class MyDataBaseHelper extends SQLiteOpenHelper {


    private Context context;
    private static final String DATABASE_NAME ="Cantique_Bulu.db";
    private static final int  DATABASE_VERSION =1;
    private static final String TABLE_NAME = "favoris";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NUMERO = "_numero";
    private static final String COLUMN_NOMCANTIQUE="_nomCantique";
    private static final String COLUMN_ISFAVORIS="_isFavoris";



    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME+
                        "("+COLUMN_ID+" INTEGER PRIMARY KEY, " +
                        COLUMN_NUMERO +" INT,"+
                        COLUMN_NOMCANTIQUE +" TEXT, "+
                        COLUMN_ISFAVORIS +" INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }



   public void addFavoris(Cantique cantique){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, cantique.getNumero());
        cv.put(COLUMN_NUMERO,Integer.valueOf(cantique.getNumeroTitre()));
        cv.put(COLUMN_NOMCANTIQUE,cantique.getTitre());
        cv.put(COLUMN_ISFAVORIS,1);
        long result = db.insert(TABLE_NAME,null,cv);

        if(result==-1){
            Toast.makeText(context, "Insertion Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }

    }

    public List<Favoris> getAllFavoris(){
        List<Favoris> listFavoris = new ArrayList<>();

        //TODO
        String query ="select * from "+TABLE_NAME+" order by "+COLUMN_ID+" asc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Favoris favoris = new Favoris();

            favoris.setId(cursor.getInt(0));
            favoris.setNumero(String.valueOf(cursor.getInt(1)));
            favoris.setNom(cursor.getString(2));
            favoris.setIsFavoris(cursor.getInt(3) );

            listFavoris.add(favoris);
            cursor.moveToNext();
        }


        return listFavoris;
    }

    public Boolean verifIfExist(Cantique cantique){
        Boolean result;
        int idd =0;
        //TODO
        String query ="select "+COLUMN_ID+" from "+TABLE_NAME+" where "+COLUMN_ID+"="+cantique.getNumero();
        Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            idd = cursor.getInt(0);
            cursor.moveToNext();
        }

        if (idd== 0){
            result = false;
        }else{
            result =true;
        }

        return result;
    }
}
