package comdis_4.server;

import comdis_4.client.ClientInterface;
import comdis_4.database.ImpBD;
import comdis_4.classes.User;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements the remote interface HelloInterface.
 *
 * @author M. L. Liu
 */
public class ServerImplementation extends UnicastRemoteObject implements ServerInterface {
    
    private final ImpBD db;
    private final HashMap<String,User> onlineClients;
    private final ServerGUI gui;

    public ServerImplementation(ServerGUI gui) throws RemoteException, SQLException {
        super();
        db = new ImpBD();
        onlineClients = new HashMap<>();
        this.gui = gui;
    }
    
    private HashMap<String, ClientInterface> getFriends(String nickname) throws SQLException, RemoteException{
        ArrayList<String> friends = db.getFriends(nickname);
        HashMap<String, ClientInterface> onlineFriends = new HashMap<>();
        User client;
        for(String friend : friends){
            client = onlineClients.get(friend);
            if(client != null){
                onlineFriends.put(friend, client.getProxy());
            }
        }
        return onlineFriends;
    }
    
    
    private User getUser(ClientInterface client, String nickname, String password) throws RemoteException {
        try {
            if(!db.isUserPassword(nickname, password)){
                return null;
            }
            User newClient = db.getUser(nickname).setProxy(client).setFriendsProxys(this.getFriends(nickname));
            return newClient;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    private User updatedUser(ClientInterface client, String nickname) throws SQLException, RemoteException{
        return db.getUser(nickname).setProxy(client).setFriendsProxys(this.getFriends(nickname)).setFriendRequests(db.getDestinationRequests(nickname));
    }
    
    
    private void updateFriends(String nickname) throws RemoteException, SQLException {
        HashMap<String, ClientInterface> onlineFriends = this.getFriends(nickname);
        for(String friend : onlineFriends.keySet()){
            onlineClients.replace(friend, updatedUser(onlineClients.get(friend).getProxy(), friend));
            onlineClients.get(friend).getProxy().updateData(onlineClients.get(friend));
        }
    }

    @Override
    public User connect(ClientInterface client, String nickname, String password) throws RemoteException {
        try {
            if(!db.isUserPassword(nickname, password)){
                return null;
            }
            User newClient = updatedUser(client, nickname);
            onlineClients.put(nickname, newClient);
            updateFriends(nickname);
            client.receiveMessage("Conectado");
            gui.appendText("["+nickname+"] se ha conectado");
            return newClient;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Boolean disconnect(User client) throws RemoteException {
        try {
            if(!db.isUserPassword(client.getNickname(), client.getPassword())){
                return false;
            }
            client.getProxy().receiveMessage("Desconectando");
            gui.appendText("["+client.getNickname()+"] se ha desconectado");
            this.onlineClients.remove(client.getNickname());
            updateFriends(client.getNickname());
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public User signUp(ClientInterface client, String nickname, String password) throws RemoteException {
        try {
            if(db.isUser(nickname)){
                return null;
            }
            User newClient = updatedUser(client, nickname);
            onlineClients.put(nickname, newClient);
            updateFriends(nickname);
            client.receiveMessage("Dado de alta");
            gui.appendText("["+nickname+"] se ha dado de alta");
            return newClient;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Boolean deleteUser(User client) throws RemoteException {
        try {
            if(!db.isUserPassword(client.getNickname(), client.getPassword())){
                return false;
            }
            db.deleteUser(client.getNickname());
            onlineClients.remove(client.getNickname());
            updateFriends(client.getNickname());
            client.getProxy().receiveMessage("Borrando del server");
            gui.appendText("["+client.getNickname()+"] ha sido borrado");
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Boolean friendRequest(User client, String friend) throws RemoteException {
        try {
            if(!db.isUserPassword(client.getNickname(), client.getPassword()) || !db.isUser(friend) || db.areFriends(client.getNickname(), friend)){
                return false;
            }
            db.addRequest(client.getNickname(), friend);
            client.getProxy().receiveMessage("Peticion de amistad creada");
            gui.appendText("Petición de amistad de ["+client.getNickname()+"] a ["+friend+"] creada");
            if(onlineClients.keySet().contains(friend)){
                onlineClients.get(friend).getProxy().receiveMessage("Peticion de amistad de ["+client.getNickname()+"] recibida" );
                onlineClients.get(friend).getProxy().receiveFriendRequest(client.getNickname());
            }
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public User acceptFriendRequest(User client, String friend) throws RemoteException {
        try {
            if(!db.isUserPassword(client.getNickname(), client.getPassword()) || !db.isUser(friend) || db.areFriends(client.getNickname(), friend)){
                return null;
            }
            db.addFriend(client.getNickname(), friend);
            client.getProxy().receiveMessage("Amistad con ["+friend+"] creada");
            gui.appendText("Amistad entre ["+client.getNickname()+"] y ["+friend+"] creada");
            if(onlineClients.keySet().contains(friend)){
                onlineClients.get(friend).getProxy().receiveMessage("El usuario ["+client.getNickname()+"] te ha añadido a amigos" );
                onlineClients.get(friend).getProxy().updateData(updatedUser(onlineClients.get(friend).getProxy(), onlineClients.get(friend).getNickname()));
            }
            return this.getUser(client.getProxy(), client.getNickname(), client.getPassword());
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public User removeFriend(User client, String friend) throws RemoteException {
        try {
            if(!db.isUserPassword(client.getNickname(), client.getPassword()) || !db.isUser(friend) || !db.areFriends(client.getNickname(), friend)){
                return null;
            }
            db.deleteFriend(client.getNickname(), friend);
            client.getProxy().receiveMessage("Amistad con ["+friend+"] eliminada");
            gui.appendText("Amistad entre ["+client.getNickname()+"] y ["+friend+"] eliminada");
            if(onlineClients.keySet().contains(friend)){
                onlineClients.get(friend).getProxy().receiveMessage("El usuario ["+client.getNickname()+"] te ha eliminado de amigos" );
                onlineClients.get(friend).getProxy().updateData(updatedUser(onlineClients.get(friend).getProxy(), onlineClients.get(friend).getNickname()));
            }
            return this.getUser(client.getProxy(), client.getNickname(), client.getPassword());
        } catch (SQLException ex) {
            return null;
        }
    }

    
    
} // end class
