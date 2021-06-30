package com.techelevator.tenmo.controllers;


import com.techelevator.tenmo.daos.AccountDAO;
import com.techelevator.tenmo.models.Account;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class AccountController {
    private AccountDAO accountDAO;

    public AccountController(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
        }

@RequestMapping(path = "accounts" , method = RequestMethod.GET)

    public List<Account> getAccountBalance (Principal principal){
        String username = principal.getName();
        return accountDAO.getAccount(username);
}



    }
