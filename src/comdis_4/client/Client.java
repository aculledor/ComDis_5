/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_4.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author aculledor
 */
public class Client {
    private ClientGUI gui;
    private ClientImplementation proxy;
    private String nickname, password, registry;
    private HashMap<String, ClientInterface> friendsProxys;
    private ArrayList<String> friendRequests;

    public Client(String nickname, String password) {
        //gui = new ClientGUI(this);
        registry = "rmi://localhost:7777/P2P";
        this.nickname = nickname;
        this.password = password;
        friendsProxys = new HashMap<>();
        friendRequests = new ArrayList<>();
    }

    public Client() {
        friendsProxys = new HashMap<>();
        friendRequests = new ArrayList<>();
    }
    
    
    
    //CONNECTION FUNCTIONS
    public void connect(){
        try{
            proxy = new ClientImplementation(this);
            Boolean success = proxy.connect();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void signUp(){
        try{
            proxy = new ClientImplementation(this);
            Boolean success = proxy.signUp();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void disconnect(){
        try{
            System.out.println(proxy.disconnect());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void deleteUser(){
        try{
            System.out.println(proxy.deleteUser());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //USABILITY FUNCTIONS    
    public void receiveMessage(String message){
        System.out.println(message);
    }
    
    public void receiveFriendRequest(String sourceNickname){
        System.out.println(sourceNickname);
    }
    
    public void addFriendToList(String sourceNickname, ClientInterface connectedFriend) throws RemoteException {
        this.friendsProxys.put(nickname, connectedFriend);
    }

    public void removeFriendFromList(String sourceNickname) throws RemoteException {
        this.friendsProxys.remove(nickname);
    }

    
    //GETTERS AND SETTERS
    public ClientGUI getGui() {
        return gui;
    }

    public void setGui(ClientGUI gui) {
        this.gui = gui;
    }

    public ClientImplementation getProxy() {
        return proxy;
    }

    public void setProxy(ClientImplementation proxy) {
        this.proxy = proxy;
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

    public HashMap<String, ClientInterface> getFriendsProxys() {
        return friendsProxys;
    }

    public void setFriendsProxys(HashMap<String, ClientInterface> friendsProxys) {
        this.friendsProxys = friendsProxys;
    }

    public ArrayList<String> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(ArrayList<String> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }
    
    
    
}
