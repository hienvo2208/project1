package com.javafx.librarian.dao;

import com.javafx.librarian.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    private static AccountDao instance;

    private AccountDao() {
    }

    public static AccountDao getInstance() {
        if (instance == null) {
            instance = new AccountDao();
        }
        return instance;
    }

    public List<Account> getAllUsers() {
        List<Account> users = new ArrayList<>();

        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select * from tbaccount";

        try {
            assert connection != null;
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Account user = new Account(rs.getString("idaccount"), rs.getString("password"), rs.getInt("idper"), "","");
                users.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return users;
    }

    public List<String> getAllUserKeys() {
        List<String> users = new ArrayList<>();

        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select idaccount from tbaccount";

        try {
            assert connection != null;
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                users.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return users;
    }

    public List<Account> getUserNoOwner() {
        List<Account> users = new ArrayList<>();

        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select c.idaccount, c.password, c.idper from (select a.idaccount, a.password, a.idper, b.madocgia from tbaccount as a left join tbdocgia as b on a.idaccount = b.idaccount where a.idper=1 and a.record_status=1) as c where c.madocgia is null;";

        try {
            assert connection != null;
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Account user = new Account(rs.getString("idaccount"), rs.getString("password"), rs.getInt("idper"), "","");
                users.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return users;
    }

    public void addUser(Account user) {
        Connection connection = JDBCConnection.getJDBCConnection();

//        String sql = "INSERT INTO USER(Username, Password, Email) VALUES (?,?,?)";
        String sql = "INSERT INTO tbaccount(idaccount, password, idper) VALUES (?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getIdper());

            int rs = preparedStatement.executeUpdate();
            System.out.println(rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Account getUser(String username, String password) {
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "SELECT idper FROM tbaccount WHERE idaccount=? and password=? limit 1";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();
            rs.last();

            if(rs.getInt("idper")==1){
                sql = "SELECT * FROM tbaccount as a inner join tbdocgia as b on a.idaccount=b.idaccount WHERE a.idaccount=? and a.password=? limit 1";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                rs = preparedStatement.executeQuery();
                rs.last();
                return new Account(rs.getString("idaccount"), rs.getString("password"),
                        rs.getInt("idper"), rs.getString("tendocgia"), rs.getString("email"));
            } else {
                sql = "SELECT * FROM tbaccount as a inner join tbadmin as b on a.idaccount=b.idaccount WHERE a.idaccount=? and a.password=? limit 1";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                rs = preparedStatement.executeQuery();
                rs.last();
                return new Account(rs.getString("idaccount"), rs.getString("password"),
                        rs.getInt("idper"), rs.getString("hoten"), rs.getString("email"));
            }

//            if (rs.next()) {
//            }
        } catch (SQLException e) {

        }
        return null;
    }

    public Account getUserById(String username) {
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "SELECT * FROM tbaccount WHERE idaccount=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return new Account(rs.getString("idaccount"), rs.getString("Password"), rs.getInt("idper"), "", "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkCreateUser(String username, String email) {
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "SELECT * FROM tbaccount WHERE idaccount=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            rs.last();
            return !(rs.getRow() > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public int editUser(Account user) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection();) {
            PreparedStatement ps = conn.prepareStatement("update tbaccount set password=?, idper=? where idaccount=?");
            ps.setString(1, user.getPassword());
            ps.setInt(2, user.getIdper());
            ps.setString(3, user.getUsername());
            res = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}

