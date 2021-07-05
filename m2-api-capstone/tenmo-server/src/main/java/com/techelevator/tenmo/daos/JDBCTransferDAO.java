package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.auth.model.User;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCTransferDAO implements TransferDAO {

    private JdbcTemplate jdbcTemplate;
    private AccountDAO accountDAO;

    public JDBCTransferDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> getListOfAllTransfersByAccountId(int userId) {
        List<Transfer> transferList = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to , amount " +
                "FROM transfers " +
                "WHERE account_from = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userId);
        while (rows.next()) {
            transferList.add(mapRowToTransfer(rows));
        }
        return transferList;
    }

    @Override
    public List<Transfer> findAll() {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount, a.username AS from_username, b.username AS to_username " +
                "FROM transfers " +
                "JOIN accounts ac ON transfers.account_from = ac.account_id " +
                "JOIN accounts bc ON transfers.account_to = bc.account_id " +
                "JOIN users a ON ac.user_id = a.user_id " +
                "JOIN users b ON bc.user_id = b.user_id ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()){
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }


    //Maybe check out taking the (int userFromId, int userToId, double amount)
    @Override
    public Transfer createTransfer( int userFromId, int userToId, double amount ) {
        Transfer transfer = new Transfer();
        Integer newId;

        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (2, 2, ?, ?, ?) " +
                "RETURNING transfer_id";

        //Look into Query For Object? I don't know
            newId = jdbcTemplate.queryForObject(sql, Integer.class, userFromId, userToId, amount);

            transfer.setTransferId(newId);
//            if(rows.next()){
//                //transfer.setTransferId(rows.getInt("transfer_id"));
//                transfer.setTransferTypeId(rows.getInt("transfer_type_id"));
//                transfer.setTransferStatusId(rows.getInt("transfer_status_id"));
//                transfer.setAmount(rows.getDouble("amount"));
//                transfer.setAccountFrom(rows.getInt("account_from"));
//                transfer.setAccountTo(rows.getInt("account_to"));
//                //transfer.setUserFrom("from_username");
//                //transfer.setUserTo("to_username");
//            }

            //accountDAO.withdrawAmount(accountFrom, amount);
            //accountDAO.addAmount(accountTo, amount);

        return transfer;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        Transfer transfer = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount, a.username AS from_username, b.username AS to_username " +
                "FROM transfers " +
                "JOIN accounts ac ON transfers.account_from = ac.account_id " +
                "JOIN accounts bc ON transfers.account_to = bc.account_id " +
                "JOIN users a ON ac.user_id = a.user_id " +
                "JOIN users b ON bc.user_id = b.user_id " +
                "WHERE transfer_id = ?";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, transferId);
        if(row.next()){
            transfer = mapRowToTransfer(row);
        }
        return transfer;
    }

    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setAccountFrom(results.getInt("account_from"));
        transfer.setAccountTo(results.getInt("account_to"));
        transfer.setAmount(results.getDouble("amount"));
        transfer.setTransferTypeId(results.getInt("transfer_type_id"));
        transfer.setTransferStatusId(results.getInt("transfer_status_id"));
        try {
            transfer.setUserFrom(results.getString("from_username"));
            transfer.setUserTo(results.getString("to_username"));
        } catch (Exception e) {

        }

        return transfer;
    }


}
