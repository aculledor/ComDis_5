/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_5.database;

import comdis_5.classes.Request;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author aculledor
 */
public interface ImpBDInterface {

    //Return the given nickname's friend list after inserting the given relationship
    ArrayList<String> addFriend(String nickname, String friend) throws SQLException;

    //Returns the source's pending requests after saving the new one in the database
    ArrayList<String> addRequest(String source, String destination) throws SQLException;

    //Returns true after saving it and retrieving it from the database
    ArrayList<String> addUser(String nickname, String password) throws SQLException;

    //Returns the list of the nickname's user friends after deleting the given friend relationship
    ArrayList<String> deleteFriend(String nickname, String friend) throws SQLException;

    //Returns the list of all saved requests after deleting the given one
    ArrayList<Request> deleteRequest(String source, String destination) throws SQLException;

    //Returns the list of saved users after deleting the given nickname's user
    ArrayList<ArrayList<String>> deleteUser(String nickname) throws SQLException;

    //Returns the list of the nickname's friends
    ArrayList<String> getFriends(String nickname) throws SQLException;

    //Returns the list of saved requests
    ArrayList<Request> getRequests() throws SQLException;

    //Returns the list of saved requests for the user 
    ArrayList<String> getRequestsFor(String destination) throws SQLException;

    //Returns the list of saved requests from the user 
    ArrayList<String> getRequestsFrom(String source) throws SQLException;

    //Returns the user data with the given nickname or null
    ArrayList<String> getUser(String nickname) throws SQLException;

    //Checks if the user exists in the DB
    Boolean isUser(String nickname) throws SQLException;

    //Checks if the two users are friends
    Boolean areFriends(String nickname, String friendNickname) throws SQLException;

    //hecks if the password is correct
    Boolean isUserPassword(String nickname, String password) throws SQLException;

    //Returns the list of saved users
    ArrayList<ArrayList<String>> getUsers() throws SQLException;
    
}
