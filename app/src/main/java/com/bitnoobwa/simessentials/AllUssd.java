package com.bitnoobwa.simessentials;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bitnoobwa.operators.Operator;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AllUssd extends ActionBarActivity {
    private List<Operator> operatorList;
    private void setOperatoList(List<Operator> list){
        this.operatorList=list;
    }
    private List<Operator> getOperatorList(){
        return operatorList;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_ussd);
        Intent intent=getIntent();
        String countryCode=intent.getStringExtra("countryCode");
        Log.v("countryCode",countryCode);
        XmlResourceParser myXml=getApplicationContext().getResources().getXml(R.xml.ussd);
        UssdXmlParser xmlParser=new UssdXmlParser(countryCode,myXml);
        try{
            //Collections.sort(tempOperatorList);
            setOperatoList(xmlParser.parse());
            Log.v("AllUSSD","AllUssd Activity");
            if(operatorList.isEmpty())
                Log.v("List","AllUssd Operator is empty List");
            for(Operator itr:operatorList)
                Log.v("operatorWa",itr.getOperatorName());
            populateOperatorsListView();
        }catch (XmlPullParserException e){
            setContentView(R.layout.activity_main_activity_sim_essentials_error);
            setErrorText(R.id.error_msg,"1:"+e.getMessage());
        }catch (IOException e2){
            setContentView(R.layout.activity_main_activity_sim_essentials_error);
            setErrorText(R.id.error_msg,"2:"+e2.getMessage());
        }catch (NullPointerException e3){
            setContentView(R.layout.activity_main_activity_sim_essentials_error);
            setErrorText(R.id.error_msg,"3:"+e3.getMessage());
        }catch (Exception e4){
            setContentView(R.layout.activity_main_activity_sim_essentials_error);
            setErrorText(R.id.error_msg,"4:"+e4.getMessage());
        }
        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewexampleactivity);

        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);*/
    }
    private void populateOperatorsListView(){
        Log.v("OperatorsListView","Inside populateOperatorsListView()");
        // Create the adapter to convert the array to views
       OperatorDetailsAdapter adapter = new OperatorDetailsAdapter(this,R.layout.activity_all_ussd,getOperatorList());
        // Attach the adapter to a ListView
        //ListView listView = (ListView) findViewById(R.id.operator);
        final ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
    }
    private void setErrorText(int textViewId,int textToSet){
        TextView rowView = (TextView)findViewById(textViewId);
        rowView.setText(textToSet);
    }
    private void setErrorText(int textViewId,String errText){
        TextView rowView = (TextView)findViewById(textViewId);
        rowView.setText(errText);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_ussd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
