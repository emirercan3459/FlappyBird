package com.emirercan.flappybird;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge; // Kenarlara kadar tam ekran desteği için
import androidx.appcompat.app.AppCompatActivity;

public class GameEndActivity extends AppCompatActivity {

    private String score; // Oyuncunun son skoru
    private Veritabani veritabani; // Veritabanı bağlantısı ve işlemleri için nesne

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Ekran kenarlarına kadar içeriğin genişlemesini sağlar
        setContentView(R.layout.activity_gameend); // activity_gameend.xml layout dosyasını yükler

        Intent intent = getIntent(); // Önceki aktiviteden gelen intent alınır
        score = intent.getStringExtra("score"); // Intent ile gönderilen skor alınır
        veritabani = new Veritabani(this); // Veritabanı nesnesi oluşturulur
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, GameActivity.class); // Oyunu yeniden başlatmak için intent oluşturulur
        startActivity(intent); // GameActivity başlatılır
    }

    public void infoButton(View view){
        // Skoru ve en yüksek skoru kullanıcıya Toast mesajı ile gösterir
        Toast.makeText(this,"Score: " + score + "\nHigh Score: " + veritabani.getHighScore(), Toast.LENGTH_LONG).show();
    }
}
