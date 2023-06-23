package com.etjava.test;

public class StringTest {

	public static void main(String[] args) {
		String s = "a.b.c.jpg";
		String  s2 = s.substring(s.length()-4, s.length());
		System.out.println(s2.contains("jpg"));
	}
}
