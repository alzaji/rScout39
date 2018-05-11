/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.siliconvalley.scout39.modelo.*;

/**
 *
 * @author aruizdlt
 */
@Named(value = "beanEventos")
@SessionScoped
public class beanEventos implements Serializable {

    private long idEvento = 1;

    private Eventos evento;
    private List<Eventos> eventosTHA;
    private List<Eventos> eventosSIRYU;
    private List<Eventos> eventosKIM;
    private List<Eventos> eventosALMOGAMA;
    @Inject
    private ControlAutorizacion control;

    public beanEventos() {
        eventosTHA = new ArrayList<>();
        eventosSIRYU = new ArrayList<>();
        eventosSIRYU.add(crearEvento("Evento1", "Este es el evento de prueba", new Date(), new BigDecimal(36.7147093), new BigDecimal(-4.4757148)));
        eventosSIRYU.add(crearEvento("Evento2", "Este es el evento de prueba2", new Date(), new BigDecimal(36.7147093), new BigDecimal(-4.4757148)));
        eventosKIM = new ArrayList<>();
        eventosALMOGAMA = new ArrayList<>();
    }

    public List<Eventos> misEventos() {
        switch (control.getUsuario().getRoles().getNombrerol()) {

            case "ScouterTHA":
            case "EducandoTHA":
                return eventosTHA;

            case "ScouterKIM":
            case "EducandoKIM":
                return eventosKIM;

            case "ScouterSIRYU":
            case "EducandoSIRYU":
                return eventosSIRYU;

            case "ScouterALMOGAMA":
            case "EducandoALMOGAMA":
                return eventosALMOGAMA;

            default:
                return new ArrayList<Eventos>();
        }
    }

    public String doCrearEvento(String nombre, String descripcion, String fecha, String latitud, String longitud) {
        Eventos evento = crearEvento(nombre, descripcion, new Date(fecha), new BigDecimal(latitud), new BigDecimal(longitud));
        switch (control.getUsuario().getRoles().getNombrerol()) {
            case "ScouterTHA":
                eventosTHA.add(evento);
                return "tha.xhtml";
            case "ScouterKIM":
                eventosKIM.add(evento);
                return "kim.xhtml";
            case "ScouterSIRYU":
                eventosSIRYU.add(evento);
                return "siryu.xhtml";
            case "ScouterALMOGAMA":
                eventosALMOGAMA.add(evento);
                return "almogama.xhtml";
            default:
                return "index.xhtml";
        }        
    }

    private Eventos crearEvento(String nombre, String descripcion, Date fecha, BigDecimal latitud, BigDecimal longitud) {
        Eventos evento = new Eventos();
        evento.setNombre(nombre);
        evento.setId(idEvento);
        idEvento++;
        evento.setDescripcion(descripcion);
        evento.setFecha(fecha);
        evento.setLatitud(latitud);
        evento.setLongitud(longitud);
        return evento;
    }

    public Eventos getEvento() {
        return evento;
    }

    public void setEvento(Eventos evento) {
        this.evento = evento;
    }

    public List<Eventos> getEventosTHA() {
        return eventosTHA;
    }

    public void setEventosTHA(List<Eventos> eventosTHA) {
        this.eventosTHA = eventosTHA;
    }

    public List<Eventos> getEventosSIRYU() {
        return eventosSIRYU;
    }

    public void setEventosSIRYU(List<Eventos> eventosSIRYU) {
        this.eventosSIRYU = eventosSIRYU;
    }

    public List<Eventos> getEventosKIM() {
        return eventosKIM;
    }

    public void setEventosKIM(List<Eventos> eventosKIM) {
        this.eventosKIM = eventosKIM;
    }

    public List<Eventos> getEventosALMOGAMA() {
        return eventosALMOGAMA;
    }

    public void setEventosALMOGAMA(List<Eventos> eventosALMOGAMA) {
        this.eventosALMOGAMA = eventosALMOGAMA;
    }

}
