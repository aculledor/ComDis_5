package comdis_4.client;

import comdis_4.classes.User;
import comdis_4.server.ServerInterface;
import comdis_4.server.ServerInterface;
import comdis_4.classes.User;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;

/**
 * This class implements the remote interface HelloInterface.
 *
 * @author M. L. Liu
 */
public class ClientImplementation extends UnicastRemoteObject implements ClientInterface {
    
    private ServerInterface server;
    private final Client client;
    private final String nickname;

    public ClientImplementation(Client client, String nickname) throws RemoteException {
        super();
        this.client = client;
        this.nickname = nickname;
    }
    
    public User connect(String registry, String nickname, String password) throws NotBoundException, MalformedURLException{
        try{
            server = (ServerInterface) Naming.lookup(registry);
            return server.connect(this, nickname, password);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public Boolean disconnect() throws NotBoundException, MalformedURLException{
        try{
            return server.disconnect(this);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    public User signUp(String registry, String nickname, String password) throws NotBoundException, MalformedURLException{
        try{
            server = (ServerInterface) Naming.lookup(registry);
            return server.signUp(this, nickname, password);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public Boolean deleteUser(String nickname, String password) throws NotBoundException, MalformedURLException{
        try{
            return server.deleteUser(this, nickname, password);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    public Boolean friendRequest(String nickname, String password, String friend) throws NotBoundException, MalformedURLException{
        try{
            return server.friendRequest(nickname, password, friend);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public User removeFriend(String nickname, String password, String friend) throws NotBoundException, MalformedURLException{
        try{
            return server.removeFriend(nickname, password, friend);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void receiveMessage(String message) throws RemoteException{
        client.receiveMessage(message);
    }
    
    @Override
    public String getNickname() throws RemoteException{
        return nickname;
    }
    
} // end class
