package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeightTest {

    @Test
    void getWeight() {
        Weight weight = new Weight();
        Assertions.assertEquals(weight.getWeight(), 80);
    }

    @Test
    void testToString() {
        Weight weight = new Weight();
        Assertions.assertEquals(weight.toString(),"01012020  -  80.0");
    }
}