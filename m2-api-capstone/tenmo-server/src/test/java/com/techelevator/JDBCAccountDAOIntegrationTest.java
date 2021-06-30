package com.techelevator;

import com.techelevator.tenmo.daos.AccountDAO;
import com.techelevator.tenmo.daos.JDBCAccountDAO;
import com.techelevator.tenmo.models.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class JDBCAccountDAOIntegrationTest extends DAOIntegrationTest{

    private AccountDAO accountDAO;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup(){
        accountDAO = new JDBCAccountDAO(jdbcTemplate);
        jdbcTemplate = new JdbcTemplate(getDataSource());
    }

    @Test
    public void list_balance_by_username(){
        List<Account> originalAccount = accountDAO.getAccountBalance("username");
        addAccount(new Account(0, 1, 1000.00 , "username"));

        List<Account> newAccount = accountDAO.getAccountBalance("username");

        Assert.assertEquals(originalAccount.size() + 1, newAccount.size());


    }

    private void addAccount(Account account){
        String sql = "INSERT INTO accounts(account_id, user_id, balance)" +
                "VALUES(DEFAULT, (SELECT user_id FROM users WHERE username = ?), ?)";
        jdbcTemplate.update(sql, account.getUserId(), account.getBalance());
    }



}
