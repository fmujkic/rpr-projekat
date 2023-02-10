package ba.unsa.etf.rpr.projekat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

/***
 * Data access object layer, singleton connection to database.
 */
public class DAO {

    private static DAO instance;
    private Connection conn;
    public static int UserID = 0;
    public String config = "Resources/config.properties";
    private Properties properties;
    private PreparedStatement userList, userByID, weightsForUserByID, addWeightForUser,setNewUserID,userExists, createUser;

    /***
     * Getting instance of a data access object.
     * @return Instance of DAO object.
     */
    public static DAO getInstance() {
        if (instance == null) instance = new DAO();
        return instance;
    }

    /***
     * Private constructor for DAO, forbids multiple DB connections, to not lock DB.
     */
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

    /***
     * Returns user for given ID;
     * @param userID ID for filtering the user
     * @return instance of an User object.
     */
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

    /***
     * Private method to extract user from result set.
     * @param rs ResultSet is given parameter.
     * @return returns Instance of an User object.
     * @throws SQLException of there is SQL issue throws SQLException .
     */
    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), null);
        u.setWeights( getWeightsForUser(rs.getInt(1)));
        return u;
    }

    /***
     * Gets list of weights for user.
     * @param userID ID for finding the User.
     * @return list of objects of type weights.
     */
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

    /***
     * Extracts list of weights from dataset.
     * @param rs data for extraction.
     * @return list of objects of type Weight.
     */
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

    /***
     * Extracts one instance of weight from dataset.
     * @param rs data for extraction.
     * @return Object of type Weight.
     */
    private Weight getWeightFromResultSet(ResultSet rs) throws SQLException {
        return new Weight(rs.getString(1), rs.getDouble(3));
    }

    /***
     * Method for recreating database from dump.
     */
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


    /***
     * Adds weight on weight list for given User.
     * @param id for given user.
     * @param weight to add to user.
     * @throws SQLException if there is DB issue
     */
    public void addWeightForUser(int id, Weight weight) throws SQLException {
        addWeightForUser.setString(1, weight.getDate());
        addWeightForUser.setInt(2, id);
        addWeightForUser.setDouble(3, weight.getWeight());
        addWeightForUser.executeUpdate();

    }

    /***
     * Metod for login user or registration if user does not exist.
     * @param userName name of the user.
     * @param password users password.
     * @return Id of logged user.
     */
    public int loginRegisterUser(String userName, String password)  {
        int userID = userExists(userName, password);
        if (userID == 0){
            userID = createUser(userName, password);
        }
        return userID;
    }


    /***
     * Method for creating new user.
     * @param userName parameter for userName of new user.
     * @param password parameter for password of new user.
     * @return id of new user.
     */
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

    /***
     * Checks if user exist in database.
     * @param userName checking if user with this name exists.
     * @param password checking if user with this name exists.
     * @return
     */
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
