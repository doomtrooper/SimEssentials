package com.bitnoobwa.simessentials;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitnoobwa.operators.Operator;

import java.util.List;

/**
 * Created by razor on 4/8/2015.
 */
public class OperatorDetailsAdapter extends ArrayAdapter<Operator>{
    private int resourceId;
    private void setResourceId(int textViewResourceId){
        this.resourceId=textViewResourceId;
    }
    public OperatorDetailsAdapter(Context context, int textViewResourceId,
                                  List<Operator> objects) {
        super(context, textViewResourceId, objects );
        setResourceId(textViewResourceId);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the data item for this position
        Operator operator=getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        }
        // Lookup view for data population
        TextView opName=(TextView) convertView.findViewById(R.id.op_name);
        TextView opBal=(TextView) convertView.findViewById(R.id.op_bal_ussd);
        // Populate the data into the template view using the data object
        opName.setText(operator.getOperatorName());
        opBal.setText(operator.getBalanceUSSD());
        // Return the completed view to render on screen
        return convertView;
    }
}
