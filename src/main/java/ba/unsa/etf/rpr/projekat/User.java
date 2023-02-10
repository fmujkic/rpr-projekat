package main.java.ba.unsa.etf.rpr.projekat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/***
 * Creating DTO for user.
 */
public class User implements Serializable {
    private int ID;
    private String userName, password;
    private List<Weight> weights;

    /***
     * User constructor without parameters.
     */
    public User() {
        this.ID = 0;
        this.userName = "defaultUser";
        this.password = "defaultPassword";
        this.weights = new ArrayList();
    }

    /***
     * User constructor with parameters.
     */
    public User(int ID, String userName, String password, List<Weight> weights) {
        this.ID = ID;
        this.userName = userName;
        this.password = password;
        this.weights = weights;
    }

    public User(int ID, String userName, String password) {
        this.ID = ID;
        this.userName = userName;
        this.password = password;
        this.weights = null;
    }

    public User(int ID, String userName) {
        this.ID = ID;
        this.userName = userName;
        this.password = "";
        this.weights = null;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Weight> getWeights() {
        return weights;
    }

    public void setWeights(List<Weight> weights) {
        this.weights = weights;
    }

}
