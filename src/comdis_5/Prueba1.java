/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_5;


import comdis_5.client.Client;
import comdis_5.database.ImpBD;
import comdis_5.server.ServerImplementation;
import java.util.ArrayList;

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
//            ImpBD database = new ImpBD();
//            System.out.println("INICIO");
//            System.out.println(database.getUsers().toString());
//            
//            Client user = new Client("Proba_1", "1111");
//            database.addUser(user.getNickname(), user.getPassword());
//            
//            user = new Client("Proba_2", "2222");
//            database.addFriend("Proba_1", "Proba_2");
//            database.addUser(user.getNickname(), user.getPassword());
//            
//            user = new Client("Proba_3", "3333");
//            database.addUser(user.getNickname(), user.getPassword());
//            
//            
//            System.out.println("TRAS METER PROBA");
//            System.out.println(database.getUsers().toString());
//            System.out.println(database.getFriends("Proba_1").toString());
//            
//            System.out.println("FINAL");
//            database.deleteUser("Proba_1");
//            database.deleteUser("Proba_2");
//            System.out.println(database.deleteUser("Proba_3"));


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
