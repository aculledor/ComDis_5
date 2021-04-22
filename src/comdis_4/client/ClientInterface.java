package comdis_4.client;

// A simple RMI interface file - M. Liu
import java.rmi.*;
import java.util.List;
import java.util.Map;

/**
 * This is a remote interface.
 *
 * @author M. L. Liu
 */
public interface ClientInterface extends Remote {

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

    /**
     * This remote method gives the other client your message
     *
     * @param sourceNickname - the name of the request's source.
     * @throws java.rmi.RemoteException
     */
    public void receiveFriendRequest(String sourceNickname) throws java.rmi.RemoteException;

    /**
     * This remote method sets the Connected Friend list of the client
     *
     * @param connectedFriends
     * @throws java.rmi.RemoteException
     */
    public void setFriendList(Map<String, ClientInterface> connectedFriends) throws java.rmi.RemoteException;

    /**
     * This remote method sets the Friend Request list of the client
     *
     * @param friendRequests
     * @throws java.rmi.RemoteException
     */
    public void setFriendRequestList(List<String> friendRequests) throws java.rmi.RemoteException;

    /**
     * This remote method gives the other client your message
     *
     * @param nickname
     * @param message - an string containing the message.
     * @throws java.rmi.RemoteException
     */
    public void receiveMessage(String nickname, String message) throws java.rmi.RemoteException;
    
    

}  //end interface
