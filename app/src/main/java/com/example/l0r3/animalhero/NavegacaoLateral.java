package com.example.l0r3.animalhero;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.l0r3.animalhero.dao.HeroDAO;
import com.example.l0r3.animalhero.modelo.Hero;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class NavegacaoLateral extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listHeros;
    private static final String TAG = "navegaçãoTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacao_lateral);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listHeros = (ListView) findViewById(R.id.list_heros);


        listHeros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Hero hero = (Hero) listHeros.getItemAtPosition(position);
                Toast.makeText(NavegacaoLateral.this, "Hero: " + hero.getNome() + " selecionado!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NavegacaoLateral.this, FormActivity.class);
                intent.putExtra("hero", hero);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavegacaoLateral.this, FormActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        registerForContextMenu(listHeros);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navegacao_lateral, menu);
        return true;
    }

    public void carregaLista() {
        HeroDAO dao = new HeroDAO(this);
        List<Hero> heros = dao.buscaHeros();
        dao.close();

        ArrayAdapter<Hero> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, heros);
        listHeros.setAdapter(adapter);
    }

    public void onResume() {
        super.onResume();
        carregaLista();
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
                if (ActivityCompat.checkSelfPermission(NavegacaoLateral.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(NavegacaoLateral.this,
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

                HeroDAO dao = new HeroDAO(NavegacaoLateral.this);
                dao.deleta(heroClicado);
                Toast.makeText(NavegacaoLateral.this, "Hero(a):" + heroClicado.getNome() + "deletado(a)", Toast.LENGTH_SHORT).show();
                dao.close();
                carregaLista();
                return false;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.map) {
            Intent intent = new Intent(NavegacaoLateral.this, MapsActivity.class);
            startActivity(intent);
        } else if (id == R.id.my_profile) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            HeroDAO dao = new HeroDAO(NavegacaoLateral.this);
            Hero hero = dao.getHeroByEmail(user.getEmail());
            //Hero hero = (Hero) listHeros.getItemAtPosition(position);
            Toast.makeText(NavegacaoLateral.this, "Hero: " + hero.getNome() + " selecionado!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(NavegacaoLateral.this, FormActivity.class);
            intent.putExtra("hero", hero);
            startActivity(intent);

        } else if (id == R.id.logout) {
            Log.d(TAG, "onAuthStateChanged:signed_in:" + id);
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(NavegacaoLateral.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
