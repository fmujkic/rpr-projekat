package sample;

import java.util.List;

public class User {
    int ID;
    String userName, password;
    List<Weight> weights;
    public User(int ID, String userName, String password, List<Weight> weights) {
        this.ID = ID;
        this.userName = userName;
        this.password = password;
        this.weights = weights;
    }

    public void setWeights(List<Weight> weights) {
        this.weights = weights;
    }
}
