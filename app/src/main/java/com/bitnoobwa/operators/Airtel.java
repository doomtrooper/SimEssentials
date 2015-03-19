package com.bitnoobwa.operators;

/**
 * Created by aparsh on 3/19/2015.
 */
public class Airtel implements Operator {
    private String balUSSD="*123#";
    private String ownNoUSSD="*1#";

    public String getBalUSSD(){
        return this.balUSSD;
    }

    public String getOwnNoUSSD(){
        return this.ownNoUSSD;
    }
}
