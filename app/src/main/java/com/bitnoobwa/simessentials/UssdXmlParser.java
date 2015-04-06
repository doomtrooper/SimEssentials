package com.bitnoobwa.simessentials;

import com.bitnoobwa.operators.Operator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by razor on 3/4/15.
 */
public class UssdXmlParser {
    private String operatorName;
    private String countryCode;
    private XmlPullParserFactory xmlFactoryObject;
    private XmlPullParser myparser;
    private FileInputStream fis;
    private String ns=null; //namespace
    public String getOperatorName() {
        return operatorName;
    }
    public String getCountryCode(){
        return countryCode;
    }
    public UssdXmlParser(String operatorName, String countryCode, FileInputStream fis) {
        this.operatorName = operatorName;
        this.countryCode=countryCode;
        this.fis = fis;
    }

    public FileInputStream getFis() {
        return fis;
    }

    public Operator parse() throws XmlPullParserException, IOException {
        InputStreamReader isr = null;
        char[] inputBuffer = null;
        String data = null;
        ArrayList<String> items = new ArrayList<String>();
        try {
            isr = new InputStreamReader(getFis());
            inputBuffer = new char[fis.available()];
            isr.read(inputBuffer);
            data = new String(inputBuffer);
            isr.close();
            fis.close();
        } catch (FileNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            myparser = xmlFactoryObject.newPullParser();
            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            myparser.setInput(new StringReader(data));
            myparser.nextTag();
        }finally {

        }
        return readFeed(myparser);
    }

    private Operator readFeed(XmlPullParser parser) throws XmlPullParserException, IOException{
        Operator operator=null,temp=null;
        parser.require(XmlPullParser.START_TAG,ns,"serviceproviders");
        while (parser.nextTag()!=XmlPullParser.END_TAG){
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.toLowerCase().equals("country")) {
                if(parser.getAttributeValue(ns,"code").toLowerCase().equals("in")){
                    //parser.require(XmlPullParser.START_TAG,ns,"provider");
                    while (parser.nextTag()!=XmlPullParser.END_TAG){
                        if(parser.getEventType()!=XmlPullParser.START_TAG)
                            continue;
                        String tagNameInsideCountry=parser.getName();
                        if(tagNameInsideCountry.toLowerCase().equals("provider")){
                            if((temp=readProvider(parser,getOperatorName()))!=null)
                                operator=temp;
                        }else {
                            skip(parser);
                        }
                    }
                }
            } else {
                skip(parser);
            }
        }
        return operator;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private Operator readProvider(XmlPullParser parser,String operatorName) throws IOException,XmlPullParserException{
        Operator tempOperator=null;
        parser.require(XmlPullParser.START_TAG,ns,"provider");
        while (parser.next()!=XmlPullParser.END_TAG){
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName=parser.getName();
            if(tagName.toLowerCase().equals("name")){
                String operator=parser.getText();
                if(operator.equals(operatorName)){
                    tempOperator=getOperatorObject(parser);
                }
            }else {
                skip(parser);
            }
        }
        return tempOperator;
    }

    private Operator getOperatorObject(XmlPullParser parser){
        Operator operator=null;

    }
}
