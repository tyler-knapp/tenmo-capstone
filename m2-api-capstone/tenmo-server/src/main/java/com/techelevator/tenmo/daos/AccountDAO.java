package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.models.Account;

import java.util.List;

public interface AccountDAO {

    //Questioning whether to pass in a userId or a accountId maybe userName through a JOIN?
    Account getAccount(String username);

//    double addToAccount(int userTo, double amount);
//
//    double subtractFromAccount(int userFrom, double amount);

    Account findAccountById(int id);




}
