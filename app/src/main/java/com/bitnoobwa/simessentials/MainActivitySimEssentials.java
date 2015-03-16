package com.bitnoobwa.simessentials;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;


public class MainActivitySimEssentials extends ActionBarActivity {
    private String operatorName=null;
    public void setOperatorName(String str){
        this.operatorName=str;
    }
    public String getOperatorName(){
        return operatorName;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_main_activity_sim_essentials);*/
        TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        setOperatorName(telMgr.getNetworkOperatorName());
        int simState = telMgr.getSimState();
        if(simState==TelephonyManager.SIM_STATE_READY){
            setContentView(R.layout.activity_main_activity_sim_essentials);
        }else{
            setContentView(R.layout.activity_main_activity_sim_essentials_nosim);
        }
    }
    /** Called when the user clicks the Sim Info button */
    public void viewSimInfo(View view) {

        //Do something in response to button Sim Info button
        Intent intent=new Intent(this,SimDetails.class);
        startActivity(intent);
    }
    public void viewSimBalance(View view){
        getBalance();
        String ussdCode = "*" + "367" + Uri.encode("#");
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ussdCode)));
    }
    public void viewSimOwnNumber(View view){
        Context context=getApplicationContext();
        CharSequence text="Own number will be Displayed";
        int duration= Toast.LENGTH_SHORT;
        Toast.makeText(context,getOperatorName(),duration).show();
        String ussdCode = Uri.encode("*1#");
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ussdCode)));
    }
    public void getBalance(){

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
