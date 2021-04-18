package comdis_4.client;

// A simple RMI interface file - M. Liu
import java.rmi.*;

/**
 * This is a remote interface.
 *
 * @author M. L. Liu
 */
public interface ClientInterface extends Remote {

    /**
     * This remote method gives the other client your message
     *
     * @param message - an string containing the message.
     * @throws java.rmi.RemoteException
     */
    public void receiveMessage(String message) throws java.rmi.RemoteException;

    /**
     * This remote method gives the other client your message
     *
     * @param sourceNickname - the name of the request's source.
     * @throws java.rmi.RemoteException
     */
    public void receiveFriendRequest(String sourceNickname) throws java.rmi.RemoteException;

    /**
     * This remote method adds a friend to the client list
     *
     * @param sourceNickname
     * @param connectedFriend
     * @throws java.rmi.RemoteException
     */
    public void addFriendToList(String sourceNickname, ClientInterface connectedFriend) throws java.rmi.RemoteException;

    /**
     * This remote method removes a friend to the client list
     *
     * @param sourceNickname
     * @throws java.rmi.RemoteException
     */
    public void removeFriendFromList(String sourceNickname) throws java.rmi.RemoteException;
    
    

}  //end interface
