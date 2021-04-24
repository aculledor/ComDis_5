/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_5.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author aculledor
 */
public class Client {
    private ClientGUI gui;
    private ClientImplementation proxy;
    private String registry;
    private Integer seconds;

    public Client() {
        registry = "rmi://localhost:7777/P2P";
    }
    
    public void start(){
        gui = new ClientGUI(this);
        gui.start();
    }
    
    private void showError(String message){
        if(message == null){
            this.gui.showError("Un error inesperado a ocurrido");
            return;
        }
        this.gui.showError(message);
    }
    
    //CONNECTION FUNCTIONS
    public void subscribe(){
        try{
            proxy = new ClientImplementation(this);
            Boolean bool = proxy.connect();
            if(bool){
                this.gui.cerrarInicio();
                this.gui.setVisible(true);
            }else{
                this.showError("No pudo conectarse al servidor");
                this.gui.setInteractiveInit(true);
            }
        }catch(MalformedURLException | NotBoundException | RemoteException e){
            this.showError(e.getLocalizedMessage());
        }catch(NullPointerException e){
            this.showError("Ocurrió un error insesperado");
        }
    }
    
    public void renew(){
        try{
            proxy = new ClientImplementation(this);
            Boolean bool = proxy.connect();
            if(bool){
                this.gui.cerrarInicio();
                this.gui.setVisible(true);
            }else{
                this.showError("No pudo conectarse al servidor");
                this.gui.setInteractiveInit(true);
            }
        }catch(MalformedURLException | NotBoundException | RemoteException e){
            this.showError(e.getLocalizedMessage());
        }catch(NullPointerException e){
            this.showError("Ocurrió un error insesperado");
        }
    }
    
    public void disconnect(){
        try{
            Boolean bool = proxy.disconnect();
            if(bool){
                this.gui.dispose();
            }else{
                this.showError("No pudo desconectarse del servidor");
            }
        }catch(MalformedURLException | NotBoundException | RemoteException e){
            this.showError(e.toString());
        }catch(NullPointerException e){
            this.showError("Ocurrió un error insesperado");
        }
    }
    
    //USABILITY FUNCTIONS 
    public void receiveMessage(String message){
        this.gui.receiveMessage(message);
    }
    
    public void receiveData(Double data){
        this.gui.receiveData(data);
    }

    
    //GETTERS AND SETTERS
    public ClientGUI getGui() {
        return gui;
    }

    public Client setGui(ClientGUI gui) {
        this.gui = gui;
        return this;
    }

    public ClientImplementation getProxy() {
        return proxy;
    }

    public Client setProxy(ClientImplementation proxy) {
        this.proxy = proxy;
        return this;
    }

    public String getRegistry() {
        return registry;
    }

    public Client setRegistry(String registry) {
        this.registry = registry;
        return this;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public Client setSeconds(Integer seconds) {
        this.seconds = seconds;
        return this;
    }
    
    
    
}
