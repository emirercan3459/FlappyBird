package com.emirercan.flappybird;

import android.os.Handler;
import android.widget.ImageView;

public class KusAnimasyon {
    private final ImageView kus; // Animasyon uygulanacak kuş görseli
    private final Handler handler; // Zamanlayıcı, belirli aralıklarla işlem yapmak için
    private int currentFrame = 0; // Şu an gösterilen animasyon karesi
    private static final int[] FRAMES = {
            R.drawable.yellowbirdupflap,     // Yukarı kanat çırpma görseli
            R.drawable.yellowbirdmidflap,    // Orta pozisyondaki kuş görseli
            R.drawable.yellowbirddownflap    // Aşağı kanat çırpma görseli
    };
    private boolean isRunning = false; // Animasyon çalışıyor mu kontrolü

    public KusAnimasyon(ImageView kus) {
        this.kus = kus; // Animasyon yapılacak ImageView
        this.handler = new Handler(); // Yeni bir zamanlayıcı (ana thread'e bağlı)
    }

    public void start() {
        isRunning = true; // Animasyonu başlat
        animateFrame(); // İlk kareyi göster
    }

    private void animateFrame() {
        if (!isRunning) return; // Eğer durdurulmuşsa, hiçbir şey yapma

        kus.setImageResource(FRAMES[currentFrame]); // Kuş görselini şu anki kare ile değiştir
        currentFrame = (currentFrame + 1) % FRAMES.length; // Sonraki kareye geç (sonsuz döngü)

        handler.postDelayed(this::animateFrame, 100); // 100 ms sonra tekrar animateFrame() çalıştır
    }

    public void stop() {
        isRunning = false; // Animasyonu durdur
    }
}
