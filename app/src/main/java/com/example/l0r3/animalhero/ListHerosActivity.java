package com.example.l0r3.animalhero;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.l0r3.animalhero.dao.HeroDAO;
import com.example.l0r3.animalhero.modelo.Hero;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ListHerosActivity extends AppCompatActivity {

    private static final String TAG = "ListHerosActivity";

    public static final String EXTRA_POST_KEY = "post_key";

    private ListView listHeros;
    private DatabaseReference mHeroReference;
    private String mPostKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_heros);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(ListHerosActivity.this, FormActivity.class);
                startActivity(intent);

            }
        });
        final ArrayList<Hero> list = new ArrayList<Hero>();

//        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
//        mHeroReference = FirebaseDatabase.getInstance().getReference("/hero");

        HeroDAO dao = new HeroDAO(this);
        List<Hero> heros = dao.buscaHeros();
        dao.close();
        ArrayAdapter<Hero> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, heros);
        listHeros = (ListView) findViewById(R.id.list_heros);
        listHeros.setAdapter(adapter);

        registerForContextMenu(listHeros);

        listHeros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Hero hero = (Hero) listHeros.getItemAtPosition(position);
                Toast.makeText(ListHerosActivity.this, "Hero: " + hero.getNome() + " selecionado!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListHerosActivity.this, FormActivity.class);
                intent.putExtra("hero", hero);
                startActivity(intent);

            }


        });
    }



    public void carregaLista() {
        HeroDAO dao = new HeroDAO(this);
        List<Hero> heros = dao.buscaHeros();
        dao.close();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, heros);
        listHeros.setAdapter(adapter);
    }

    public void onResume() {
        super.onResume();
        carregaLista();
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navegacao_lateral, menu);
        return true;
    }

    @Override
    // Criando menu de contexto
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        final AdapterView.AdapterContextMenuInfo posicao = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Hero heroClicado = (Hero) listHeros.getItemAtPosition(posicao.position);

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
                if (ActivityCompat.checkSelfPermission(ListHerosActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListHerosActivity.this,
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

                HeroDAO dao = new HeroDAO(ListHerosActivity.this);
                dao.deleta(heroClicado);
                Toast.makeText(ListHerosActivity.this, "Hero(a):" + heroClicado.getNome() + "deletado(a)", Toast.LENGTH_SHORT).show();
                dao.close();
                carregaLista();
                return false;
            }
        }) ;

    }

}
