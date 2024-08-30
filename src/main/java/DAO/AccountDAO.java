/* You will need to design and create your own DAO classes from scratch. 
You should refer to prior mini-project lab examples and course material for guidance.

Please refrain from using a 'try-with-resources' block when connecting to your database. 
The ConnectionUtil provided uses a singleton, and using a try-with-resources will cause issues in the tests.
*/

package DAO;

import Util.ConnectionUtil;
import Model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO{
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {        
            String sql = "INSERT INTO account (username, password) VALUES(?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                int newId = rs.getInt(1);
                account.setAccount_id(newId);
            }

            return account;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountbyUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account where username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);            

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),                        
                        rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        };
        return null;
    }

    public Account getAccountbyId(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account where account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);            

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),                        
                        rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        };
        return null;
    }
}