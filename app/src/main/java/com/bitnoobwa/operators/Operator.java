package com.bitnoobwa.operators;

/**
 * Created by aparsh on 3/19/2015.
 */
public class Operator {
    public final String balanceUSSD;
    public final String ownNoUSSD;
    public final String operatorUSSD;
    public final String operatorName;
    public Operator(String balanceUSSD, String ownNoUSSD, String operatorUSSD,String operatorName) {
        this.balanceUSSD = balanceUSSD;
        this.ownNoUSSD = ownNoUSSD;
        this.operatorUSSD = operatorUSSD;
        this.operatorName=operatorName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public String getBalanceUSSD() {
        return balanceUSSD;
    }

    public String getOwnNoUSSD() {
        return ownNoUSSD;
    }

    public String getOperatorUSSD() {
        return operatorUSSD;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "balanceUSSD='" + balanceUSSD + '\'' +
                ", ownNoUSSD='" + ownNoUSSD + '\'' +
                ", operatorUSSD='" + operatorUSSD + '\'' +
                ", operatorName='" + operatorName + '\'' +
                '}';
    }
}
