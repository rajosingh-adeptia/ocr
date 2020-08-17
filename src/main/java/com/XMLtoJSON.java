package com;
import com.adeptia.indigo.utils.XmlException;
import org.w3c.dom.NodeList;
import com.adeptia.indigo.utils.XmlUtils;
import java.util.Iterator;
import javax.xml.transform.Transformer;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import com.adeptia.indigo.logging.Logger;
import java.io.IOException;
import com.adeptia.indigo.utils.IndigoException;
import org.json.JSONObject;
import java.util.Map;
import org.json.XML;
import org.apache.commons.io.IOUtils;
import java.util.HashMap;
import com.googlecode.jslint4java.UnicodeBomInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import java.io.Writer;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import org.w3c.dom.Node;
import javax.xml.transform.dom.DOMSource;
import javax.xml.parsers.DocumentBuilderFactory;
import com.adeptia.indigo.services.transform.ScriptedService;
import java.io.InputStream;

//
//Decompiled by Procyon v0.5.36
//

public class XMLtoJSON
{
   public void convertData(final String jsonKeys, final InputStream inputStream) throws IndigoException {
      // final Logger log = service.getLogger();
       String jsonDocString = "";
       final String escapeChar = "&#13;";
       final String jsonExtraVar = ",{}|,\"\"";
       UnicodeBomInputStream uis = null;
       StringWriter writer = null;
       try {
           final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
           final DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
           final Document doc = documentBuilder.parse(inputStream);
           if (!jsonKeys.trim().equals("")) {
               final String[] ArraysJsonKeys = jsonKeys.split(",");
               for (int i = 0; i < ArraysJsonKeys.length; ++i) {
                   this.inserNodeInXML(doc, ArraysJsonKeys[i]);
               }
           }
           final DOMSource domSource = new DOMSource(doc);
           writer = new StringWriter();
           final StreamResult result = new StreamResult(writer);
           final TransformerFactory tf = TransformerFactory.newInstance();
           final Transformer transformer = tf.newTransformer();
           transformer.setOutputProperty("indent", "yes");
           transformer.transform(domSource, result);
           jsonDocString = writer.toString();
           if (!escapeChar.trim().equals("")) {
               final String[] ArrayEscapeChar = escapeChar.split("\\|");
               for (int j = 0; j < ArrayEscapeChar.length; ++j) {
                   final String esacpeChar = ArrayEscapeChar[j];
                   jsonDocString = jsonDocString.replace(esacpeChar, "");
               }
           }
           final InputStream is = new ByteArrayInputStream(jsonDocString.getBytes("UTF-8"));
           final StringBuffer jsonStringBuffer = new StringBuffer();
           uis = new UnicodeBomInputStream(is);
           uis.skipBOM();
           final HashMap map = new HashMap();
           map.put("isRemoveLeadingZeroes", "false");
           map.put("isConvertStringToValue", "false");
           //XML.toJSONObject
          // XML.toJSONObject
          // final JSONObject jObject = XML.toJSONObject(IOUtils.toString((InputStream)uis), false);
           final JSONObject jObject = XML.toJSONObject(IOUtils.toString(uis), true);
           final Iterator keys = jObject.keys();
           while (keys.hasNext()) {
               final String jsonKey = (String) keys.next();
               if (jObject.get(jsonKey) instanceof JSONObject && jsonKey.equalsIgnoreCase("Root")) {
                   jsonStringBuffer.append(jObject.get(jsonKey));
                   break;
               }
           }
           String jsonString = jsonStringBuffer.toString();
           if (!jsonExtraVar.trim().equals("")) {
               final String[] ArrayJsonVar = jsonExtraVar.split("\\|");
               for (int k = 0; k < ArrayJsonVar.length; ++k) {
                   final String jsonVar = ArrayJsonVar[k];
                   jsonString = jsonString.replace(jsonVar, "");
               }
           }
          // jsonString=jsonString.replaceAll("((\\.[0-9]),\")", "$2"+"0,\"");
          // jsonString=jsonString.replaceAll("(\":[0-9]+\\.[0-9])(\\s|\\n|})", "$1"+"0"+"$2");
          // log.debug("JSON Output::" + jsonString);
          // service.write(jsonString.getBytes(), "default");
           System.out.println(jsonString);
       }
       catch (Exception e) {
          // log.error("Error while converting JSON from XML:" + e.getMessage(), (Throwable)e);
           throw new IndigoException("Error while converting JSON from XML:" + e.getMessage(), (Throwable)e);
       }
       finally {
           if (uis != null) {
               try {
                   uis.close();
               }
               catch (IOException e2) {
                   e2.printStackTrace();
               }
           }
           if (writer != null) {
               try {
                   writer.close();
               }
               catch (IOException e2) {
                   e2.printStackTrace();
               }
           }
       }
       if (uis != null) {
           try {
               uis.close();
           }
           catch (IOException e2) {
               e2.printStackTrace();
           }
       }
       if (writer != null) {
           try {
               writer.close();
           }
           catch (IOException e2) {
               e2.printStackTrace();
           }
       }
   }
   
   public void inserNodeInXML(final Document doc, final String key) throws XmlException {
       final NodeList nodeList = doc.getElementsByTagName(key);
      // log.debug("NodeList Length:::" + nodeList.getLength());
       Node currentNode = null;
       Node nextNode = null;
       Node newNode = null;
       for (int i = 0; i < nodeList.getLength(); ++i) {
         //  log.debug("Updated NodeList Length:::" + nodeList.getLength());
           currentNode = nodeList.item(i);
           for (nextNode = currentNode.getNextSibling(); nextNode != null && nextNode.getNodeType() != 1; nextNode = nextNode.getNextSibling()) {}
           if (nextNode == null || !nextNode.getNodeName().equals(key)) {
            //   log.debug("Node Name::::" + currentNode.getNodeName());
               if (currentNode.getNodeType() == 1 && currentNode.getNodeName().equals(key)) {
                   newNode = doc.createElement(key);
                   XmlUtils.insertAfter(newNode, currentNode);
                   ++i;
               }
           }
       }
   }
   
   public static void main(String[] args) throws IndigoException, FileNotFoundException {
	  XMLtoJSON xMLtoJSON=new XMLtoJSON();
	  File file=new File("D:/shiporder.xml");
	  InputStream is=new FileInputStream(file);
	  xMLtoJSON.convertData("shipto", is);
	  System.out.println();
}
}

