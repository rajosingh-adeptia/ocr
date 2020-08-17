package com.adeptia.ocr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HtmlImageReplacer {

	public static void main(String[] args) throws IOException {
		String msgContent = new String(Files.readAllBytes(Paths.get("C:\\Users\\Rajo\\Documents\\R\\CompleteEmail.html")));
		
		//String modifiedMsgContent=msgContent.replaceAll("<img(.[^<]*?)/>", "");
		System.out.println(msgContent);

	}

}
