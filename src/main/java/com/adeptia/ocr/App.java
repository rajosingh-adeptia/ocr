package com.adeptia.ocr;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws TesseractException
    {
        File file =new File("C:\\Users\\Rajo\\Downloads\\6.jpg");
       // File file =new File("D:\\OCR\\pdftoimage\\sample - Copy-06.jpg");
        //D:\ScreenShot
        Tesseract tesseract =new Tesseract();
       // tesseract.setPageSegMode(3);
        tesseract.setDatapath("D:/tessdata/");
       String str= tesseract.doOCR(file);
      
       System.out.println(str);
        
    }
}
