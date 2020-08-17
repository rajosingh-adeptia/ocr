package com.adeptia.ocr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPatternFinder {

	public static void main(String[] args) throws IOException {
		
		//Map<String, Map<String,String>> 
		
		//File file=new File("C:\\Users\\Rajo\\Desktop\\onetimedata.xml");
		String contents = new String(Files.readAllBytes(Paths.get("C:\\Users\\Rajo\\Desktop\\onetimedata.xml"))); 
		
		Pattern pattern= Pattern.compile("<xsl:value-of select=\"saxonJavaMassMutualTransformationUtil:setOneTimeDataEntryField(.[^<]*?)xmlns:saxonJavaMassMutualTransformationUtil=\"java:MassMutualTransformationUtil\"/>");
		Matcher matcher=pattern.matcher(contents);
		
		while(matcher.find()){
			String str=matcher.group(1);
			str=str.replace("(", "").replace(")", "").replace("\"", "").trim();
			System.out.println(str);
			
		}

	}

}
