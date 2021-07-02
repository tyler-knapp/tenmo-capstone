package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.models.Transfer;

public interface TransferDAO {

    Transfer transferAddAmount(int transferTo , double amount);

    Transfer transferWithdrawAmount(int transferFrom, double amount);
}
