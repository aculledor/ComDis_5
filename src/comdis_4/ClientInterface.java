package comdis_4;

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
     * This remote method returns the nickname of the proxy's user
     *
     * @return a Boolean confirmation.
     * @throws java.rmi.RemoteException
     */
    public String getNickname() throws java.rmi.RemoteException;

}  //end interface
