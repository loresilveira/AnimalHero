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

    public Hero() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
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
        return getId() + " - " + getNome();

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
