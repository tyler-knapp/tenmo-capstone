package com.techelevator.tenmo.controllers;


import com.techelevator.tenmo.auth.dao.UserDAO;
import com.techelevator.tenmo.auth.model.User;
import com.techelevator.tenmo.daos.AccountDAO;
import com.techelevator.tenmo.models.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class tenmoController {

    //private String apiUrl = "http://localhost:8080/";
    private AccountDAO accountDAO;
    private UserDAO userDAO;

    public tenmoController(AccountDAO accountDAO, UserDAO userDAO){
        this.accountDAO = accountDAO;
        this.userDAO = userDAO;
    }

    @RequestMapping(path = "users", method = RequestMethod.GET)
    public List<User> list(){
        return userDAO.findAll();
    }


//    @RequestMapping(path = "users", method = RequestMethod.GET)
//    public List<User> list(){
//
//    }


    @RequestMapping(path = "accounts", method = RequestMethod.GET)
    public Account getAccount(Principal principal) {
        return this.accountDAO.getAccount(principal.getName());
    }



}
