package com;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToString {
	public String toString(){
		return null;
	}
	public static String sequenceCalculator(String strInput){
		String strNew="{\"contactSupport\":\"Please contact your MassMutual Account Manager for further assistance.<br><style:\"   \"margin-top\":3px\"><span class=\"\"alert-message-box-sub-msg-icon ai-start\" \" style=\"\"float\":\"left\" \"></span> Thank you</style>\"}";
		Pattern pattPTPH=Pattern.compile("PTPH(.*?)\\|");
		Matcher matcherPTPH = pattPTPH.matcher(strInput);
		StringBuffer stringBuffer =new StringBuffer();
		StringBuffer stringBufferPTPH =new StringBuffer();
		StringBuffer stringBufferFinal =new StringBuffer();
		while(matcherPTPH.find()){
			String str=matcherPTPH.group();
			//System.out.println(str);
			stringBufferPTPH.append(str);
			matcherPTPH.appendReplacement(stringBuffer, "");
	       // System.out.println(stringBuffer.toString());
			
		}
		matcherPTPH.appendTail(stringBuffer);

	    //System.out.println(stringBuffer.toString());
	    //System.out.println(stringBufferPTPH.toString());
	    //stringBufferFinal.append(stringBuffer1).append(stringBuffer);
	    //System.out.println(stringBufferFinal.toString());
	    StringBuffer stringBufferFinalPTPS =new StringBuffer();
	    //if(stringBufferFinal.toString().contains("PTPS")){
	    	Pattern pattPTPS=Pattern.compile("PTPS(.*?)\\|");
	    	Matcher matcherPTPS = pattPTPS.matcher(stringBuffer.toString());
	    	StringBuffer stringBufferRemPTPS =new StringBuffer();
	    	StringBuffer stringBufferPTPS =new StringBuffer();
	    	//StringBuffer stringBuffer =new StringBuffer();
	    	//StringBuffer stringBuffer1 =new StringBuffer();
	    	//StringBuffer stringBufferFinal =new StringBuffer();
	    	while(matcherPTPS.find()){
	    		String str=matcherPTPS.group();
	    		//System.out.println(str);
	    		stringBufferPTPS.append(str);
	    		matcherPTPS.appendReplacement(stringBufferRemPTPS, "");
	    	}
	    	matcherPTPS.appendTail(stringBufferRemPTPS);
	       // System.out.println(stringBufferRemPTPS.toString());
	        //System.out.println(stringBufferPTPS.toString());
	        stringBufferFinal.append(stringBufferPTPH).append(stringBufferPTPS).append(stringBufferRemPTPS);
	        System.out.println("-->"+stringBufferFinal);
	   // }
		return stringBufferFinal.toString();
	}

	public static void main(String[] args) {
		
		//ToString toString= new ToString();
		//System.out.println(toString.toString());
		String actualString="PTPHPartNum=999995096|PTPHNewEnroll=A|PTPHDivSub=0000|PTPHSex=2|PTPHDoB=19660907|PTPHAddrL1=5924 Brickleberry Lane 202"
				+ "|PTPHCity=Zephyrhills|PTPHState=FL|PTPHZip=33541|PTPHName=Sasser, Shawntria|PTPFFundSrc=D|PTPFFundIv=40|PTPFIvElectionPct=50|PTPFFundIv=AF|PTPFIvElectionPct=50|PTPFFundSrc=1|PTPFFundIv=40|PTPFIvElectionPct=50|PTPFFundIv=AF|PTPFIvElectionPct=50|PTPFFundSrc=a|PTPFFundIv=qq|"
				+ "PTPFIvElectionPct=12|PTPSFundIv=qw|PTPFIvElectionPct=12|PTPFFundIv=qe|PTPFIvElectionPct=11|PTPFFundIv=qr|PTPFIvElectionPct=32|PTPFFundIv=ee|PTPFIvElectionPct=32|PTPFFundIv=rr|PTPFIvElectionPct=98|PTPFFundIv=ss|PTPFIvElectionPct=31|PTPFFundIv=yy|PTPFIvElectionPct=98|PTPFFundIv=bb|PTPFIvElectionPct=1|PTPFFundIv=mm|PTPFIvElectionPct=12|PTPFFundIv=mn|PTPFIvElectionPct=12|PTPFFundIv=jj|PTPFIvElectionPct=12|PTPFFundIv=kj|PTPFIvElectionPct=12|PTPFFundIv=lk|PTPFIvElectionPct=12|PTPFFundIv=bv|PTPFIvElectionPct=12|PTPFFundIv=id|PTPFIvElectionPct=12|PTPFFundIv=qq|PTPFIvElectionPct=12|PTPFFundIv=zx|PTPFIvElectionPct=12|PTPFFundIv=zz|PTPFIvElectionPct=12|PTPFFundIv=lm|PTPFIvElectionPct=12|PTPFFundIv=kn|PTPFIvElectionPct=12|PTPFFundIv=bh|PTPFIvElectionPct=12|PTPFFundIv=uj|PTPFIvElectionPct=12|PTPFFundIv=ji|PTPFIvElectionPct=12|PTPFFundIv=pb|PTPFIvElectionPct=12|PTPFFundIv=oo|PTPFIvElectionPct=12|PTPFFundIv=pp|PTPFIvElectionPct=12|PTPFFundIv=ww|PTPFIvElectionPct=12|PTPFFundIv=hj|PTPFIvElectionPct=12|PTPFFundIv=bb|PTPFIvElectionPct=12|PTPFFundIv=nn|PTPFIvElectionPct=12|PTPFFundIv=jn|PTPFIvElectionPct=12|PTPFFundIv=il|PTPFIvElectionPct=12|PTPFFundIv=qa|PTPFIvElectionPct=12|PTPFFundIv=ss|PTPFIvElectionPct=12|PTPFFundIv=za|PTPFIvElectionPct=12|PTPFFundIv=dr|PTPFIvElectionPct=12|PTPFFundIv=uy|PTPFIvElectionPct=12|PTPFFundIv=hb|PTPFIvElectionPct=12|PTPFFundIv=kl|PTPFIvElectionPct=12|PTPFFundIv=pp|PTPFIvElectionPct=12|PTPFFundIv=ee|PTPFIvElectionPct=12|PTPFFundIv=vb|PTPFIvElectionPct=12|PTPFFundIv=zxc|PTPFIvElectionPct=12|PTPHEmplDate=20180901|PTPHPlentDate=01/09/2018||";
		System.out.println(sequenceCalculator(actualString));
	/*Pattern pattPTPH=Pattern.compile("PTPH(.*?)\\|");
	Matcher matcherPTPH = pattPTPH.matcher(actualString);
	StringBuffer stringBuffer =new StringBuffer();
	StringBuffer stringBufferPTPH =new StringBuffer();
	StringBuffer stringBufferFinal =new StringBuffer();
	while(matcherPTPH.find()){
		String str=matcherPTPH.group();
		//System.out.println(str);
		stringBufferPTPH.append(str);
		matcherPTPH.appendReplacement(stringBuffer, "");
       // System.out.println(stringBuffer.toString());
		
	}
	matcherPTPH.appendTail(stringBuffer);

    System.out.println(stringBuffer.toString());
    System.out.println(stringBufferPTPH.toString());
    //stringBufferFinal.append(stringBuffer1).append(stringBuffer);
    System.out.println(stringBufferFinal.toString());
    StringBuffer stringBufferFinalPTPS =new StringBuffer();
    //if(stringBufferFinal.toString().contains("PTPS")){
    	Pattern pattPTPS=Pattern.compile("PTPS(.*?)\\|");
    	Matcher matcherPTPS = pattPTPS.matcher(stringBuffer.toString());
    	StringBuffer stringBufferRemPTPS =new StringBuffer();
    	StringBuffer stringBufferPTPS =new StringBuffer();
    	//StringBuffer stringBuffer =new StringBuffer();
    	//StringBuffer stringBuffer1 =new StringBuffer();
    	//StringBuffer stringBufferFinal =new StringBuffer();
    	while(matcherPTPS.find()){
    		String str=matcherPTPS.group();
    		//System.out.println(str);
    		stringBufferPTPS.append(str);
    		matcherPTPS.appendReplacement(stringBufferRemPTPS, "");
    	}
    	matcherPTPS.appendTail(stringBufferRemPTPS);
        System.out.println(stringBufferRemPTPS.toString());
        System.out.println(stringBufferPTPS.toString());
        stringBufferFinal.append(stringBufferPTPH).append(stringBufferPTPS).append(stringBufferRemPTPS);
        System.out.println("-->"+stringBufferFinal);
*/   // }
    
    
	}

}
