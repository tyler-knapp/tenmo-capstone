package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.models.Transfer;

public interface TransferDAO {

    //Should this be broken into two methods? Or should a transaction take place in on method?
    //Simultaneous updates?
    //Two separate updates?

    Transfer addTransfer(int transferFrom, int transferTo, double amount);

    double addAmount(int transferTo , double amount);

    double withdrawAmount(int transferFrom, double amount);
}
