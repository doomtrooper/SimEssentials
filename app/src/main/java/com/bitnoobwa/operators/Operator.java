package com.bitnoobwa.operators;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by razor on 3/19/2015.
 */
public class Operator implements Comparable<Operator>,Parcelable{
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
    @Override
    public int compareTo(Operator obj2){
        return this.getOperatorName().compareTo(obj2.getOperatorName());
    }

    /* everything below here is for implementing Parcelable */
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(getOperatorName());
        out.writeString(getBalanceUSSD());
        out.writeString(getOwnNoUSSD());
        out.writeString(getOperatorUSSD());
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Operator> CREATOR = new Parcelable.Creator<Operator>() {
        public Operator createFromParcel(Parcel source) {
            String operatorName=source.readString();
            String balanceUSSD=source.readString();
            String ownNoUSSD=source.readString();
            String operatorUSSD=source.readString();
            return new Operator(balanceUSSD, ownNoUSSD, operatorUSSD, operatorName);
        }

        public Operator[] newArray(int size) {
            return new Operator[size];
        }
    };

}
