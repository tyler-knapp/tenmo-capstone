package com.techelevator;

import com.techelevator.tenmo.auth.model.User;
import com.techelevator.tenmo.daos.AccountDAO;
import com.techelevator.tenmo.daos.JDBCAccountDAO;
import com.techelevator.tenmo.daos.JDBCTransferDAO;
import com.techelevator.tenmo.daos.TransferDAO;
import com.techelevator.tenmo.models.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JDBCTransferDAOIntegrationTest extends DAOIntegrationTest{

    private AccountDAO accountDAO;
    private TransferDAO transferDAO;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(getDataSource());
        accountDAO = new JDBCAccountDAO(jdbcTemplate);
        transferDAO = new JDBCTransferDAO(jdbcTemplate);
    }

    @Test
    public void retrieveTransferByTransferId(){
        Transfer transfer = getTransferById(3017);

        //Assert.assertTrue( transfer.getTransferId() = 3017);
    }


    //Can we retrieve a list of all transfers given our accountId?
    //Should we get our accountID from our username?
    //GET /transfers
    @Test
    public void getListOfAllTransfers(){
        List<Transfer> original_list = transferDAO.getListOfAllTransfersByAccountId(100);
    }


    //Can we add a transfer to the list of transfers?
    @Test
    public void addATransferToList(){
      //List<Transfer> originalList = getTransfer(1,3,50);

      //transferDAO.createTransfer(1,2,3);

        //Assert.assertTrue();
    }

    //Check into what exactly we need as our getTransfer() private method inputs
    private void addTransfer(Transfer transfer){
        Transfer newTransfer = getTransfer(1,2,3);

        String sql = "INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) \n" +
                "VALUES (DEFAULT, 2, 2, ?, ?, ?)";
        jdbcTemplate.update(sql, transfer.getAccountFrom() , transfer.getAccountTo() );
    }

    private Transfer getTransferById(int transferId){
        Transfer transfer = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount, a.username AS from_username, b.username AS to_username\n" +
                "FROM transfers " +
                "JOIN accounts ac ON transfers.account_from = ac.account_id " +
                "JOIN accounts bc ON transfers.account_to = bc.account_id " +
                "JOIN users a ON ac.user_id = a.user_id " +
                "JOIN users b ON bc.user_id = b.user_id " +
                "WHERE transfer_id = ?";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, transferId);

        if(row.next()){
            transfer = new Transfer();
            transfer.setTransferId(row.getInt("transfer_id"));
            transfer.setAccountTo(row.getInt("account_to"));
            transfer.setAccountFrom(row.getInt("account_from"));
            transfer.setTransferTypeId(row.getInt("transfer_type_id"));
            transfer.setAmount(row.getDouble("amount"));
            transfer.setTransferStatusId(row.getInt("transfer_status_id"));
            transfer.setUserTo(row.getString("to_username"));
            transfer.setUserFrom(row.getString("from_username"));
        }
        return transfer;
    }

    //What does getTransfer do for us?
    //Sets dummy data
    private Transfer getTransfer( int accountFrom, int accountTo, int amount){
        Transfer transfer = new Transfer();
        transfer.setTransferId(1); //Does this need to be set?
        transfer.setTransferTypeId(2);
        transfer.setAccountTo(accountFrom);
        transfer.setAccountTo(accountTo);
        transfer.setTransferStatusId(2);
        transfer.setAmount(amount);
        transfer.setUserFrom("Giver");
        transfer.setUserTo("Taker");
        return transfer;
    }

   }
