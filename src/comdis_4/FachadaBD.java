/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_4;

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
public class FachadaBD {
    
    private Connection database = null;
    private Statement mystatement = null;
    private ResultSet myresult=null;

    public FachadaBD() throws SQLException{
        try {
            database = DriverManager.getConnection("jdbc:derby://localhost:1527/Chat_P2P", "Abraham", "1234");
            mystatement = database.createStatement();
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public ArrayList<User> getUsers() throws SQLException{
        try {
            myresult = mystatement.executeQuery("SELECT * FROM Abraham.Users");
            ArrayList<User> users = new ArrayList<>();
            User user;
            ArrayList<String> friends;
            while(myresult.next()){
                String nick = myresult.getString("Nickname");
                String pass = myresult.getString("Password");
                friends = this.getFriends(nick);
                user = new User(nick, pass, friends);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public User getUser(String nickname) throws SQLException{
        try {
            myresult = mystatement.executeQuery("SELECT * FROM Abraham.Users WHERE nickname = '"+nickname+"'");
            User user = null;
            ArrayList<String> friends;
            while(myresult.next()){
                String nick = myresult.getString("Nickname");
                String pass = myresult.getString("Password");
                friends = this.getFriends(nick);
                user = new User(nick, pass, friends);
            }
            return user;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public User addUsers(User user) throws SQLException{
        try {
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
    
    public ArrayList<User> deleteUsers(String nickname) throws SQLException{
        try {
            mystatement.execute("DELETE FROM Abraham.Users WHERE nickname = '"+nickname+"'");
            mystatement.execute("DELETE FROM Abraham.Friends WHERE nickname = '"+nickname+"' OR friend = '"+nickname+"'");
            return this.getUsers();
        } catch (SQLException e) {
            throw e;
        }
    }

    public ArrayList<String> getFriends(String nickname) throws SQLException{
        try {
            myresult = mystatement.executeQuery("SELECT * FROM Abraham.Friends where nickname = '"+nickname+"'");
            ArrayList<String> friends = new ArrayList<>();
            while(myresult.next()){
                friends.add(myresult.getString("Friend"));
            }
            return friends;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public User addFriend(String nickname, String friend) throws SQLException{
        try {
            mystatement.execute("INSERT INTO Abraham.Friends (Nickname, Friend) VALUES ('"+nickname+"', '"+friend+"')");
            mystatement.execute("INSERT INTO Abraham.Friends (Nickname, Friend) VALUES ('"+friend+"', '"+nickname+"')");
            return getUser(nickname);
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public ArrayList<User> deleteFriend(String nickname, String friend) throws SQLException{
        try {
            mystatement.execute("DELETE FROM Abraham.Friends WHERE nickname = '"+nickname+"' AND friend = '"+nickname+"'");
            return this.getUsers();
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public ArrayList<Request> getRequests() throws SQLException{
        try {
            myresult = mystatement.executeQuery("SELECT * FROM Abraham.Request");
            ArrayList<Request> requests = new ArrayList<>();
            Request request;
            while(myresult.next()){
                String source = myresult.getString("Source");
                String destination = myresult.getString("Destination");
                request = new Request(source, destination);
                requests.add(request);
            }
            return requests;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public Request getRequests(String source) throws SQLException{
        try {
            myresult = mystatement.executeQuery("SELECT * FROM Abraham.Requests WHERE Source = '"+source+"'");
            Request req = null;
            while(myresult.next()){
                String sour = myresult.getString("Source");
                String requ = myresult.getString("Destination");
                req = new Request(sour, requ);
            }
            return req;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public Request addRequest(String source, String destination) throws SQLException{
        try {
            mystatement.execute("INSERT INTO Abraham.Requests (Source, Destination) VALUES ('"+source+"', '"+destination+"')");
            return getRequests(source);
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public ArrayList<Request> deleteRequest(String nickname, String destination) throws SQLException{
        try {
            mystatement.execute("DELETE FROM Abraham.Requests WHERE Source = '"+nickname+"' AND Destination = '"+nickname+"'");
            return this.getRequests();
        } catch (SQLException e) {
            throw e;
        }
    }
}
