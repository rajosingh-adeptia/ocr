import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToMilliseconds {
	
	public static long dateConversion(Date date){
		return date.getTime();
		
		
	}
	public static Date millsToDate(long milli){
	
		return new Date(milli);
	}

	public static void main(String[] args) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyyMMDD"); 
		Date dateDOB = (Date)formatter.parse("20050201");
		Date dateOriginalHire = new Date();
		long diffInMilliSec = dateOriginalHire.getTime() - dateDOB.getTime();
		long years =  (diffInMilliSec / (1000l * 60 * 60 * 24 * 365));
		if(years>15){
			//dateOfOriginalHireFinal="dateOfOriginalHire";
		}
		else if(years==15){
			long days = (diffInMilliSec / (1000 * 60 * 60 * 24)) % 365;
			if(days>=1){
				System.out.println(days);
				//dateOfOriginalHireFinal="dateOfOriginalHire";
			}
		}
		System.out.println(dateConversion(new Date()));
		System.out.println(millsToDate(dateConversion(new Date())));
		Date date=millsToDate(dateConversion(new Date()));
		DateFormat df =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println(df.format(date));
		System.err.println("");
		String temp="hello";
		temp=temp+",";		

	}

}
