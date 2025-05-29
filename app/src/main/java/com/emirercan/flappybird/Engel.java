package com.emirercan.flappybird;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Engel {
    private ImageView upperPipe; // Yukarıdaki borunun görsel nesnesi
    private ImageView lowerPipe; // Aşağıdaki borunun görsel nesnesi
    private static final int OBSTACLE_WIDTH = 200; // Boruların genişliği (px)
    private static final int GAP_HEIGHT = 600; // İki boru arasındaki boşluk (kuşun geçeceği alan)
    private static final int MIN_HEIGHT = 200; // Boruların alabileceği minimum yükseklik
    private int speed = 10; // Boruların sola doğru hareket etme hızı (px/frame)

    public Engel(Context context, RelativeLayout gameLayout, int screenWidth, int screenHeight) {
        createPipes(context, gameLayout, screenWidth, screenHeight); // Obstacle nesnesi oluşturulunca boruları yarat
    }

    private void createPipes(Context context, RelativeLayout gameLayout, int screenWidth, int screenHeight) {
        int upperHeight = MIN_HEIGHT + (int)(Math.random() * (screenHeight - GAP_HEIGHT - 2 * MIN_HEIGHT)); // Yukarı borunun yüksekliğini rastgele belirle
        int lowerHeight = screenHeight - upperHeight - GAP_HEIGHT; // Aşağı borunun yüksekliği, boşluk ve yukarı borudan kalan alan

        upperPipe = new ImageView(context); // Yeni ImageView nesnesi oluştur (yukarı boru)
        upperPipe.setImageResource(R.drawable.pipegreen); // Görsel olarak yeşil boru resmi ata
        upperPipe.setScaleType(ImageView.ScaleType.FIT_XY); // Görseli genişlik ve yüksekliğe göre esnet
        upperPipe.setRotation(180); // Yukarı boruyu ters çevir (aşağıdan yukarıya doğru gelsin)
        RelativeLayout.LayoutParams upperParams = new RelativeLayout.LayoutParams(OBSTACLE_WIDTH, upperHeight); // Yukarı boru için boyut bilgisi
        upperParams.leftMargin = screenWidth; // Ekranın en sağına yerleştir (görünüm dışında başlat)
        upperParams.topMargin = 0; // Yukarıdan başlasın
        upperPipe.setLayoutParams(upperParams); // Yukarı boruya bu parametreleri ata

        lowerPipe = new ImageView(context); // Yeni ImageView nesnesi oluştur (aşağı boru)
        lowerPipe.setImageResource(R.drawable.pipegreen); // Aynı yeşil boru resmi
        lowerPipe.setScaleType(ImageView.ScaleType.FIT_XY); // Görseli genişlik/yükseklikle orantılı büyüt
        RelativeLayout.LayoutParams lowerParams = new RelativeLayout.LayoutParams(OBSTACLE_WIDTH, lowerHeight); // Aşağı boru için boyut bilgisi
        lowerParams.leftMargin = screenWidth; // Ekranın en sağına yerleştir
        lowerParams.topMargin = upperHeight + GAP_HEIGHT; // Yukarı boru + boşluk kadar aşağıdan başlasın
        lowerPipe.setLayoutParams(lowerParams); // Aşağı boruya bu parametreleri ata

        gameLayout.addView(upperPipe); // Oyun sahnesine yukarı boruyu ekle
        gameLayout.addView(lowerPipe); // Oyun sahnesine aşağı boruyu ekle
    }

    public void move() {
        RelativeLayout.LayoutParams upperParams = (RelativeLayout.LayoutParams) upperPipe.getLayoutParams(); // Yukarı borunun mevcut konumunu al
        RelativeLayout.LayoutParams lowerParams = (RelativeLayout.LayoutParams) lowerPipe.getLayoutParams(); // Aşağı borunun mevcut konumunu al

        upperParams.leftMargin -= speed; // Yukarı boruyu sola kaydır
        lowerParams.leftMargin -= speed; // Aşağı boruyu sola kaydır

        upperPipe.setLayoutParams(upperParams); // Yeni konumu uygula
        lowerPipe.setLayoutParams(lowerParams); // Yeni konumu uygula
    }

    public View getUpperPipe() {
        return upperPipe; // Yukarı borunun nesnesini döndür (çarpışma kontrolü gibi işlemler için)
    }

    public View getLowerPipe() {
        return lowerPipe; // Aşağı borunun nesnesini döndür
    }
}
