package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.models.Account;

import java.util.List;

public interface AccountDAO {


    Account getAccount(String username);

//    double addToAccount(int userTo, double amount);
//
//    double subtractFromAccount(int userFrom, double amount);

    //Would this make more sense to these in the transfer class?
    public double withdrawAmount(int transferFrom, double amount);

    public double addAmount(int transferFrom, double amount);

    Account findAccountById(int id);




}
