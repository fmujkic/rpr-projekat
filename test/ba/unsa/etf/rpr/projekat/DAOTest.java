package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

class DAOTest {

    @Test
    void getInstance() {
        DAO dao = DAO.getInstance();
        Assertions.assertEquals(dao,DAO.getInstance());
    }

    @Test
    void getUser() {
        int userID = DAO.getInstance().loginRegisterUser("ime", "password");
        User user = DAO.getInstance().getUser(userID);
        Assertions.assertEquals(user.getUserName(),"ime");
    }

    @Test
    void getWeightsForUser() throws SQLException {
        int userID = DAO.getInstance().loginRegisterUser("ime", "password");
        DAO.getInstance().addWeightForUser(userID, new Weight ("12.12.2012.", 123));
        ArrayList<Weight> weights = DAO.getInstance().getWeightsForUser(userID);
        Assertions.assertEquals(weights.get(0).getWeight(),123);
    }

    @Test
    void addWeightForUser() throws SQLException {
        int userID = DAO.getInstance().loginRegisterUser("ime", "password");
        DAO.getInstance().addWeightForUser(userID, new Weight ("12.12.2012.", 123));
        ArrayList<Weight> weights = DAO.getInstance().getWeightsForUser(userID);
        Assertions.assertEquals(weights.get(0).getDate(),"12.12.2012.");
    }

    @Test
    void loginRegisterUser() {
        int userID = DAO.getInstance().loginRegisterUser("ime", "password");
        Assertions.assertEquals(userID,DAO.getInstance().loginRegisterUser("ime", "password"));

    }
}