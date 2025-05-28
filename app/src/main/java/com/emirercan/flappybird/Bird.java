package com.emirercan.flappybird;

import android.widget.ImageView;

public class Bird {
    private ImageView birdView;  // Kuşun ekrandaki görselini tutar
    private float velocity = 0;  // Kuşun dikey hızını (yönüyle birlikte) tutar
    private final float gravity = 3.5f;  // Yerçekimi kuvveti, kuşun aşağı doğru hızlanmasını sağlar
    private final float jumpForce = -35f;  // Zıplama kuvveti, negatif olduğu için yukarı doğru hareket

    public Bird(ImageView birdView) {
        this.birdView = birdView;  // Kuşun görseli dışarıdan alınır ve atanır
    }

    public void jump() {
        velocity = jumpForce;  // Kuş zıpladığında hız yukarı doğru negatif bir değere set edilir
    }

    public void update(int screenHeight) {
        velocity += gravity;  // Her frame'de yerçekimi nedeniyle hız arttırılır (aşağı doğru hızlanma)
        float newY = birdView.getY() + velocity;  // Kuşun yeni Y pozisyonu mevcut pozisyon + hız

        if (newY < 0) newY = 0;  // Kuş ekranın en üstüne çıkamaz (y<0 olamaz)
        if (newY > screenHeight - birdView.getHeight()) {  // Kuş ekranın alt sınırını aşamaz
            newY = screenHeight - birdView.getHeight();
        }

        birdView.setY(newY);  // Kuşun Y pozisyonu güncellenir
    }

    public ImageView getView() {
        return birdView;  // Kuşun ImageView nesnesini dışarıya verir
    }
}
