package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.models.Account;

import java.util.List;

public interface AccountDAO {


    Account getAccount(String username);

    //Account getAccountByUsername(String username);

    Account findAccountById(int id);

//    double addToAccount(String userToUsername, double amount);
//
 //   double subtractFromAccount(String userFromUserName, double amount);

    //Would this make more sense to these in the transfer class?
    public double withdrawAmount(String userFromUserName , double amount);

    public double addAmount(String username, double amount);






}
