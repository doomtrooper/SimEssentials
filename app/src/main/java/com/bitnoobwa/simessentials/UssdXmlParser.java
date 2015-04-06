package com.bitnoobwa.simessentials;

import android.content.res.XmlResourceParser;
import android.util.Log;

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
import java.util.List;

/**
 * Created by razor on 3/4/15.
 */
public class UssdXmlParser {
    private String operatorName;
    private String countryCode;
    /*private XmlPullParserFactory xmlFactoryObject;
    private XmlPullParser myparser;
    private FileInputStream fis;*/
    private XmlResourceParser myXml;
    private String ns=null; //namespace is set to null
    public String getOperatorName() {
        return operatorName;
    }
    public String getCountryCode(){
        return countryCode;
    }
    public UssdXmlParser(String operatorName, String countryCode, XmlResourceParser myXml) {
        this.operatorName = operatorName;
        this.countryCode=countryCode;
        this.myXml = myXml;
    }

    /*public FileInputStream getFis() {
        return fis;
    }*/

    public Operator parse() throws XmlPullParserException, IOException {
        /*InputStreamReader isr = null;
        char[] inputBuffer = null;
        String data = null;
        try {
            isr = new InputStreamReader(getFis());
            inputBuffer = new char[fis.available()];
            isr.read(inputBuffer);
            data = new String(inputBuffer);
            isr.close();
            fis.close();
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try{
            /*xmlFactoryObject = XmlPullParserFactory.newInstance();
            myparser = xmlFactoryObject.newPullParser();
            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            myparser.setInput(new StringReader(data));
            myparser.nextTag();*/
            myXml.next();
            return readFeed(myXml);
        }catch (XmlPullParserException e){
            Log.e("error-xmlparsing",e.getMessage());
        }catch (IOException e2){
            Log.e("error-IO",e2.getMessage());
        }
        return null;
    }

    private Operator readFeed(XmlResourceParser parser) throws XmlPullParserException, IOException{
        List<Operator> operatorList=new ArrayList<>();
        Operator temp=null;
        parser.require(XmlPullParser.START_TAG,ns,"serviceproviders");
        Log.d("entry-method","parser enters-reedFeed()");
        while (parser.next()!=XmlPullParser.END_TAG){
            Log.d("entry-xml","parser enters the serviceproviders");
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equalsIgnoreCase("country")) {
                if(parser.getAttributeValue(ns,"code").toLowerCase().equals(getCountryCode())){
                    Log.v("enter-county","parser enters county tag!!!");
                    while (parser.next()!=XmlPullParser.END_TAG){
                        if(parser.getEventType()!=XmlPullParser.START_TAG)
                            continue;
                        String tagNameInsideCountry=parser.getName();
                        if(tagNameInsideCountry.equalsIgnoreCase("provider")){
                            operatorList.add(readProvider(parser));
                        }else {
                            skip(parser);
                        }
                    }
                }
            } else {
                skip(parser);
            }
        }
        return findOperator(operatorList);
    }

    private void skip(XmlResourceParser parser) throws XmlPullParserException, IOException {
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

    private Operator readProvider(XmlResourceParser parser) throws IOException,XmlPullParserException{
        String balanceUSSD=null;
        String ownNoUSSD=null;
        String operatorUSSD=null;
        String operatorName=null;
        Log.v("entry-readProvider","Entry to read provider!!!");
        parser.require(XmlPullParser.START_TAG,ns,"provider");
        while (parser.next()!=XmlPullParser.END_TAG){
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName=parser.getName();
            if(tagName.equalsIgnoreCase("name")){
                    operatorName=readName(parser);
            }else if (tagName.equalsIgnoreCase("balance-check")){
                    balanceUSSD=getBalanceUSSD(parser);
            }else if (tagName.equalsIgnoreCase("operator")){
                    operatorUSSD=getOperatorUSSD(parser);
            }else if (tagName.equalsIgnoreCase("own-no")){
                    ownNoUSSD=getOwnNoUSSD(parser);
            }
        }
        return new Operator(balanceUSSD, ownNoUSSD, operatorUSSD,operatorName);
    }

    private String readName(XmlResourceParser parser) throws IOException,XmlPullParserException{
        Log.v("entry-readName","Entry to readName!!!");
        parser.require(XmlPullParser.START_TAG,ns,"name");
        String opName=parser.getText();
        parser.require(XmlPullParser.END_TAG,ns,"name");
        return opName;
    }

    private String getBalanceUSSD(XmlResourceParser parser) throws  IOException,XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,ns,"balance-check");
        String balUSSD=parser.getText();
        parser.require(XmlPullParser.END_TAG,ns,"balance-check");
        return balUSSD;
    }
    
    private String getOperatorUSSD(XmlResourceParser parser) throws IOException,XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,ns,"operator");
        String opUSSD=parser.getText();
        parser.require(XmlPullParser.END_TAG,ns,"operator");
        return opUSSD;
    }

    private String getOwnNoUSSD(XmlResourceParser parser) throws IOException,XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,ns,"own-no");
        String ownNoUSSD=parser.getText();
        parser.require(XmlPullParser.END_TAG,ns,"own-no");
        return ownNoUSSD;
    }

    private Operator findOperator(List<Operator> operatorArrayList){
        Log.v("entry-findOperator","Entry to findOperator!!!");
        Operator operator=null;
        for (Operator itr:operatorArrayList) {
            if (itr.getOperatorName().contains(operatorName.toLowerCase())) {
                Log.v("operator found", itr.toString());
                return itr;
            }
        }
        return operator;
    }
}
