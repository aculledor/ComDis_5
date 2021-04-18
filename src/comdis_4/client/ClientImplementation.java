package comdis_4.client;

import comdis_4.server.ServerInterface;
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
    
    //LOCAL FUNCTION
    public Boolean connect() throws NotBoundException, MalformedURLException, RemoteException{
        server = (ServerInterface) Naming.lookup(client.getRegistry());
        return server.connect(this, client.getNickname(), client.getPassword());
    }
    
    public Boolean disconnect() throws NotBoundException, MalformedURLException, RemoteException{
        return server.disconnect(client.getNickname(), client.getPassword());
    }
    
    public Boolean signUp() throws NotBoundException, MalformedURLException, RemoteException{
        server = (ServerInterface) Naming.lookup(client.getRegistry());
        return server.signUp(client.getProxy(), client.getNickname(), client.getPassword());
    }
    
    public Boolean deleteUser() throws NotBoundException, MalformedURLException, RemoteException{
        return server.deleteUser(client.getNickname(), client.getPassword());
    }
    
    public Boolean friendRequest(String nickname, String password, String friend) throws NotBoundException, MalformedURLException, RemoteException{
        return server.friendRequest(client.getNickname(), client.getPassword(), friend);
    }
    
    public Boolean removeFriend(String nickname, String password, String friend) throws NotBoundException, MalformedURLException, RemoteException{
        return server.removeFriend(client.getNickname(), client.getPassword(), friend);
    }
    
    public Boolean addFriendToList(String nickname, String password, String friend) throws NotBoundException, MalformedURLException, RemoteException{
        return server.removeFriend(client.getNickname(), client.getPassword(), friend);
    }
    
    public Boolean removeFriendFromList(String nickname, String password, String friend) throws NotBoundException, MalformedURLException, RemoteException{
        return server.removeFriend(client.getNickname(), client.getPassword(), friend);
    }

    
    //SERVER FUNTIONS
    @Override
    public void addFriendToList(String sourceNickname, ClientInterface connectedFriend) throws RemoteException {
        client.addFriendToList(sourceNickname, connectedFriend); 
    }

    @Override
    public void removeFriendFromList(String sourceNickname) throws RemoteException {
        client.removeFriendFromList(sourceNickname);
    }
    
    @Override
    public void receiveFriendRequest(String sourceNickname) throws RemoteException{
        client.receiveFriendRequest(sourceNickname);
    }
    
    
    //CLIENT FUNCTION
    @Override
    public void receiveMessage(String message) throws RemoteException{
        client.receiveMessage(message);
    }
    
    
}
