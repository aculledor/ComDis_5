package comdis_4.server;

// A simple RMI interface file - M. Liu
import comdis_4.client.ClientInterface;
import java.rmi.*;

/**
 * This is a remote interface.
 *
 * @author M. L. Liu
 */
public interface ServerInterface extends Remote {

    /**
     * This remote method returns true if the connection is successfull or false if it isnt
     * @param client
     * @param nickname
     * @param password
     * @return the user or null.
     * @throws java.rmi.RemoteException
     */
    public Boolean connect(ClientInterface client, String nickname, String password) throws java.rmi.RemoteException;

    /**
     * This remote method lets the server know the client is disconnecting
     * @param nickname
     * @param password
     * @return 
     * @throws java.rmi.RemoteException
     */
    public Boolean disconnect(String nickname, String password) throws java.rmi.RemoteException;

    /**
     * This remote method returns true if the sign up is successfull or false if it isnt
     * @param client
     * @param nickname
     * @param password
     * @return the user or null.
     * @throws java.rmi.RemoteException
     */
    public Boolean signUp(ClientInterface client, String nickname, String password) throws java.rmi.RemoteException;

    /**
     * This remote method returns true if the deletion is successfull or false if it isnt
     * @param nickname 
     * @param password 
     * @return the user or null.
     * @throws java.rmi.RemoteException
     */
    public Boolean deleteUser(String nickname, String password) throws java.rmi.RemoteException;

    /**
     * This remote method returns the user if the connection is successfull or null if it isnt
     * @param nickname
     * @param password
     * @param friend
     * @return the user or null.
     * @throws java.rmi.RemoteException
     */
    public Boolean createFriendRequest(String nickname, String password, String friend) throws java.rmi.RemoteException;

    /**
     * This remote method returns true if the connection is successfull or false if it isnt
     * @param nickname
     * @param password
     * @param friend
     * @return the user or null.
     * @throws java.rmi.RemoteException
     */
    public Boolean acceptFriendRequest(String nickname, String password, String friend) throws java.rmi.RemoteException;

    /**
     * This remote method returns true if the connection is successfull or false if it isnt
     * @param nickname
     * @param password
     * @param friend
     * @return the user or null.
     * @throws java.rmi.RemoteException
     */
    public Boolean rejectFriendRequest(String nickname, String password, String friend) throws java.rmi.RemoteException;

    /**
     * This remote method returns the user after updating it
     * @param nickname
     * @param password
     * @param friend
     * @return the user or null.
     * @throws java.rmi.RemoteException
     */
    public Boolean removeFriend(String nickname, String password, String friend) throws java.rmi.RemoteException;

}  //end interface
