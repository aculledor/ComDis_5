/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comdis_4.classes;

import java.io.Serializable;

/**
 *
 * @author aculledor
 */
public class Request implements Serializable {
    private String source;
    private String destination;

    public Request() {
    }

    public Request(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public String getSource() {
        return source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Origen: "+source+"; Destino: "+destination;
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    
}
