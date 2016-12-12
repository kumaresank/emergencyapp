package com.example.emergencyapp.app;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class WebService {
	
	private static String NAMESPACE = "http://tempuri.org/";
	private static String URL = "http://kumaresan.somee.com/Service1.asmx";
	private static String SOAP_ACTION = "http://tempuri.org/";

	public static String invokeHelloWorldWS(String device_Id) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, "AddRecord");
		// Property which holds input parameters
		PropertyInfo ApplicationName = new PropertyInfo();
        ApplicationName.setName("ApplicationName");
        ApplicationName.setValue("SMART_ALERT");
        ApplicationName.setType(String.class);
		request.addProperty(ApplicationName);

        PropertyInfo Device_Type = new PropertyInfo();
        Device_Type.setName("Device_Type");
        Device_Type.setValue("MOBILE");
        Device_Type.setType(String.class);
        request.addProperty(Device_Type);

        PropertyInfo Device_Id = new PropertyInfo();
        Device_Id.setName("Device_Id");
        Device_Id.setValue(device_Id);
        Device_Id.setType(String.class);
        request.addProperty(Device_Id);

        PropertyInfo License_Type = new PropertyInfo();
        License_Type.setName("License_Type");
        License_Type.setValue("TRIAL");
        License_Type.setType(String.class);
        request.addProperty(License_Type);

        PropertyInfo License_Start_Date = new PropertyInfo();
        License_Start_Date.setName("License_Start_Date");
        SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");
        Date dt_License_Start_Date = new Date();
        License_Start_Date.setValue(fmt.format(dt_License_Start_Date));
        License_Start_Date.setType(String.class);
        request.addProperty(License_Start_Date);

        PropertyInfo License_End_Date = new PropertyInfo();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 5);
        License_End_Date.setName("License_End_Date");
        License_End_Date.setValue(fmt.format(c.getTime()));
        License_End_Date.setType(String.class);
        request.addProperty(License_End_Date);

        // Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+"AddRecord", envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "Error occured :" + e.toString();
		} 
		
		return resTxt;
	}

    public static Document getDomElement(String xml){
        Document doc = null;
        String resTxt = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            resTxt = "Error: "+ e.getMessage();
            return null;
        } catch (SAXException e) {
            resTxt = "Error: "+ e.getMessage();
            return null;
        } catch (IOException e) {
            resTxt = "Error: "+ e.getMessage();
            return null;
        }
        // return DOM
        return doc;
    }

    public static String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        Node elem = n.item(0);
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    public final String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

}
