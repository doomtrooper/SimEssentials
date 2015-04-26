package com.bitnoobwa.simessentials;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bitnoobwa.operators.Operator;


public class OperatorDetails extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_details);
        Operator operator=(Operator)getIntent().getParcelableExtra("Operator");
        setOperatorDetailsView(operator);
    }
    private void setOperatorDetailsView(Operator operator){
        setRowContentText(R.id.OperatorName,operator.getOperatorName().toUpperCase());
        setRowContentText(R.id.Bal_USSD,operator.getBalanceUSSD());
        setRowContentText(R.id.Own_USSD,operator.getOwnNoUSSD());
        setRowContentText(R.id.Op_USSD,operator.getOperatorUSSD());
    }

    private void setRowContentText(int rowId,String rowTxt){
        TextView rowView = (TextView)findViewById(rowId);
        if(rowTxt!=null){
            rowView.setText(rowTxt);
            //Log.v("textView", rowId + rowTxt);
        }else rowView.setText(R.string.na);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_operator_details, menu);
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
