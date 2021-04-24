package comdis_5.server;

import comdis_5.client.ClientInterface;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the remote interface HelloInterface.
 *
 * @author M. L. Liu
 */
public class ServerImplementation extends UnicastRemoteObject implements ServerInterface {
    
    private final HashMap<ClientInterface, Integer> onlineClients;
    private ServerGUI gui;
    private ScheduledFuture<?> beeperHandle;

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
        try{
            File myObj = new File("rr1.txt");
            Scanner myReader = new Scanner(myObj);
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            Runnable beeper = () -> {
                if (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    System.out.println(data);
                    onlineClients.keySet().forEach((client) -> {
                        try {
                            if(onlineClients.get(client) == 0){
                                onlineClients.remove(client);
                                client.receiveMessage("Tu subscripcion ha caducado y has sido eliminado de la lista de mensajería.");
                                gui.appendText("Borrando a ["+client.toString()+"] por timeout.");
                            }else{
                                client.receiveData(Double.parseDouble(data));
                                onlineClients.put(client, onlineClients.get(client) - 1);
                            }
                        } catch (RemoteException ex) {
                            gui.appendText("Conexion con ["+client.toString()+"] no conseguida. Borrandolo del sistema.");
                            onlineClients.remove(client);
                        }
                    });
                }
                else beeperHandle.cancel(true);
            };
            beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, 1, TimeUnit.SECONDS);
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void turnOff(){
        beeperHandle.cancel(true);
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
