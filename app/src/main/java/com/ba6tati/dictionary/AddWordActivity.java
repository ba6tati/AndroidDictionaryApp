package com.ba6tati.dictionary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import com.google.gson.Gson;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddWordActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        Intent intent = getIntent();

        Spinner wordType = findViewById(R.id.wordType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.wordTypes_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wordType.setAdapter(adapter);

        EditText wordInput = findViewById(R.id.wordInput);
        EditText definitionInput = findViewById(R.id.wordDefinition);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(AddWordActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });

        if (intent.hasExtra("word")) {
            String wordString = intent.getStringExtra("word");

            File wordDirectory = new File(getFilesDir(), "words");

            Word word = Utility.LoadWord(new File(wordDirectory, wordString + ".json"));

            wordInput.setText(wordString);
            definitionInput.setText(word.getDefinition());
            wordType.setSelection(Utility.GetSpinnerElementId(word.getType()));
        }

        Button addWordButton = findViewById(R.id.addWordButton);
        addWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                Intent mainActivity = new Intent(AddWordActivity.this, MainActivity.class);

                String word = wordInput.getText().toString();
                String definition = definitionInput.getText().toString();
                String type = wordType.getSelectedItem().toString();

                String filename = word + ".json";

                File wordsDirectory = new File(getFilesDir(), "words");

                if (!wordsDirectory.exists()) {
                    wordsDirectory.mkdirs();
                }

                File file = new File(wordsDirectory, filename);

                Log.d("INFO", "Created file " + filename);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("word", word);
                jsonObject.addProperty("definition", definition);
                jsonObject.addProperty("type", type);
                String jsonData = gson.toJson(jsonObject);

                try (FileOutputStream fos = new FileOutputStream(file, false)) {
                    fos.write(jsonData.getBytes());
                    // fos.write(word.getBytes());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                Log.d("INFO", "Saved data to file " + filename + ": " + jsonData);

                mainActivity.putExtra("word", word);


                startActivity(mainActivity);
            }
        });
    }
}
