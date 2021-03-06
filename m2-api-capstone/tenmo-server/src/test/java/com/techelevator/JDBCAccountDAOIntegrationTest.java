package com.techelevator;

import com.techelevator.tenmo.auth.dao.JdbcUserDAO;
import com.techelevator.tenmo.auth.dao.UserDAO;
import com.techelevator.tenmo.auth.model.User;
import com.techelevator.tenmo.daos.AccountDAO;
import com.techelevator.tenmo.daos.JDBCAccountDAO;
import com.techelevator.tenmo.daos.TransferDAO;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class JDBCAccountDAOIntegrationTest extends DAOIntegrationTest{

    private UserDAO userDAO;
    private AccountDAO accountDAO;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup(){
        jdbcTemplate = new JdbcTemplate(getDataSource());
        userDAO = new JdbcUserDAO(jdbcTemplate);
        accountDAO = new JDBCAccountDAO(jdbcTemplate);

    }
    //Of all the problems we encountered in this capstone, I think having more
    //knowledge and confidence in integration testing as well as debugging would have
    //gone a long way. It was hard for me to create tests and knowing if our tests are passing
    //for the right reasons.



    //We weren't sure how to test this. Would we create a test account and add money to it
    //I think something else may be missing from our arrange section?
    @Test
    public void subtract_amount_from_account(){
        //ARRANGE
        User originalUser;
        double originalAccount = accountDAO.addAmount("test", 12);
        //TEST
        double newBalance = accountDAO.addAmount("testUsername", 100);

        //ASSERTS

        Assert.assertEquals(100, newBalance, 0.9);

    }


    //To test  we would need to first create a test user, then a test account.
    @Test
    public void get_account_by_username() {
        Account account = getAccount("testUserName",1000);
        //createNewTestAccount(account);

        Account newAccount = accountDAO.getAccount(account.getUsername());

        Assert.assertNotNull(newAccount);

    }

//    private Account getAccount(int userId ){
//        Account testAccount = new Account();
//        //testAccount.setAccountId(accountId);
//        testAccount.setUserId(userId);
//        testAccount.setBalance(100);
//        return testAccount;
//    }


    //Why are we passing user here? Not sure what exactly we were thinking about for this one.
    private Account getAccountByUserId( int userId , User user){
        Account account = new Account();
        account.setUserId(userId);
        account.setBalance(103);
        return account;
    }

    //What do I need to set as method argument?
    private User getUser(String username){
        User testUser = new User();
        testUser.setUsername(username);
        testUser.setPassword("password");

        return testUser;
    }

    private Account createNewTestAccount(Account account){
        //Does creating a new account make sense?
        Integer newAccountId;
        String sql = "INSERT INTO accounts( user_id , balance) " +
                "VALUES(?, ?) RETURNING account_id";
        //Not sure if this should be jdbc.update?
        newAccountId = jdbcTemplate.update(sql, account.getUserId(), account.getBalance());
        account.setAccountId(newAccountId);

        return account;
    }


    private Account getAccount(String username, double balance) {
        Account account = new Account();
        account.setUsername(username);
        account.setBalance(balance);
        account.setUserId(1);
        return account;
    }


    //This private method is the same as below, but has a returning.
    //Not sure if it makes sense to take in a User to create a user?
    private void createTestUser(User user){
        String sql = "INSERT INTO users(user_id, username, password_hash) " +
                "VALUES (DEFAULT, ?, ?) RETURNING user_id";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, user.getUsername(), user.getPassword());
        rowSet.next();
        user.setId(rowSet.getLong("user_id"));
    }

    private void addUser(User user){
        String sql = "INSERT INTO users (user_id , username , password_hash) " +
                "VALUES (DEFAULT , ? , ? )";
        jdbcTemplate.update(sql, user.getUsername() , user.getPassword() );
    }

}
