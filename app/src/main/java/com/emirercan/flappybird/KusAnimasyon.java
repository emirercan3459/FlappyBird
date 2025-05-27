package com.emirercan.flappybird;

import android.os.Handler;
import android.widget.ImageView;

public class KusAnimasyon {
    private final ImageView kus;
    private final Handler handler;
    private int currentFrame = 0;
    private static final int[] FRAMES = {
            R.drawable.yellowbirdupflap,
            R.drawable.yellowbirdmidflap,
            R.drawable.yellowbirddownflap
    };
    private boolean isRunning = false;

    public KusAnimasyon(ImageView kus) {
        this.kus = kus;
        this.handler = new Handler();
    }

    public void start() {
        isRunning = true;
        animateFrame();
    }

    private void animateFrame() {
        if (!isRunning) return;

        kus.setImageResource(FRAMES[currentFrame]);
        currentFrame = (currentFrame + 1) % FRAMES.length;

        handler.postDelayed(this::animateFrame, 100);
    }

    public void stop() {
        isRunning = false;
    }
}