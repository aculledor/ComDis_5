package comdis_4.client;

import comdis_4.server.ServerInterface;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    public Boolean createFriendRequest(String friend) throws NotBoundException, MalformedURLException, RemoteException{
        return server.createFriendRequest(client.getNickname(), client.getPassword(), friend);
    }
    
    public Boolean removeFriend(String friend) throws NotBoundException, MalformedURLException, RemoteException{
        return server.removeFriend(client.getNickname(), client.getPassword(), friend);
    }
    
    public Boolean acceptFriendRequest(String friend) throws NotBoundException, MalformedURLException, RemoteException{
        return server.acceptFriendRequest(client.getNickname(), client.getPassword(), friend);
    }
    
    public Boolean rejectFriendRequest(String friend) throws NotBoundException, MalformedURLException, RemoteException{
        return server.rejectFriendRequest(client.getNickname(), client.getPassword(), friend);
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

    @Override
    public void setFriendList(Map<String, ClientInterface> connectedFriends) throws RemoteException {
        client.setFriendList((HashMap<String, ClientInterface>) connectedFriends);
    }

    @Override
    public void setFriendRequestList(List<String> friendRequests) throws RemoteException {
        client.setFriendRequestList((ArrayList<String>) friendRequests);
    }
    
    
    //CLIENT FUNCTION
    @Override
    public void receiveMessage(String nickname, String message) throws RemoteException{
        client.receiveMessage(nickname, message);
    }
    
}
