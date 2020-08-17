package com.adeptia.ocr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.asprise.ocr.Ocr;
import com.asprise.util.pdf.PDFReader;
public class AspriseJava {

	public static void main(String[] args) throws IOException {
		//PDFReader reader = new PDFReader(new File("D:\\OCR\\Doc for IL_images\\scansmpl.pdf"));
		//reader.open(); // open the file. 
		//int pages = reader.getNumberOfPages();
		 
		/*for(int i=0; i < pages; i++) {
		   BufferedImage img = reader.getPageAsImage(i);
		  // String text = reader.extractTextFromPage(i);
		   // recognizes both characters and barcodes
		   String text = new Ocr().recog;
		   System.out.println("Page " + i + ": " + text); 
		}
		 
		reader.close(); // finally, close the file.
*/
		Ocr.setUp(); // one time setup
		Ocr ocr = new Ocr(); // create a new OCR engine
		ocr.startEngine("eng", Ocr.SPEED_FASTEST); // English
		String s = ocr.recognize(new File[] {new File("C:\\Users\\Rajo\\Downloads\\6.jpg")}, Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT);
		System.out.println("Result: " + s);
		// ocr more images here ...
		ocr.stopEngine();
		
		
	}

}
