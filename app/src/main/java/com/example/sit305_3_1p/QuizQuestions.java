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

import java.util.Arrays;

public class QuizQuestions extends AppCompatActivity {
    TextView hello;
    TextView progressText;
    ProgressBar progressBar;
    TextView questionTitle;
    TextView questionDetails;
    AppCompatButton answer1;
    AppCompatButton answer2;
    AppCompatButton answer3;
    AppCompatButton submit;

    int picked;
    int currentQuestion = 1;
    Boolean submitted = false;
    String currentProgress;
    int correctly = 0;
    int incrementPercentage;
    String[] questionTitles;
    String[] questions;
    String[][] answers;

    public void nextQuestion() {
        progressBar.incrementProgressBy(incrementPercentage);
        picked = 0;
        answerPicked();
        currentQuestion++;
        submit.setText(R.string.submit);
        answer1.setEnabled(true);
        answer2.setEnabled(true);
        answer3.setEnabled(true);
        submitted = false;
        submit.setEnabled(false);
        questionTitle.setText(questionTitles[currentQuestion-1]);
        questionDetails.setText(questions[currentQuestion-1]);
        answer1.setText(answers[currentQuestion-1][0]);
        answer2.setText(answers[currentQuestion-1][1]);
        answer3.setText(answers[currentQuestion-1][2]);
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
        int totalQuestions = getResources().getInteger(R.integer.totalQuestions);
        double totalQ = totalQuestions;

        questionTitles = getResources().getStringArray(R.array.question_titles);
        questions = getResources().getStringArray(R.array.questions);
        String[] unsplitAnswers = getResources().getStringArray(R.array.answers);
        answers = new String[unsplitAnswers.length][];
        String test[];
        Log.i("answers", "test initialised");
        for (int i = 0; i < unsplitAnswers.length; i++) {
            Log.i("answers", "iteration");
            test = unsplitAnswers[i].split(",");
            Log.i("answers", Arrays.toString(test));
            answers[i] = test;
            Log.i("answers", Arrays.toString(answers[i]));
        }
        //String[] test = unsplitAnswers[0].split(",");
        Log.i("answers", Arrays.toString(answers));

        hello = findViewById(R.id.hello);
        progressText = findViewById(R.id.progressText);
        progressBar = findViewById(R.id.progressBar);
        questionTitle = findViewById(R.id.questionTitle);
        questionDetails = findViewById(R.id.questionDetails);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        submit = findViewById(R.id.submit);
        submit.setEnabled(false);

        String name = sharedPreferences.getString("USERNAME", "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String output = getString(R.string.hello, name);

        questionTitle.setText(questionTitles[currentQuestion-1]);
        questionDetails.setText(questions[currentQuestion-1]);
        answer1.setText(answers[currentQuestion-1][0]);
        answer2.setText(answers[currentQuestion-1][1]);
        answer3.setText(answers[currentQuestion-1][2]);

        currentProgress = getString(R.string.progress_bar, currentQuestion, totalQuestions);


        hello.setText(output);
        progressText.setText(currentProgress);
        incrementPercentage = (int) Math.ceil(100 / totalQ);
        progressBar.setProgress(incrementPercentage);

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
                else if (currentQuestion == totalQuestions) {
                    editor.putInt("correctly", correctly).apply();
                    startActivity(FinishQuiz);
                }
                else {
                    nextQuestion();
                    currentProgress = getString(R.string.progress_bar, currentQuestion, totalQuestions);
                    progressText.setText(currentProgress);
                }
            }
        });

    }
}