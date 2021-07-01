package com.techelevator;

import com.techelevator.tenmo.daos.AccountDAO;
import com.techelevator.tenmo.daos.JDBCAccountDAO;
import com.techelevator.tenmo.models.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class JDBCAccountDAOIntegrationTest extends DAOIntegrationTest{

    private AccountDAO accountDAO;
    private JdbcTemplate jdbcTemplate;




    @Before
    public void setup(){
        jdbcTemplate = new JdbcTemplate(getDataSource());
        accountDAO = new JDBCAccountDAO(jdbcTemplate);



 //       String sqlInsertAccount = "INSERT INTO accounts(account_id, user_id, balance)" +
 //               "VALUES(DEFAULT, (SELECT user_id FROM users WHERE username = ?), ?)";

 //       jdbcTemplate.update(sqlInsertAccount , TEST_USERNAME );


    }

    @Test
    public void list_accounts_by_username() {
        List<Account> originalAccount = accountDAO.getAccount("user");
        addAccount(new Account(0, 1, 1000.00 ));

        List<Account> newAccount = accountDAO.getAccount("user");

        Assert.assertEquals(originalAccount.size() + 1, newAccount.size());
        Account account = testAccount(0, 1 , 1000.00);


    }

    private void addAccount(Account account){
        String sql = "INSERT INTO accounts(account_id, user_id, balance)" +
                "VALUES(DEFAULT, (SELECT user_id FROM users WHERE username = ?), ?)";
        jdbcTemplate.update(sql, "user", account.getBalance());
    }

    private Account testAccount(int accountId , int userId, double balance ){
        Account testAccount = new Account();
        testAccount.setAccountId(accountId);
        testAccount.setUserId(userId);
        testAccount.setBalance(balance);
        //testAccount.setUsername(username);
        return testAccount;
    }



}
