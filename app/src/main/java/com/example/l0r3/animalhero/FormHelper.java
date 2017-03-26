package com.example.l0r3.animalhero;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.l0r3.animalhero.modelo.Hero;

public class FormHelper {
    private final EditText campoFormNome;
    private final EditText campoFormEndereco;
    private final EditText campoFormTelefone;
    private final EditText campoFormEmail;
    private final EditText campoFormSite;
    private final RatingBar campoFormNota;
    private final ImageView campoFormFoto;
    private CheckBox checkBox_cao;
    private CheckBox checkBox_gato;
    private CheckBox checkBox_pasGra;
    private CheckBox checkBox_pasPeq;
    private CheckBox checkBox_ramister;
    private CheckBox checkBox_outros;
    private Hero hero;

    public FormHelper(FormActivity activity) {
        campoFormNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoFormEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        campoFormTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        campoFormEmail = (EditText) activity.findViewById(R.id.formulario_email);
        campoFormSite = (EditText) activity.findViewById(R.id.formulario_site);
        campoFormNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        campoFormFoto = (ImageView) activity.findViewById(R.id.formulario_foto);
        checkBox_cao = (CheckBox) activity.findViewById(R.id.checkbox_cao);
        checkBox_gato = (CheckBox) activity.findViewById(R.id.checkbox_gato);
        checkBox_pasGra = (CheckBox) activity.findViewById(R.id.checkbox_pasgra);
        checkBox_pasPeq = (CheckBox) activity.findViewById(R.id.checkbox_paspeq);
        checkBox_ramister = (CheckBox) activity.findViewById(R.id.checkbox_ramis);
        checkBox_outros = (CheckBox) activity.findViewById(R.id.checkbox_outros);
        hero = new Hero();
    }

    public Hero pegaHero(){

        hero.setNome(campoFormNome.getText().toString());
        hero.setEndereco(campoFormEndereco.getText().toString());
        hero.setTelefone(campoFormTelefone.getText().toString());
        hero.setEmail(campoFormEmail.getText().toString());
        hero.setNota(Double.valueOf(campoFormNota.getProgress()));
        hero.setSite(campoFormSite.getText().toString());
        hero.setCaminhoFoto((String) campoFormFoto.getTag());
        if (checkBox_cao.isChecked()){
            hero.setCheck_cao((short) 1);
        } else {
            hero.setCheck_cao((short) 0);
        }

        if (checkBox_gato.isChecked()){
            hero.setCheck_gato((short) 1);
        } else {
            hero.setCheck_gato((short) 0);
        }

        if (checkBox_pasGra.isChecked()){
            hero.setCheck_pasGra((short) 1);
        } else {
            hero.setCheck_pasGra((short) 0);
        }

        if (checkBox_pasPeq.isChecked()){
            hero.setCheck_pasPeq((short) 1);
        } else {
            hero.setCheck_pasPeq((short) 0);
        }

        if (checkBox_ramister.isChecked()){
            hero.setCheck_ramister((short) 1);
        } else {
            hero.setCheck_ramister((short) 0);
        }

        if (checkBox_outros.isChecked()){
            hero.setCheck_outros((short) 1);
        } else {
            hero.setCheck_outros((short) 0);
        }

        return hero;
    }

    public void preencheForm(Hero hero) {

        campoFormNome.setText(hero.getNome());
        campoFormEndereco.setText(hero.getEndereco());
        campoFormTelefone.setText(hero.getTelefone());
        campoFormEmail.setText(hero.getEmail());
        campoFormSite.setText(hero.getSite());
        carregaFoto(hero.getCaminhoFoto());
        if (hero.getNota() != null) {
            campoFormNota.setProgress(hero.getNota().intValue());
        } else {
            // Teste de pull --rebase
        }
        checkBox_cao.setChecked(hero.getCheck_cao() == 1);
        checkBox_gato.setChecked(hero.getCheck_gato() == 1);
        checkBox_pasGra.setChecked(hero.getCheck_pasGra() == 1);
        checkBox_pasPeq.setChecked(hero.getCheck_pasPeq() == 1);
        checkBox_ramister.setChecked(hero.getCheck_ramister() == 1);
        checkBox_outros.setChecked(hero.getCheck_outros() == 1);
        this.hero = hero;
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
