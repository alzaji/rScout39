/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import org.siliconvalley.scout39.modelo.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/**
 *
 * @author hidden-process
 */
@Named(value = "ControlAsistencia")
@SessionScoped
public class ControlAsistencia implements Serializable{
    
    private Map<Eventos,List<Progresion>> eventosKIM;
    private Map<Eventos,List<Progresion>> eventosSIRYU;
    private Map<Eventos,List<Progresion>> eventosTHA;
    private Map<Eventos,List<Progresion>> eventosALMOGAMA;
    
    @Inject
    private ControlAutorizacion control;
    
    public ControlAsistencia(){
      eventosKIM = new HashMap<>();
      eventosSIRYU = new HashMap<>();
      eventosTHA = new HashMap<>();
      eventosALMOGAMA = new HashMap<>();
    
    }
    
    public String AsistirEvento(Eventos i){
        
        List<Progresion> p = new ArrayList<>();
        
        switch(control.getUsuario().getRoles().getNombrerol()){
            case "EducandoKIM" : eventosKIM.put(i, p);
            return "kim.xhtml";
            case "EducandoSIRYU" : eventosSIRYU.put(i,p);
            return "siryu.xhtml";
            case "EducandoTHA" : eventosTHA.put(i, p);
            return "tha.xhtml";
            case "EducandoALMOGAMA" : eventosALMOGAMA.put(i, p);
            return "almogama.xhtml";
            default : return "index.xhtml";
        }
            
    }
    
    public String NoAsistir(Eventos i){
        
        switch(control.getUsuario().getRoles().getNombrerol()){
            case "EducandoKIM" : eventosKIM.remove(i);
            return "kim.xhtml";
            case "EducandoSIRYU" : eventosSIRYU.remove(i);
            return "siryu.xhtml";
            case "EducandoTHA" : eventosTHA.remove(i);
            return "tha.xhtml";
            case "EducandoALMOGAMA" : eventosALMOGAMA.remove(i);
            return "almogama.xhtml";
            default : return "index.xhtml";
        }
    }

    public ControlAutorizacion getControl() {
        return control;
    }

    public void setControl(ControlAutorizacion control) {
        this.control = control;
    }
    

    public Map<Eventos, List<Progresion>> getEventosKIM() {
        return eventosKIM;
    }

    public void setEventosKIM(Map<Eventos, List<Progresion>> eventosKIM) {
        this.eventosKIM = eventosKIM;
    }

    public Map<Eventos, List<Progresion>> getEventosSIRYU() {
        return eventosSIRYU;
    }

    public void setEventosSIRYU(Map<Eventos, List<Progresion>> eventosSIRYU) {
        this.eventosSIRYU = eventosSIRYU;
    }

    public Map<Eventos, List<Progresion>> getEventosTHA() {
        return eventosTHA;
    }

    public void setEventosTHA(Map<Eventos, List<Progresion>> eventosTHA) {
        this.eventosTHA = eventosTHA;
    }

    public Map<Eventos, List<Progresion>> getEventosALMOGAMA() {
        return eventosALMOGAMA;
    }

    public void setEventosALMOGAMA(Map<Eventos, List<Progresion>> eventosALMOGAMA) {
        this.eventosALMOGAMA = eventosALMOGAMA;
    }
    
}
