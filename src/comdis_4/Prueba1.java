/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_4;

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
            FachadaBD database = new FachadaBD();
            
            try {
                database = new FachadaBD();
                System.out.println(database.getUsers().toString());
                //System.out.println(database.addFriend("Abraham", "Lois"));
                //System.out.println(database.deleteUsers("Chiara"));
                //System.out.println(database.deleteUsers("Miguel"));
                //System.out.println(database.getUser("Chiara"));
                //ArrayList<String> amigos = new ArrayList<>();amigos.add("Chiara"); amigos.add("Lois");
                //User user = new User("Miguel", "7777", amigos);
                //System.out.println(database.addUsers(user));

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
