package com.bitnoobwa.simessentials;

import android.content.res.XmlResourceParser;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bitnoobwa.operators.Operator;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;


public class AllUssd extends ActionBarActivity {
    private List<Operator> operatorList;
    private void setOperatoList(List<Operator> list){
        this.operatorList=list;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_all_ussd);
        XmlResourceParser myXml=getApplicationContext().getResources().getXml(R.xml.ussd);
        UssdXmlParser xmlParser=new UssdXmlParser(myXml);
        try{
            setOperatoList(xmlParser.parse());
            populateOperatorsListView();
        }catch (XmlPullParserException e){
            setContentView(R.layout.activity_main_activity_sim_essentials_error);
            setErrorText(R.id.error_msg,R.string.errorXMLparsing);
        }catch (IOException e2){
            setContentView(R.layout.activity_main_activity_sim_essentials_error);
            setErrorText(R.id.error_msg,R.string.errorXMLparsing);
        }
    }
    private void populateOperatorsListView(){
        // Create the adapter to convert the array to views
       OperatorDetailsAdapter adapter = new OperatorDetailsAdapter(this,R.layout.activity_all_ussd,operatorList);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.operator);
        listView.setAdapter(adapter);
    }
    private void setErrorText(int textViewId,int textToSet){
        TextView rowView = (TextView)findViewById(textViewId);
        rowView.setText(textToSet);
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
