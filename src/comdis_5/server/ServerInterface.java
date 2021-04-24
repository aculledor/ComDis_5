package comdis_5.server;

// A simple RMI interface file - M. Liu
import comdis_5.client.ClientInterface;
import java.rmi.*;

/**
 * This is a remote interface.
 *
 * @author M. L. Liu
 */
public interface ServerInterface extends Remote {

    /**
     * This remote method returns true if the subscription is successfull or false if it isnt
     * @param client
     * @param seconds
     * @return the user or null.
     * @throws java.rmi.RemoteException
     */
    public Boolean subscribe(ClientInterface client, Integer seconds) throws java.rmi.RemoteException;

    /**
     * This remote method returns true if the renewal is successfull or false if it isnt
     * @param client
     * @param seconds
     * @return the user or null.
     * @throws java.rmi.RemoteException
     */
    public Boolean renew(ClientInterface client, Integer seconds) throws java.rmi.RemoteException;

    /**
     * This remote method returns true if the unsubscription is successfull or false if it isnt
     * @param client
     * @return 
     * @throws java.rmi.RemoteException
     */
    public Boolean unsubscribe(ClientInterface client) throws java.rmi.RemoteException;

}  //end interface
