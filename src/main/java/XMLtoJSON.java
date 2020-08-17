import com.adeptia.indigo.logging.Logger;
import com.adeptia.indigo.services.transform.ScriptedService;
import com.adeptia.indigo.utils.IndigoException;
import com.adeptia.indigo.utils.XmlException;
import com.adeptia.indigo.utils.XmlUtils;
import com.googlecode.jslint4java.UnicodeBomInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLtoJSON
{
  public void convertData(String jsonKeys, InputStream inputStream, ScriptedService service)
    throws IndigoException
  {
    Logger log = service.getLogger();

    String jsonDocString = "";
    String escapeChar = "&#13;";
    String jsonExtraVar = ",{}|,\"\"";
    UnicodeBomInputStream uis = null;
    StringWriter writer = null;
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
      Document doc = documentBuilder.parse(inputStream);
      if (!jsonKeys.trim().equals("")) {
        String[] ArraysJsonKeys = jsonKeys.split(",");
        for (int i = 0; i < ArraysJsonKeys.length; i++) {
          inserNodeInXML(doc, ArraysJsonKeys[i], log);
        }
      }
      DOMSource domSource = new DOMSource(doc);
      writer = new StringWriter();
      StreamResult result = new StreamResult(writer);
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      transformer.setOutputProperty("indent", "yes");
      transformer.transform(domSource, result);
      jsonDocString = writer.toString();
      if (!escapeChar.trim().equals("")) {
        String[] ArrayEscapeChar = escapeChar.split("\\|");
        for (int i = 0; i < ArrayEscapeChar.length; i++) {
          String esacpeChar = ArrayEscapeChar[i];
          jsonDocString = jsonDocString.replace(esacpeChar, "");
        }
      }
      InputStream is = new ByteArrayInputStream(jsonDocString.getBytes("UTF-8"));
      StringBuffer jsonStringBuffer = new StringBuffer();
      uis = new UnicodeBomInputStream(is);
      uis.skipBOM();
      HashMap map = new HashMap();
      map.put("isRemoveLeadingZeroes", "false");
      map.put("isConvertStringToValue", "false");
      JSONObject jObject = XML.toJSONObject(IOUtils.toString(uis), false);
      Iterator keys = jObject.keys();
      while (keys.hasNext()) {
        String jsonKey = (String)keys.next();
        if (((jObject.get(jsonKey) instanceof JSONObject)) && (jsonKey.equalsIgnoreCase("Root"))) {
          jsonStringBuffer.append(jObject.get(jsonKey));
          break;
        }
      }
      String jsonString = jsonStringBuffer.toString();
      if (!jsonExtraVar.trim().equals("")) {
        String[] ArrayJsonVar = jsonExtraVar.split("\\|");
        for (int i = 0; i < ArrayJsonVar.length; i++) {
          String jsonVar = ArrayJsonVar[i];
          jsonString = jsonString.replace(jsonVar, "");
        }
      }
      log.debug("JSON Output::" + jsonString);

      service.write(jsonString.getBytes(), "default");
    } catch (Exception e) {
      log.error("Error while converting JSON from XML:" + e.getMessage(), e);
      throw new IndigoException("Error while converting JSON from XML:" + e.getMessage(), e);
    } finally {
      if (uis != null) {
        try {
          uis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (writer != null)
        try {
          writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
  }

  public void inserNodeInXML(Document doc, String key, Logger log)
    throws XmlException
  {
    NodeList nodeList = doc.getElementsByTagName(key);
    log.debug("NodeList Length:::" + nodeList.getLength());

    Node currentNode = null;
    Node nextNode = null;
    Node newNode = null;

    for (int i = 0; i < nodeList.getLength(); i++) {
      log.debug("Updated NodeList Length:::" + nodeList.getLength());
      currentNode = nodeList.item(i);
      nextNode = currentNode.getNextSibling();

      while ((nextNode != null) && (nextNode.getNodeType() != 1)) {
        nextNode = nextNode.getNextSibling();
      }

      if ((nextNode != null) && (nextNode.getNodeName().equals(key)))
      {
        continue;
      }
      log.debug("Node Name::::" + currentNode.getNodeName());
      if ((currentNode.getNodeType() == 1) && (currentNode.getNodeName().equals(key))) {
        newNode = doc.createElement(key);
        XmlUtils.insertAfter(newNode, currentNode);
        i++;
      }
    }
  }
}
