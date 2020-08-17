import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import com.adeptia.indigo.logging.Logger;

public class MMValidationUtils
{
  static Logger log = Logger.getLogger();
  
  @Comment("This is generic method  to check empty string,null, number and length")
  public static boolean validateInput(String input, int length)
  {
    if ((StringUtils.isEmpty(input)) || (!NumberUtils.isNumber(input)) || (input.length() != length)) {
      return false;
    }
    return true;
  }
  
  @Comment("This is generic method to check Empty and null")
  public static boolean isEmptyField(String input)
  {
    return (input == null) || (input.equals(""));
  }
  
  public static boolean validateGroupNumber(String groupNumber)
  {
    if (isEmptyField(groupNumber)) {
      return false;
    }
    try
    {
      int valueOfgroupNumber = Integer.parseInt(groupNumber);
      if ((valueOfgroupNumber > 0) && (validateInput(groupNumber, 6))) {
        return true;
      }
    }
    catch (Exception e)
    {
      log.error("Error while validating Group Number:\"" + groupNumber + "\"" + e.getMessage(), e);
      
      return false;
    }
    return false;
  }
  
  @Comment("This method validate Location Code ,it should be of length 4 and alphanumeric.It takes one input and return boolean")
  public static boolean validateLocationCode(String locationCode)
  {
    if (isEmptyField(locationCode)) {
      return false;
    }
    try
    {
      if (locationCode.length() == 4) {
        return true;
      }
    }
    catch (Exception e)
    {
      log.error("Error while validating locationCode Code:\"" + locationCode + "\"" + e.getMessage(), e);
      return false;
    }
    return false;
  }
  
  @Comment("This method validate SSN ,It be 9 digit numbers.It takes one input and return boolean")
  public static boolean validateSSN(String inputSSN)
  {
    if (isEmptyField(inputSSN)) {
      return false;
    }
    try
    {
      int valueOfinputSSN = Integer.parseInt(inputSSN);
      if ((valueOfinputSSN > 0) && (validateInput(inputSSN, 9)) && (!inputSSN.equals("999999999"))) {
        return true;
      }
    }
    catch (Exception e)
    {
      log.error("Error while validating inputSSN:\"" + inputSSN + "\"" + e.getMessage(), e);
      return false;
    }
    return false;
  }
  
  @Comment("This method validate Source Code ,It should not exceed length of 1 .It takes one input and return boolean")
  public static boolean validateSourceCode(String sourceCode)
  {
    try
    {
      if ((isEmptyField(sourceCode)) || (sourceCode.trim().length() != 1) || (sourceCode.trim().equals("0"))) {
        return false;
      }
    }
    catch (Exception e)
    {
      log.error("Error while validating sourceCode:\"" + sourceCode + "\"" + e.getMessage(), e);
      return false;
    }
    return true;
  }
  
  @Comment("This method Validate Loan Number,It can't be zero,It takes one input and return boolean")
  public static boolean validateLoanNumber(String loanNumber)
  {
    if (isEmptyField(loanNumber)) {
      return false;
    }
    try
    {
      int valueOfLoanNumber = Integer.parseInt(loanNumber.trim());
      if ((valueOfLoanNumber <= 0) || (!validateInput(loanNumber.trim(), 3))) {
        return false;
      }
    }
    catch (Exception e)
    {
      log.error("Error while validating loanNumber:\"" + loanNumber + "\"" + e.getMessage(), e);
      return false;
    }
    return true;
  }
  
  @Comment("This method Validate money,It should be i the format of #.###, It takes one input and return boolean.")
  public static boolean validateMoney(String money)
  {
    if (isEmptyField(money)) {
      return false;
    }
    try
    {
      if (money.contains("."))
      {
        String[] lenOfMoneyAfterDecimal = money.trim().split("\\.");
        if ((lenOfMoneyAfterDecimal.length == 2) && (lenOfMoneyAfterDecimal[1].length() > 2)) {
          return false;
        }
      }
    }
    catch (Exception e)
    {
      log.error("Error while validating money:\"" + money + "\"" + e.getMessage(), e);
      return false;
    }
    return true;
  }
  
  @Comment("This function valiadte,dateOfBirth must be greater than 15 years from the current date.It takes one input and return boolean. ")
  public static boolean dateOfBirth(String inputDOB)
  {
    if (isEmptyField(inputDOB)) {
      return false;
    }
    try
    {
      SimpleDateFormat formatteddate = new SimpleDateFormat("yyyyMMdd");
      
      Date dateOfbirth = formatteddate.parse(inputDOB);
      Date currentdate = new Date();
      long dateDiff = currentdate.getTime() - dateOfbirth.getTime();
      long result = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);
      long years = result / 365L;
      if ((years == 15L) || (years > 15L)) {
        return true;
      }
    }
    catch (Exception e)
    {
      log.error("Error while validating DOB : \"" + inputDOB + "\"" + e.getMessage(), e);
    }
    return false;
  }
  
  @Comment("This function valiadtes,Original hire date must be greater than date of birth.It takes two input respectively \"Original Hire Date\" and \"date of birth\" and retun boolean")
  public static boolean dateOforiginalHireDate(String DOH, String DOB)
  {
    if ((isEmptyField(DOH)) || (isEmptyField(DOB))) {
      return false;
    }
    try
    {
      SimpleDateFormat formatteddate = new SimpleDateFormat("yyyyMMdd");
      
      Date doh = formatteddate.parse(DOH);
      Date dob = formatteddate.parse(DOB);
      long dateDiff = doh.getTime() - dob.getTime();
      long result = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);
      long years = result / 365L;
      System.out.println(years);
      if ((years == 15L) || (years > 15L)) {
        return true;
      }
    }
    catch (Exception e)
    {
      log.error("Error while validating Date of original Hire date : \"" + DOH + "\"" + " must be greater than " + "\"" + DOB + "\"" + e.getMessage(), e);
    }
    return false;
  }
  
  /**
	 * @param dateReceivedFromUser
	 * @return boolean
	 * @throws exception
	 */
	@Comment("This method Validate the format of Date,It should be yyyyMMdd,It takes one input and return boolean.")
	public static boolean validateDate(String dateReceivedFromUser,String dateFormat) {
		
		if(isEmptyField(dateReceivedFromUser)|| MMValidationUtils.isEmptyField(dateFormat))
			return false;
		try {
	    	/*if (dateFormat.equals("yyyyMMdd")){
	    		return true;
	    	}*/
	    	DateFormat inputDateFormat = new SimpleDateFormat(dateFormat);
			Date date = inputDateFormat.parse(dateReceivedFromUser.trim());
			String outputDatFormat = inputDateFormat.format(date);
			if (  outputDatFormat.equals(dateReceivedFromUser.trim())) {
				return true;
			}
		} catch (Exception e) {

			log.error("Error while validating validateDate:" + "\"" + dateReceivedFromUser + "\"" + e.getMessage(), e);
			return false;
		}

		return false;

	}
public static void main(String[] args) {
	Boolean flag=dateOfBirth("20200134");
	System.out.println(flag);
}

}
