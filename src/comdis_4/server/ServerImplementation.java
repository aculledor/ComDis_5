package comdis_4.server;

import comdis_4.client.ClientInterface;
import comdis_4.database.ImpBD;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * This class implements the remote interface HelloInterface.
 *
 * @author M. L. Liu
 */
public class ServerImplementation extends UnicastRemoteObject implements ServerInterface {
    
    private final ImpBD db;
    private final HashMap<String, ClientInterface> onlineClients;
    private ServerGUI gui;
    private final String name = "Server_P2P";

    public String getUsers(){
        return this.onlineClients.keySet().toString();
    }
    
    public ServerImplementation() throws RemoteException, SQLException {
        super();
        db = new ImpBD();
        onlineClients = new HashMap<>();
    }
    
    public void start(){
        gui = new ServerGUI(this);
        gui.start();
    }
    
    private HashMap<String, ClientInterface> getOnlineFriends(String nickname) throws SQLException, RemoteException{
        ArrayList<String> friends = db.getFriends(nickname);
        HashMap<String, ClientInterface> onlineFriends = new HashMap<>();
        ClientInterface friendProxy;
        for(String friend : friends){
            friendProxy = onlineClients.get(friend);
            if(friendProxy != null){
                onlineFriends.put(friend, friendProxy);
            }
        }
        return onlineFriends;
    }
    
    private void setClientData(String nickname) throws RemoteException, SQLException {
        HashMap<String, ClientInterface> onlineFriends = this.getOnlineFriends(nickname);
        ArrayList<String> friendRequests = this.db.getRequestsFor(nickname);
        ClientInterface client = onlineClients.get(nickname);
        client.setFriendList(onlineFriends);
        client.setFriendRequestList(friendRequests);
    }
    
    private void addClientToFriends(String nickname) throws RemoteException, SQLException {
        HashMap<String, ClientInterface> onlineFriends = this.getOnlineFriends(nickname);
        ClientInterface client = onlineClients.get(nickname);
        for(ClientInterface friend : onlineFriends.values()){
            friend.addFriendToList(nickname, client);
        }
    }
    
    private void removeClientFromFriends(String nickname) throws RemoteException, SQLException {
        HashMap<String, ClientInterface> onlineFriends = this.getOnlineFriends(nickname);
        for(ClientInterface friend : onlineFriends.values()){
            friend.removeFriendFromList(nickname);
        }
    }

    @Override
    public Boolean connect(ClientInterface client, String nickname, String password) throws RemoteException {
        try {
            if(!db.isUserPassword(nickname, password)){
                return false;
            }
            onlineClients.put(nickname, client);
            this.addClientToFriends(nickname);
            this.setClientData(nickname);
            client.receiveMessage(name, nickname+" se ha conectado");
            gui.appendText("["+nickname+"] se ha conectado");
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Boolean signUp(ClientInterface client, String nickname, String password) throws RemoteException {
        try {
            if(nickname.equals(this.name) || db.isUser(nickname)){
                return false;
            }
            db.addUser(nickname, password);
            onlineClients.put(nickname, client);
            client.receiveMessage(name, nickname+" se ha dado de alta");
            gui.appendText("["+nickname+"] se ha dado de alta");
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Boolean disconnect(String nickname, String password) throws RemoteException {
        try {
            if(!db.isUserPassword(nickname, password)){
                return false;
            }
            onlineClients.get(nickname).receiveMessage(name, nickname+" se ha desconectando");
            gui.appendText("["+nickname+"] se ha desconectado");
            this.onlineClients.remove(nickname);
            this.removeClientFromFriends(nickname);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Boolean deleteUser(String nickname, String password) throws RemoteException {
        try {
            if(!db.isUserPassword(nickname, password)){
                return false;
            }
            onlineClients.get(nickname).receiveMessage(name, nickname+" se ha borrado del server");
            gui.appendText("["+nickname+"] ha sido borrado");
            this.removeClientFromFriends(nickname);
            onlineClients.remove(nickname);
            db.deleteUser(nickname);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Boolean createFriendRequest(String nickname, String password, String friend) throws RemoteException {
        try {
            if(!db.isUserPassword(nickname, password) || !db.isUser(friend) || db.areFriends(nickname, friend)){
                return false;
            }
            db.addRequest(nickname, friend);
            onlineClients.get(nickname).receiveMessage(name, "Peticion de amistad creada");
            gui.appendText("Petición de amistad de ["+nickname+"] a ["+friend+"] creada");
            if(onlineClients.keySet().contains(friend)){
                onlineClients.get(friend).receiveMessage(name, "Peticion de amistad de ["+nickname+"] recibida" );
                onlineClients.get(friend).receiveFriendRequest(nickname);
            }
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Boolean acceptFriendRequest(String nickname, String password, String friend) throws RemoteException {
        try {
            if(!db.isUserPassword(nickname, password) || !db.isUser(friend) || db.areFriends(nickname, friend)){
                return false;
            }
            db.addFriend(nickname, friend);
            onlineClients.get(nickname).receiveMessage(name, "Amistad con ["+friend+"] creada");
            gui.appendText("Amistad entre ["+nickname+"] y ["+friend+"] creada");
            if(onlineClients.keySet().contains(friend)){
                onlineClients.get(friend).receiveMessage(name, "El usuario ["+nickname+"] te ha añadido a amigos" );
                onlineClients.get(nickname).addFriendToList(friend, onlineClients.get(friend));
                onlineClients.get(friend).addFriendToList(nickname, onlineClients.get(nickname));
            }
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Boolean rejectFriendRequest(String nickname, String password, String friend) throws RemoteException {
        try {
            if(!db.isUserPassword(nickname, password) || !db.getRequestsFor(nickname).contains(friend)){
                return false;
            }
            db.deleteRequest(friend, nickname);
            onlineClients.get(nickname).receiveMessage(name, "Peticion de amistad con ["+friend+"] eliminada");
            gui.appendText("Peticion de amistad entre ["+nickname+"] y ["+friend+"] eliminada");
            if(onlineClients.keySet().contains(friend)){
                onlineClients.get(friend).receiveMessage(name, "El usuario ["+nickname+"] ha rechazado tu petición de amistad" );
            }
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Boolean removeFriend(String nickname, String password, String friend) throws RemoteException {
        try {
            if(!db.isUserPassword(nickname, password) || !db.isUser(friend) || !db.areFriends(nickname, friend)){
                return false;
            }
            db.deleteFriend(nickname, friend);
            onlineClients.get(nickname).receiveMessage(name, "Amistad con ["+friend+"] eliminada");
            gui.appendText("Amistad entre ["+nickname+"] y ["+friend+"] eliminada");
            if(onlineClients.keySet().contains(friend)){
                onlineClients.get(friend).receiveMessage(name, "El usuario ["+nickname+"] te ha eliminado de amigos" );
                onlineClients.get(nickname).receiveFriendRequest(friend);
                onlineClients.get(friend).receiveFriendRequest(nickname);
            }
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    
    
} // end class
