/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_4;

import comdis_4.Request;
import comdis_4.User;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author aculledor
 */
public interface ImpBDInterface {

    //Return the given nickname's friend list after inserting the given relationship
    ArrayList<String> addFriend(String nickname, String friend) throws SQLException;

    //Returns the request after saving it in the database
    ArrayList<Request> addRequest(String source, String destination) throws SQLException;

    //Returns the given user after saving it and retrieving it from the database
    User addUser(String nickname, String password) throws SQLException;

    //Returns the given user after saving it and retrieving it from the database
    User addUser(User user) throws SQLException;

    //Returns the list of friends after deleting the given friend relationship
    ArrayList<String> deleteFriend(String nickname, String friend) throws SQLException;

    //Returns the list of saved requests after deleting the given one
    ArrayList<Request> deleteRequest(String nickname, String destination) throws SQLException;

    //Returns the list of saved users after deleting the given nickname's user
    ArrayList<User> deleteUser(String nickname) throws SQLException;

    //Returns the list of the nickname's friends
    ArrayList<String> getFriends(String nickname) throws SQLException;

    //Returns the list of saved requests
    ArrayList<Request> getRequests() throws SQLException;

    //Returns the list of saved requests where the given nickname is the destination user 
    ArrayList<Request> getDestinationRequests(String destination) throws SQLException;

    //Returns the list of saved requests where the given nickname is the source user 
    ArrayList<Request> getSourceRequests(String source) throws SQLException;

    //Returns the user with the given nickname or null
    User getUser(String nickname) throws SQLException;

    //Returns the given user after saving it and retrieving it from the database
    Boolean isUser(String nickname) throws SQLException;

    //Returns the given user after saving it and retrieving it from the database
    Boolean isPassword(String nickname, String password) throws SQLException;

    //Returns the list of saved users
    ArrayList<User> getUsers() throws SQLException;
    
}
