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
    
    public void start(){
        gui = new ClientGUI(this);
        gui.start();
    }
    
    private void showError(String message){
        if(message == null){
            this.gui.showError("Un error inesperado a ocurrido");
            return;
        }
        this.gui.showError(message);
    }
    
    
    //CONNECTION FUNCTIONS
    public void connect(){
        try{
            proxy = new ClientImplementation(this);
            Boolean bool = proxy.connect();
            if(bool){
                this.gui.cerrarInicio();
                this.gui.setVisible(true);
            }else{
                this.showError("No pudo conectarse al servidor");
            }
        }catch(Exception e){
                this.showError(e.getLocalizedMessage());
        }
    }
    public void signUp(){
        try{
            proxy = new ClientImplementation(this);
            
            if(proxy.signUp()){
                this.gui.cerrarInicio();
            }else{
                
            }
        }catch(Exception e){
                this.showError(e.toString());
        }
    }
    
    public void disconnect(){
        try{
            System.out.println(proxy.disconnect());
        }catch(Exception e){
                this.showError(e.toString());
        }
    }
    
    public void deleteUser(){
        try{
            System.out.println(proxy.deleteUser());
        }catch(Exception e){
                this.showError(e.toString());
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

    public Client setGui(ClientGUI gui) {
        this.gui = gui;
        return this;
    }

    public ClientImplementation getProxy() {
        return proxy;
    }

    public Client setProxy(ClientImplementation proxy) {
        this.proxy = proxy;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public Client setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Client setPassword(String password) {
        this.password = password;
        return this;
    }

    public HashMap<String, ClientInterface> getFriendsProxys() {
        return friendsProxys;
    }

    public Client setFriendsProxys(HashMap<String, ClientInterface> friendsProxys) {
        this.friendsProxys = friendsProxys;
        return this;
    }

    public ArrayList<String> getFriendRequests() {
        return friendRequests;
    }

    public Client setFriendRequests(ArrayList<String> friendRequests) {
        this.friendRequests = friendRequests;
        return this;
    }

    public String getRegistry() {
        return registry;
    }

    public Client setRegistry(String registry) {
        this.registry = registry;
        return this;
    }
    
    
    
}
