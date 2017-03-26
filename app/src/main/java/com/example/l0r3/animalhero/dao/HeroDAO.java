package com.example.l0r3.animalhero.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.l0r3.animalhero.modelo.Hero;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lore on 15/03/2017.
 */
public class HeroDAO extends SQLiteOpenHelper{



    public HeroDAO(Context contexto) {
        super(contexto, "Animal Hero", null, 1);
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
                "nota REAL, " +
                "caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion) {
            case 1:
                sql = "ALTER TABLE Heros ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql);

        }
    }

    public void insere(Hero hero){
        SQLiteDatabase db = getWritableDatabase(); //recupera ref do banco

        ContentValues dados = pegaDadosDoHero(hero);

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
            hero.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
            heros.add(hero);
        }

        c.close();
        return heros;
    }

    public Hero getHeroByEmail(String email){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "email =?";

        Hero hero = new Hero();
        Cursor c = db.rawQuery("select * from Heros where email = ?", new String[] {email});
        Log.d("dao", "cursor:" + c.toString());
        if (c.moveToNext()) {
            //c.moveToFirst();
            hero.setId(c.getLong(c.getColumnIndex("id")));
            hero.setNome(c.getString(c.getColumnIndex("nome")));
            hero.setEndereco(c.getString(c.getColumnIndex("endereco")));
            hero.setTelefone(c.getString(c.getColumnIndex("telefone")));
            hero.setEmail(c.getString(c.getColumnIndex("email")));
            hero.setSite(c.getString(c.getColumnIndex("site")));
            hero.setNota(c.getDouble(c.getColumnIndex("nota")));
            hero.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
        } else {
            hero.setEmail(email);
        }
        return hero;
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
        dados.put("caminhoFoto", hero.getCaminhoFoto());
        return dados;
    }

}
