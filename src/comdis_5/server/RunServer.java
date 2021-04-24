package comdis_5.server;

import comdis_5.ServerGUI_Ex;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * This class represents the object server for a distributed object of class
 * Hello, which implements the remote interface HelloInterface.
 *
 * @author M. L. Liu
 */
public class RunServer {

    public static void main(String args[]) {
        try{
            ServerImplementation server = new ServerImplementation();
            server.start();
        }catch(RemoteException | SQLException e){
            System.exit(1);
        }
    } // end main

} // end class
