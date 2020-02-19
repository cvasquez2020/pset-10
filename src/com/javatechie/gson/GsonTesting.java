package com.javatechie.gson;


import com.google.gson.Gson;

public interface GsonTesting {
	public static void main(String[] args) {
		
		Gson gson = new Gson();
		
		int[] array = new int[] {1,2,3,4,5,6};
		System.out.println(gson.toJson(array));
	}
}
