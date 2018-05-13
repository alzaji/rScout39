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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
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
@SessionScoped
public class beanEventos implements Serializable {

    private long idEvento = 1;

    private Eventos evento;
    private Comentarios comentario;
    private List<Eventos> eventosTHA;
    private List<Eventos> eventosSIRYU;
    private List<Eventos> eventosKIM;
    private List<Eventos> eventosALMOGAMA;
    private Map<Eventos, List<Comentarios>> comentariosTHA;
    private Map<Eventos, List<Comentarios>> comentariosSIRYU;
    private Map<Eventos, List<Comentarios>> comentariosKIM;
    private Map<Eventos, List<Comentarios>> comentariosALMOGAMA;

    @Inject
    private ControlAutorizacion control;

    public beanEventos() {
        eventosTHA = new ArrayList<>();
        eventosSIRYU = new ArrayList<>();
        eventosKIM = new ArrayList<>();
        eventosALMOGAMA = new ArrayList<>();
        comentariosALMOGAMA = new HashMap<>();
        comentariosKIM = new HashMap<>();
        comentariosSIRYU = new HashMap<>();
        comentariosTHA = new HashMap<>();
        Eventos e1 = crearEvento("Evento1", "Este es el evento de prueba", new Date(), new BigDecimal(36.7147093), new BigDecimal(-4.4757148));
        Eventos e2 = crearEvento("Evento2", "Este es el evento de prueba2", new Date(), new BigDecimal(36.7147093), new BigDecimal(-4.4757148));
        eventosSIRYU.add(e1);
        eventosSIRYU.add(e2);
        eventosTHA.add(e1);
        eventosTHA.add(e2);
        eventosKIM.add(e1);
        eventosKIM.add(e2);
        eventosALMOGAMA.add(e1);
        eventosALMOGAMA.add(e2);
        comentariosSIRYU.put(e1, new ArrayList<Comentarios>());
        comentariosSIRYU.put(e2, new ArrayList<Comentarios>());
        comentariosTHA.put(e1, new ArrayList<Comentarios>());
        comentariosTHA.put(e2, new ArrayList<Comentarios>());
        comentariosKIM.put(e1, new ArrayList<Comentarios>());
        comentariosKIM.put(e2, new ArrayList<Comentarios>());
        comentariosALMOGAMA.put(e1, new ArrayList<Comentarios>());
        comentariosALMOGAMA.put(e2, new ArrayList<Comentarios>());
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

    public List<Comentarios> doObtenerComentarios(Eventos e) {
        switch (control.getUsuario().getRoles().getNombrerol()) {

            case "ScouterTHA":
            case "EducandoTHA":
                return comentariosTHA.get(e);

            case "ScouterKIM":
            case "EducandoKIM":
                return comentariosKIM.get(e);

            case "ScouterSIRYU":
            case "EducandoSIRYU":
                return comentariosSIRYU.get(e);

            case "ScouterALMOGAMA":
            case "EducandoALMOGAMA":
                return comentariosALMOGAMA.get(e);

            default:
                return new ArrayList<Comentarios>();
        }

    }

    public String doNuevoComentario(Eventos e) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String cuerpo = request.getParameter("formComentarioEvento" + e.getId().toString() + ":textoComentario");
        ComentariosUsuarioEventosDebil idComentario = crearIdComentario(e.getId(), control.getUsuario().getId());
        Comentarios c = crearComentario(idComentario, cuerpo);
        c.setUsuario(control.getUsuario());

        switch (control.getUsuario().getRoles().getNombrerol()) {
            case "EducandoTHA":
            case "ScouterTHA":
                comentariosTHA.get(e).add(c);
                return "tha.xhtml?faces-redirect=true";
            case "EducandoKIM":
            case "ScouterKIM":
                comentariosKIM.get(e).add(c);
                return "kim.xhtml?faces-redirect=true";
            case "EducandoSIRYU":
            case "ScouterSIRYU":
                comentariosSIRYU.get(e).add(c);
                return "siryu.xhtml?faces-redirect=true";
            case "EducandoALMOGAMA":
            case "ScouterALMOGAMA":
                comentariosALMOGAMA.get(e).add(c);
                return "almogama.xhtml?faces-redirect=true";
            default:
                return "index.xhtml";
        }
    }

    public int tamañoListaComentarios(Eventos evento) {
        switch (control.getUsuario().getRoles().getNombrerol()) {

            case "ScouterTHA":
            case "EducandoTHA":
                return comentariosTHA.get(evento).size();

            case "ScouterKIM":
            case "EducandoKIM":
                return comentariosKIM.get(evento).size();

            case "ScouterSIRYU":
            case "EducandoSIRYU":
                return comentariosSIRYU.get(evento).size();

            case "ScouterALMOGAMA":
            case "EducandoALMOGAMA":
                return comentariosALMOGAMA.get(evento).size();

            default:
                return 0;
        }
    }

