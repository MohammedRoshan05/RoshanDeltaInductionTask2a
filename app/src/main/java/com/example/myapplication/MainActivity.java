package com.example.myapplication;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static TextView scoreText;
    public static void setScoreText(String score){
        scoreText.setText("Score = " + score);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        MyCanvasView canvasView = findViewById(R.id.mainCanvas);
        scoreText = findViewById(R.id.scoreText);
        scoreText.setX(350);
        scoreText.setY(100);
        scoreText.setTextColor(Color.parseColor("#e99650"));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void WinDialog(String score) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        MyCanvasView canvasView = findViewById(R.id.mainCanvas);

        // Inflate and set the custom layout
        View dialogView = getLayoutInflater().inflate(R.layout.win_dialog, null);
        builder.setView(dialogView);

        // Get references to the buttons in the dialog box
        TextView playAgainButton = dialogView.findViewById(R.id.playAgain);
        EditText score_display = dialogView.findViewById(R.id.score_dialog);

        // Set score in the editText
        score_display.setText("Score = " + score);


        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Set click listeners for the buttons
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasView.resetGameState();
                canvasView.startGame();
                dialog.dismiss(); // Remove the dialog when "Play Again" button is clicked
            }
        });

        // Set the background of the dialog window to transparent
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }
    public void normal_Dialog(String score) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        MyCanvasView canvasView = findViewById(R.id.mainCanvas);

        // Inflate and set the custom layout
        View dialogView = getLayoutInflater().inflate(R.layout.normal_dialogbox, null);
        builder.setView(dialogView);

        // Get references to the buttons in the dialog box
        TextView playAgainButton = dialogView.findViewById(R.id.playAgain_lose);
        EditText score_display = dialogView.findViewById(R.id.score_dialog_lose);

        // Set score in the editText
        score_display.setText("Score = " + score);


        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Set click listeners for the buttons
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasView.resetGameState();
                canvasView.startGame();
                dialog.dismiss(); // Remove the dialog when "Play Again" button is clicked
            }
        });

        // Set the background of the dialog window to transparent
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

}