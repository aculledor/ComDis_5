package comdis_5.server;

import comdis_5.client.ClientInterface;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
                try{
                    if (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        System.out.println(data);
                        Iterator<ClientInterface> iterator = onlineClients.keySet().iterator();
                        ClientInterface client;
                        while(iterator.hasNext()){
                            client = iterator.next();
                            try {
                                if(onlineClients.get(client) == 0){
                                    onlineClients.remove(client);
                                    client.receiveMessage("Tu subscripcion ha caducado y has sido eliminado de la lista de mensajer??a.");
                                    gui.appendText("Borrando a ["+client.toString()+"] \n\tpor timeout.");
                                }else{
                                    client.receiveData(Double.parseDouble(data));
                                    onlineClients.put(client, onlineClients.get(client) - 1);
                                }
                            } catch (RemoteException ex) {
                                gui.appendText("Conexion con ["+client.toString()+"] \n\tno conseguida. Borrandolo del sistema.");
                                onlineClients.remove(client);
                            }
                        }
                    }
                    else beeperHandle.cancel(true);
                }catch(Throwable t){
                    t.printStackTrace();
                }
            };
            beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, 1, TimeUnit.SECONDS);
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void turnOff(){
        beeperHandle.cancel(true);
    }

    public String getUsers(){
        return this.onlineClients.keySet().toString();
    }

    @Override
    public Boolean subscribe(ClientInterface client, Integer seconds) throws RemoteException {
        try {
            if(onlineClients.containsKey(client) || seconds < 1){
                return false;
            }
            onlineClients.put(client, seconds);
            client.receiveMessage("Te has subscrito");
            gui.appendText("["+client.toString()+"] \n\tse ha subscrito por "+seconds+" segundos");
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
            gui.appendText("["+client.toString()+"] \n\tse ha desconectado");
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
            client.receiveMessage("Has renovado tu subscripci??n. Duraci??n actual: "+seconds+" segundos");
            gui.appendText("["+client.toString()+"] \n\tha renovado su subscripci??n por "+seconds+" segundos");
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }
    
} // end class