    public String doBorrarEvento(Eventos e) {
        switch (control.getUsuario().getRoles().getNombrerol()) {
            case "ScouterTHA":
                borrarEvento(eventosTHA, comentariosTHA, e);
                return "tha.xhtml?faces-redirect=true";
            case "ScouterKIM":
                borrarEvento(eventosKIM, comentariosKIM, e);
                return "kim.xhtml?faces-redirect=true";
            case "ScouterSIRYU":
                borrarEvento(eventosSIRYU, comentariosSIRYU, e);
                return "siryu.xhtml?faces-redirect=true";
            case "ScouterALMOGAMA":
                borrarEvento(eventosALMOGAMA, comentariosALMOGAMA, e);
                return "almogama.xhtml?faces-redirect=true";
            default:
                return "index.xhtml";
        }
    }

    public String doCrearEvento() {
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
                comentariosTHA.put(evento, new ArrayList<Comentarios>());
                return "tha.xhtml?faces-redirect=true";
            case "ScouterKIM":
                eventosKIM.add(evento);
                comentariosKIM.put(evento, new ArrayList<Comentarios>());
                return "kim.xhtml?faces-redirect=true";
            case "ScouterSIRYU":
                eventosSIRYU.add(evento);
                comentariosSIRYU.put(evento, new ArrayList<Comentarios>());
                return "siryu.xhtml?faces-redirect=true";
            case "ScouterALMOGAMA":
                eventosALMOGAMA.add(evento);
                comentariosALMOGAMA.put(evento, new ArrayList<Comentarios>());
                return "almogama.xhtml?faces-redirect=true";
            default:
                return "index.xhtml?faces-redirect=true";
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
                return "tha.xhtml?faces-redirect=true";
            case "ScouterKIM":
                modificarEvento(eventosKIM, evento);
                return "kim.xhtml?faces-redirect=true";
            case "ScouterSIRYU":
                modificarEvento(eventosSIRYU, evento);
                return "siryu.xhtml?faces-redirect=true";
            case "ScouterALMOGAMA":
                modificarEvento(eventosALMOGAMA, evento);
                return "almogama.xhtml?faces-redirect=true";
            default:
                return "index.xhtml?faces-redirect=true";
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

    private void modificarEvento(List<Eventos> eventos, Eventos e) {
        int posicion = eventos.indexOf(e);
        eventos.toArray()[posicion] = e;
    }

    private void borrarEvento(List<Eventos> eventos, Map<Eventos, List<Comentarios>> comentarios, Eventos e) {
        eventos.remove(e);
        comentarios.remove(e);
    }

    private Comentarios crearComentario(ComentariosUsuarioEventosDebil idComentario, String cuerpo) {
        Comentarios c = new Comentarios();
        c.setIdComentarios(idComentario);
        c.setCuerpo(cuerpo);
        return c;
    }

    private ComentariosUsuarioEventosDebil crearIdComentario(Long idEvento, Long idUsuario) {
        ComentariosUsuarioEventosDebil cId = new ComentariosUsuarioEventosDebil();
        cId.setIdEvento(idEvento);
        cId.setIdUsuario(idUsuario);
        cId.setIdComentarios(new Random().nextLong());
        return cId;
    }

    public Comentarios getComentario() {
        return comentario;
    }

    public void setComentario(Comentarios comentario) {
        this.comentario = comentario;
    }

    public Map<Eventos, List<Comentarios>> getComentariosTHA() {
        return comentariosTHA;
    }

    public void setComentariosTHA(Map<Eventos, List<Comentarios>> comentariosTHA) {
        this.comentariosTHA = comentariosTHA;
    }

    public Map<Eventos, List<Comentarios>> getComentariosSIRYU() {
        return comentariosSIRYU;
    }

    public void setComentariosSIRYU(Map<Eventos, List<Comentarios>> comentariosSIRYU) {
        this.comentariosSIRYU = comentariosSIRYU;
    }

    public Map<Eventos, List<Comentarios>> getComentariosKIM() {
        return comentariosKIM;
    }

    public void setComentariosKIM(Map<Eventos, List<Comentarios>> comentariosKIM) {
        this.comentariosKIM = comentariosKIM;
    }

    public Map<Eventos, List<Comentarios>> getComentariosALMOGAMA() {
        return comentariosALMOGAMA;
    }

    public void setComentariosALMOGAMA(Map<Eventos, List<Comentarios>> comentariosALMOGAMA) {
        this.comentariosALMOGAMA = comentariosALMOGAMA;
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
