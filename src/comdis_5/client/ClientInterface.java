package comdis_5.client;

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
     * This remote method gives the other client your message
     * @param message - an string containing the message.
     * @throws java.rmi.RemoteException
     */
    public void receiveMessage(String message) throws java.rmi.RemoteException;

    /**
     * This remote method gives the other client your message
     * @param data - a double containing the data.
     * @throws java.rmi.RemoteException
     */
    public void receiveData(Double data) throws java.rmi.RemoteException;
    
    

}  //end interface
