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
    // View declarations
    TextView hello;
    TextView progressText;
    ProgressBar progressBar;
    TextView questionTitle;
    TextView questionDetails;
    AppCompatButton answer1;
    AppCompatButton answer2;
    AppCompatButton answer3;
    AppCompatButton submit;

    // Variable declarations
    int picked;
    int currentQuestion = 1;
    int correctly = 0;
    int incrementPercentage;
    Boolean submitted = false;
    String currentProgress;
    String[] questionTitles;
    String[] questions;
    String[][] answers;

    // Updates the views for the next question
    public void nextQuestion() {
        picked = 0;
        answerPicked();
        progressBar.incrementProgressBy(incrementPercentage);
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

    // Changes the background of the answer buttons depending on selection
    public void answerPicked() {
        // Enable submit button and reset all backgrounds
        submit.setEnabled(true);
        answer1.setBackgroundResource(R.drawable.ans_btn);
        answer2.setBackgroundResource(R.drawable.ans_btn);
        answer3.setBackgroundResource(R.drawable.ans_btn);
        // Change the selected answer to highlighted background
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

        // Intent to next activity
        Intent FinishQuiz = new Intent(this, FinishPage.class);

        // Set up shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.sit305_3_1p", MODE_PRIVATE);

        int totalQuestions = getResources().getInteger(R.integer.totalQuestions);
        double totalQ = totalQuestions;

        // Read in values for question titles, details and answers from strings.xml
        questionTitles = getResources().getStringArray(R.array.question_titles);
        questions = getResources().getStringArray(R.array.questions);
        // Convert 1D array with delimiters to 2D array
        String[] unsplitAnswers = getResources().getStringArray(R.array.answers);
        answers = new String[unsplitAnswers.length][];
        String[] currentArray;
        for (int i = 0; i < unsplitAnswers.length; i++) {
            currentArray = unsplitAnswers[i].split(",");
            answers[i] = currentArray;
        }

        // Link views to xml file
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

        // Get shared preferences data
        String name = sharedPreferences.getString("USERNAME", "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String output = getString(R.string.hello, name);

        // Set text of question title, details and answers views
        questionTitle.setText(questionTitles[currentQuestion-1]);
        questionDetails.setText(questions[currentQuestion-1]);
        answer1.setText(answers[currentQuestion-1][0]);
        answer2.setText(answers[currentQuestion-1][1]);
        answer3.setText(answers[currentQuestion-1][2]);

        currentProgress = getString(R.string.progress_bar, currentQuestion, totalQuestions);

        // Set text and progress of progress bar
        hello.setText(output);
        progressText.setText(currentProgress);
        incrementPercentage = (int) Math.ceil(100 / totalQ);
        progressBar.setProgress(incrementPercentage);

        // Read in array of correct answers
        int[] correctAnswers = getResources().getIntArray(R.array.correct_answers);

        // When each answer or submit button is pressed
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
                // Submit question if not already submitted
                if (!submitted) {
                    // Disable answer buttons
                    answer1.setEnabled(false);
                    answer2.setEnabled(false);
                    answer3.setEnabled(false);
                    // Change correct answer background to green
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
                    // If wrong answer selected change background to red
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
                    // Increment value of times answered correctly if correct
                    else {
                        correctly++;
                    }
                    submitted = true;
                    submit.setText(R.string.next);
                }
                // If last question has been submitted button takes user to next activity
                else if (currentQuestion == totalQuestions) {
                    editor.putInt("correctly", correctly).apply();
                    startActivity(FinishQuiz);
                }
                // If submitted the next question is loaded
                else {
                    nextQuestion();
                    currentProgress = getString(R.string.progress_bar, currentQuestion, totalQuestions);
                    progressText.setText(currentProgress);
                }
            }
        });

    }
}