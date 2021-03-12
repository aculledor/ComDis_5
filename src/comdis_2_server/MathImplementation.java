package comdis_2_server;

import java.rmi.*;
import java.rmi.server.*;

/**
 * This class implements the remote interface HelloInterface.
 *
 * @author M. L. Liu
 */
public class MathImplementation extends UnicastRemoteObject implements MathInterface {

    public MathImplementation() throws RemoteException {
        super();
    }

    public long validatePairs(Double[][] pairs){
        long validated = 0;
        for (Double[] pair : pairs) {
            if( (pair[0] * pair[0]) + (pair[1] * pair[1]) <= 1 )
                validated++;
        }
        return validated;
    }
} // end class
