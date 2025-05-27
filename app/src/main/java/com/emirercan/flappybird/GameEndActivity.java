package com.emirercan.flappybird;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameEndActivity extends AppCompatActivity {

    private String score;
    private Veritabani veritabani;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gameend);
        Intent intent = getIntent();
        score = intent.getStringExtra("score");
        veritabani = new Veritabani(this);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void infoButton(View view){
        Toast.makeText(this,"Score: " + score + "\nHigh Score: " + veritabani.getHighScore(), Toast.LENGTH_LONG).show();
    }
}