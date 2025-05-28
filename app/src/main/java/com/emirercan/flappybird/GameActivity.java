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
    private RelativeLayout oyunAlani; // Oyunun oynandığı ana düzen (layout)
    private TextView skorText; // Skoru gösteren TextView
    private Bird kus; // Kuş karakterini temsil eden sınıf nesnesi
    private ArrayList<Obstacle> engeller = new ArrayList<>(); // Oyundaki engellerin listesi
    private Handler handler = new Handler(); // Döngü ve zamanlamalar için handler
    private Veritabani veritabani = new Veritabani(this); // Skorları kaydetmek için veritabanı nesnesi
    private boolean oyunBitti = false; // Oyun durumunu tutar (bitti mi?)
    private int skor = 0; // Oyuncunun skor değeri
    private boolean skorAlindi = false; // Her engel için skor sadece 1 kez artmalı kontrolü
    private int ekranYukseklik, ekranGenislik; // Cihazın ekran boyutları
    private ImageView oyuncu; // Kuşun ImageView nesnesi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game); // activity_game layoutunu ayarla
        oyunAlani = findViewById(R.id.gameLayout); // Oyun alanını bul
        skorText = findViewById(R.id.scoreText); // Skor textini bul
        ekranYukseklik = getResources().getDisplayMetrics().heightPixels; // Ekran yüksekliğini al
        ekranGenislik = getResources().getDisplayMetrics().widthPixels; // Ekran genişliğini al
        oyuncu = findViewById(R.id.player); // Kuşun görselini al
        kus = new Bird(findViewById(R.id.player)); // Bird sınıfı ile kuşu oluştur
        KusAnimasyon kusAnimasyon = new KusAnimasyon(findViewById(R.id.player)); // Kuş animasyonu başlatmak için nesne
        kusAnimasyon.start(); // Kuş kanat çırpma animasyonunu başlat
        oyunuBaslat(); // Oyunu başlatan metodu çağır
    }

    public void kusZipla(View view) {
        kus.jump(); // Ekrana dokunulduğunda kuşun zıplamasını tetikle
    }

    private void oyunuBaslat() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!oyunBitti) {
                    oyunGuncelle(); // Oyun içi güncellemeleri yap (kuş, engeller)
                    handler.postDelayed(this, 30); // 30 ms sonra tekrar bu işlemi yap (yaklaşık 33 FPS)
                }
            }
        }, 30);

        engelUret(); // Engellerin düzenli olarak oluşturulmasını başlat
    }

    private void oyunGuncelle() {
        kus.update(ekranYukseklik); // Kuşun pozisyonunu güncelle (yerçekimi vs)
        for (int i = 0; i < engeller.size(); i++) {
            Obstacle engel = engeller.get(i); // Listedeki engeli al
            engel.move(); // Engeli sola kaydır
            carpismaKontrol(engel); // Kuş ile engel çarpışmasını kontrol et
            skorKontrol(engel); // Kuş engeli geçtiyse skoru arttır
        }
    }

    private void engelUret() {
        Thread engelThread = new Thread() {
            @Override
            public void run() {
                while (!oyunBitti) { // Oyun bitene kadar devam et
                    try {
                        Thread.sleep(3500); // 3.5 saniyede bir yeni engel oluştur
                        runOnUiThread(() -> { // UI thread üzerinde engeli oluştur
                            Obstacle yeniEngel = new Obstacle(GameActivity.this, oyunAlani, ekranGenislik, ekranYukseklik);
                            engeller.add(yeniEngel); // Yeni engeli listeye ekle
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        engelThread.start(); // Engel üretme thread'ini başlat
    }

    private void carpismaKontrol(Obstacle engel) {
        Rect kusAlani = new Rect();
        kus.getView().getHitRect(kusAlani); // Kuşun alanını dikdörtgen olarak al

        Rect ustBoru = new Rect();
        Rect altBoru = new Rect();
        engel.getUpperPipe().getHitRect(ustBoru); // Üst borunun alanını al
        engel.getLowerPipe().getHitRect(altBoru); // Alt borunun alanını al

        if (Rect.intersects(kusAlani, ustBoru) || Rect.intersects(kusAlani, altBoru)) {
            oyunBitir(); // Kuş borulara temas ettiyse oyunu bitir
        }
    }

    private void skorKontrol(Obstacle engel) {
        // Eğer skor daha önce bu engel için alınmadıysa ve kuş üst borunun sağından geçtiyse
        if (!skorAlindi && engel.getUpperPipe().getX() + engel.getUpperPipe().getWidth() < kus.getView().getX()) {
            skor++; // Skoru arttır
            skorAlindi = true; // Bu engel için skor alındı işaretle
            skorText.setText("" + skor); // Skoru ekranda göster
        }

        // Eğer engel ekranın dışına tamamen çıktıysa
        if (engel.getUpperPipe().getX() + engel.getUpperPipe().getWidth() < 0) {
            engeller.remove(engel); // Engeli listeden çıkar
            oyunAlani.removeView(engel.getUpperPipe()); // Üst boruyu görünümden kaldır
            oyunAlani.removeView(engel.getLowerPipe()); // Alt boruyu görünümden kaldır
            skorAlindi = false; // Bir sonraki engel için skoru yeniden alabilmek adına sıfırla
        }
    }

    private void oyunBitir() {
        oyunBitti = true; // Oyun durumunu bitmiş olarak ayarla
        veritabani.saveScore(skor); // Skoru veritabanına kaydet
        Intent intent = new Intent(this, GameEndActivity.class); // Oyun sonu ekranına geçiş için intent
        intent.putExtra("score", String.valueOf(skor)); // Skoru intent ile gönder
        startActivity(intent); // GameEndActivity'i başlat
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null); // Handler üzerindeki tüm işlemleri iptal et, memory leak önle
    }
}
