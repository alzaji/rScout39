/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.siliconvalley.scout39.modelo.*;
import org.siliconvalley.scout39.negocio.NegocioEventos;
import org.siliconvalley.scout39.negocio.NegocioEventosLocal;

/**
 *
 * @author aruizdlt
 */
@Named(value = "beanEventos")
@ManagedBean
@SessionScoped
public class beanEventos implements Serializable {

    private Eventos infoEvento;
    private List<Eventos> listaEventos = new ArrayList<>();
    private Eventos evento;
    private Comentarios comentario;
    private Comentarios respuesta;

    @Inject
    private ControlAutorizacion control;

    @EJB
    private NegocioEventosLocal eventos;

    public beanEventos() {

    }

    public void misEventos(int idGrupo) {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String nombre = request.getParameter("seleccionarEvento:opcion");
            if (nombre.equals("0") || nombre.equals("1")) {
                this.setListaEventos(eventos.eventosProximos(control.getGrupo().getId()));
            } else {
                this.setListaEventos(eventos.eventosPasados(control.getGrupo().getId()));
            }
        } catch (NullPointerException npe) {
            this.setListaEventos(eventos.eventosProximos(control.getGrupo().getId()));
        }
    }

    public String buscarEvento(Eventos evento) {
        infoEvento = eventos.buscarEvento(evento);
        return "evento.xhtml";
    }

    public List<Comentarios> doObtenerComentarios(Eventos e) {
        return eventos.listaComentarios(control.getGrupo().getId(), e);
    }

    public List<Comentarios> doObtenerRespuestasComentarios(Comentarios c) {
        return eventos.listaRespuestasComentarios(c);
    }
    
    public List<Progresion> doListaParticipantes(Eventos e){
        return eventos.obtenerParticipantes(e);
    }
    
    public boolean doComprobarAsistencia(Eventos e){
        return eventos.comprobarAsistencia(control.getUsuario(), e);        
    }
    
    public void doAsistirEvento(Eventos e){
        eventos.asistirEvento(control.getUsuario(), e);     
        //return "siryu.xhtml?faces-redirect=true";
    }
    
    public void doNoAsistirEvento(Eventos e){
        eventos.noAsistirEvento(control.getUsuario(), e);
        //return "siryu.xhtml?faces-redirect=true";        
    }
    
    public String doNuevoComentario(Eventos e) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String cuerpo = request.getParameter("formComentarioEvento:cuerpoComentario");
        Comentarios c = new Comentarios();
        c.setUsuario(control.getUsuario());
        c.setCuerpo(cuerpo);
        c.setEventoC(e);
        
        eventos.nuevoComentario(c);

        return "evento.xhtml?faces-redirect=true";
    }

    public String doRespuestaComentario(Eventos e, Comentarios c, int indice) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String cuerpo = request.getParameter("Cpadre:"+indice+":formRespuestaComentarioEvento:cuerpoRespuestaComentario");
        Comentarios respuesta = new Comentarios();
        respuesta.setUsuario(control.getUsuario());
        respuesta.setCuerpo(cuerpo);
        respuesta.setEventoC(e);
        respuesta.setRespuesta(c);            
        eventos.respuestaComentario(respuesta);
        return "evento.xhtml?faces-redirect=true";
    }

   
    public String doBorrarEvento(Eventos e) {
        switch (control.getUsuario().getRoles().getNombrerol()) {
            case "ScouterTHA":
                eventos.borrarEvento(e);
                return "tha.xhtml?faces-redirect=true";
            case "ScouterKIM":
                eventos.borrarEvento(e);
                return "kim.xhtml?faces-redirect=true";
            case "Scouter":
                try {
                    if (control.getGrupo().getNombre().equals("Unidad Esculta Siryu")) {
                        //userTransaction.begin();
                        eventos.borrarEvento(e);
                        //userTransaction.commit();
                    }
                } catch (Exception re) {
                    Logger.getLogger(NegocioEventos.class.getName()).log(Level.SEVERE, re.getMessage(), re.getCause());
                }
                return "siryu.xhtml?faces-redirect=true";
            case "ScouterALMOGAMA":
                eventos.borrarEvento(e);
                return "almogama.xhtml?faces-redirect=true";
            default:
                return "index.xhtml";
        }
    }

    public String doCrearEvento() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String nombre = request.getParameter("formCrearEvento:crearNombre");
        String descripcion = request.getParameter("formCrearEvento:crearDescripcion");
        String fecha = request.getParameter("formCrearEvento:crearFecha");
        String latitud = request.getParameter("formCrearEvento:crearLatitud");
        String longitud = request.getParameter("formCrearEvento:crearLongitud");
        Eventos evento = crearEvento(nombre, descripcion, fecha, new BigDecimal(latitud), new BigDecimal(longitud));
        switch (control.getUsuario().getRoles().getNombrerol()) {
            case "ScouterTHA":
//                eventosTHA.add(evento);
//                comentariosTHA.put(evento, new ArrayList<Comentarios>());
                return "tha.xhtml?faces-redirect=true";
            case "ScouterKIM":
//                eventosKIM.add(evento);
//                comentariosKIM.put(evento, new ArrayList<Comentarios>());
                return "kim.xhtml?faces-redirect=true";
            case "Scouter":
                try {
                    eventos.crearEvento(evento, control.getGrupo().getId());
                } catch (Exception re) {
                    Logger.getLogger(NegocioEventos.class.getName()).log(Level.SEVERE, re.getMessage(), re.getCause());
                }
                return "siryu.xhtml?faces-redirect=true";
            case "ScouterALMOGAMA":
//                eventosALMOGAMA.add(evento);
//                comentariosALMOGAMA.put(evento, new ArrayList<Comentarios>());
                return "almogama.xhtml?faces-redirect=true";
            default:
                return "index.xhtml?faces-redirect=true";
        }
    }

    public String doModificarEvento() {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String nombre = request.getParameter("formModificarEvento:modificarNombre");
            String descripcion = request.getParameter("formModificarEvento:modificarDescripcion");
            String fecha = request.getParameter("formModificarEvento:modificarFecha");
            String latitud = request.getParameter("formModificarEvento:modificarLatitud");
            String longitud = request.getParameter("formModificarEvento:modificarLongitud");
            infoEvento.setNombre(nombre);
            infoEvento.setDescripcion(descripcion);
            if (fecha != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                Date date = sdf.parse(fecha);
                infoEvento.setFecha(date);
            }
            infoEvento.setLatitud(new BigDecimal(latitud));
            infoEvento.setLongitud(new BigDecimal(longitud));
        } catch (Exception ex) {
            Logger.getLogger(beanEventos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        switch (control.getUsuario().getRoles().getNombrerol()) {
            case "ScouterTHA":

                //modificarEvento(eventosTHA, evento);
                return "tha.xhtml?faces-redirect=true";
            case "ScouterKIM":
                //modificarEvento(eventosKIM, evento);
                return "kim.xhtml?faces-redirect=true";
            case "Scouter":
                try {
                    infoEvento = eventos.modificarEvento(infoEvento);
                } catch (Exception re) {
                    Logger.getLogger(NegocioEventos.class.getName()).log(Level.SEVERE, re.getMessage(), re.getCause());
                }
                return "evento.xhtml?faces-redirect=true";
            case "ScouterALMOGAMA":
                //modificarEvento(eventosALMOGAMA, evento);
                return "almogama.xhtml?faces-redirect=true";
            default:
                return "index.xhtml?faces-redirect=true";
        }
    }

    private Eventos crearEvento(String nombre, String descripcion, String fecha, BigDecimal latitud, BigDecimal longitud) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            Date date = sdf.parse(fecha);
            Eventos evento = new Eventos();
            evento.setNombre(nombre);
            evento.setDescripcion(descripcion);
            evento.setFecha(date);
            evento.setLatitud(latitud);
            evento.setLongitud(longitud);
            return evento;
        } catch (ParseException ex) {
            Logger.getLogger(beanEventos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String borrarEvento(Eventos e) {
        eventos.borrarEvento(e);
        return "siryu.xhtml?faces-redirect=true";
    }

    public String parseFecha(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        try {
            Date date = sdf.parse(fecha.toString());
            return date.toString();
        } catch (ParseException e) {
            Logger.getLogger(beanEventos.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        }
    }

    public Comentarios getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Comentarios respuesta) {
        this.respuesta = respuesta;
    }

    public Eventos getInfoEvento() {
        return infoEvento;
    }

    public void setInfoEvento(Eventos infoEvento) {
        this.infoEvento = infoEvento;
    }

    public Comentarios getComentario() {
        return comentario;
    }

    public void setComentario(Comentarios comentario) {
        this.comentario = comentario;
    }

    public Eventos getEvento() {
        return evento;
    }

    public void setEvento(Eventos evento) {
        this.evento = evento;
    }

    public List<Eventos> getListaEventos() {
        //Logger.getLogger(beanEventos.class.getName()).log(Level.WARNING, "Tamaño lista getter: " + this.listaEventos.size());
        return listaEventos;
    }

    public void setListaEventos(List<Eventos> listaEventos) {
        this.listaEventos = listaEventos;
        //Logger.getLogger(beanEventos.class.getName()).log(Level.WARNING, "Tamaño lista setter: " + this.listaEventos.size());
    }

}
