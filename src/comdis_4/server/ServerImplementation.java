package comdis_4.server;

import comdis_4.client.ClientInterface;
import comdis_4.database.ImpBD;
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
    private final HashMap<String, ClientInterface> onlineClients;
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
        ClientInterface friendProxy;
        for(String friend : friends){
            friendProxy = onlineClients.get(friend);
            if(friendProxy != null){
                onlineFriends.put(friend, friendProxy);
            }
        }
        return onlineFriends;
    }
    
    private void addClientToFriends(String nickname) throws RemoteException, SQLException {
        HashMap<String, ClientInterface> onlineFriends = this.getFriends(nickname);
        ClientInterface client = onlineClients.get(nickname);
        for(ClientInterface friend : onlineFriends.values()){
            friend.addFriendToList(nickname, client);
        }
    }
    
    private void removeClientFromFriends(String nickname) throws RemoteException, SQLException {
        HashMap<String, ClientInterface> onlineFriends = this.getFriends(nickname);
        for(ClientInterface friend : onlineFriends.values()){
            friend.removeFriendFromList(nickname);
        }
    }

    @Override
    public Boolean connect(ClientInterface client, String nickname, String password) throws RemoteException {
        try {
            if(!db.isUserPassword(nickname, password)){
                return null;
            }
            onlineClients.put(nickname, client);
            this.addClientToFriends(nickname);
            client.receiveMessage("Conectado");
            gui.appendText("["+nickname+"] se ha conectado");
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Boolean signUp(ClientInterface client, String nickname, String password) throws RemoteException {
        try {
            if(db.isUser(nickname)){
                return null;
            }
            db.addUser(nickname, password);
            onlineClients.put(nickname, client);
            client.receiveMessage("Dado de alta");
            gui.appendText("["+nickname+"] se ha dado de alta");
            return true;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Boolean disconnect(String nickname, String password) throws RemoteException {
        try {
            if(!db.isUserPassword(nickname, password)){
                return false;
            }
            onlineClients.get(nickname).receiveMessage("Desconectando...");
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
            db.deleteUser(nickname);
            onlineClients.remove(nickname);
            this.removeClientFromFriends(nickname);
            onlineClients.get(nickname).receiveMessage("Borrado del server");
            gui.appendText("["+nickname+"] ha sido borrado");
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Boolean friendRequest(String nickname, String password, String friend) throws RemoteException {
        try {
            if(!db.isUserPassword(nickname, password) || !db.isUser(friend) || db.areFriends(nickname, friend)){
                return false;
            }
            db.addRequest(nickname, friend);
            onlineClients.get(nickname).receiveMessage("Peticion de amistad creada");
            gui.appendText("Petición de amistad de ["+nickname+"] a ["+friend+"] creada");
            if(onlineClients.keySet().contains(friend)){
                onlineClients.get(friend).receiveMessage("Peticion de amistad de ["+nickname+"] recibida" );
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
                return null;
            }
            db.addFriend(nickname, friend);
            onlineClients.get(nickname).receiveMessage("Amistad con ["+friend+"] creada");
            gui.appendText("Amistad entre ["+nickname+"] y ["+friend+"] creada");
            if(onlineClients.keySet().contains(friend)){
                onlineClients.get(friend).receiveMessage("El usuario ["+nickname+"] te ha añadido a amigos" );
                onlineClients.get(nickname).addFriendToList(friend, onlineClients.get(friend));
                onlineClients.get(friend).addFriendToList(nickname, onlineClients.get(nickname));
            }
            return true;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Boolean removeFriend(String nickname, String password, String friend) throws RemoteException {
        try {
            if(!db.isUserPassword(nickname, password) || !db.isUser(friend) || !db.areFriends(nickname, friend)){
                return null;
            }
            db.deleteFriend(nickname, friend);
            onlineClients.get(nickname).receiveMessage("Amistad con ["+friend+"] eliminada");
            gui.appendText("Amistad entre ["+nickname+"] y ["+friend+"] eliminada");
            if(onlineClients.keySet().contains(friend)){
                onlineClients.get(friend).receiveMessage("El usuario ["+nickname+"] te ha eliminado de amigos" );
                onlineClients.get(nickname).receiveFriendRequest(friend);
                onlineClients.get(friend).receiveFriendRequest(nickname);
            }
            return true;
        } catch (SQLException ex) {
            return null;
        }
    }

    
    
} // end class
