package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.models.Account;
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
    public List<Account> getAccountBalance(String username) {
        String sql = "SELECT balance FROM accounts" +
                "JOIN users ON accounts.user_id = users.user_id" +
                "WHERE username = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, username);

        List<Account> accounts = new ArrayList<Account>();

        while (rows.next()) {
            accounts.add(accountsMapRow(rows));
        }
        return accounts;
    }

    private Account accountsMapRow(SqlRowSet row){
        Account account = new Account();
        account.setAccountId(row.getInt("account_id"));
        account.setUserId(row.getInt("user_id"));
        account.setBalance(row.getDouble("balance"));
        account.setUsername(row.getString("username"));
        return account;
    }
}
