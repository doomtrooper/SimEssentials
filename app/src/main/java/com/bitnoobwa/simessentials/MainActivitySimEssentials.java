package com.bitnoobwa.simessentials;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.bitnoobwa.operators.Operator;

import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class MainActivitySimEssentials extends ActionBarActivity {
    private String operatorName;
    private String countryCode;
    private Operator operator;

    public void setOperatorName(String str){
        this.operatorName=str;
    }
    public void setCountryCode(String str){ this.countryCode=str;}
    public String getOperatorName(){
        return operatorName;
    }
    public String getCountryCode(){
        return countryCode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        setOperatorName(telMgr.getNetworkOperatorName().toLowerCase());
        //Log.d("op-name","setting operator name:"+getOperatorName());
        setCountryCode(telMgr.getSimCountryIso().toLowerCase());
        //Log.d("county","setting county:"+getCountryCode());
        int simState = telMgr.getSimState();
        if(simState==TelephonyManager.SIM_STATE_READY){
            setContentView(R.layout.activity_main_activity_sim_essentials);
            try{
                XmlResourceParser myXml=getApplicationContext().getResources().getXml(R.xml.ussd);
                UssdXmlParser xmlParser=new UssdXmlParser(getOperatorName(),getCountryCode(),myXml);
                operator=xmlParser.parse();
                //Log.v("opName",operator.toString());
            }catch (FileNotFoundException e){
                setContentView(R.layout.activity_main_activity_sim_essentials_error);
                TextView errorView=(TextView)findViewById(R.id.error_msg);
                errorView.setText(e.getMessage());
            }catch (IOException e2){
                setContentView(R.layout.activity_main_activity_sim_essentials_error);
                TextView errorView=(TextView)findViewById(R.id.error_msg);
                errorView.setText(e2.getMessage());
            }catch (XmlPullParserException e3){
                setContentView(R.layout.activity_main_activity_sim_essentials_error);
                TextView errorView=(TextView)findViewById(R.id.error_msg);
                errorView.setText(e3.getMessage());
            }catch (Exception e4){
                setContentView(R.layout.activity_main_activity_sim_essentials_error);
                TextView errorView=(TextView)findViewById(R.id.error_msg);
                errorView.setText(e4.getMessage());
            }
        }else{
            setContentView(R.layout.activity_main_activity_sim_essentials_nosim);
        }
    }
    /** Called when the user clicks the Sim Info button */
    public void viewSimInfo(View view) {
        //Log.d("sim-details","starting new Activity SIm Details");
        //Create new Activity in response to button Sim Info button
        Intent intent=new Intent(this,SimDetails.class);
        startActivity(intent);
    }
    public void viewSimBalance(View view){
        runUSSDCode(operator.getBalanceUSSD());
    }
    public void viewSimOwnNumber(View view){
        runUSSDCode(operator.getOwnNoUSSD());
    }
    private void runUSSDCode(String ussdCode){
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Uri.encode(ussdCode))));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_sim_essentials, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //noinspection SimplifiableIfStatement
        /*
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }
}
