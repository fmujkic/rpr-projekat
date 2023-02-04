package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class DAO {

    private static DAO instance;
    private Connection conn;

    private PreparedStatement userList, userByID, weightsForUserByID;

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

        try {
            userList = conn.prepareStatement("SELECT * FROM User");
        } catch (SQLException e) {
            DBRefresh();
            try {
                userList = conn.prepareStatement("SELECT * FROM User");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            userByID = conn.prepareStatement("SELECT * FROM User WHERE UserID=?");
            weightsForUserByID = conn.prepareStatement("DELETE FROM grad WHERE drzava=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    private void DBRefresh() {
        Scanner enter;
        try {
            enter = new Scanner(new FileInputStream("baza.db.sql"));
            StringBuilder sqlQuery = new StringBuilder();
            while (enter.hasNext()) {
                sqlQuery.append(enter.nextLine());
                if ( sqlQuery.charAt( sqlQuery.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlQuery.toString());
                        sqlQuery = new StringBuilder();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            enter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
