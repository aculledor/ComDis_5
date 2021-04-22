/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_4.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    
    private void restartSession(){
        friendsProxys = new HashMap<>();
        friendRequests = new ArrayList<>();
        this.start();
    }
    
    
    public void updateGUIData(){
        List<String> friends = new ArrayList<String>();
        friends.addAll(friendsProxys.keySet());
        this.gui.updateData((ArrayList<String>) friends, friendRequests);
        
        System.out.println(this.friendRequests.toString());
        System.out.println(this.friendsProxys.keySet().toString());
    }
    
    //CONNECTION FUNCTIONS
    public void connect(){
        try{
            proxy = new ClientImplementation(this);
            Boolean bool = proxy.connect();
            if(bool){
                this.gui.cerrarInicio();
                this.gui.setVisible(true);
                this.updateGUIData();
            }else{
                this.showError("No pudo conectarse al servidor");
                this.gui.setInteractiveInit(true);
            }
        }catch(MalformedURLException | NotBoundException | RemoteException e){
            this.showError(e.getLocalizedMessage());
        }catch(NullPointerException e){
            this.showError("Ocurrió un error insesperado");
        }
    }
    
    public void signUp(){
        try{
            proxy = new ClientImplementation(this);
            Boolean bool = proxy.signUp();
            if(bool){
                this.gui.cerrarInicio();
                this.gui.setVisible(true);
                this.updateGUIData();
            }else{
                this.showError("No pudo darse de alta en el servidor");
                this.gui.setInteractiveInit(true);
            }
        }catch(MalformedURLException | NotBoundException | RemoteException e){
            this.showError(e.toString());
        }catch(NullPointerException e){
            this.showError("Ocurrió un error insesperado");
        }
    }
    
    public void disconnect(){
        try{
            Boolean bool = proxy.disconnect();
            if(bool){
                this.gui.dispose();
                this.restartSession();
            }else{
                this.showError("No pudo desconectarse del servidor");
            }
        }catch(MalformedURLException | NotBoundException | RemoteException e){
            this.showError(e.toString());
        }catch(NullPointerException e){
            this.showError("Ocurrió un error insesperado");
        }
    }
    
    public void deleteUser(){
        try{
            Boolean bool = proxy.deleteUser();
            if(bool){
                this.gui.dispose();
                this.restartSession();
            }else{
                this.showError("No pudo borrarse el usuario del servidor");
            }
        }catch(MalformedURLException | NotBoundException | RemoteException e){
            this.showError(e.toString());
        }catch(NullPointerException e){
            this.showError("Ocurrió un error insesperado");
        }
    }
    
    public void acceptFriendRequest(String friend){
        try{
            Boolean bool = this.proxy.acceptFriendRequest(friend);
            if(bool){
                this.friendRequests.remove(friend);
                //this.gui.removeFriendRequest(friend);
                //this.gui.updateListaPeticiones(friendRequests);
                this.updateGUIData();
            }else{
                this.showError("No pudo borrarse el usuario del servidor");
            }
        }catch(MalformedURLException | NotBoundException | RemoteException e){
            this.showError(e.toString());
        }catch(NullPointerException e){
            this.showError("Ocurrió un error insesperado");
        }
    }
    
    public void rejectFriendRequest(String friend){
        try{
            Boolean bool = this.proxy.rejectFriendRequest(friend);
            if(bool){
                this.friendRequests.remove(friend);
                //this.gui.removeFriendRequest(friend);
                //this.gui.updateListaPeticiones(friendRequests);
                this.updateGUIData();
            }else{
                this.showError("No pudo borrarse la peticion de amistad");
            }
        }catch(MalformedURLException | NotBoundException | RemoteException e){
            this.showError(e.toString());
        }catch(NullPointerException e){
            this.showError("Ocurrió un error insesperado");
        }
    }
    
    public void sendMessage(String friend, String message){
        try{
            this.getFriendsProxys().get(friend).receiveMessage(nickname, message);
            this.gui.confirmSent();
        }catch(RemoteException e){
            this.showError(e.toString());
        }catch(NullPointerException e){
            this.showError("Ocurrió un error insesperado");
        }
    }
    
    public void sendFriendRequest(String friend){
        try{
            Boolean bool = this.proxy.createFriendRequest(friend);
            if(!bool) this.showError("No pudo crearse la peticion de amistad");
        }catch(MalformedURLException | NotBoundException | RemoteException e){
            this.showError(e.toString());
        }catch(NullPointerException e){
            this.showError("Ocurrió un error insesperado");
        }
    }
    
    //USABILITY FUNCTIONS 
    public void receiveMessage(String friend, String message){
        if(friend.equals("Server_P2P")){
            this.gui.receiveServerMessage(friend, message);
            return;
        }
        this.gui.receiveMessage(friend, message);
    }
    
    public void receiveFriendRequest(String friend){
        try{
            this.friendRequests.add(friend); //esta puede que esté mal
            //this.gui.addFriendRequest(friend);
            //this.gui.updateListaPeticiones(friendRequests);
            this.updateGUIData();
        }catch(Exception e){
            this.showError(e.toString());
        }
    }
    
    public void setFriendList(HashMap<String, ClientInterface> connectedFriends){
        try{
            this.friendsProxys = connectedFriends;
            this.updateGUIData();
        }catch(Exception e){
            this.showError(e.toString());
        }
    }
    
    public void setFriendRequestList(ArrayList<String> friendRequests){
        try{
            this.friendRequests = friendRequests;  
            this.updateGUIData();  
        }catch(Exception e){
            this.showError(e.toString());
        }
    }
    
    public void addFriendToList(String friend, ClientInterface connectedFriend) throws RemoteException {
        try{
            this.friendsProxys.put(friend, connectedFriend); 
            this.gui.addOnlineFriend(friend);
        }catch(Exception e){
            this.showError(e.toString());
        }
    }

    public void removeFriendFromList(String friend) throws RemoteException {
        try{
            this.friendsProxys.remove(friend);  
            this.gui.removeOnlineFriend(friend);
        }catch(Exception e){
            this.showError(e.toString());
        }
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
