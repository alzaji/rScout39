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
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.siliconvalley.scout39.modelo.*;

/**
 *
 * @author aruizdlt
 */
@Named(value = "beanEventos")
@ManagedBean
@RequestScoped
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
        eventosKIM = new ArrayList<>();
        eventosALMOGAMA = new ArrayList<>();
        eventosSIRYU.add(crearEvento("Evento1", "Este es el evento de prueba", new Date(), new BigDecimal(36.7147093), new BigDecimal(-4.4757148)));
        eventosSIRYU.add(crearEvento("Evento2", "Este es el evento de prueba2", new Date(), new BigDecimal(36.7147093), new BigDecimal(-4.4757148)));       
        eventosTHA.add(crearEvento("Evento1", "Este es el evento de prueba", new Date(), new BigDecimal(36.7147093), new BigDecimal(-4.4757148)));
        eventosTHA.add(crearEvento("Evento2", "Este es el evento de prueba2", new Date(), new BigDecimal(36.7147093), new BigDecimal(-4.4757148)));
        eventosKIM.add(crearEvento("Evento1", "Este es el evento de prueba", new Date(), new BigDecimal(36.7147093), new BigDecimal(-4.4757148)));
        eventosKIM.add(crearEvento("Evento2", "Este es el evento de prueba2", new Date(), new BigDecimal(36.7147093), new BigDecimal(-4.4757148)));
        eventosALMOGAMA.add(crearEvento("Evento1", "Este es el evento de prueba", new Date(), new BigDecimal(36.7147093), new BigDecimal(-4.4757148)));
        eventosALMOGAMA.add(crearEvento("Evento2", "Este es el evento de prueba2", new Date(), new BigDecimal(36.7147093), new BigDecimal(-4.4757148)));
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

    public void doCrearEvento() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String nombre = request.getParameter("formCrearEvento:crearNombre");
        String descripcion = request.getParameter("formCrearEvento:crearDescripcion");
        //String fecha = request.getParameter("formCrearEvento:crearFecha");
        String latitud = request.getParameter("formCrearEvento:crearLatitud");
        String longitud = request.getParameter("formCrearEvento:crearLongitud");
        Eventos evento = crearEvento(nombre, descripcion, new Date(), new BigDecimal(latitud), new BigDecimal(longitud));
        switch (control.getUsuario().getRoles().getNombrerol()) {
            case "ScouterTHA":
                eventosTHA.add(evento);
                break;
            case "ScouterKIM":
                eventosKIM.add(evento);
                break;
            case "ScouterSIRYU":
                eventosSIRYU.add(evento);
                break;
            case "ScouterALMOGAMA":
                eventosALMOGAMA.add(evento);
                break;
            default:
                break;
        }
    }

    public String doModificarEvento(String idEvento) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String nombre = request.getParameter("formModificarEvento" + idEvento + ":modificarNombre");
        String descripcion = request.getParameter("formModificarEvento" + idEvento + ":modificarDescripcion");
        //String fecha = request.getParameter("formModificarEvento" + idEvento + ":modificarFecha");
        //String latitud = request.getParameter("formModificarEvento" + idEvento + ":modificarLatitud");
        //String longitud = request.getParameter("formModificarEvento" + idEvento + ":modificarLongitud");
        Eventos evento = crearEventoId(idEvento, nombre, descripcion, new Date(), new BigDecimal(36.7147093), new BigDecimal(36.7147093));
        switch (control.getUsuario().getRoles().getNombrerol()) {
            case "ScouterTHA":
                modificarEvento(eventosTHA, evento);
                return "tha.xhtml";
            case "ScouterKIM":
                modificarEvento(eventosKIM, evento);
                return "kim.xhtml";
            case "ScouterSIRYU":
                modificarEvento(eventosSIRYU, evento);
                return "siryu.xhtml";
            case "ScouterALMOGAMA":
                modificarEvento(eventosALMOGAMA, evento);
                return "almogama.xhtml";
            default:
                return "index.xhtml";
        }
    }

    private Eventos crearEventoId(String id, String nombre, String descripcion, Date fecha, BigDecimal latitud, BigDecimal longitud) {
        Eventos evento = new Eventos();
        evento.setNombre(nombre);
        evento.setId(new Long(id));
        evento.setDescripcion(descripcion);
        evento.setFecha(fecha);
        evento.setLatitud(latitud);
        evento.setLongitud(longitud);
        return evento;
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
    
    private void modificarEvento(List<Eventos> eventos, Eventos e){
        int posicion = eventos.indexOf(e);
        eventos.toArray()[posicion] = e;
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
