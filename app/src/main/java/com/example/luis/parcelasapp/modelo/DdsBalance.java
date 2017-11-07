package com.example.luis.parcelasapp.modelo;

/**
 * Created by Luis on 07/11/2017.
 */

public class DdsBalance {
    private double balance;
    private int dds;

    public DdsBalance(double balance, int dds) {
        this.balance = balance;
        this.dds = dds;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getDds() {
        return dds;
    }

    public void setDds(int dds) {
        this.dds = dds;
    }
}
