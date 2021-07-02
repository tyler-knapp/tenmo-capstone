import com.techelevator.DAOIntegrationTest;
import com.techelevator.tenmo.auth.dao.JdbcUserDAO;
import com.techelevator.tenmo.auth.dao.UserDAO;
import com.techelevator.tenmo.auth.model.User;
import com.techelevator.tenmo.daos.JDBCAccountDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JDBCUserDAOIntegrationTest extends DAOIntegrationTest {

    private UserDAO userDAO;
    private JdbcTemplate jdbcTemplate;


    @Before
    public void setup(){
        jdbcTemplate = new JdbcTemplate(getDataSource());
        userDAO = new JdbcUserDAO(jdbcTemplate);
    }

    @Test
    public void retrieve_list_of_all_users() {
        List<User> originalList = userDAO.findAll();
        addUser(new User(1L, "Test", "TestPassword" , "Test"));

        List<User> newUser = userDAO.findAll();

        Assert.assertEquals(originalList.size() + 1 , newUser.size());

    }

    private void addUser(User user){
        String sql = "INSERT INTO users (user_id , username , password_hash) " +
                "VALUES (DEFAULT , ? , ? )";
        jdbcTemplate.update(sql, user.getUsername() , user.getPassword() );
    }
}
