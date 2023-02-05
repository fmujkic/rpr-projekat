package ba.unsa.etf.rpr.projekat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
            weightsForUserByID = conn.prepareStatement("SELECT * FROM Weight WHERE UserID=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(int userID) {
        try {
            userByID.setInt(1, userID);
            ResultSet rs = userByID.executeQuery();
            if (!rs.next()) return null;
            return getUserFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), null);
        u.setWeights( getWeightsForUser(rs.getInt(1)));

        return u;
    }

    private List<Weight> getWeightsForUser(int userID) {
        try {
            weightsForUserByID.setInt(1, userID);
            ResultSet rs = weightsForUserByID.executeQuery();
            if (!rs.next()) return null;
            return getWeightsResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Weight> getWeightsResultSet(ResultSet rs) {
        ArrayList<Weight> result = new ArrayList<>();
        try{
        while (rs.next()) {
            Weight weight = getWeightFromResultSet(rs);
            result.add(weight);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Weight getWeightFromResultSet(ResultSet rs) throws SQLException {
        return new Weight(rs.getString(1), rs.getDouble(2));
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
