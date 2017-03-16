package com.example.l0r3.animalhero;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.l0r3.animalhero.dao.HeroDAO;
import com.example.l0r3.animalhero.modelo.Hero;

import java.io.File;

public class FormActivity extends AppCompatActivity {

    private FormHelper helper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        helper = new FormHelper(this);

        Intent intent = getIntent();
        Hero hero = (Hero) intent.getSerializableExtra("hero");
        if (hero != null) {
            helper.preencheForm(hero);
        }

        Button botaoFoto = (Button) findViewById(R.id.formulario_botao_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, 560);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return  super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_form_ok:
                Hero hero = helper.pegaHero();
                HeroDAO dao = new HeroDAO(this);
                if (hero.getId() != null) {
                    dao.altera(hero);
                } else {
                    dao.insere(hero);
                }
                dao.close();

                Toast.makeText(FormActivity.this, "Usuário " + " salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 560) {
                helper.carregaFoto(caminhoFoto);
            }
        }

    }
}
