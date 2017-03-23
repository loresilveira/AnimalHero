package com.example.l0r3.animalhero.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.l0r3.animalhero.modelo.Hero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lore on 15/03/2017.
 */
public class HeroDAO extends SQLiteOpenHelper{



    public HeroDAO(Context contexto) {
        super(contexto, "Agenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Heros( " +
                "id INTEGER PRIMARY KEY, "+
                "nome TEXT NOT NULL, "+
                "endereco TEXT, " +
                "telefone TEXT, " +
                "email TEXT, " +
                "site TEXT, " +
                "nota REAL" +
                "caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Heros";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Hero hero){
        SQLiteDatabase db = getWritableDatabase(); //recupera ref do banco

        ContentValues dados = new ContentValues();
        dados.put("nome", hero.getNome());
        dados.put("endereco", hero.getEndereco());
        dados.put("telefone", hero.getTelefone());
        dados.put("email", hero.getEmail());
        dados.put("site", hero.getSite());
        dados.put("nota", hero.getNota());

        db.insert("Heros", null, dados);

    }

    public List<Hero> buscaHeros(){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM Heros;";
        Cursor c = db.rawQuery(sql,null);
        List<Hero> heros = new ArrayList<Hero>();
        while (c.moveToNext()){
            Hero hero = new Hero();
            hero.setId(c.getLong(c.getColumnIndex("id")));
            hero.setNome(c.getString(c.getColumnIndex("nome")));
            hero.setEndereco(c.getString(c.getColumnIndex("endereco")));
            hero.setTelefone(c.getString(c.getColumnIndex("telefone")));
            hero.setEmail(c.getString(c.getColumnIndex("email")));
            hero.setSite(c.getString(c.getColumnIndex("site")));
            hero.setNota(c.getDouble(c.getColumnIndex("nota")));
            heros.add(hero);
        }

        c.close();
        return heros;
    }

    public void deleta(Hero hero){
        SQLiteDatabase db = getWritableDatabase();
        String[] heroSelecionado = {hero.getId().toString()};
        db.delete("Heros", "id =?", heroSelecionado);
    }

    public void altera(Hero hero) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoHero(hero);

        String[] params = {hero.getId().toString()};
        db.update("Heros", dados, "id = ?", params);
    }

    private ContentValues pegaDadosDoHero(Hero hero) {
        ContentValues dados = new ContentValues();
        dados.put("nome", hero.getNome());
        dados.put("endereco", hero.getEndereco());
        dados.put("telefone", hero.getTelefone());
        dados.put("email", hero.getEmail());
        dados.put("site", hero.getSite());
        dados.put("nota", hero.getNota());
        return dados;
    }

}
