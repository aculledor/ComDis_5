/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_4;

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
            ImpBD database = new ImpBD();
            System.out.println("INICIO");
            System.out.println(database.getUsers().toString());
            
            ArrayList<String> amigos = new ArrayList<>();amigos.add("Proba_2"); amigos.add("Proba_3");
            User user = new User("Proba_1", "1111", amigos);
            database.addUser(user);
            
            amigos = new ArrayList<>();amigos.add("Proba_1"); amigos.add("Proba_3");
            user = new User("Proba_2", "2222", amigos);
            database.addUser(user);
            
            amigos = new ArrayList<>();amigos.add("Proba_1"); amigos.add("Proba_2");
            user = new User("Proba_3", "3333", amigos);
            database.addUser(user);
            
            
            System.out.println("TRAS METER PROBA");
            System.out.println(database.getUsers().toString());
            
            System.out.println("FINAL");
            database.deleteUser("Proba_1");
            database.deleteUser("Proba_2");
            System.out.println(database.deleteUser("Proba_3"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
