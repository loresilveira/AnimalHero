package com.example.l0r3.animalhero;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.l0r3.animalhero.dao.HeroDAO;
import com.example.l0r3.animalhero.modelo.Hero;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FormActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_CAMERA = 560;
    private FormHelper helper;
    private String caminhoFoto;
    private DatabaseReference mDatabase;

    private static final String TAG = "FormActivity";

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

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button botaoFoto = (Button) findViewById(R.id.formulario_botao_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, REQUEST_CODE_CAMERA);
            }
        });

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_cao:
                if (checked) {
                    helper.pegaHero().setCheck_cao((short) 1);
                }
                else
                    helper.pegaHero().setCheck_cao((short) 0);
                break;

            case R.id.checkbox_gato:
                if (checked){
                    helper.pegaHero().setCheck_gato((short) 1);
                }
                else
                    helper.pegaHero().setCheck_gato((short) 0);
                break;

            case R.id.checkbox_pasgra:
                if (checked){
                    helper.pegaHero().setCheck_pasGra((short) 1);
                }
                else
                    helper.pegaHero().setCheck_pasGra((short) 0);
                break;

            case R.id.checkbox_paspeq:
                if (checked){
                    helper.pegaHero().setCheck_pasPeq((short) 1);
                }
                else
                    helper.pegaHero().setCheck_pasPeq((short) 0);
                break;

            case R.id.checkbox_ramis:
                if (checked){
                    helper.pegaHero().setCheck_ramister((short) 1);
                }
                else
                    helper.pegaHero().setCheck_ramister((short) 0);
                break;

            case R.id.checkbox_outros:
                if (checked){
                    helper.pegaHero().setCheck_outros((short) 1);
                }
                else
                    helper.pegaHero().setCheck_outros((short) 0);
                break;

            // TODO: Veggie sandwich
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_form_ok:
                Hero hero = helper.pegaHero();
                HeroDAO dao = new HeroDAO(this);
                if (hero.getId() != null) {
                    dao.altera(hero);
                } else {
                   // submitHero();
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
            if (requestCode == REQUEST_CODE_CAMERA) {
                helper.carregaFoto(caminhoFoto);
            }
        }

    }
}
