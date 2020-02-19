//package reading_file;

import java.io.*;
import java.util.ArrayList;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException; 
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class GSON {
public static void main(String [] args) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
	Words[] allWords = new Gson().fromJson(new FileReader(".\\JSON\\words.json"), Words[].class);
	System.out.print(allWords.length);
}

}