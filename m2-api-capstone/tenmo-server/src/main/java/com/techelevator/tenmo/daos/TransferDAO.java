package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.models.Transfer;

import java.util.List;

public interface TransferDAO {

    //Should this be broken into two methods? Or should a transaction take place in on method?
    //Simultaneous updates?
    //Two separate updates?


    List<Transfer> getListOfAllTransfersByAccountId(int accountFromId);

    List<Transfer> findAll();

    Transfer createTransfer(int id1, int id2, double amount);

    Transfer getTransferById(int transferId);

}
