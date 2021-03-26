/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_4;

import java.util.ArrayList;

/**
 *
 * @author aculledor
 */
public class User {
    private String nickname;
    private String password;
    private ArrayList<String> friends;

    public User() {
    }

    public User(String nickname, String password, ArrayList<String> friends) {
        this.nickname = nickname;
        this.password = password;
        this.friends = friends;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public void addFriends(String friend) {
        this.friends.add(friend);
    }

    public void removeFriends(String friend) {
        this.friends.remove(friend);
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
    
    
    
    
    
    
}
