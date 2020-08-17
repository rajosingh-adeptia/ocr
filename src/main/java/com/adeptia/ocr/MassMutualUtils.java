package com.adeptia.ocr;

import java.util.Date;
import java.util.Random;

public class MassMutualUtils {
	public static String generateRandomID() {

		final int ID_SIZE = 15;
		final int NUM_OF_CHARS = 36;
		StringBuffer id = new StringBuffer();
		long now = new Date().getTime();

		// Set the new Seed as current timestamp
		Random r = new Random(now);

		int index = 0;
		int x = 0;

		while (x < ID_SIZE) {
			index = r.nextInt(NUM_OF_CHARS);
			// System.out.println("Index="+ index);
			if (index < 10) {
				id.append((char) (48 + index));
			} else if (10 <= index && index < 36) {
				index = index - 10;
				id.append((char) (97 + index));
			}
			x++;
		}

		return id.toString();
	}
	
	public static String generateNumericRandomID(){

		StringBuffer id = new StringBuffer();
		long now = new Date().getTime();
		Random r = new Random();
		int num = r.nextInt(90) + 10;
        id.append(now).append(num);

		return id.toString();
		}

	public static void main(String args[]) {
		MassMutualUtils gd = new MassMutualUtils();
		//System.out.println("Generated random String is :" + gd.generateRandomID());
		System.out.println(generateRandomID());
	}
}
