package com.example.l0r3.animalhero;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.l0r3.animalhero.dao.HeroDAO;
import com.example.l0r3.animalhero.modelo.Hero;

import java.util.List;

public class ListaHerosActivity extends AppCompatActivity {

    private ListView listaHeros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_heros);

        HeroDAO dao = new HeroDAO(this);
        List<Hero> heros = dao.buscaHeros();
        dao.close();
        ArrayAdapter<Hero> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, heros);
        listaHeros = (ListView) findViewById(R.id.lista_heros);
        listaHeros.setAdapter(adapter);

        Button novoHero = (Button) findViewById(R.id.novo_hero);
        novoHero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // A intent faz com que o Android permita mudar de activity
                Intent intent = new Intent(ListaHerosActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });

        registerForContextMenu(listaHeros);


        listaHeros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Hero hero = (Hero) listaHeros.getItemAtPosition(position);
                Toast.makeText(ListaHerosActivity.this, "Hero: " + hero.getNome() + " selecionado!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListaHerosActivity.this, FormActivity.class);
                intent.putExtra("Hero", hero);
                startActivity(intent);


            }


        });
    }

    public void carregaLista() {
        HeroDAO dao = new HeroDAO(this);
        List<Hero> heros = dao.buscaHeros();
        dao.close();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, heros);
        listaHeros.setAdapter(adapter);
    }

    public void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    // Criando menu de contexto
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        final AdapterView.AdapterContextMenuInfo posicao = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Hero heroClicado = (Hero) listaHeros.getItemAtPosition(posicao.position);

        MenuItem itemSite = menu.add("Visitar site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);

        String site = heroClicado.getSite();
        if (!site.startsWith("http://")) {
            site = "http://" + site;
        }

        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);

        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:5082-3236"));
        itemSMS.setIntent(intentSMS);

        MenuItem itemMapa = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + heroClicado.getEndereco()));
        itemMapa.setIntent(intentMapa);

        MenuItem itemLigar = menu.add("Ligar");
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(ListaHerosActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListaHerosActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + heroClicado.getTelefone()));
                    startActivity(intentLigar);
                }
                return false;
            }
        });


        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                HeroDAO dao = new HeroDAO(ListaHerosActivity.this);
                dao.deleta(heroClicado);
                Toast.makeText(ListaHerosActivity.this, "Hero(a):" + heroClicado.getNome() + "deletado(a)", Toast.LENGTH_SHORT).show();
                dao.close();
                carregaLista();
                return false;
            }
        }) ;

    }
}