package com.example.myapplication;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Random;

public class MyCanvasView extends View {

    private Paint lane_fill;
    private Paint obstaclePaint;
    private ObstacleSquare obstacle1;
    private ObstacleSquare obstacle3;
    private ObstacleSquare obstacle5;

    private Paint jerryPaint;
    private float jerryX = 540;
    public float jerryY = 1500;
    private float jerryRadius;
    private Paint TomPaint;
    private float TomX = 540;
    private float TomY = 1800;
    private float TomRadius;

    private int num_collisions;
    private int score;

    private static final String TAG = "MyCanvasView"; // Define TAG here

    public MyCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void obstaclesetter(){
        obstaclePaint = new Paint();
        obstaclePaint.setColor(Color.parseColor("#2a83eb"));

        obstacle1 = new ObstacleSquare();
        obstacle1.setObstacleProps(obstaclePaint, 10, 90, 0);


        obstacle3 = new ObstacleSquare();
        obstacle3.setObstacleProps(obstaclePaint, 10, 435, 400);


        obstacle5 = new ObstacleSquare();
        obstacle5.setObstacleProps(obstaclePaint, 10, 780, 800);
    }

    private void init() {
        lane_fill = new Paint();
        lane_fill.setColor(Color.parseColor("#e5dccd"));
        lane_fill.setStyle(Paint.Style.FILL);

        num_collisions = 0;
        score = 0;

        obstaclesetter();


        jerryPaint = new Paint();
        jerryPaint.setColor(Color.parseColor("#d48a1d"));
        jerryPaint.setStyle(Paint.Style.FILL);
        jerryRadius = 80;

        TomPaint = new Paint();
        TomPaint.setColor(Color.parseColor("#959ca2"));
        TomPaint.setStyle(Paint.Style.FILL);
        TomRadius = 100;
        startGame();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        // Clear the canvas
        canvas.drawColor(Color.WHITE);
        drawBackground(canvas);
        drawObstacles(canvas);
        drawCharacters(canvas);
    }

    private void drawObstacles(Canvas canvas) {
        drawObstacle(canvas, obstacle1);
//        drawObstacle(canvas, obstacle2);
        drawObstacle(canvas, obstacle3);
//        drawObstacle(canvas, obstacle4);
        drawObstacle(canvas, obstacle5);
    }

    private void drawObstacle(Canvas canvas, ObstacleSquare obstacle) {
        canvas.drawRect(
                obstacle.getObstacleX(),
                obstacle.getObstacleY(),
                obstacle.getObstacleX() + obstacle.getObstacleWidth(),
                obstacle.getObstacleY() + obstacle.getObstacleHeight(),
                obstacle.getObstacleColor()
        );
    }

    private void drawCharacters(Canvas canvas) {
        canvas.drawCircle(jerryX, jerryY, jerryRadius, jerryPaint);
        canvas.drawCircle(TomX, TomY, TomRadius, TomPaint);
    }


