package com.techelevator.tenmo.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {

    private int accountId;
    private int userId;
    private double balance;

    public Account(){
    }


    public Account(int accountId, int userId, double balance ){
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}

