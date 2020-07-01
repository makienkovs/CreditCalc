package com.makienkovs.creditcalc;

import android.annotation.SuppressLint;

public class Note {
    private int number;
    private String date;
    private double amount;
    private double main;
    private double percent;
    private double remains;
    private String numberString;
    private String amountString;
    private String mainString;
    private String percentString;
    private String remainsString;

    public Note() {
    }

    @SuppressLint("DefaultLocale")
    public void writeStringParams() {
        numberString = "" + number;
        amountString = String.format("%.2f", amount);
        mainString = String.format("%.2f", main);
        percentString = String.format("%.2f", percent);
        remainsString = String.format("%.2f", remains);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getMain() {
        return main;
    }

    public void setMain(double main) {
        this.main = main;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public double getRemains() {
        return remains;
    }

    public void setRemains(double remains) {
        this.remains = remains;
    }

    public String getNumberString() {
        return numberString;
    }

    public void setNumberString(String numberString) {
        this.numberString = numberString;
    }

    public String getAmountString() {
        return amountString;
    }

    public void setAmountString(String amountString) {
        this.amountString = amountString;
    }

    public String getMainString() {
        return mainString;
    }

    public void setMainString(String mainString) {
        this.mainString = mainString;
    }

    public String getPercentString() {
        return percentString;
    }

    public void setPercentString(String percentString) {
        this.percentString = percentString;
    }

    public String getRemainsString() {
        return remainsString;
    }

    public void setRemainsString(String remainsString) {
        this.remainsString = remainsString;
    }
}
