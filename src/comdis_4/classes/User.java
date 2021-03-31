/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_4.classes;

import comdis_4.client.ClientImplementation;
import comdis_4.client.ClientInterface;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author aculledor
 */
public class User implements Serializable{
    private String nickname;
    private String password;
    private ArrayList<String> friends;
    private ClientInterface myProxy;
    private ArrayList<ClientInterface> friendsProxys;

    public User() {
    }

    public User(String nickname, String password, ArrayList<String> friends) {
        this.nickname = nickname;
        this.password = password;
        this.friends = friends;
        this.friendsProxys = null;
    }

    public User(String nickname, String password, ArrayList<String> friends, ArrayList<ClientInterface> friendsProxys) {
        this.nickname = nickname;
        this.password = password;
        this.friends = friends;
        this.friendsProxys = friendsProxys;
    }

    public User(String nickname, String password, ArrayList<String> friends, ClientInterface myProxy, ArrayList<ClientInterface> friendsProxys) {
        this.nickname = nickname;
        this.password = password;
        this.friends = friends;
        this.myProxy = myProxy;
        this.friendsProxys = friendsProxys;
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

    public ArrayList<String> getFriends() {
        return friends;
    }

    public User setFriends(ArrayList<String> friends) {
        this.friends = friends;
        return this;
    }

    public User addFriends(String friend) {
        this.friends.add(friend);
        return this;
    }

    public User removeFriends(String friend) {
        this.friends.remove(friend);
        return this;
    }

    public User setFriendsProxys(ArrayList<ClientInterface> friendsProxys) {
        this.friendsProxys = friendsProxys;
        return this;
    }

    public User addFriendsProxys(ClientImplementation friendProxys) {
        this.friendsProxys.add(friendProxys);
        return this;
    }

    public User removeFriendsProxys(ClientImplementation friendProxys) {
        this.friendsProxys.remove(friendProxys);
        return this;
    }

    public ClientInterface getMyProxy() {
        return myProxy;
    }

    public User setMyProxy(ClientInterface myProxy) {
        this.myProxy = myProxy;
        return this;
    }
    
    

    @Override
    public String toString() {
        String text = "\nNickname: "+nickname
                +". Password: "+password
                +"\nFriends:\n";
        if(friends != null && !friends.isEmpty()){
            for(String friend : friends){
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
