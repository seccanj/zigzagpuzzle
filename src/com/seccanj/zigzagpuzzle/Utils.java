package com.seccanj.zigzagpuzzle;

public class Utils {

	public static String padWitZeros(int num, int len) {
		String result = String.valueOf(num);
		
		if (result.length() < len) {
			result = "0000000000".substring(0, len - result.length()) + result;
		}
		
		return result;
	}
	
}
