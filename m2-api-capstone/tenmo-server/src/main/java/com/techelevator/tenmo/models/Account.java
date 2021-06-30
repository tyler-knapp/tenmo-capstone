package com.techelevator.tenmo.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {

    private int accountId;
    private int userId;
    private double balance;
    private String username;

    public Account(){

    }

    public Account(int accountId, int userId, double balance , String username){
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
        this.username = username;
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

   public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

