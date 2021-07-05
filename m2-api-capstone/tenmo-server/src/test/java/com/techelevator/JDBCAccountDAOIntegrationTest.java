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

    @Test
    public void subtract_amount_from_account(){
        User user = getUser("testUser");
        addUser(user);
        //1002 is actually in our user table
        //Is there a way to incorporate dummy data here?
        //Should we do that?
        Account account = getAccountByUserId(1002 ,user);
        createNewTestAccount(account);

        double newBalance = accountDAO.withdrawAmount(1004, 100);

        Assert.assertTrue( account.getBalance() > newBalance);
    }

    @Test
    public void get_account_by_username() {
    }

//    private Account getAccount(int userId ){
//        Account testAccount = new Account();
//        //testAccount.setAccountId(accountId);
//        testAccount.setUserId(userId);
//        testAccount.setBalance(100);
//        return testAccount;
//    }

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
        //testUser.setId((long)100);
        //testUser.setActivated(true);
        return testUser;
    }

    private void createNewTestAccount(Account account){
        String sql = "INSERT INTO accounts(account_id, user_id, balance) " +
                "VALUES(DEFAULT, ?, ?) RETURNING account_id";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, account.getUserId(), account.getBalance());
        rowSet.next();
        account.setAccountId(rowSet.getInt("account_id"));

    }

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
