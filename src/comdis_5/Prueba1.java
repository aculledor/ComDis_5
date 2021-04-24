/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_5;


import comdis_5.client.Client;
import comdis_5.server.ServerImplementation;

/**
 *
 * @author aculledor
 */
public class Prueba1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {


            ServerImplementation server = new ServerImplementation();
            server.start();
            Client client1 = new Client();
            client1.start();
            Client client2 = new Client();
            client2.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
