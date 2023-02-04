package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {

    private static DAO instance;
    private Connection conn;

    public static DAO getInstance() {
        if (instance == null) instance = new DAO();
        return instance;
    }
    private DAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
