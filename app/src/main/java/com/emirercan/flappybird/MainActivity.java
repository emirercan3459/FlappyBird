package com.emirercan.flappybird;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Kenardan kenara ekran kullanımı aktif
        setContentView(R.layout.activity_main); // activity_main layout'u yüklenir
    }

    // Butona tıklanınca çağrılır, oyunu başlatmak için GameActivity'yi açar
    public void startGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
