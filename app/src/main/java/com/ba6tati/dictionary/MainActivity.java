package com.ba6tati.dictionary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

        Intent intent = getIntent();

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addWordActivity = new Intent(MainActivity.this, AddWordActivity.class);
                startActivity(addWordActivity);
            }
        });

        ArrayList<Word> words = Utility.LoadWords(new File(getFilesDir(), "words"));

        if (!words.isEmpty()) {
            for (Word word: words) {
                RenderWord(word.getWord());
            }
        }
    }

    void RenderWord(String word) {
        LinearLayout wordLayout = findViewById(R.id.wordLayout);

        Button wordButton = new Button(this);
        wordButton.setText(word);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        wordButton.setLayoutParams(params);

        wordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addWordActivity = new Intent(MainActivity.this, AddWordActivity.class);

                addWordActivity.putExtra("word", word);

                startActivity(addWordActivity);
            }
        });

        wordLayout.addView(wordButton);
    }
}