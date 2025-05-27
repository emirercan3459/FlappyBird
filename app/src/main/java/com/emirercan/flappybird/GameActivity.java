package com.emirercan.flappybird;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private RelativeLayout oyunAlani;
    private TextView skorText;
    private Bird kus;
    private ArrayList<Obstacle> engeller = new ArrayList<>();
    private Handler handler = new Handler();
    private Veritabani veritabani = new Veritabani(this);
    private boolean oyunBitti = false;
    private int skor = 0;
    private boolean skorAlindi = false;
    private int ekranYukseklik, ekranGenislik;
    private ImageView oyuncu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        oyunAlani = findViewById(R.id.gameLayout);
        skorText = findViewById(R.id.scoreText);
        ekranYukseklik = getResources().getDisplayMetrics().heightPixels;
        ekranGenislik = getResources().getDisplayMetrics().widthPixels;
        oyuncu = findViewById(R.id.player);
        kus = new Bird(findViewById(R.id.player));
        KusAnimasyon kusAnimasyon = new KusAnimasyon(findViewById(R.id.player));
        kusAnimasyon.start();
        oyunuBaslat();
    }

    public void kusZipla(View view) {
        kus.jump();
    }

    private void oyunuBaslat() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!oyunBitti) {
                    oyunGuncelle();
                    handler.postDelayed(this, 30);
                }
            }
        }, 30);

        engelUret();
    }

    private void oyunGuncelle() {
        kus.update(ekranYukseklik);
        for (int i = 0; i < engeller.size(); i++) {
            Obstacle engel = engeller.get(i);
            engel.move();
            carpismaKontrol(engel);
            skorKontrol(engel);
        }
    }

    private void engelUret() {
        Thread engelThread = new Thread() {
            @Override
            public void run() {
                while (!oyunBitti) {
                    try {
                        Thread.sleep(3500);
                        runOnUiThread(() -> {
                            Obstacle yeniEngel = new Obstacle(GameActivity.this, oyunAlani, ekranGenislik, ekranYukseklik);
                            engeller.add(yeniEngel);
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        engelThread.start();
    }

    private void carpismaKontrol(Obstacle engel) {
        Rect kusAlani = new Rect();
        kus.getView().getHitRect(kusAlani);

        Rect ustBoru = new Rect();
        Rect altBoru = new Rect();
        engel.getUpperPipe().getHitRect(ustBoru);
        engel.getLowerPipe().getHitRect(altBoru);

        if (Rect.intersects(kusAlani, ustBoru) || Rect.intersects(kusAlani, altBoru)) {
            oyunBitir();
        }
    }

    private void skorKontrol(Obstacle engel) {
        if (!skorAlindi && engel.getUpperPipe().getX() + engel.getUpperPipe().getWidth() < kus.getView().getX()) {
            skor++;
            skorAlindi = true;
            skorText.setText("" + skor);
        }

        if (engel.getUpperPipe().getX() + engel.getUpperPipe().getWidth() < 0) {
            engeller.remove(engel);
            oyunAlani.removeView(engel.getUpperPipe());
            oyunAlani.removeView(engel.getLowerPipe());
            skorAlindi = false;
        }
    }

    private void oyunBitir() {
        oyunBitti = true;
        veritabani.saveScore(skor);
        Intent intent = new Intent(this, GameEndActivity.class);
        intent.putExtra("score", String.valueOf(skor));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}