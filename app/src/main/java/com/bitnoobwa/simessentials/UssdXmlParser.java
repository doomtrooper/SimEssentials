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
    private XmlResourceParser myXml;
    private String ns=null; //namespace is set to null
    public String getOperatorName() {
        return operatorName;
    }
    public String getCountryCode(){
        return countryCode;
    }
    public UssdXmlParser(XmlResourceParser myXml){
        this.myXml=myXml;
    }
    public UssdXmlParser(String operatorName, String countryCode, XmlResourceParser myXml) {
        this.operatorName = operatorName;
        this.countryCode = countryCode;
        this.myXml = myXml;
    }
    public Operator getOperator() throws XmlPullParserException,IOException{
        return findOperator(parse());
    }
    public List<Operator> parse() throws XmlPullParserException, IOException {
        try{
            myXml.next();
            myXml.next();
            return readFeed(myXml);
        }catch (XmlPullParserException e){
            Log.e("error-xmlparsing",e.getMessage());
        }catch (IOException e2){
            Log.e("error-IO",e2.getMessage());
        }
        return null;
    }

    private List<Operator> readFeed(XmlResourceParser parser) throws XmlPullParserException, IOException{
        List<Operator> operatorList=new ArrayList<>();
        parser.require(XmlPullParser.START_TAG,ns,"serviceproviders");
        //Log.d("entry-method","parser enters-reedFeed()");
        while (parser.next()!=XmlPullParser.END_TAG){
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            //Log.d("entry-xml","parser enters the serviceproviders");
            String name = parser.getName();
            Log.d("entry-xml",name);
            // Starts by looking for the country tag
            if (name.equalsIgnoreCase("country")) {
                if(parser.getAttributeValue(ns,"code").toLowerCase().equals(getCountryCode())){
                    //Log.v("enter-county","parser enters county tag!!!");
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
        return operatorList;
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
        //Log.v("entry-readProvider","Entry to read provider!!!");
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
        parser.require(XmlPullParser.START_TAG,ns,"name");
        String opName=parser.nextText();
        //Log.v("entry-readName","Entry to readName:"+opName);
        parser.require(XmlPullParser.END_TAG,ns,"name");
        return opName.toLowerCase();
    }

    private String getBalanceUSSD(XmlResourceParser parser) throws  IOException,XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,ns,"balance-check");
        String balUSSD=parser.nextText();
        //Log.v("entry-readName","Entry to readName:"+balUSSD);
        parser.require(XmlPullParser.END_TAG,ns,"balance-check");
        return balUSSD.toLowerCase();
    }
    
    private String getOperatorUSSD(XmlResourceParser parser) throws IOException,XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,ns,"operator");
        String opUSSD=parser.nextText();
        //Log.v("entry-readName","Entry to readName:"+opUSSD);
        parser.require(XmlPullParser.END_TAG,ns,"operator");
        return opUSSD.toLowerCase();
    }

    private String getOwnNoUSSD(XmlResourceParser parser) throws IOException,XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,ns,"own-no");
        String ownNoUSSD=parser.nextText();
        //Log.v("entry-readName","Entry to readName:"+ownNoUSSD);
        parser.require(XmlPullParser.END_TAG,ns,"own-no");
        return ownNoUSSD.toLowerCase();
    }

    private Operator findOperator(List<Operator> operatorArrayList){
        Log.v("entry-findOperator","Entry to findOperator!!!");
        Operator operator=null;
        for (Operator itr:operatorArrayList) {
            if (itr.getOperatorName().contains(getOperatorName().toLowerCase())) {
                //Log.v("findOperator","Operator Found:"+itr.getOperatorName());
                return itr;
            }
        }
        return operator;
    }
}
