/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_4.database;

import comdis_4.classes.Request;
import comdis_4.classes.Request;
import comdis_4.classes.User;
import comdis_4.classes.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author aculledor
 */
public class ImpBD implements ImpBDInterface {
    
    private Connection database = null;
    String pass = "HOLA MUNDOOO";

    public ImpBD() throws SQLException{
        try {
            database = DriverManager.getConnection("jdbc:derby://localhost:1527/Chat_P2P", "Abraham", "1234");
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public ArrayList<String> addFriend(String nickname, String friend) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            mystatement.execute("INSERT INTO Abraham.Friends (Nickname, Friend) VALUES ('"+nickname+"', '"+friend+"')");
            mystatement.execute("INSERT INTO Abraham.Friends (Nickname, Friend) VALUES ('"+friend+"', '"+nickname+"')");
            return getFriends(nickname);
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public ArrayList<Request> addRequest(String source, String destination) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            mystatement.execute("INSERT INTO Abraham.Requests (Source, Destination) VALUES ('"+source+"', '"+destination+"')");
            return getSourceRequests(source);
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public User addUser(String nickname, String password) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            mystatement.execute("INSERT INTO Abraham.Users (Nickname, Password) VALUES ('"+nickname+"', '"+password+"')");
            return getUser(nickname);
        } catch (SQLException e) {
            throw e;
        }
    }
    
    @Override
    public User addUser(User user) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            mystatement.execute("INSERT INTO Abraham.Users (Nickname, Password) VALUES ('"+user.getNickname()+"', '"+user.getPassword()+"')");
            if(user.getFriends() != null && !user.getFriends().isEmpty()){
                for(String friend: user.getFriends()){
                    mystatement.execute("INSERT INTO Abraham.Friends (Nickname, Friend) VALUES ('"+user.getNickname()+"', '"+friend+"')");
                }
            }
            return getUser(user.getNickname());
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public ArrayList<String> deleteFriend(String nickname, String friend) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            mystatement.execute("DELETE FROM Abraham.Friends WHERE nickname = '"+nickname+"' AND friend = '"+friend+"'");
            mystatement.execute("DELETE FROM Abraham.Friends WHERE nickname = '"+friend+"' AND friend = '"+nickname+"'");
            return getFriends(nickname);
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public ArrayList<Request> deleteRequest(String nickname, String destination) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            mystatement.execute("DELETE FROM Abraham.Requests WHERE Source = '"+nickname+"' AND Destination = '"+nickname+"'");
            return this.getRequests();
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public ArrayList<User> deleteUser(String nickname) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            mystatement.execute("DELETE FROM Abraham.Users WHERE nickname = '"+nickname+"'");
            mystatement.execute("DELETE FROM Abraham.Friends WHERE nickname = '"+nickname+"' OR friend = '"+nickname+"'");
            mystatement.execute("DELETE FROM Abraham.Requests WHERE Source = '"+nickname+"' OR Destination = '"+nickname+"'");
            return this.getUsers();
        } catch (SQLException e) {
            throw e;
        }
    }

    
    @Override
    public ArrayList<String> getFriends(String nickname) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            ResultSet myresult = mystatement.executeQuery("SELECT * FROM Abraham.Friends where nickname = '"+nickname+"'");
            ArrayList<String> friends = new ArrayList<>();
            while(myresult.next()){
                friends.add(myresult.getString("Friend"));
            }
            return friends;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public ArrayList<Request> getRequests() throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            ResultSet myresult = mystatement.executeQuery("SELECT * FROM Abraham.Request");
            ArrayList<Request> requests = new ArrayList<>();
            Request request;
            String source, destination;
            while(myresult.next()){
                source = myresult.getString("Source");
                destination = myresult.getString("Destination");
                request = new Request(source, destination);
                requests.add(request);
            }
            return requests;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public ArrayList<Request> getDestinationRequests(String destination) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            ResultSet myresult = mystatement.executeQuery("SELECT * FROM Abraham.Requests WHERE Destination = '"+destination+"'");
            Request request;
            ArrayList<Request> requests = new ArrayList<>();
            String sour, dest;
            while(myresult.next()){
                sour = myresult.getString("Source");
                dest = myresult.getString("Destination");
                request = new Request(sour, dest);
                requests.add(request);
            }
            return requests;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public ArrayList<Request> getSourceRequests(String source) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            ResultSet myresult = mystatement.executeQuery("SELECT * FROM Abraham.Requests WHERE Source = '"+source+"'");
            Request request;
            ArrayList<Request> requests = new ArrayList<>();
            String sour, dest;
            while(myresult.next()){
                sour = myresult.getString("Source");
                dest = myresult.getString("Destination");
                request = new Request(sour, dest);
                requests.add(request);
            }
            return requests;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public User getUser(String nickname) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            ResultSet myresult = mystatement.executeQuery("SELECT * FROM Abraham.Users WHERE nickname = '"+nickname+"'");
            User user = null;
            String nick, pass;
            ArrayList<String> friends;
            if(myresult.next()){
                nick = myresult.getString("Nickname");
                pass = myresult.getString("Password");
                friends = this.getFriends(nick);
                user = new User(nick, pass, friends);
            }
            return user;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public Boolean isUser(String nickname) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            ResultSet myresult = mystatement.executeQuery("SELECT * FROM Abraham.Users WHERE nickname = '"+nickname+"'");
            return myresult.next();
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public Boolean isPassword(String nickname, String password) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            ResultSet myresult = mystatement.executeQuery("SELECT * FROM Abraham.Users WHERE nickname = '"+nickname+"'");
            
            if(myresult.next()){
                pass = myresult.getString("Password");
                return password.equals(pass);
            }
            return false;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public ArrayList<User> getUsers() throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            ResultSet myresult = mystatement.executeQuery("SELECT * FROM Abraham.Users");
            ArrayList<User> users = new ArrayList<>();
            User user;
            ArrayList<String> friends;
            String nick, pass;
            while(myresult.next()){
                nick = myresult.getString("Nickname");
                pass = myresult.getString("Password");
                friends = this.getFriends(nick);
                user = new User(nick, pass, friends);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw e;
        }
    }
}
