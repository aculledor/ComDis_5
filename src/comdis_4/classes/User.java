/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_4.classes;

import comdis_4.client.ClientInterface;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author aculledor
 */
public class User implements Serializable{
    private String nickname;
    private String password;
    private ClientInterface proxy;
    private HashMap<String, ClientInterface> friendsProxys;
    private ArrayList<String> friendRequests;

    public User() {
    }

    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
        this.proxy = null;
        this.friendsProxys = null;
        this.friendRequests = null;
    }

    public String getNickname() {
        return nickname;
    }

    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public HashMap<String, ClientInterface> getFriendsProxys() {
        return friendsProxys;
    }

    public User setFriendsProxys(HashMap<String, ClientInterface> friendsProxys) {
        this.friendsProxys = friendsProxys;
        return this;
    }

    public ClientInterface getProxy() {
        return proxy;
    }

    public User setProxy(ClientInterface myProxy) {
        this.proxy = myProxy;
        return this;
    }

    public ArrayList<String> getFriendRequests() {
        return friendRequests;
    }

    public User setFriendRequests(ArrayList<String> friendRequests) {
        this.friendRequests = friendRequests;
        return this;
    }
    
    

    @Override
    public String toString() {
        String text = "\nNickname: "+nickname
                +". Password: "+password
                +"\nFriends:\n";
                if(friendsProxys != null && !friendsProxys.isEmpty()){
                    for(String friend : friendsProxys.keySet()){
                        text = text + "\t-> "+friend+"\n";
                    }
                }
                text = text +"\nFriendship request:\n";
                if(friendRequests != null && !friendRequests.isEmpty()){
                    for(String friend : friendRequests){
                        text = text + "\t-> "+friend+"\n";
                    }
                }
        text = text +"\n";
        return text; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }
 
}
