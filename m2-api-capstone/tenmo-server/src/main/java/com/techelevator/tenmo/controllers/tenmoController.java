package com.techelevator.tenmo.controllers;


import com.techelevator.tenmo.auth.dao.UserDAO;
import com.techelevator.tenmo.auth.model.User;
import com.techelevator.tenmo.daos.AccountDAO;
import com.techelevator.tenmo.daos.TransferDAO;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class tenmoController {

    //private String apiUrl = "http://localhost:8080/";
    private AccountDAO accountDAO;
    private UserDAO userDAO;
    private TransferDAO transferDAO;

    public tenmoController(AccountDAO accountDAO, UserDAO userDAO, TransferDAO transferDAO){
        this.accountDAO = accountDAO;
        this.userDAO = userDAO;
        this.transferDAO = transferDAO;
    }

    @RequestMapping(path = "users", method = RequestMethod.GET)
    public List<User> list(){
        return userDAO.findAll();
    }


    @RequestMapping(path = "accounts", method = RequestMethod.GET)
    public Account getAccount(Principal principal) {
        return accountDAO.getAccount(principal.getName());
    }

    @RequestMapping(path = "users/" + "{id}/" + "transfers", method = RequestMethod.POST)
    public Transfer sendTransferRequest(@RequestBody Transfer transfer) {
        Transfer result = transferDAO.addTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        return result;
    }



}
