package com.emirercan.flappybird;

import android.widget.ImageView;

public class Bird {
    private ImageView birdView;
    private float velocity = 0;
    private final float gravity = 3.5f;
    private final float jumpForce = -35f;

    public Bird(ImageView birdView) {
        this.birdView = birdView;
    }

    public void jump() {
        velocity = jumpForce;
    }

    public void update(int screenHeight) {
        velocity += gravity;
        float newY = birdView.getY() + velocity;

        if (newY < 0) newY = 0;
        if (newY > screenHeight - birdView.getHeight()) {
            newY = screenHeight - birdView.getHeight();
        }

        birdView.setY(newY);
    }

    public ImageView getView() {
        return birdView;
    }
}