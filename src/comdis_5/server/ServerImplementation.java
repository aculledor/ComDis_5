package comdis_5.server;

import comdis_5.client.ClientInterface;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements the remote interface HelloInterface.
 *
 * @author M. L. Liu
 */
public class ServerImplementation extends UnicastRemoteObject implements ServerInterface {
    
    private final HashMap<ClientInterface, Integer> onlineClients;
    private ServerGUI gui;
    private final String name = "Server_P2P";

    public String getUsers(){
        return this.onlineClients.keySet().toString();
    }
    
    public ServerImplementation() throws RemoteException, SQLException {
        super();
        onlineClients = new HashMap<>();
    }
    
    public void start(){
        gui = new ServerGUI(this);
        gui.start();
    }

    @Override
    public Boolean subscribe(ClientInterface client, Integer seconds) throws RemoteException {
        try {
            if(onlineClients.containsKey(client) || seconds < 1){
                return false;
            }
            onlineClients.put(client, seconds);
            client.receiveMessage("Te has subscrito");
            gui.appendText("["+client.toString()+"] se ha conectado");
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public Boolean unsubscribe(ClientInterface client) throws RemoteException {
        try {
            if(!onlineClients.containsKey(client)){
                return false;
            }
            gui.appendText("["+client.toString()+"] se ha desconectado");
            this.onlineClients.remove(client);
            client.receiveMessage("Te has desconectando");
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }    

    @Override
    public Boolean renew(ClientInterface client, Integer seconds) throws RemoteException {
        try {
            if(!onlineClients.containsKey(client) || seconds < 1){
                return false;
            }
            onlineClients.put(client, seconds);
            client.receiveMessage("Has renovado tu subscripción. Duración actual: "+seconds+" segundos");
            gui.appendText("["+client.toString()+"] ha renovado su subscripción por "+seconds+" segundos");
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }
    
} // end class
