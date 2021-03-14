/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_2_server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aculledor
 */
public class MathHilo extends Thread{
    
    ArrayList<Double[]> pairs;
    ArrayList<Long> result;
    String registry;
    
    public MathHilo(String name, ArrayList<Double[]> pairs, String registry, ArrayList<Long> result){
        super(name);
        this.pairs = pairs;
        this.registry = registry;
        this.result = result;
    }
    
    public void run(){
        MathInterface h;
        try {
            h = (MathInterface) Naming.lookup(this.registry);
            this.result.add(h.validatePairs(this.pairs));
        } catch (Exception ex) {
            Logger.getLogger(MathHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("MathClient: " + this.result.get(0));
    }
}
