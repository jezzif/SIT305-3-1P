package com.example.sit305_3_1p;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent StartQuiz = new Intent(this, QuizQuestions.class);

        AppCompatButton start_btn = findViewById(R.id.start_btn);
        EditText name_input = findViewById(R.id.name_input);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.sit305_3_1p", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        boolean again = false;
        Bundle newQuizIntent = getIntent().getExtras();
        if (newQuizIntent != null) {
            again = newQuizIntent.getBoolean("Again");
        }
        if (again) {
            String name = sharedPreferences.getString("USERNAME", "");
            name_input.setText(name);
        }

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name_input.getText().toString().isEmpty()) {
                    editor.putString("USERNAME", name_input.getText().toString()).apply();
                    startActivity(StartQuiz);
                }
            }
        });
    }
}