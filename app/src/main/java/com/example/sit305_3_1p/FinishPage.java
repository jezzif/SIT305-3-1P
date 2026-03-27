package com.example.sit305_3_1p;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FinishPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_finish_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Intent to first activity
        Intent NewQuiz = new Intent(this, MainActivity.class);
        NewQuiz.putExtra("Again",true);

        // Set up shared preferences and retrieve data
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.sit305_3_1p", MODE_PRIVATE);
        int correctly = sharedPreferences.getInt("correctly", 0);
        String name = sharedPreferences.getString("USERNAME", "");

        // Variables to be used in textViews
        String congrats = getString(R.string.congrats, name);
        int totalQuestions = getResources().getInteger(R.integer.totalQuestions);
        String score = getString(R.string.progress_bar, correctly, totalQuestions);

        // View declarations
        TextView correctText = findViewById(R.id.correctText);
        TextView congratsText = findViewById(R.id.congratsText);
        AppCompatButton newQuizBtn = findViewById(R.id.newQuizBtn);
        AppCompatButton finishBtn = findViewById(R.id.finishBtn);

        // Set text based on variables
        correctText.setText(score);
        congratsText.setText(congrats);

        // Restart the quiz
        newQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(NewQuiz);
            }
        });

        // Exit the app
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });
    }
}