package com.example.myapplication;

import android.graphics.Color;
import android.graphics.Paint;

public class ObstacleSquare {
    private Paint obstacleColor;
    private int obstacleX;
    private int obstacleY;
    private int obstacleVelocity;
    private int obstacleWidth ;
    private int obstacleHeight ;
    public void setObstacleProps(Paint obstacleColor,int obstacleVelocity,int obstacleX,int obstacleY){
        this.obstacleColor = obstacleColor;
        this.obstacleX = obstacleX;
        this.obstacleY = obstacleY;
        this.obstacleVelocity = obstacleVelocity;
        obstacleWidth = 210;
        obstacleHeight = 100;
    }
    public Paint getObstacleColor() {
        return obstacleColor;
    }

    public int getObstacleVelocity() {
        return obstacleVelocity;
    }

    public int getObstacleWidth() {
        return obstacleWidth;
    }

    public int getObstacleHeight() {
        return obstacleHeight;
    }

    public int getObstacleX() {
        return obstacleX;
    }

    public void setObstacleX(int obstacleX) {
        this.obstacleX = obstacleX;
    }

    public int getObstacleY() {
        return obstacleY;
    }

    public void setObstacleY(int obstacleY) {
        this.obstacleY = obstacleY;
    }
}
