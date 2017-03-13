package com.example.l0r3.animalhero;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InicializacaoActivity extends AppCompatActivity {
    private static int tempo_inicializacao = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicializacao);

        // Thread que vai carregar intent
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Método vai rodar por 5 segundos
                Intent intent = new Intent(InicializacaoActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }, tempo_inicializacao);
    }
}
