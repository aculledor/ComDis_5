package comdis_4;

import comdis_4.ImpBD;
import comdis_4.User;
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
    
    public ArrayList<ClientInterface> getFriends(String nickname) throws SQLException, RemoteException{
        ArrayList<String> friends = db.getFriends(nickname);
        ArrayList<ClientInterface> onlineFriends = new ArrayList<>();
        User client;
        for(String friend : friends){
            client = onlineClients.get(friend);
            if(client != null){
                onlineFriends.add(client.getMyProxy());
            }
        }
        return onlineFriends;
    }

    @Override
    public User connect(ClientInterface client, String nickname, String password) throws RemoteException {
        try {
            if(!db.isUser(nickname)||!db.isPassword(nickname, password)){
                return null;
            }
            User newClient = db.getUser(nickname);
            newClient.setMyProxy(client).setFriendsProxys(this.getFriends(nickname));
            onlineClients.put(nickname, newClient);
            client.receiveMessage("Conectado");
            return newClient;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Boolean disconnect(ClientInterface client) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User signUp(ClientInterface client, String nickname, String password) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean deleteUser(ClientInterface client, String nickname, String password) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean friendRequest(String nickname, String password, String friend) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User removeFriend(String nickname, String password, String friend) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
} // end class
