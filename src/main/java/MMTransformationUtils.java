import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import org.apache.commons.lang.StringUtils;

public class MMTransformationUtils
{
  @Comment("Format Group Number, pad with leading zero until the length is 6,It takes one input and return String.")
  public static String groupNumberTransformation(String groupNumber)
  {
    if (MMValidationUtils.isEmptyField(groupNumber)) {
      return groupNumber;
    }
    if (!groupNumber.trim().isEmpty())
    {
      groupNumber = StringUtils.leftPad(groupNumber.trim(), 6, '0');
      return groupNumber;
    }
    return groupNumber;
  }
  
  @Comment("Format Location Code, pad with leading zero until the length is 4, It takes one input and return String.")
  public static String locationCodeTransformation(String locationCode)
  {
    if ((MMValidationUtils.isEmptyField(locationCode)) || (locationCode.trim().isEmpty())) {
      return "0000";
    }
    if (!locationCode.trim().isEmpty()) {
      return locationCode = StringUtils.leftPad(locationCode.trim(), 4, '0');
    }
    return locationCode;
  }
  
  @Comment("Format SSN, pad with leading zero until the length is 9, It takes one input and return String.")
  public static String SSNTransformation(String inputSSN)
  {
    if (MMValidationUtils.isEmptyField(inputSSN)) {
      return inputSSN;
    }
    String reg = "[-]";
    inputSSN = inputSSN.replaceAll(reg, "");
    if (!inputSSN.trim().isEmpty()) {
      return inputSSN = StringUtils.leftPad(inputSSN.trim(), 9, '0');
    }
    return inputSSN;
  }
  
  @Comment("Date transformation,It takes two input 1.XPATH of source date 2.Date formate of input date like 'yyyyMMdd',and it returns String.")
  public static String dateTransformation(String inputdate, String dateFormat)
  {
    if ((MMValidationUtils.isEmptyField(inputdate)) || (MMValidationUtils.isEmptyField(dateFormat))) {
      return "";
    }
    String result = "";
    SimpleDateFormat inputDateFormat;
    SimpleDateFormat outputDateFormat;
    try
    {
      if (dateFormat.equals("yyyyMMdd"))
      {
       
        return inputdate;
      }
       inputDateFormat = new SimpleDateFormat(dateFormat);
       outputDateFormat = new SimpleDateFormat("yyyyMMdd");
      result = outputDateFormat.format(inputDateFormat.parse(inputdate));
    }
    catch (Exception e)
    {
      e.printStackTrace();
     // SimpleDateFormat inputDateFormat;
      //SimpleDateFormat outputDateFormat;
      return "";
    }
    finally
    {
      inputDateFormat = null;
      outputDateFormat = null;
    }
   // SimpleDateFormat inputDateFormat = null;
   // SimpleDateFormat outputDateFormat = null;
    
    return result;
  }
  
  @Comment("Format loan number,left pad zero if length is less than 3 digit code")
  public static String loanNumberTransformation(String loanNumber)
  {
	  try{
		  if (MMValidationUtils.isEmptyField(loanNumber)) {
		      return loanNumber;
		    }
		  Integer.parseInt(loanNumber.trim());
		    loanNumber = StringUtils.leftPad(loanNumber.trim(), 3, '0');
		    return loanNumber;
	  }
	  catch(Exception e){
		  return loanNumber;
	  }
   
  }
  
  @Comment("This method is use to Strip $ symbols ,Strip commas from  amounts,It takes one input and return String.")
  public static String moneyTransformation(String money)
  {
    if ((money == null) || (money.equals(""))) {
      return money;
    }
    String reg = "[$,]";
    money = money.trim();
    money = money.replaceAll(reg, "");
    try
    {
      String impliedZero = "0.00";
      
      Double localDouble = new Double(money);
      DecimalFormat localDecimalFormat = new DecimalFormat(impliedZero);
      money = localDecimalFormat.format(localDouble);
    }
    catch (Exception localException)
    {
      return money;
    }
    return money;
  }
  
  @Comment("This method will return a constant value \"OTDE\"(for OneTimeDataEntryField) for those element on which this function will applly.")
  public static String setOneTimeDataEntryField()
  {
    return "OTDE";
  }
}
