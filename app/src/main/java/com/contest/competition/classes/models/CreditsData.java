package com.contest.competition.classes.models;

/**
 * Created by M Ahmed Mushtaq on 9/20/2018.
 */

public class CreditsData {
    public int noOfCreditsEarn,price;
    public String priceWithDollarSign,noOfCreditsString,mProductId;

    public CreditsData(int noOfCreditsEarn, int price,String priceWithDollarSign,String noOfCreditsString,String productId) {
        this.noOfCreditsEarn = noOfCreditsEarn;
        this.price = price;
        this.priceWithDollarSign = priceWithDollarSign;
        this.noOfCreditsString = noOfCreditsString;
        this.mProductId = productId;
    }

    public int getNoOfCreditsEarn() {
        return noOfCreditsEarn;
    }

    public int getPrice() {
        return price;
    }

    public String getProductId() {
        return mProductId;
    }

    public String getNoOfCreditsString() {
        return noOfCreditsString;
    }

    public String getPriceWithDollarSign() {
        return priceWithDollarSign;
    }


}
