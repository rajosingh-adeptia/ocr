package com;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class Main {
	public static int INDENTATION = 4;
	public static String XML_STRING =
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			"<root>" +
				"<firstName>John</firstName>" +
				"<lastName>Snow</lastName>" +
				"<age>25.30</age>" +
				"<spouse/>" +
				"<address>" +
					"<street>237 Harrison Street</street>" +
					"<city>Brooklyn, NY</city>" +
					"<state>New York</state>" +
					"<postalCode>11238</postalCode>" +
				"</address>" +
				"<phoneNumbers>" +
					"<type>mobile</type>" +
					"<number>212 555-3346</number>" +
				"</phoneNumbers>" +
				"<phoneNumbers>" +
					"<type>fax</type>" +
					"<number>646 555-4567</number>" +
				"</phoneNumbers>" +
			"</root>";

	public static void main(String[] args) {
		try {
			JSONObject jsonObj = XML.toJSONObject(XML_STRING,false);
			String json = jsonObj.toString(INDENTATION);

			System.out.println(json);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

}
