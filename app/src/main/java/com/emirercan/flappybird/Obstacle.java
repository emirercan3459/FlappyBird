package com.emirercan.flappybird;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Obstacle {
    private ImageView upperPipe;
    private ImageView lowerPipe;
    private static final int OBSTACLE_WIDTH = 200;
    private static final int GAP_HEIGHT = 600;
    private static final int MIN_HEIGHT = 200;
    private int speed = 10;

    public Obstacle(Context context, RelativeLayout gameLayout, int screenWidth, int screenHeight) {
        createPipes(context, gameLayout, screenWidth, screenHeight);
    }

    private void createPipes(Context context, RelativeLayout gameLayout, int screenWidth, int screenHeight) {
        int upperHeight = MIN_HEIGHT + (int)(Math.random() * (screenHeight - GAP_HEIGHT - 2 * MIN_HEIGHT));
        int lowerHeight = screenHeight - upperHeight - GAP_HEIGHT;

        upperPipe = new ImageView(context);
        upperPipe.setImageResource(R.drawable.pipegreen);
        upperPipe.setScaleType(ImageView.ScaleType.FIT_XY);
        upperPipe.setRotation(180);
        RelativeLayout.LayoutParams upperParams = new RelativeLayout.LayoutParams(OBSTACLE_WIDTH, upperHeight);
        upperParams.leftMargin = screenWidth;
        upperParams.topMargin = 0;
        upperPipe.setLayoutParams(upperParams);

        lowerPipe = new ImageView(context);
        lowerPipe.setImageResource(R.drawable.pipegreen);
        lowerPipe.setScaleType(ImageView.ScaleType.FIT_XY);
        RelativeLayout.LayoutParams lowerParams = new RelativeLayout.LayoutParams(OBSTACLE_WIDTH, lowerHeight);
        lowerParams.leftMargin = screenWidth;
        lowerParams.topMargin = upperHeight + GAP_HEIGHT;
        lowerPipe.setLayoutParams(lowerParams);

        gameLayout.addView(upperPipe);
        gameLayout.addView(lowerPipe);
    }

    public void move() {
        RelativeLayout.LayoutParams upperParams = (RelativeLayout.LayoutParams) upperPipe.getLayoutParams();
        RelativeLayout.LayoutParams lowerParams = (RelativeLayout.LayoutParams) lowerPipe.getLayoutParams();

        upperParams.leftMargin -= speed;
        lowerParams.leftMargin -= speed;

        upperPipe.setLayoutParams(upperParams);
        lowerPipe.setLayoutParams(lowerParams);
    }

    public View getUpperPipe() {
        return upperPipe;
    }

    public View getLowerPipe() {
        return lowerPipe;
    }
}