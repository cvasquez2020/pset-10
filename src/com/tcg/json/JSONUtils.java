package com.tcg.json;

import java.util.Scanner;

import org.JSON.JSONObject;

import java.io.InputStream;

public class JSONUtils {
	
	public static String getJSONStringFromFile(String path) {
		Scanner scanner;
		InputStream in = FileHandle.inputStreamFromFile(path);
		scanner = new Scanner(in);
		String json = scanner.useDelimiter("\\z").next();
		scanner.close();
		return json;
	}

	public static JSONObject getJSONObjectFromFile(String path) {
		return new JSONObject(getJSONStringFromFile(path));
	}
	
	public static boolean objectExists(JSONObject jsonObjects, String key) {
		Object o;
		try {
			o = jsonObjects.get(key);
		} catch(Exception e) {
			return false;
		}
		
		return o != null;
		
	}
}
