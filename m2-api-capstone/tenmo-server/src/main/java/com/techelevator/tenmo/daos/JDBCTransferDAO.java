package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.models.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JDBCTransferDAO implements  TransferDAO {

    private JdbcTemplate jdbcTemplate;

    public JDBCTransferDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer transferAddAmount(int transferTo, double amount) {
        
        return null;
    }

    @Override
    public Transfer transferWithdrawAmount(int transferFrom, double amount) {
        return null;
    }
}