    public void drawBackground(Canvas canvas) {
        int width = getWidth();  // to get the width of the canvas
        int height = getHeight();  // to get the height of the canvas

        // For demonstration, logging the width and height
        Log.d(TAG, "Width: " + width + ", Height: " + height);

        // Drawing the three lanes
        drawLane(canvas, 60, 0, 330, height, lane_fill);
        drawLane(canvas, 390, 0, 690, height, lane_fill);
        drawLane(canvas, 750, 0, 1020, height, lane_fill);
    }
    private void drawLane(Canvas canvas, int x1, int y1, int x2, int y2, Paint paint) {
        Path lane = new Path();
        lane.moveTo(x1, y1);
        lane.lineTo(x1, y2);
        lane.lineTo(x2, y2);
        lane.lineTo(x2, y1);
        lane.lineTo(x1, y1);
        canvas.drawPath(lane, paint);
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:  // When the touch starts
//            case MotionEvent.ACTION_MOVE:  // When the touch moves
//                float touchX = event.getX();
//                float touchY = event.getY();
//
//                if (isTouchWithinJerry(touchX, touchY)) {
//                    // Check if the touch is within the Jerry circle
//                    if (touchX < jerryX) {
//                        // If touch is to the left of Jerry, move left
//                        if(jerryX >= 390){
//                            // we dont move left if already in left lane
//                            moveJerryLeft();
//                        }
//                    }else {
//                        // If touch is to the right of Jerry, move right
//                        if(jerryX <= 690){
//                            // we dont move right if already in right lane
//                            moveJerryRight();
//                        }
//                    }
//                    return true;  // Event handled, stop further propagation
//                }
//                return true;  // Touch is within Jerry, event handled
//        }
//        return super.onTouchEvent(event);  // For other actions, use default handling
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:  // When the touch starts
            case MotionEvent.ACTION_MOVE:  // When the touch moves
                float touchX = event.getX();
                float touchY = event.getY();

                if (touchX > jerryX) {
                    moveJerryRight();
                }else if(touchX < jerryX){
                    moveJerryLeft();
                }
        }
        return super.onTouchEvent(event);  // For other actions, use default handling
    }





    private boolean isTouchWithinJerry(float touchX, float touchY) {
        float dx = touchX - jerryX;
        float dy = touchY - jerryY;
        return (dx * dx + dy * dy) <= (jerryRadius * jerryRadius);
    }

    private void moveJerryLeft() {
        jerryX -= 330; // Step size can be adjusted as required
        invalidate();
    }

    private void moveJerryRight() {
        jerryX += 330; // Step size can be adjusted as required
        invalidate();
    }
    private void moveTom(){
        if(TomX > 60 && TomX < 330){
            if(TomY - TomRadius - (obstacle1.getObstacleY() + obstacle1.getObstacleHeight()) < 10){
                moveTomRight();
            }
        }else if(TomX > 390 && TomX < 690){
            if(TomY - TomRadius - (obstacle3.getObstacleY() + obstacle3.getObstacleHeight()) < 10){
                if(TomY - TomRadius - (obstacle1.getObstacleY() + obstacle1.getObstacleHeight()) < 10){
                    moveTomRight();
                }else if(TomY - TomRadius - (obstacle5.getObstacleY() + obstacle5.getObstacleHeight()) < 10){
                    moveTomLeft();
                }else{
                    moveTomLeft();
                }
            }
        }else if(TomX > 750 && TomX < 1020){
            if(TomY - TomRadius - (obstacle5.getObstacleY() + obstacle5.getObstacleHeight()) < 10) {
                moveTomLeft();
            }
        }
    }
    private void moveTomLeft() {
        TomX -= 330; // Step size can be adjusted as required
        invalidate();
    }

    private void moveTomRight() {
        TomX += 330; // Step size can be adjusted as required
        invalidate();
    }
    boolean alreadycollided1 = false;
    boolean alreadycollided5 = false;
    boolean alreadycollided3 = false;
    public void Collission(){
        if((jerryX > 60 && jerryX < 330)){
            if(jerryY - jerryRadius <= obstacle1.getObstacleY()+obstacle1.getObstacleHeight() && jerryY - jerryRadius >= obstacle1.getObstacleY()){
                if(alreadycollided1 == false){
                    alreadycollided1 = true;
                    num_collisions++;
                    if(num_collisions == 1){
                        displayToast("Collision detected, Tom has closed in");
                        CloserTom1();
                    }else if(num_collisions == 2){
                        CloserTom2();
                        displayToast("Game Over");
                    }
                }
            }
        }else if(jerryX > 390 && jerryX < 690){
            if(jerryY - jerryRadius <= obstacle3.getObstacleY()+obstacle3.getObstacleHeight() && jerryY - jerryRadius >= obstacle3.getObstacleY()){
                if(alreadycollided3 == false){
                    alreadycollided3 = true;
                    num_collisions++;
                    if(num_collisions == 1){
                        displayToast("Collision detected, Tom has closed in");
                        CloserTom1();
                    }else if(num_collisions == 2){
                        CloserTom2();
                        displayToast("Game Over");
                    }
                }
            }
        }else if( (jerryX > 750 && jerryX < 1020)){
            if(jerryY - jerryRadius <= obstacle5.getObstacleY()+obstacle5.getObstacleHeight() && jerryY - jerryRadius >= obstacle5.getObstacleY()){
                if(alreadycollided5 == false){
                    alreadycollided5 = true;
                    num_collisions++;
                    if(num_collisions == 1){
                        displayToast("Collision detected, Tom has closed in");
                        CloserTom1();
                    }else if(num_collisions == 2){
                        CloserTom2();
                        displayToast("Game Over");
                    }
                }
            }
        }
        if(alreadycollided1){
            if(jerryY - jerryRadius > obstacle1.getObstacleY()+obstacle1.getObstacleHeight() || jerryY + jerryRadius< obstacle1.getObstacleY()){
                alreadycollided1 = false;
            }
        }
        if(alreadycollided5){
            if(jerryY - jerryRadius > obstacle5.getObstacleY()+obstacle5.getObstacleHeight() || jerryY + jerryRadius< obstacle5.getObstacleY()){
                alreadycollided5 = false;
            }
        }
        if(alreadycollided3){
            if(jerryY - jerryRadius > obstacle3.getObstacleY()+obstacle3.getObstacleHeight() || jerryY + jerryRadius< obstacle3.getObstacleY()){
                alreadycollided3 = false;
            }
        }
        if (updateScore() >= 5000) {
            ((MainActivity) getContext()).WinDialog("5000");
        }
        if(num_collisions == 2){
            ((MainActivity) getContext()).normal_Dialog(String.valueOf(score));
        }
    }
    public void startGame() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (num_collisions < 2) {
                    RegenerateObstacles();
                    moveTom();
                    Collission();
                    if(score >= 5000){
                        num_collisions = 2;
                        displayToast("Jerry has escaped Tom, well done");
                    }
                    handler.postDelayed(this, 30); // 30 milliseconds interval for animation speed
                }
            }
        };
        handler.post(runnable);
    }
    private void moveObstacle(ObstacleSquare obstacle){
        obstacle.setObstacleY(obstacle.getObstacleY() + obstacle.getObstacleVelocity());
        invalidate();
    }
    private void RegenerateObstacles() {
        moveObstacle(obstacle1);
        moveObstacle(obstacle3);
        moveObstacle(obstacle5);

        // Whichever obstacle appears to move off screen, reset it back on screen
        resetIfOffScreen(obstacle1,0,50);
        resetIfOffScreen(obstacle5,250,300);
        resetIfOffScreen(obstacle3,500,550);
    }

    private void resetIfOffScreen(ObstacleSquare obstacle,int l_bound, int u_bound) {
        // Assuming getHeight() gives the height of your view
        int viewHeight = getHeight();
        if (obstacle.getObstacleY() > TomY + 50) {

            obstacle.setObstacleY(randomnum(l_bound,u_bound)); // Move it above the screen
            invalidate();
        }
    }
    private void displayToast(String s){
        Toast.makeText(getContext().getApplicationContext(),s,Toast. LENGTH_SHORT).show();
    }
    private void CloserTom1(){
        TomY -= 100;
    }
    private void CloserTom2(){
        TomY = jerryY;
    }
    public int updateScore(){
        score += (3-num_collisions);
        MainActivity.setScoreText("" + score);
        return score;
    }
    public void resetGameState() {
        // Reset all game-related variables
        score = 0;
        num_collisions = 0;
        alreadycollided1 = false;
        alreadycollided5 = false;
        alreadycollided3 = false;

        // Reset positions of Jerry and Tom
        jerryX = 540;
        jerryY = 1500;
        TomX = 540;
        TomY = 1800;

        // Reset obstacle positions
        obstaclesetter();
        // Invalidate the canvas to reflect the changes
        invalidate();
    }
    private int randomnum(int l_bound,int u_bound){
        Random rand = new Random();
        int Y = rand.nextInt(u_bound - l_bound) + l_bound;
        return Y;
    }
}