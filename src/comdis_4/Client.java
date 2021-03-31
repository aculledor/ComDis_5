/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_4;

import comdis_4.User;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author aculledor
 */
public class Client {
    private ClientGUI gui;
    private ClientImplementation proxy;
    private User myData;

    public Client() {
        //gui = new ClientGUI(this);
    }
    
    public void connect(){
        try{
            proxy = new ClientImplementation(this, "Abraham");
            myData = (User)proxy.connect("rmi://localhost:7777/P2P", "Abraham", "4321");
            System.out.println(myData.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void disconnect(){
        try{
            System.out.println(proxy.disconnect());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void receiveMessage(String message){
        System.out.println(message);
    }
    
    
}
