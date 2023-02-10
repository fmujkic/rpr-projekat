package ba.unsa.etf.rpr.projekat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class DAO {

    private static DAO instance;
    private Connection conn;
    public static int UserID = 0;
    public String config = "Resources/config.properties";
    Properties properties;

    private PreparedStatement userList, userByID, weightsForUserByID, addWeightForUser,setNewUserID,userExists, createUser;

    public static DAO getInstance() {
        if (instance == null) instance = new DAO();
        return instance;
    }

    private DAO() {
        try {
            properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(config);
            properties.load(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(properties.getProperty("dbURL"));
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
            userExists = conn.prepareStatement("SELECT userID FROM User WHERE UserName=? and Password=?");
            addWeightForUser = conn.prepareStatement("INSERT INTO Weight VALUES(?,?,?)");
            setNewUserID = conn.prepareStatement("SELECT MAX(userid)+1 FROM user");
            createUser = conn.prepareStatement("INSERT INTO User VALUES(?,?,?)");

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

    public ArrayList<Weight> getWeightsForUser(int userID) {
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

    private ArrayList<Weight> getWeightsResultSet(ResultSet rs) {
        ArrayList<Weight> result = new ArrayList<>();
        try{
            do {
                Weight weight = getWeightFromResultSet(rs);
                result.add(weight);
            }while (rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Weight getWeightFromResultSet(ResultSet rs) throws SQLException {
        return new Weight(rs.getString(1), rs.getDouble(3));
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


    public void addWeightForUser(int id, Weight weight) throws SQLException {
        addWeightForUser.setString(1, weight.getDate());
        addWeightForUser.setInt(2, id);
        addWeightForUser.setDouble(3, weight.getWeight());
        addWeightForUser.executeUpdate();

    }

    public int loginRegisterUser(String userName, String password)  {
        int userID = userExists(userName, password);
        if (userID == 0){
            userID = createUser(userName, password);
        }
        return userID;
    }

    private int createUser(String userName, String password) {
        ResultSet rs;
        int id = 0;
        try {
            rs = setNewUserID.executeQuery();
            id = rs.getInt(1);
            createUser.setInt(1,id);
            createUser.setString(2,userName);
            createUser.setString(3,password);
            createUser.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }

    private int userExists(String userName, String password)  {

        try {
            userExists.setString(1,userName);
            userExists.setString(2,password);
            ResultSet rs = userExists.executeQuery();
            if (!rs.next()) return 0;
            return rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
