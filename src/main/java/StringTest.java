
public class StringTest {
	private static final java.text.SimpleDateFormat sdf = 
		    new java.text.SimpleDateFormat("yyyyMMdd");

		public static java.util.Date verifyInput(String input) {
		  if (input != null) {
		    try {
		      java.util.Date ret = sdf.parse(input.trim());
		      if (sdf.format(ret).equals(input.trim())) {
		        return ret;
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		  }
		  return null;
		}

		public static void main(String[] args) {
		  String[] dates = new String[] { "20000230" };
		  for (String str : dates) {
		    System.out.println(verifyInput(str));
		  }
		}
}

	/*public static void main(String[] args) {
		try{
			String json="{\"id\":filequeueid,\"groupNumber\":\"GN\",\"payrollGroupId\":\"paGroupId\",\"fundingType\":\"fundType\",\"referenceType\":\"tableName\","
					+ "\"type\":\"Financial\",\"totalAmount\":\"\",\"isforFeiture\" :\"F\",\"sources\":[],\"BankId\":\"\"}";
			Long id=null;
			String referenceType="Wire";
			String type="";
			json=json.replace("filequeueid", id.toString()).replace("tableName", referenceType)
					.replace("Financial", type).replace("paGroupId", "").replace("fundType", ""
							.replace("GN", ""));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		

	}

}*/
