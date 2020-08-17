package com.adeptia.ocr;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * @author Rajo Adeptia Process file and generate an Omni Request file based on
 *         a unique combination of Payroll Date and Group Number. Adeptia treats
 *         each file as individual and never merge one file data into another
 *         even if they belong to the same groups like if Adeptia got two
 *         different files of Contributions(Financial) then Adeptia will
 *         generate different Omni request file for both the file Date
 *         26-07-2019
 */
public class OmniRequestFileBuilderNB {
	// maximum line characters. could provide ability to break a line into
	// separate lines
	// Maximun lenght for a line which OMNI will accept is 1000 charactes so for
	// safer side we have taken 800 as the max limit for a line.
	public static int MAX_LINE_LENGTH = 800;
	// These three lines are the constants that will be used in header for each
	// OMNI request files creation. and the values will be replaced accordingly.
	//public static String constant1 = "PRLLBEI=12345678|PRLLDATE=20170115|PRLLFREQ=5|PRLLSEQ=1|PRLLCCAID=CCYYMMDD|\n";
	public static String constant1 = "PTCLType=PARTCONV\n";
	public static String constant3 = "PRLLPlan=SONNY1|";
	static Logger log = Logger.getLogger("OmniRequestFileBuilder.class");
	public static String tableName = "";
	public static void main(String[] args) {
		// A connection (session) with a specific database JDBC mysql.
		Connection con = null;
		ResultSet rs = null;
		ResultSet genericResultSet = null;
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> mapComment = new HashMap<String, String>();
		Map fundSourceMap = new HashMap();
		// ResultSet object which contains the results of an SQL query, table
		// name record_type
		String sqlQuery = "";
		Map mapRecorType = new HashMap();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// con =
			// DriverManager.getConnection("jdbc:mysql://52.55.96.137:3306/mm_solution?useSSL=false",
			// "massmutual",
			// "OKjU6%6NtUgf");

			// con =
			// DriverManager.getConnection("jdbc:mysql://52.55.96.137:3306/mm_solution?useSSL=false",
			// "root",
			// "Adeptia@123");
			con = DriverManager.getConnection("jdbc:mysql://3.220.163.151:3306/mm_nb?useSSL=false", "massmutual",
					"OKjU6%6NtUgf");
			// con1 =
			// con =
			// DriverManager.getConnection("jdbc:mysql://192.168.1.151:3306/mm_solution",
			// "root", "adeptia");

			
			String group_number = "020009";
			String fileQueueID = "370";
			//String payrollDate = "20200302";
			//String payroll_BI = "159099133197595";
			tableName = "participant_ytd";
			String ccaid = "";
			// 01-06-2020 , New Implementation, applicable for Loan and
			// Contribution financial files and will be part of the header.
			// String tradeDate = context.get("TradeDate") == null?"":
			// context.get("TradeDate");
			String tradeDate = "";
			if (group_number == null || group_number.length() < 1) {
				throw new Exception("Group_Number must not be empty.");
			}
			if (fileQueueID == null || fileQueueID.length() < 1) {
				throw new Exception("fileQueueID must not be empty.");
			}
			/*if (payrollDate == null || payrollDate.length() < 1) {
				throw new Exception("payrollDate must not be empty.");
			}*/
			if (tableName == null || tableName.length() < 1) {
				throw new Exception("tableName must not be empty.");
			}
			log.debug("Cconnection pooling time--> " + new Date());
			boolean flagToControlPayroll = false;
			StringBuffer addOmniRequestFinalOutput = new StringBuffer();
			String formatteddate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			String ssn = "PTPHPartNum";
			String PTPHPartNum_old = "";
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery("select field_name,lti_field_name,ac_comment from omni_fields_mapping where reference_type in( '"+tableName+"','participant_detail')");
			while (rs.next()) {
				if (rs.getString(2) != null && rs.getString(1) != null) {
					map.put(rs.getString(1), rs.getString(2));
					mapComment.put(rs.getString(1), rs.getString(3));
				}
			}
			/*
			 * We are fetching two fields from record_type table. s * 1.)
			 * reference_type- that will fetch tables name includes loan,
			 * contribution, enrollment etc. 2.) prll_type- The corresponding
			 * prll type value which will be used while creating OMNI files.
			 * 'prll_type'
			 */

			constant3 = constant3.replace("SONNY1", group_number) + "\n";
			// specific prlltype will get set for enrollment

			//constant1 = constant1.replace("20170115", payrollDate.replace("-", "")).replace("12345678", payroll_BI);
			// this need to add 25-06-2019
			// ------
			// end

			// fundSourceMap key - value(08-11-2019)
			fundSourceMap.put("A,E,D,1,R", "ALLSRCS");
			fundSourceMap.put("*", "ALLSRCS");
			fundSourceMap.put("A,E", "ALLEESRCS");
			fundSourceMap.put("D,1", "ALLERSRCS");
			fundSourceMap.put("R", "ALLROSRCS");
			fundSourceMap.put("Default", "A");
			// end
/*
			sqlQuery = "select  p.social_security_number,p.location,p.address_line_1,p.address_line_2,p.city,p.state,p.zip,p.date_of_birth,p.gender,p.marital_status,"
					+ " c.source_code, l.loan_number , p.dollar_amount from payroll p left join contribution c on c.payroll_id = p.id  left join loan l on p.id=l.payroll_id "
					+ "where p.group_number= " + group_number + " and p.payroll_date='" + payrollDate
					+ "' and p.file_queue_id = " + fileQueueID + " order by  p.social_security_number desc";

			if (tableName.toLowerCase().contains("takeover")) {
				sqlQuery = "select  p.social_security_number,p.location,p.address_line_1,p.address_line_2,p.city,p.state,p.zip,p.date_of_birth,p.gender,p.marital_status,"
						+ " c.source_code, l.loan_number , p.dollar_amount from payroll p left join takeover c on c.payroll_id = p.id  left join loan l on p.id=l.payroll_id "
						+ "where p.group_number= " + group_number + " and p.payroll_date='" + payrollDate
						+ "' and p.file_queue_id = " + fileQueueID + " order by  p.social_security_number desc";
			}*/

			if ((tableName.toLowerCase().contains("contribution") || tableName.toLowerCase().contains("loan")
					|| tableName.toLowerCase().contains("takeover"))) {

				Statement stmt1 = con.createStatement();
				genericResultSet = stmt1.executeQuery(sqlQuery);

			}

			// name_and_address
			else {
				genericResultSet = null;
				Statement stmt2 = con.createStatement();
				genericResultSet = stmt2.executeQuery("select * from participant_detail p  join " + tableName
						+ " na on na.payroll_id = p.id " + "where p.file_queue_id = " + fileQueueID
						+ " order by  p.social_security_number desc");

				while (genericResultSet.next()) {
					flagToControlPayroll = true;

					int count = genericResultSet.getMetaData().getColumnCount();
					for (int i = 1; i <= count; i++) {

						// System.out.println(genericResultSet.getMetaData().getColumnName(i)
						// + " = "
						// + genericResultSet.getObject(i));
						Object result = genericResultSet.getObject(i);
						String key = genericResultSet.getMetaData().getColumnName(i);
						/*
						 * if (key!=null && key.equals("source_code") &&
						 * result!=null && result.toString().length() > 0) {
						 * contri = true; } else { if (result != null &&
						 * key.equals("loan_number")) loan = true; }
						 */
						if (result == null || result.toString().trim().length() < 1)
							continue;
						if (map.containsKey(key) && map.get(key).length() > 0) {

							System.out.println("*** " + map.get(key));
							String value = map.get(key);
							if (PTPHPartNum_old.length() < 1) {
								if (value.equals(ssn)) {
									 PTPHPartNum_old = result.toString();
									//PTPHPartNum_old = AESEncrypterCustom.AESDecrypter(result.toString());
									addOmniRequestFinalOutput.append(value + "=").append(result + "|");
									if (tableName.toLowerCase().equals("enrollment")) {
										addOmniRequestFinalOutput.append("PTPHNewEnroll=A|");
									} else {
										addOmniRequestFinalOutput.append("PTPHNewEnroll=N|");
									}

									// flagToControlPayroll = true;

								}
							}
							if (value.equals(ssn)) {
								String PTPHPartNum_new = result.toString();
								//String PTPHPartNum_new = AESEncrypterCustom.AESDecrypter(result.toString());
								if (PTPHPartNum_old.equals(PTPHPartNum_new)) {

								} else {
									// flagToControlPayroll = true;
									//String PTPHPartNum_new1 = AESEncrypterCustom.AESDecrypter(result.toString());
									addOmniRequestFinalOutput.append(value + "=").append(result.toString() + "|");
									if (tableName.toLowerCase().equals("enrollment")) {
										addOmniRequestFinalOutput.append("PTPHNewEnroll=A|");
									} else {
										addOmniRequestFinalOutput.append("PTPHNewEnroll=N|");
									}
									PTPHPartNum_old = PTPHPartNum_new;

								}
							}

							else {
								if (!(value.equals("PRLLPlan") || value.equals("PRLLDATE")
										|| value.equals("PRLLBEI"))) {
									System.out.println("---->>" + value);
									addOmniRequestFinalOutput.append(value + "=").append(result + "|");
								}
							}

						}

					}
				}

			}

			if (addOmniRequestFinalOutput.toString().length() < 1) {
				throw new Exception("No data found for the payroll");
			}

			System.out.println(addOmniRequestFinalOutput.toString());
			System.out.println("************Final Output..**********");
			System.out.println("");
			String output = addLinebreaks_new(addOmniRequestFinalOutput.toString());
			addOmniRequestFinalOutput.delete(0, addOmniRequestFinalOutput.length());
			addOmniRequestFinalOutput.append(constant1).append(constant3).append(output);

			// String finalOutput=sb.toString().replace("|LNLPPmtAmt", "");
			System.out.println(addOmniRequestFinalOutput.toString());
			// context.put("UniqueCombination",uniqueCombination.toString());
			// context.put("startPosition","0");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public static String addLinebreaks(String input) {
		StringTokenizer tok = new StringTokenizer(input, "|");
		StringBuffer output = new StringBuffer(input.length());
		int lineLen = 0;
		while (tok.hasMoreTokens()) {
			String word = tok.nextToken();
			if (lineLen + word.length() > MAX_LINE_LENGTH) {
				output.append("\n");
				lineLen = 0;
			}
			output.append(word + "|");
			lineLen += word.length() + 1;
		}
		return output.toString();
	}

