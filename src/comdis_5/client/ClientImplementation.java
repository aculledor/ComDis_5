package comdis_5.client;

import comdis_5.server.ServerInterface;
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
        return server.subscribe(this, client.getSeconds());
    }
    
    public Boolean disconnect() throws NotBoundException, MalformedURLException, RemoteException{
        return server.unsubscribe(this);
    }
    
    public Boolean renew() throws NotBoundException, MalformedURLException, RemoteException{
        return server.renew(this, client.getSeconds());
    }
    
    
    //CLIENT FUNCTION
    @Override
    public void receiveMessage(String message) throws RemoteException{
        client.receiveMessage(message);
    }
    
    @Override
    public void receiveData(Double data) throws RemoteException{
        client.receiveData(data);
    }
    
}
