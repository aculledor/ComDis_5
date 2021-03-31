package comdis_4.server;

import comdis_4.client.ClientInterface;
import comdis_4.database.ImpBD;
import comdis_4.database.ImpBD;
import comdis_4.classes.User;
import comdis_4.classes.User;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the remote interface HelloInterface.
 *
 * @author M. L. Liu
 */
public class ServerImplementation extends UnicastRemoteObject implements ServerInterface {
    
    private ImpBD db;
    private HashMap<String,User> onlineClients;

    public ServerImplementation() throws RemoteException {
        super();
        try {
            db = new ImpBD();
            onlineClients = new HashMap<>();
        } catch (SQLException ex) {
            Logger.getLogger(ServerImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public HashMap<String, ClientInterface> getFriends(String nickname) throws SQLException, RemoteException{
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
    
    
    public User getUser(ClientInterface client, String nickname, String password) throws RemoteException {
        try {
            if(!db.isUserPassword(nickname, password)){
                return null;
            }
            User newClient = db.getUser(nickname).setMyProxy(client).setFriendsProxys(this.getFriends(nickname));
            return newClient;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public User connect(ClientInterface client, String nickname, String password) throws RemoteException {
        try {
            if(!db.isUserPassword(nickname, password)){
                return null;
            }
            User newClient = db.getUser(nickname).setMyProxy(client).setFriendsProxys(this.getFriends(nickname)).setFriendRequests(db.getDestinationRequests(nickname));
            onlineClients.put(nickname, newClient);
            client.receiveMessage("Conectado");
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
            client.getProxy().receiveMessage("Desconectando...");
            this.onlineClients.remove(client.getNickname());
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
            User newClient = db.addUser(nickname, password).setMyProxy(client).setFriendsProxys(this.getFriends(nickname));
            onlineClients.put(nickname, newClient);
            client.receiveMessage("Conectado");
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
            client.getProxy().receiveMessage("Borrando del server");
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
            client.getProxy().receiveMessage("Peticion de amistad guardada");
            if(onlineClients.keySet().contains(friend)){
                onlineClients.get(friend).getProxy().receiveMessage("Peticion de amistad de ["+client.getNickname()+"]" );
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
            if(onlineClients.keySet().contains(friend)){
                onlineClients.get(friend).getProxy().receiveMessage("El usuario ["+client.getNickname()+"] te ha añadido a amigos" );
                onlineClients.get(friend).getProxy().updateData(this.getUser(onlineClients.get(friend).getProxy(), onlineClients.get(friend).getNickname(), onlineClients.get(friend).getPassword()));
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
            if(onlineClients.keySet().contains(friend)){
                onlineClients.get(friend).getProxy().receiveMessage("El usuario ["+client.getNickname()+"] te ha eliminado de amigos" );
                onlineClients.get(friend).getProxy().updateData(this.getUser(onlineClients.get(friend).getProxy(), onlineClients.get(friend).getNickname(), onlineClients.get(friend).getPassword()));
            }
            return this.getUser(client.getProxy(), client.getNickname(), client.getPassword());
        } catch (SQLException ex) {
            return null;
        }
    }

    
    
} // end class