	public static String sequenceCalculator(String strInput) {

		Pattern pattPTPH = Pattern.compile("PTPH(.*?)\\|");
		Matcher matcherPTPH = pattPTPH.matcher(strInput);
		StringBuffer stringBuffer = new StringBuffer();
		StringBuffer stringBufferPTPH = new StringBuffer();
		StringBuffer stringBufferFinal = new StringBuffer();
		while (matcherPTPH.find()) {
			String str = matcherPTPH.group();
			stringBufferPTPH.append(str);
			matcherPTPH.appendReplacement(stringBuffer, "");
		}
		matcherPTPH.appendTail(stringBuffer);

		Pattern pattPTPS = Pattern.compile("PTPS(.*?)\\|");
		Matcher matcherPTPS = pattPTPS.matcher(stringBuffer.toString());
		StringBuffer stringBufferRemPTPS = new StringBuffer();
		StringBuffer stringBufferPTPS = new StringBuffer();
		while (matcherPTPS.find()) {
			String str = matcherPTPS.group();
			// System.out.println(str);
			stringBufferPTPS.append(str);
			matcherPTPS.appendReplacement(stringBufferRemPTPS, "");
		}
		matcherPTPS.appendTail(stringBufferRemPTPS);
		stringBufferFinal.append(stringBufferPTPH).append(stringBufferPTPS).append(stringBufferRemPTPS);
		return stringBufferFinal.toString();
	}

