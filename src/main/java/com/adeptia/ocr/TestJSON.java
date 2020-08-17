package com.adeptia.ocr;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
public class TestJSON {

	public static void main(String[] args) {
     String test="[{\"assetAllocationModelName\":\"Conservative\",\"assetAllocationModelRankingNum\":2,\"AssetClassAllocations\":[{\"assetClassDesc\":\"Stock\",\"assetClassAllocationPct\":30,\"AssetCategoryGroupAllocations\":[{\"InvestmentElections\":[{\"agreementInvestmentProductId\":\"184059\",\"agreementInvestmentProductDesc\":\"BNYM MC INDX\",\"investmentElectionPct\":2,\"hostFIVInvestmentProductNum\":\"LQ\",\"agreementInvestmentProductLongDesc\":\"BNY MELLON MIDCAP INDEX\",\"hostInvestmentProductNum\":\"LQ000R001\",\"agreementInvestmentProductShortDesc\":\"DMCIN\"},{\"agreementInvestmentProductId\":\"347134\",\"agreementInvestmentProductDesc\":\"LA VAL OPP\",\"investmentElectionPct\":2,\"hostFIVInvestmentProductNum\":\"TW\",\"agreementInvestmentProductLongDesc\":\"LORD ABBETT VALUE OPP\",\"hostInvestmentProductNum\":\"TW000R002\",\"agreementInvestmentProductShortDesc\":\"LAVAO\"},{\"agreementInvestmentProductId\":\"305987\",\"agreementInvestmentProductDesc\":\"VIC SE VAL\",\"investmentElectionPct\":2,\"hostFIVInvestmentProductNum\":\"X7\",\"agreementInvestmentProductLongDesc\":\"VICTORY SYCAMORE EST VALUE\",\"hostInvestmentProductNum\":\"X7000R002\",\"agreementInvestmentProductShortDesc\":\"VSEVL\"}],\"assetCategoryGroupAllocationPct\":6,\"assetCategoryGroupName\":\"Mid Cap\"},{\"InvestmentElections\":[{\"agreementInvestmentProductId\":\"226911\",\"agreementInvestmentProductDesc\":\"AF EUROPAC\",\"investmentElectionPct\":2,\"hostFIVInvestmentProductNum\":\"7K\",\"agreementInvestmentProductLongDesc\":\"AMERICAN FUNDS EUROPACIFIC\",\"hostInvestmentProductNum\":\"7K000R001\",\"agreementInvestmentProductShortDesc\":\"EUPGR\"},{\"agreementInvestmentProductId\":\"106741\",\"agreementInvestmentProductDesc\":\"INV DV MKT\",\"investmentElectionPct\":2,\"hostFIVInvestmentProductNum\":\"K8\",\"agreementInvestmentProductLongDesc\":\"INVESCO DEVELOPING MARKETS\",\"hostInvestmentProductNum\":\"K8000R002\",\"agreementInvestmentProductShortDesc\":\"INVDM\"},{\"agreementInvestmentProductId\":\"278567\",\"agreementInvestmentProductDesc\":\"MFS IN INTVL\",\"investmentElectionPct\":1,\"hostFIVInvestmentProductNum\":\"YI\",\"agreementInvestmentProductLongDesc\":\"MFS INTERNATIONAL INTRINSIC VA\",\"hostInvestmentProductNum\":\"YI000R002\",\"agreementInvestmentProductShortDesc\":\"MFSIV\"}],\"assetCategoryGroupAllocationPct\":5,\"assetCategoryGroupName\":\"International\"},{\"InvestmentElections\":[{\"agreementInvestmentProductId\":\"96586\",\"agreementInvestmentProductDesc\":\"BNYM SC STIN\",\"investmentElectionPct\":1,\"hostFIVInvestmentProductNum\":\"LR\",\"agreementInvestmentProductLongDesc\":\"BNY MELLON SMLCAP STOCK INDEX\",\"hostInvestmentProductNum\":\"LR000R001\",\"agreementInvestmentProductShortDesc\":\"DSCST\"}],\"assetCategoryGroupAllocationPct\":1,\"assetCategoryGroupName\":\"Small Cap\"},{\"InvestmentElections\":[{\"agreementInvestmentProductId\":\"381770\",\"agreementInvestmentProductDesc\":\"AC EQ INC\",\"investmentElectionPct\":5,\"hostFIVInvestmentProductNum\":\"5X\",\"agreementInvestmentProductLongDesc\":\"AMERICAN CENTURY EQUITY INCOME\",\"hostInvestmentProductNum\":\"5X000R001\",\"agreementInvestmentProductShortDesc\":\"ACEI\"},{\"agreementInvestmentProductId\":\"36938\",\"agreementInvestmentProductDesc\":\"DOMINI IMPEQ\",\"investmentElectionPct\":5,\"hostFIVInvestmentProductNum\":\"KY\",\"agreementInvestmentProductLongDesc\":\"DOMINI IMPACT EQUITY\",\"hostInvestmentProductNum\":\"KY000R001\",\"agreementInvestmentProductShortDesc\":\"DOIEQ\"},{\"agreementInvestmentProductId\":\"23230\",\"agreementInvestmentProductDesc\":\"FR GROWTH\",\"investmentElectionPct\":4,\"hostFIVInvestmentProductNum\":\"ND\",\"agreementInvestmentProductLongDesc\":\"FRANKLIN GROWTH\",\"hostInvestmentProductNum\":\"ND000R002\",\"agreementInvestmentProductShortDesc\":\"FRKGR\"},{\"agreementInvestmentProductId\":\"100655\",\"agreementInvestmentProductDesc\":\"BNYM SP500IN\",\"investmentElectionPct\":4,\"hostFIVInvestmentProductNum\":\"SX\",\"agreementInvestmentProductLongDesc\":\"BNY MELLON S&P 500 INDEX\",\"hostInvestmentProductNum\":\"SX000R001\",\"agreementInvestmentProductShortDesc\":\"DS&PI\"}],\"assetCategoryGroupAllocationPct\":18,\"assetCategoryGroupName\":\"Large Cap\"}]},{\"assetClassDesc\":\"Cash\",\"assetClassAllocationPct\":20,\"AssetCategoryGroupAllocations\":[{\"InvestmentElections\":[{\"agreementInvestmentProductId\":\"94522\",\"agreementInvestmentProductDesc\":\"JPM US GV MM\",\"investmentElectionPct\":20,\"hostFIVInvestmentProductNum\":\"GE\",\"agreementInvestmentProductLongDesc\":\"JPMORGAN US GOVT MONEY MARKET\",\"hostInvestmentProductNum\":\"3SY000005\",\"agreementInvestmentProductShortDesc\":\"JPUGM\"}],\"assetCategoryGroupAllocationPct\":20,\"assetCategoryGroupName\":\"Stable Value/Money Market\"}]},{\"assetClassDesc\":\"Bond\",\"assetClassAllocationPct\":50,\"AssetCategoryGroupAllocations\":[{\"InvestmentElections\":[{\"agreementInvestmentProductId\":\"150476\",\"agreementInvestmentProductDesc\":\"BNYM BN MKID\",\"investmentElectionPct\":13,\"hostFIVInvestmentProductNum\":\"6K\",\"agreementInvestmentProductLongDesc\":\"BNY MELLON BOND MARKET INDEX\",\"hostInvestmentProductNum\":\"6K000R002\",\"agreementInvestmentProductShortDesc\":\"DBMI\"},{\"agreementInvestmentProductId\":\"221461\",\"agreementInvestmentProductDesc\":\"JPM CORE BND\",\"investmentElectionPct\":12,\"hostFIVInvestmentProductNum\":\"K3\",\"agreementInvestmentProductLongDesc\":\"JP MORGAN CORE BOND\",\"hostInvestmentProductNum\":\"K3000R002\",\"agreementInvestmentProductShortDesc\":\"JPMCB\"},{\"agreementInvestmentProductId\":\"300717\",\"agreementInvestmentProductDesc\":\"LMSSY BD\",\"investmentElectionPct\":12,\"hostFIVInvestmentProductNum\":\"SB\",\"agreementInvestmentProductLongDesc\":\"LOOMIS SAYLES BOND\",\"hostInvestmentProductNum\":\"SB000R002\",\"agreementInvestmentProductShortDesc\":\"LSYBD\"},{\"agreementInvestmentProductId\":\"148855\",\"agreementInvestmentProductDesc\":\"TMPL GL BOND\",\"investmentElectionPct\":13,\"hostFIVInvestmentProductNum\":\"Y2\",\"agreementInvestmentProductLongDesc\":\"TEMPLETON GLOBAL BOND\",\"hostInvestmentProductNum\":\"Y2000R002\",\"agreementInvestmentProductShortDesc\":\"TGBND\"}],\"assetCategoryGroupAllocationPct\":50,\"assetCategoryGroupName\":\"Bond\"}]}],\"assetAllocationModelCd\":\"00004\"}]";
     StringBuffer sbVal=new StringBuffer();
		StringBuffer sbValInner=new StringBuffer();
		StringBuffer sbTemp=new StringBuffer("{ \"test\":").append(test.toString()).append("}");
		JSONObject jsonObjectSourceLevel = new JSONObject(sbTemp.toString());
		JSONObject jsonObjectFundLevel = new JSONObject();
		JSONObject jsonObjectAssetCategoryGroupAllocations = new JSONObject();
		Iterator iteratorLevelOne = jsonObjectSourceLevel.keys();
		String pDate="444907";
		String modelCd="";
		String modelName="";
		sbVal.append("BABTType=UUAD|BABTKeySeq=001|BABT300= A*"+pDate+"|");
		sbValInner.append("PTPFFundSrc=ALLSRCS|");
		//Map jsonMap = new HashMap();
		while (iteratorLevelOne.hasNext()) {
			String s = iteratorLevelOne.next().toString();
			JSONArray arrayInvestmentModels = jsonObjectSourceLevel.getJSONArray(s);
			//"AssetClassAllocations"
			 jsonObjectFundLevel=arrayInvestmentModels.getJSONObject(0);
			 modelName=(String) jsonObjectFundLevel.get("assetAllocationModelName");
			 modelCd=(String) jsonObjectFundLevel.get("assetAllocationModelCd");
			 JSONArray arrayInvestmentModelsNew =(JSONArray) jsonObjectFundLevel.get("AssetClassAllocations");
			 //AssetCategoryGroupAllocations
			 for(int i=0;i<arrayInvestmentModelsNew.length();i++){
				 //JSONArray arrayAssetCategoryGroupAllocationsNew =(JSONArray) arrayInvestmentModelsNew.getJSONObject(0).get("AssetCategoryGroupAllocations"); 
				 jsonObjectAssetCategoryGroupAllocations=arrayInvestmentModelsNew.getJSONObject(i);
				 JSONArray arrayAssetCategoryGroupAllocationsNew=jsonObjectAssetCategoryGroupAllocations.getJSONArray("AssetCategoryGroupAllocations");
			// JSONArray arrayInvestmentModelsNew = jsonObjectSourceLevel.getJSONArray( jsonObjectFundLevel.get("AssetClassAllocations"));
			
			 for(int j=0;j<arrayAssetCategoryGroupAllocationsNew.length();j++){
				jsonObjectFundLevel=arrayAssetCategoryGroupAllocationsNew.getJSONObject(j);
				
				JSONArray arrayInvestment = jsonObjectFundLevel.getJSONArray("InvestmentElections");
				for(int k=0;k<arrayInvestment.length();k++){
					String hostInvestmentProductNum=(String) arrayInvestment.getJSONObject(k).get("hostFIVInvestmentProductNum");
					Integer modelInvestmentAllocationPct=(Integer) arrayInvestment.getJSONObject(k).get("investmentElectionPct");
					//System.out.println(modelInvestmentAllocationPct);
					//System.out.println(hostInvestmentProductNum);
					sbValInner.append("PTPFFundIv="+hostInvestmentProductNum+"|");
					sbValInner.append("PTPFIvElectionPct="+modelInvestmentAllocationPct+"|");
				}
				//AssetCategoryGroupAllocations
				
			}
			 }
			//jsonObjectFundLevel = (JSONObject) jsonObjectSourceLevel.get(s);
			//JSONArray arrayInvestmentModels = jsonObjectFundLevel.getJSONArray("InvestmentModels");
		}
		System.out.println(sbValInner);
		 char[] arrayModelName =modelName.toCharArray();
		 int len=arrayModelName.length;
		// char[] arrayfinal = new char[25];
		 int reqLen=25-len;
		// for()
		 char[] array = new char[reqLen];
	        int index = 0;
	        for (char c = ' '; index <= array.length-1; index++) {
	        	array[index]=c;
	        }
	        int aLen = arrayModelName.length;
	        int bLen = array.length;
	        char[] resultFinal = new char[aLen + bLen];
	        System.arraycopy(arrayModelName, 0, resultFinal, 0, aLen);
	        System.arraycopy(array, 0, resultFinal, aLen, bLen);
	        StringBuffer sbChar=new StringBuffer();
	      //  System.out.println(Arrays.toString(resultFinal));
	       // sbChar.append(Arrays.toString(resultFinal));
	       
	       /* for (char c : resultFinal) { 
	        	sbChar.append(c);
	        }*/ 
	        for (int s = 0; s < resultFinal.length; s++) {
				 sbChar.append( resultFinal[s]);
			 }
		sbVal.append("BABT400="+sbChar+"*"+modelCd+"|").append("BABT500=N|");
		System.out.println(sbVal);
		//addOmniRequestFinalOutput.append(sbValInner).append(sbVal);
     
	}

}
