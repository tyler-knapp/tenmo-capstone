package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCTransferDAO implements  TransferDAO {

    private JdbcTemplate jdbcTemplate;
    private AccountDAO accountDAO;

    public JDBCTransferDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer addTransfer( int transferFrom, int transferTo, double amount ) {
        Transfer transfer = null;
        String sql = "INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (DEFAULT, 2, 2, ?, ?, ?) RETURNING transfer_id";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, transferFrom, transferTo, amount);
        if(rows.next()){
            transfer = new Transfer();
            transfer.setAccountFrom(rows.getInt("account_from"));
            transfer.setAccountTo(rows.getInt("account_to"));
            transfer.setAmount(rows.getDouble("amount"));
            transfer.setTransactionId(rows.getInt("transfer_id"));
            transfer.setTransferTypeId(rows.getInt("transfer_type_id"));
            transfer.setTransferStatusId(rows.getInt("transfer_status_id"));

        }

        withdrawAmount(transferFrom, amount);
        addAmount(transferTo, amount);

        return transfer;
    }

    @Override
    public double addAmount(int transferTo, double amount) {
        Account account  = accountDAO.findAccountById(transferTo);
        double updatedBalance = account.getBalance() + amount;
        String sql = "UPDATE accounts SET balance = (balance + ?) WHERE user_id = ?";
        try{
            jdbcTemplate.update(sql, updatedBalance, transferTo);
        } catch (DataAccessException e){
            System.out.println("Error accessing Data");
        }
        return account.getBalance();
    }

    @Override
    public double withdrawAmount(int transferFrom, double amount) {
        Account account  = accountDAO.findAccountById(transferFrom);
        double updatedBalance = account.getBalance() + amount;
        String sql = "UPDATE accounts SET balance = (balance - ?) WHERE user_id = ?";
        try{
            jdbcTemplate.update(sql, updatedBalance, transferFrom);
        } catch (DataAccessException e){
            System.out.println("Error accessing Data");
        }
        return account.getBalance();
    }


}
