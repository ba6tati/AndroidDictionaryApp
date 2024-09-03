package com.ba6tati.dictionary;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Utility {
    public static ArrayList<Word> LoadWords(File directory) {

        ArrayList<Word> words = new ArrayList<>();

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            for (File file: files) {
                // file.delete();
                Word word = LoadWord(file);
                words.add(word);
            }
        }

        return words;
    }

    public static Word LoadWord(File file) {
        StringBuilder jsonString = new StringBuilder();

        try {
            // File file = new File(getFilesDir(), "amazon.json");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while (reader.ready()) {
                jsonString.append(reader.readLine());
                // RenderWord(reader.readLine());
            }

            Gson gson = new Gson();

            Word word = gson.fromJson(jsonString.toString(), Word.class);
            
            return word;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int GetSpinnerElementId(String spinnerElement) {
        Map<String, Integer> wordType = new HashMap<>();

        wordType.put("Verb", 0);
        wordType.put("Noun", 1);
        wordType.put("Adjective", 2);
        wordType.put("Phrase", 3);
        wordType.put("Idiom", 4);
        wordType.put("Clause", 5);
        wordType.put("Preposition", 6);
        wordType.put("Conjunction", 7);
        wordType.put("Interjection", 8);
        wordType.put("Pronoun", 9);
        wordType.put("Adverb", 10);
        wordType.put("Prefix", 11);
        wordType.put("Suffix", 12);

        return wordType.get(spinnerElement);
    }
}
