package com.bitnoobwa.simessentials;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class SimDetails extends ActionBarActivity {
    private TelephonyManager telMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        setContentView(R.layout.activity_sim_details);
        setSimDetailsView();
    }

    private void setSimDetailsView(){
        setRowContentText(R.id.OperatorName,telMgr.getNetworkOperatorName());
        setRowContentText(R.id.DeviceId,telMgr.getDeviceId());
        setRowContentText(R.id.CountryISO,telMgr.getSimCountryIso());
        setRowContentText(R.id.SimSerial,telMgr.getSimSerialNumber());
        setRowContentText(R.id.SubId,telMgr.getSubscriberId());
    }

    private void setRowContentText(int rowId,String rowTxt){
        TextView rowView = (TextView)findViewById(rowId);
        if(rowTxt!=null){
            rowView.setText(rowTxt);
        }else rowView.setText(R.string.na);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sim_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
 /*       if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }
}
