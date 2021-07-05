package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.models.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCAccountDAO implements AccountDAO{

    private JdbcTemplate jdbcTemplate;

    public JDBCAccountDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccount(String username) {
        Account account = null;

        String sql = "SELECT account_id , accounts.user_id , balance FROM accounts " +
                "JOIN users ON accounts.user_id = users.user_id " +
                "WHERE username = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, username);

        if (rows.next()) {
            account = new Account();
            account.setAccountId(rows.getInt("account_id"));
            account.setUserId(rows.getInt("user_id"));
            account.setBalance(rows.getDouble("balance"));
        }
        return account;
    }

    @Override
    public double addAmount(String username, double amount) {
        Account account  = getAccount(username);

        String sql = "UPDATE accounts " +
                "SET balance = (balance + ?) " +
                "WHERE account_id = (SELECT account_id FROM accounts " +
                "WHERE user_id = (SELECT user_id FROM users WHERE username = ?))";

        try{
            jdbcTemplate.update(sql, amount, username);
        } catch (DataAccessException e){
            System.out.println("Error accessing Data");
        }
        return account.getBalance();
    }

    @Override
    public double withdrawAmount(String username, double amount) {
        Account account  = getAccount(username);
        String sql = "UPDATE accounts " +
                "SET balance = (balance - ?) " +
                "WHERE account_id = (SELECT account_id FROM accounts " +
                "WHERE user_id = ?)";
        try{
            jdbcTemplate.update(sql, amount, username);
        } catch (DataAccessException e){
            System.out.println("Error accessing Data");
        }
        return account.getBalance();
    }
//
//    @Override
//    public double addToAccount(int userToId, double amount) {
//        Account account = findAccountById(userToId);
//        //double newAccountBalanace = account.getBalance()
//        return 0;
//    }
//
//    @Override
//    public double subtractFromAccount(int userFrom, double amount) {
//        return 0;
//    }


    private Account accountsMapRow(SqlRowSet row){
        Account account = new Account();
        account.setAccountId(row.getInt("account_id"));
        account.setUserId(row.getInt("user_id"));
        account.setBalance(row.getDouble("balance"));
        return account;
    }

    @Override
    public Account findAccountById(int id) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM accounts WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            account = accountsMapRow(results);
        }
        return account;
    }

//    @Override
//    public double subtractFromAccount(String userFromUserName, double amount) {
//        return 0;
//    }

}
