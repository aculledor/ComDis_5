package comdis_4.server;

// A simple RMI interface file - M. Liu
import comdis_4.client.ClientInterface;
import comdis_4.classes.User;
import java.rmi.*;

/**
 * This is a remote interface.
 *
 * @author M. L. Liu
 */
public interface ServerInterface extends Remote {

    /**
     * This remote method returns the user if the connection is successfull or null if it isnt
     * @param client
     * @param nickname
     * @param password
     * @return the user or null.
     * @throws java.rmi.RemoteException
     */
    public User connect(ClientInterface client, String nickname, String password) throws java.rmi.RemoteException;

    /**
     * This remote method lets the server know the client is disconnecting
     * @param client
     * @return 
     * @throws java.rmi.RemoteException
     */
    public Boolean disconnect(User client) throws java.rmi.RemoteException;

    /**
     * This remote method returns the user if the sign up is successfull or null if it isnt
     * @param client
     * @param nickname
     * @param password
     * @return the user or null.
     * @throws java.rmi.RemoteException
     */
    public User signUp(ClientInterface client, String nickname, String password) throws java.rmi.RemoteException;

    /**
     * This remote method returns true if the deletion is successfull or false if it isnt
     * @param client 
     * @return the user or null.
     * @throws java.rmi.RemoteException
     */
    public Boolean deleteUser(User client) throws java.rmi.RemoteException;

    /**
     * This remote method returns the user if the connection is successfull or null if it isnt
     * @param client
     * @param friend
     * @return the user or null.
     * @throws java.rmi.RemoteException
     */
    public Boolean friendRequest(User client, String friend) throws java.rmi.RemoteException;

    /**
     * This remote method returns the user if the connection is successfull or null if it isnt
     * @param client
     * @param friend
     * @return the user or null.
     * @throws java.rmi.RemoteException
     */
    public User acceptFriendRequest(User client, String friend) throws java.rmi.RemoteException;

    /**
     * This remote method returns the user after updating it
     * @param client
     * @param friend
     * @return the user or null.
     * @throws java.rmi.RemoteException
     */
    public User removeFriend(User client, String friend) throws java.rmi.RemoteException;

}  //end interface
