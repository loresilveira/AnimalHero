package com.example.l0r3.animalhero;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.l0r3.animalhero.modelo.Hero;

/**
 * Created by Lore on 15/03/2017.
 */
public class FormHelper {
    private final EditText campoFormNome;
    private final EditText campoFormEndereco;
    private final EditText campoFormTelefone;
    private final EditText campoFormEmail;
    private final EditText campoFormSite;
    private final RatingBar campoFormNota;
    private final ImageView campoFormFoto;
    private Hero hero;

    public FormHelper(FormActivity activity) {
        campoFormNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoFormEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        campoFormTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        campoFormEmail = (EditText) activity.findViewById(R.id.formulario_email);
        campoFormSite = (EditText) activity.findViewById(R.id.formulario_site);
        campoFormNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        campoFormFoto = (ImageView) activity.findViewById(R.id.formulario_foto);
        hero = new Hero();
    }

    public Hero pegaHero(){

        hero.setNome(campoFormNome.getText().toString());
        hero.setEndereco(campoFormEndereco.getText().toString());
        hero.setTelefone(campoFormTelefone.getText().toString());
        hero.setEmail(campoFormEmail.getText().toString());
        hero.setNota(Double.valueOf(campoFormNota.getProgress()));
        hero.setSite(campoFormSite.getText().toString());
        hero.setCaminhoFoto((String)campoFormFoto.getTag());

        return hero;
    }

    public void preencheForm(Hero hero) {
        this.hero = hero;
        campoFormNome.setText(hero.getNome());
        campoFormEndereco.setText(hero.getEndereco());
        campoFormTelefone.setText(hero.getTelefone());
        campoFormEmail.setText(hero.getEmail());
        campoFormSite.setText(hero.getSite());
        carregaFoto(hero.getCaminhoFoto());
        this.campoFormNota.setProgress(this.hero.getNota().intValue());

    }

    public void carregaFoto(String caminhoFoto) {
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFormFoto.setImageBitmap(bitmapReduzido);
            campoFormFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFormFoto.setTag(caminhoFoto);
        }
    }
}
