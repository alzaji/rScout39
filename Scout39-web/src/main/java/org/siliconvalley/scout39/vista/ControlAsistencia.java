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
    
    @Inject
    private beanEventos evento;
    
    public ControlAsistencia(){
      eventosKIM = new HashMap<>();
      eventosSIRYU = new HashMap<>();
      eventosTHA = new HashMap<>();
      eventosALMOGAMA = new HashMap<>();
              
    }
    
    private Progresion crearProgresion(Long idEvento,Long idUsuario){
        Progresion p = new Progresion();
        p.setIdProgresion(crearIdProgresion(idEvento, idUsuario));
        p.setAnimacion(0);
        p.setIntegracion(0);
        p.setParticipacion(0);
        p.setPromesa("");
        return p;
    }
    
    private ProgresionUsuarioEventosDebil crearIdProgresion(Long idEvento, Long idUsuario){
        ProgresionUsuarioEventosDebil p = new ProgresionUsuarioEventosDebil();
        p.setIdEventos(idEvento);
        p.setIdUsuario(idUsuario);
        return p;
    }
    
    public String asistirEvento(Eventos i){
        
       Progresion p = crearProgresion(i.getId(),control.getUsuario().getId());
        
        switch(control.getUsuario().getRoles().getNombrerol()){
            case "EducandoKIM" : eventosKIM.get(i).add(p);
            return "kim.xhtml";
            case "EducandoSIRYU" : eventosSIRYU.get(i).add(p);
            return "siryu.xhtml";
            case "EducandoTHA" : eventosTHA.get(i).add(p);
            return "tha.xhtml";
            case "EducandoALMOGAMA" : eventosALMOGAMA.get(i).add(p);
            return "almogama.xhtml";
            default : return "index.xhtml";
        }      
    }
    
    public String noAsistir(Eventos i){
        
        Progresion p = crearProgresion(i.getId(),control.getUsuario().getId());
        
        switch(control.getUsuario().getRoles().getNombrerol()){
            case "EducandoKIM" : eventosKIM.get(i).remove(p);
            return "kim.xhtml";
            case "EducandoSIRYU" : eventosSIRYU.get(i).remove(p);
            return "siryu.xhtml";
            case "EducandoTHA" : eventosTHA.get(i).remove(p);
            return "tha.xhtml";
            case "EducandoALMOGAMA" : eventosALMOGAMA.get(i).remove(p);
            return "almogama.xhtml";
            default : return "index.xhtml";
        }
    }
    
    
    
    
    public boolean comprobarAsistencia(Eventos i){
        
        Progresion p = crearProgresion(i.getId(),control.getUsuario().getId());
        
        switch(control.getUsuario().getRoles().getNombrerol()){
            case "EducandoKIM" : 
                return eventosKIM.get(i).contains(p);
 
            case "EducandoSIRYU" : 
                return eventosSIRYU.get(i).contains(p);
                  
            case "EducandoTHA" :
                return eventosTHA.get(i).contains(p);
             
            case "EducandoALMOGAMA" : 
                return eventosALMOGAMA.get(i).contains(p);
                
            default : return false;  
        }   
    }
    
    

    public beanEventos getEvento() {
        return evento;
    }

    public void setEvento(beanEventos evento) {
        this.evento = evento;
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
