package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getUserName() {
        User user = new User();
        Assertions.assertEquals(user.getUserName(), "defaultUser");
    }

    @Test
    void getPassword() {
        User user = new User();
        Assertions.assertEquals(user.getPassword(), "defaultPassword");
    }

    @Test
    void getWeights() {
        User user = new User();
        Assertions.assertNotEquals(user.getWeights(), null);
    }
}