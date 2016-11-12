package com.souchard.alcoolxgasolina.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.DecimalFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Vladimir on 29/10/2016.
 */

public class CriarBanco extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "historicoDB";
    private static final String TABELA_HISTORICO = "historico";
    private static final String ID = "_id";
    private static final String ALCOOL = "alcool";
    private static final String GASOLINA = "gasolina";
    private static final String DATA_PRECO = "data";
    private static final int DATABASE_VERSION = 1;

    private static final String[] COLUNAS = {ID, ALCOOL, GASOLINA, DATA_PRECO};


    public CriarBanco(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE historico ("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "alcool TEXT,"+
                "gasolina TEXT,"+
                "data TEXT);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABELA_HISTORICO);
        onCreate(db);
    }


    private String getDateTime() {
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
                "dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void addHistorico(Historico historico){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        usando constantes para chamar os elementos
        values.put(GASOLINA, historico.getGasolina());
        values.put(ALCOOL, historico.getAlcool());
        values.put(DATA_PRECO, getDateTime());
        db.insert(TABELA_HISTORICO, null, values);
        db.close();
    }

    public Historico cursorToHistorico(Cursor cursor){
        Historico historico = new Historico();
        historico.setId(Integer.parseInt(cursor.getString(0)));
        historico.setAlcool(cursor.getString(1));
        historico.setGasolina(cursor.getString(2));
        historico.setData(cursor.getString(3));
        return historico;
    }

    public ArrayList<Historico>getAllHistorico(){
        ArrayList<Historico> listaHistorico = new ArrayList<Historico>();
        String query = "SELECT * FROM " + TABELA_HISTORICO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Historico historico = cursorToHistorico(cursor);
                listaHistorico.add(historico);
            } while (cursor.moveToNext());
        }
            return listaHistorico;
    }
}
