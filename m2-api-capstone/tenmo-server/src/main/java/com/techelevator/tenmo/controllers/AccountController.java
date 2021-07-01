package com.techelevator.tenmo.controllers;


import com.techelevator.tenmo.daos.AccountDAO;
import com.techelevator.tenmo.models.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

//@PreAuthorize("isAuthenticated()")
@RestController
public class AccountController {

    private AccountDAO accountDAO;

    public AccountController(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
        }


    @RequestMapping(path = "/accounts" , method = RequestMethod.GET)
    public List<Account> getAccount(Principal principal){
        String username = principal.getName();
        return accountDAO.getAccount(username);
    }



}
