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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.siliconvalley.scout39.modelo.*;
import org.siliconvalley.scout39.negocio.NegocioEventosLocal;
import org.siliconvalley.scout39.negocio.ScoutException;

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
    private Progresion progresion;
    private Progresion mediaProgresion;
    private int participacion;
    private int integracion;
    private int animacion;
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

    public void doMediaProgresion() {
        List<Progresion> pMedia = eventos.mediaProgresion(control.getUsuario());
        participacion = 0;
        integracion = 0;
        animacion = 0;
        if (!pMedia.isEmpty()) {

            for (Progresion p : pMedia) {
                participacion += p.getParticipacion();
                integracion += p.getIntegracion();
                animacion += p.getAnimacion();
            }
            participacion = participacion / pMedia.size();
            integracion = integracion / pMedia.size();
            animacion = animacion / pMedia.size();
        }
    }

    public String buscarEvento(Eventos evento) {
        try {
            infoEvento = eventos.buscarEvento(evento);
            return "evento.xhtml?faces-redirect=true";

        } catch (ScoutException ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            return null;
        }
    }

    public List<Comentarios> doObtenerComentarios(Eventos e) {
        return eventos.listaComentarios(control.getGrupo().getId(), e);
    }

    public List<Comentarios> doObtenerRespuestasComentarios(Comentarios c) {
        return eventos.listaRespuestasComentarios(c);
    }

    public List<Progresion> doListaParticipantes(Eventos e) {
        return eventos.obtenerParticipantes(e);
    }

    public boolean doComprobarAsistencia(Eventos e) {
        return eventos.comprobarAsistencia(control.getUsuario(), e);
    }

    public void doAsistirEvento(Eventos e) {
        try {
            eventos.asistirEvento(control.getUsuario(), e);
        } catch (ScoutException ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
        }

    }

    public void doNoAsistirEvento(Eventos e) {
        try {
            eventos.noAsistirEvento(control.getUsuario(), e);
        } catch (ScoutException ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
        }

    }

    public String doNuevoComentario(Eventos e) {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String cuerpo = request.getParameter("formComentarioEvento:cuerpoComentario");
            Comentarios c = new Comentarios();
            c.setUsuario(control.getUsuario());
            c.setCuerpo(cuerpo);
            c.setEventoC(e);

            eventos.nuevoComentario(c);

            return "evento.xhtml?faces-redirect=true";
        } catch (ScoutException ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            return null;
        }
    }

    public String doRespuestaComentario(Eventos e, Comentarios c, int indice) {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String cuerpo = request.getParameter("Cpadre:" + indice + ":formRespuestaComentarioEvento:cuerpoRespuestaComentario");
            Comentarios respuesta = new Comentarios();
            respuesta.setUsuario(control.getUsuario());
            respuesta.setCuerpo(cuerpo);
            respuesta.setEventoC(e);
            respuesta.setRespuesta(c);
            eventos.respuestaComentario(respuesta);
            return "evento.xhtml?faces-redirect=true";
        } catch (ScoutException ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            return null;
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
        try {
            eventos.crearEvento(evento, control.getGrupo().getId());
            return "listaeventos.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            return null;
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

        try {
            infoEvento = eventos.modificarEvento(infoEvento);
            return "evento.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            return null;
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
        try {
            eventos.borrarEvento(e);
            return "listaeventos.xhtml?faces-redirect=true";
        } catch (ScoutException ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            return null;
        }
    }

    public void rellenarProgresion(Progresion p1, int index) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String integracion = request.getParameter("table:" + index + ":formProgresionEvento:integracion");
        String participacion = request.getParameter("table:" + index + ":formProgresionEvento:participacion");
        String animacion = request.getParameter("table:" + index + ":formProgresionEvento:animacion");

        p1.setAnimacion(Integer.parseInt(animacion));
        p1.setIntegracion(Integer.parseInt(integracion));
        p1.setParticipacion(Integer.parseInt(participacion));
        eventos.rellenarProgresion(p1);
    }

    public String parseFecha(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date = sdf.parse(fecha.toString());
            return date.toLocaleString();
        } catch (ParseException e) {
            Logger.getLogger(beanEventos.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        }
    }
    

    public int getParticipacion() {
        return participacion;
    }

    public void setParticipacion(int participacion) {
        this.participacion = participacion;
    }

    public int getIntegracion() {
        return integracion;
    }

    public void setIntegracion(int integracion) {
        this.integracion = integracion;
    }

    public int getAnimacion() {
        return animacion;
    }

    public void setAnimacion(int animacion) {
        this.animacion = animacion;
    }

    public Progresion getMediaProgresion() {
        return mediaProgresion;
    }

    public void setMediaProgresion(Progresion mediaProgresion) {
        this.mediaProgresion = mediaProgresion;
    }

    public Progresion getProgresion() {
        return progresion;
    }

    public void setProgresion(Progresion progresion) {
        this.progresion = progresion;
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
