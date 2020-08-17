package com.adeptia.ocr;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Rajo
 *
 */
public class DateCalculator
{
  //@Comment("This Method return back date as  String  by taking date(String) of format yyyy-MM-dd HH:mm:ss.S and number of day(integer) which you want to deduct")
  
	
	/**
	 * @param inputDate
	 * @param numberOfDays
	 * @return
	 * @throws Exception
	 */
	public static String dateCalculator(String inputDate, int numberOfDays)
    throws Exception
  {
    String strDate = null;
    try
    {
      Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(inputDate);
      String[] strMon = inputDate.split("-");
      int month = Integer.parseInt(strMon[1]);
      if (((numberOfDays > 0) && (numberOfDays <= 31)) && ((month > 0) && (month <= 12)))
      {
        long millis = date.getTime();
        long daynumber = numberOfDays * 1000L * 60L * 60L * 24L;
        long previousDays = millis - daynumber;
        DateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        strDate = simple.format(new Date(previousDays));
      }
    }
    catch (Exception e)
    {
      throw new Exception(e);
    }
    return strDate;
  }
}
