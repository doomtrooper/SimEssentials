package com.bitnoobwa.simessentials;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    private ArrayList<Operator> operatorList;
    private void setOperatoList(ArrayList<Operator> list){
        this.operatorList=list;
    }
    private ArrayList<Operator> getOperatorList(){
        return operatorList;
    }
    //private final String countryCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);
        Intent intent=getIntent();
        final String countryCode=intent.getStringExtra("countryCode");
        //Log.v("countryCode",countryCode);
        XmlResourceParser myXml=getApplicationContext().getResources().getXml(R.xml.ussd);
        UssdXmlParser xmlParser=new UssdXmlParser(countryCode,myXml);
        try{
            setOperatoList(xmlParser.parse());
            Collections.sort(getOperatorList());
            populateOperatorsListView();
        }catch (XmlPullParserException e){
            setContentView(R.layout.activity_main_activity_sim_essentials_error);
            setErrorText(R.id.error_msg,R.string.error);
        }catch (IOException e2){
            setContentView(R.layout.activity_main_activity_sim_essentials_error);
            setErrorText(R.id.error_msg,R.string.error);
        }catch (NullPointerException e3){
            setContentView(R.layout.activity_main_activity_sim_essentials_error);
            setErrorText(R.id.error_msg,R.string.error);
        }catch (Exception e4){
            setContentView(R.layout.activity_main_activity_sim_essentials_error);
            setErrorText(R.id.error_msg,R.string.error);
        }
    }
    private void populateOperatorsListView(){
        // Create the adapter to convert the array to views
       OperatorDetailsAdapter adapter = new OperatorDetailsAdapter(this,R.layout.activity_all_ussd,getOperatorList());
        // Attach the adapter to a ListView
        final ListView listView = (ListView) findViewById(R.id.operator);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Operator operator = (Operator) parent.getItemAtPosition(position);
                viewOperatorDetails(view,operator);
            }
        });
    }
    private void setErrorText(int textViewId,int textToSet){
        TextView rowView = (TextView)findViewById(textViewId);
        rowView.setText(textToSet);
    }
    private void setErrorText(int textViewId,String errText){
        TextView rowView = (TextView)findViewById(textViewId);
        rowView.setText(errText);
    }
    public void viewOperatorDetails(View view,Operator operator){
        Intent intent=new Intent(this,OperatorDetails.class);
        Bundle bundle=new Bundle();
        bundle.putParcelable("Operator",operator);
        intent.putExtras(bundle);
        startActivity(intent);
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
       /* int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }
}
