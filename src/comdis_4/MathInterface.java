package comdis_4;

// A simple RMI interface file - M. Liu
import java.rmi.*;
import java.util.ArrayList;

/**
 * This is a remote interface.
 *
 * @author M. L. Liu
 */
public interface MathInterface extends Remote {

    /**
     * This remote method returns athe numer of pairs that validate the math
     * formula.
     *
     * @param pairs - an array containing pairs.
     * @return a String message.
     * @throws java.rmi.RemoteException
     */
    public long validatePairs(ArrayList<Double[]> pairs) throws java.rmi.RemoteException;

} //end interface