	public static String addLinebreaks_new(String input) {
		String[] tok = input.split("\\|PTPHPartNum");
		StringBuffer output = new StringBuffer(input.length());
		for (int i = 0; i < tok.length; i++) {

			String word = tok[i];
			if (tableName.equalsIgnoreCase("enrollment")) {
				if (!word.startsWith("PTPHPartNum")) {
					word = "PTPHPartNum" + word + "|";
					// String tmp=sequenceCalculator(word);
					// System.out.println(tmp);
					Pattern pattPTPH = Pattern.compile("PTPH(.*?)\\|");
					Matcher matcherPTPH = pattPTPH.matcher(word);
					StringBuffer stringBuffer = new StringBuffer();
					StringBuffer stringBufferPTPH = new StringBuffer();
					StringBuffer stringBufferFinal = new StringBuffer();
					while (matcherPTPH.find()) {
						String str = matcherPTPH.group();
						stringBufferPTPH.append(str);
						matcherPTPH.appendReplacement(stringBuffer, "");
					}
					matcherPTPH.appendTail(stringBuffer);

					Pattern pattPTPS = Pattern.compile("PTPS(.*?)\\|");
					Matcher matcherPTPS = pattPTPS.matcher(stringBuffer.toString());
					StringBuffer stringBufferRemPTPS = new StringBuffer();
					StringBuffer stringBufferPTPS = new StringBuffer();
					while (matcherPTPS.find()) {
						String str = matcherPTPS.group();
						// System.out.println(str);
						stringBufferPTPS.append(str);
						matcherPTPS.appendReplacement(stringBufferRemPTPS, "");
					}
					matcherPTPS.appendTail(stringBufferRemPTPS);
					stringBufferFinal.append(stringBufferPTPH).append(stringBufferPTPS).append(stringBufferRemPTPS);

					// System.out.println(tmp);
					word = stringBufferFinal.toString().replace("PTPHPartNum", "");
					// word=word.replace("PTPHPartNum", "");
					word = word.substring(0, word.length() - 1);
				} else {
					word = word + "|";
					Pattern pattPTPH = Pattern.compile("PTPH(.*?)\\|");
					Matcher matcherPTPH = pattPTPH.matcher(word);
					StringBuffer stringBuffer = new StringBuffer();
					StringBuffer stringBufferPTPH = new StringBuffer();
					StringBuffer stringBufferFinal = new StringBuffer();
					while (matcherPTPH.find()) {
						String str = matcherPTPH.group();
						stringBufferPTPH.append(str);
						matcherPTPH.appendReplacement(stringBuffer, "");
					}
					matcherPTPH.appendTail(stringBuffer);

					Pattern pattPTPS = Pattern.compile("PTPS(.*?)\\|");
					Matcher matcherPTPS = pattPTPS.matcher(stringBuffer.toString());
					StringBuffer stringBufferRemPTPS = new StringBuffer();
					StringBuffer stringBufferPTPS = new StringBuffer();
					while (matcherPTPS.find()) {
						String str = matcherPTPS.group();
						// System.out.println(str);
						stringBufferPTPS.append(str);
						matcherPTPS.appendReplacement(stringBufferRemPTPS, "");
					}
					matcherPTPS.appendTail(stringBufferRemPTPS);
					stringBufferFinal.append(stringBufferPTPH).append(stringBufferPTPS).append(stringBufferRemPTPS);
					// System.out.println(tmp);
					word = stringBufferFinal.toString();
					word = word.substring(0, stringBufferFinal.toString().length() - 1);
				}

			}
			// "enrollment";)

			// new line code
			StringBuffer outputTemp = new StringBuffer(word.length());
			if (word.length() > MAX_LINE_LENGTH) {
				StringTokenizer tok1 = new StringTokenizer(word, "|");
				int lineLen = 0;
				while (tok1.hasMoreTokens()) {
					String wordName = tok1.nextToken();
					if (lineLen + wordName.length() > MAX_LINE_LENGTH) {
						outputTemp.append("\n");
						lineLen = 0;
					}
					outputTemp.append(wordName + "|");
					lineLen += wordName.length() + 1;
				}
				word = outputTemp.toString();
			} // end new line code here
				// word=sequenceCalculator(word);
			output.append(word + "|\n" + "PTPHPartNum");

		}
		String replaceSSN = output.toString().substring(0, output.toString().length() - 13);
		replaceSSN = replaceSSN.replace("||", "|");
		/*
		 * String lines[] = replaceSSN.split("\\r?\\n"); StringBuffer sbNew=new
		 * StringBuffer(); for(String str:lines){ //System.out.println(str);
		 * String tmp=sequenceCalculator(str); System.out.println(tmp);
		 * sbNew.append(tmp+"\r\n");
		 * 
		 * }
		 * 
		 * System.out.println("--->"+sbNew);
		 */
		return replaceSSN.toString();
	}
	/*
	 * public static void main(String[] args) { String str=
	 * "PTPHPartNum=415263743|PTPHSex=2|PTPHEmplDate=20110911|PTPHDoB=19700106|PTPHNewEnroll=A|PTPHDivSub=CYGN|PTPHName=Sherer,Pat|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box|PTPHSex=2|PTPHEmplDate=20110911|PTPHDoB=19700106|PTPHNewEnroll=A|PTPHDivSub=CYGN|PTPHName=Sherer,Pat|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box 75|PTPHAddrL2=|PTPHCity=Tuscaloosa|PTPHState=AL|PTPHZip=35222|PTPHPartNum=575733927|PTPHSex=1|PTPHEmplDate=20110911|PTPHDoB=19700106|PTPHNewEnroll=A|PTPHDivSub=CYGN|PTPHName=Bennett,Winston|CNDTSrcId1=ERMATCH|CNDTAmt1=50.00|NDTSrcId2=EEPRETAX|CNDTAmt2=100.00|PTPHAddrType=D|PTPHAddrL1=123 Main Street|PTPHAddrL2=|PTPHCity=Birmingham|PTPHState=AL|PTPHZip=35123||PTPHSex=2|PTPHEmplDate=20110911|PTPHDoB=19700106|PTPHNewEnroll=A|PTPHDivSub=CYGN|PTPHName=Sherer,Pat|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box||PTPHSex=2|PTPHEmplDate=20110911|PTPHDoB=19700106|PTPHNewEnroll=A|PTPHDivSub=CYGN|PTPHName=Sherer,Pat|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box||PTPHSex=2|PTPHEmplDate=20110911|PTPHDoB=19700106|PTPHNewEnroll=A|PTPHDivSub=CYGN|PTPHName=Sherer,Pat|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box||PTPHSex=2|PTPHEmplDate=20110911|PTPHDoB=19700106|PTPHNewEnroll=A|PTPHDivSub=CYGN|PTPHName=Sherer,Pat|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box|CNDTSrcId1=EEPRETAX|CNDTAmt1=7000.00|CNTSrcId2=ERMATCH|CNDTAmt2=3500.00|PTPHAddrType=D|PTPHAddrL1=PO Box|"
	 * ;
	 * 
	 * System.out.println(addLinebreaks_new(str)); }
	 */

}
