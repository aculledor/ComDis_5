package comdis_4.client;

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

    public ClientImplementation(Client client) throws RemoteException {
        super();
        this.client = client;
    }
    
    public User connect(String registry, String nickname, String password) throws NotBoundException, MalformedURLException, RemoteException{
        server = (ServerInterface) Naming.lookup(registry);
        return server.connect(this, nickname, password);
    }
    
    public Boolean disconnect() throws NotBoundException, MalformedURLException, RemoteException{
        return server.disconnect(this.client.getData());
    }
    
    public User signUp(String registry, String nickname, String password) throws NotBoundException, MalformedURLException, RemoteException{
        server = (ServerInterface) Naming.lookup(registry);
        return server.signUp(this, nickname, password);
    }
    
    public Boolean deleteUser(String nickname, String password) throws NotBoundException, MalformedURLException, RemoteException{
        return server.deleteUser(this.client.getData());
    }
    
    public Boolean friendRequest(String nickname, String password, String friend) throws NotBoundException, MalformedURLException, RemoteException{
        return server.friendRequest(this.client.getData(), friend);
    }
    
    public User removeFriend(String nickname, String password, String friend) throws NotBoundException, MalformedURLException, RemoteException{
        return server.removeFriend(this.client.getData(), friend);
    }
    
    @Override
    public void receiveMessage(String message) throws RemoteException{
        client.receiveMessage(message);
    }
    
    @Override
    public void receiveFriendRequest(String sourceNickname) throws RemoteException{
        client.receiveFriendRequest(sourceNickname);
    }

    @Override
    public void updateData(User newData) throws RemoteException {
        this.client.setData(newData);
    }
    
    
} // end class
