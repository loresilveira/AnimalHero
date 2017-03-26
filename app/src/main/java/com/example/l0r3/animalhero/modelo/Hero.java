package com.example.l0r3.animalhero.modelo;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lore on 15/03/2017.
 */
public class Hero implements Serializable {

    private Long id;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;
    private String site;
    private Double nota;
    private String caminhoFoto;
    private short check_cao;
    private short check_gato;
    private short check_pasGra;
    private short check_pasPeq;
    private short check_ramister;
    private short check_outros;




    public Hero() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public short getCheck_cao() {
        return check_cao;
    }

    public void setCheck_cao(short check_cao) {
        this.check_cao = check_cao;
    }

    public short getCheck_gato() {
        return check_gato;
    }

    public void setCheck_gato(short check_gato) {
        this.check_gato = check_gato;
    }

    public short getCheck_pasGra() {
        return check_pasGra;
    }

    public void setCheck_pasGra(short check_pasGra) {
        this.check_pasGra = check_pasGra;
    }

    public short getCheck_pasPeq() {
        return check_pasPeq;
    }

    public void setCheck_pasPeq(short check_pasPeq) {
        this.check_pasPeq = check_pasPeq;
    }

    public short getCheck_ramister() {
        return check_ramister;
    }

    public void setCheck_ramister(short check_ramister) {
        this.check_ramister = check_ramister;
    }

    public short getCheck_outros() {
        return check_outros;
    }

    public void setCheck_outros(short check_outros) {
        this.check_outros = check_outros;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    @Override
    public String toString(){
        if (getNome() != null) {
            return getNome();
        } else {
            return "Hero!";
        }
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nome", nome);
        result.put("endereco", endereco);
        result.put("telefone", telefone);
        result.put("email", email);
        result.put("site", site);
        result.put("nota", nota);

        return result;
    }

}
