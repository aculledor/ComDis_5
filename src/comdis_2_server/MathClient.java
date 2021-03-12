package comdis_2_server;

import java.io.*;
import java.rmi.*;

/**
 * This class represents the object client for a distributed
 * object of class Hello, which implements the remote interface
 * HelloInterface.
 * @author M. L. Liu
 */

public class MathClient {

   public static void main(String args[]) {
      try {
         int RMIPort;         
         String hostName;
         InputStreamReader is = new InputStreamReader(System.in);
         BufferedReader br = new BufferedReader(is);
         System.out.println("Enter the RMIRegistry host namer:");
         hostName = br.readLine();
         System.out.println("Enter the RMIregistry port number:");
         String portNum = br.readLine();
         RMIPort = Integer.parseInt(portNum);
         String registryURL =  "rmi://" + hostName+ ":" + portNum + "/hello";  
         // find the remote object and cast it to an interface object
         MathInterface h = (MathInterface)Naming.lookup(registryURL);
         System.out.println("Lookup completed " );
         // invoke the remote method
         Double[][] pairs = new Double[1][2];
         pairs[0][0] = 0.11111;
         pairs[0][1] = 0.22222;
         Long resposta = h.validatePairs(pairs);
         System.out.println("MathClient: " + resposta);
      } // end try 
      catch (Exception e) {
         System.out.println("Exception in MathClient: " + e);
      } 
   } //end main
}//end class
