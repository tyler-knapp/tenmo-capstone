package com.techelevator.tenmo.controllers;


import com.techelevator.tenmo.auth.dao.UserDAO;
import com.techelevator.tenmo.auth.model.User;
import com.techelevator.tenmo.daos.AccountDAO;
import com.techelevator.tenmo.daos.TransferDAO;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
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

    //Returns a list of our users
    @RequestMapping(path = "users", method = RequestMethod.GET)
    public List<User> list(){
        return userDAO.findAll();
    }

    //Returns a full list of all the transfers.
    @RequestMapping(path = "transfers", method = RequestMethod.GET)
    public List<Transfer> listTransfers(){
        return transferDAO.findAll();
    }

    //Retrieve's a user's account information.... account_id, username, balance)
    @RequestMapping(path = "accounts", method = RequestMethod.GET)
    public Account getAccount(Principal principal) {
        return accountDAO.getAccount(principal.getName());
    }


    //Creates a transfer
    //Doesn't quite work.
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "transfers", method = RequestMethod.POST)
    public Transfer sendTransfer(@RequestBody Transfer transfer ) {
        Transfer result = transferDAO.createTransfer( transfer );
        return result;
    }

    @RequestMapping(path = "transfers/" + "{id}" , method = RequestMethod.GET)
    public Transfer getATransferById(@RequestBody @PathVariable int id){
        Transfer result = transferDAO.getTransferById(id);
        return result;
    }

//    @RequestMapping(path = "accounts" + "{id}", method )
//
//    @RequestMapping(path = "transfers/" + "{id}", method = RequestMethod.GET)
//    public List<Transfer> listOfTransfers(@RequestBody @PathVariable int id){
//        return transferDAO.getListOfAllTransfersByAccountId(id);
//    }

   // @RequestMapping(path = "accounts/" + "{id}", method = )

//    @RequestMapping(path = "transfers/" + "{id}" , method = RequestMethod.GET)
//    public Transfer showTransferById(@RequestBody Transfer transfer @PathParam name = id ){
//        Transfer result = transferDAO.getTransferById(transfer.getTransferId(id));
//        return result;
//    }



}
