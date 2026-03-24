package com.example.sit305_3_1p;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuizQuestions extends AppCompatActivity {
    TextView hello;
    TextView progressText;
    ProgressBar progressBar;
    AppCompatButton answer1;
    AppCompatButton answer2;
    AppCompatButton answer3;
    AppCompatButton submit;

    int picked;
    int currentQuestion = 1;
    Boolean submitted = false;
    String currentProgress;
    int correctly = 0;

    public void nextQuestion() {
        progressBar.incrementProgressBy(20);
        picked = 0;
        answerPicked();
        currentQuestion++;
        submit.setText(R.string.submit);
        answer1.setEnabled(true);
        answer2.setEnabled(true);
        answer3.setEnabled(true);
        submitted = false;
        submit.setEnabled(false);
    }
    public void answerPicked() {
        submit.setEnabled(true);
        answer1.setBackgroundResource(R.drawable.ans_btn);
        answer2.setBackgroundResource(R.drawable.ans_btn);
        answer3.setBackgroundResource(R.drawable.ans_btn);
        switch(picked) {
            case 1:
                answer1.setBackgroundResource(R.drawable.standard_btn);
                break;
            case 2:
                answer2.setBackgroundResource(R.drawable.standard_btn);
                break;
            case 3:
                answer3.setBackgroundResource(R.drawable.standard_btn);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_questions);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent FinishQuiz = new Intent(this, FinishPage.class);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.sit305_3_1p", MODE_PRIVATE);

        hello = findViewById(R.id.hello);
        progressText = findViewById(R.id.progressText);
        progressBar = findViewById(R.id.progressBar);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        submit = findViewById(R.id.submit);
        submit.setEnabled(false);

        String name = sharedPreferences.getString("USERNAME", "");
        String output = getString(R.string.hello, name);

        currentProgress = getString(R.string.progress_bar, currentQuestion);


        hello.setText(output);
        progressText.setText(currentProgress);
        progressBar.setProgress(0);
        progressBar.incrementProgressBy(20);

        int[] correctAnswers = getResources().getIntArray(R.array.correct_answers);

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picked = 1;
                answerPicked();
            }
        });
        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picked = 2;
                answerPicked();
            }
        });
        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picked = 3;
                answerPicked();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!submitted) {
                    answer1.setEnabled(false);
                    answer2.setEnabled(false);
                    answer3.setEnabled(false);
                    switch (correctAnswers[currentQuestion - 1]) {
                        case 1:
                            answer1.setBackgroundResource(R.drawable.ans_btn_correct);
                            break;
                        case 2:
                            answer2.setBackgroundResource(R.drawable.ans_btn_correct);
                            break;
                        case 3:
                            answer3.setBackgroundResource(R.drawable.ans_btn_correct);
                            break;
                    }
                    if (picked != correctAnswers[currentQuestion - 1]) {
                        switch (picked) {
                            case 1:
                                answer1.setBackgroundResource(R.drawable.ans_btn_incorrect);
                                break;
                            case 2:
                                answer2.setBackgroundResource(R.drawable.ans_btn_incorrect);
                                break;
                            case 3:
                                answer3.setBackgroundResource(R.drawable.ans_btn_incorrect);
                                break;
                        }
                    }
                    else {
                        correctly++;
                    }
                    submitted = true;
                    submit.setText(R.string.next);
                }
                // Need to add logic and intent for finish activity.
                else if (currentQuestion == 5) {
                    startActivity(FinishQuiz);
                }
                else {
                    nextQuestion();
                    currentProgress = getString(R.string.progress_bar, currentQuestion);
                    progressText.setText(currentProgress);
                }
            }
        });

    }
}