/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_4.database;

import comdis_4.classes.Request;
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
            mystatement.execute("DELETE FROM Abraham.Requests WHERE Source = '"+friend+"' AND Destination = '"+nickname+"'");
            return getFriends(nickname);
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public ArrayList<String> addRequest(String source, String destination) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            mystatement.execute("INSERT INTO Abraham.Requests (Source, Destination) VALUES ('"+source+"', '"+destination+"')");
            return getRequestsFrom(source);
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public ArrayList<String> addUser(String nickname, String password) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            mystatement.execute("INSERT INTO Abraham.Users (Nickname, Password) VALUES ('"+nickname+"', '"+password+"')");
            return getUser(nickname);
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
    public ArrayList<Request> deleteRequest(String source, String destination) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            mystatement.execute("DELETE FROM Abraham.Requests WHERE Source = '"+source+"' AND Destination = '"+destination+"'");
            return this.getRequests();
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public ArrayList<ArrayList<String>> deleteUser(String nickname) throws SQLException{
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
            ResultSet myresult = mystatement.executeQuery("SELECT * FROM Abraham.Requests");
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
    public ArrayList<String> getRequestsFor(String destination) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            ResultSet myresult = mystatement.executeQuery("SELECT * FROM Abraham.Requests WHERE Destination = '"+destination+"'");
            ArrayList<String> requests = new ArrayList<>();
            String sour;
            while(myresult.next()){
                sour = myresult.getString("Source");
                requests.add(sour);
            }
            return requests;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public ArrayList<String> getRequestsFrom(String source) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            ResultSet myresult = mystatement.executeQuery("SELECT * FROM Abraham.Requests WHERE Source = '"+source+"'");
            ArrayList<String> requests = new ArrayList<>();
            String dest;
            while(myresult.next()){
                dest = myresult.getString("Destination");
                requests.add(dest);
            }
            return requests;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public ArrayList<String> getUser(String nickname) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            ResultSet myresult = mystatement.executeQuery("SELECT * FROM Abraham.Users WHERE nickname = '"+nickname+"'");
            ArrayList<String> aux = new ArrayList<>();
            if(myresult.next()){
                aux.add(myresult.getString("Nickname"));
                aux.add(myresult.getString("Password"));
                return aux;
            }
            return null;
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
    public Boolean isUserPassword(String nickname, String password) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            ResultSet myresult = mystatement.executeQuery("SELECT * FROM Abraham.Users WHERE nickname = '"+nickname+"'");
            
            //No need to check nickname since we use it to find the user
            if(myresult.next()){
                return password.equals(myresult.getString("Password"));
            }
            return false;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public Boolean areFriends(String nickname, String friendNickname) throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            ResultSet myresult = mystatement.executeQuery("SELECT * FROM Abraham.Friends WHERE nickname = '"+nickname+"' AND friend = '"+friendNickname+"'");
            return myresult.next();
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    @Override
    public ArrayList<ArrayList<String>> getUsers() throws SQLException{
        try {
            Statement mystatement = database.createStatement();
            ResultSet myresult = mystatement.executeQuery("SELECT * FROM Abraham.Users");
            ArrayList<ArrayList<String>> users = new ArrayList<>();
            ArrayList<String> aux;
            while(myresult.next()){
                aux = new ArrayList<>();
                aux.add(myresult.getString("Nickname"));
                aux.add(myresult.getString("Password"));
                users.add(aux);
            }
            return users;
        } catch (SQLException e) {
            throw e;
        }
    }
}
