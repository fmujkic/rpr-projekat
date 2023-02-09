package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DAOTest {

    @Test
    void getInstance() {
        DAO dao = DAO.getInstance();
        Assertions.assertEquals(dao,DAO.getInstance());
    }

    @Test
    void getUser() {

    }

    @Test
    void getWeightsForUser() {
    }

    @Test
    void addWeightForUser() {
    }

    @Test
    void loginRegisterUser() {
    }
}