package comdis_5.client;

import comdis_5.client.Client;


/**
 * This class represents the object client for a distributed
 * object of class Hello, which implements the remote interface
 * HelloInterface.
 * @author M. L. Liu
 */

public class RunClient {

   public static void main(String args[]) {
       Client client = new Client();
       client.start();
   } //end main
}//end class
