package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.models.Account;

import java.util.List;

public interface AccountDAO {

    //Questioning whether to pass in a userId or a accountId maybe userName through a JOIN?
    List<Account> getAccount(String username);
}
