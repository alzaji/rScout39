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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;
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

    private long idEvento = 1;

    private List<Eventos> listaEventos = new ArrayList<>();
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

    @EJB
    private NegocioEventosLocal eventos;

    @Resource
    private UserTransaction userTransaction;

    public beanEventos() {

    }

    public void misEventos(int idGrupo) {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String nombre = request.getParameter("seleccionarEvento:opcion");
            if (nombre.equals("0") || nombre.equals("1")) {
                this.setListaEventos(eventos.eventosProximos(new Long(idGrupo)));
            } else {
                this.setListaEventos(eventos.eventosPasados(new Long(idGrupo)));
            }
        } catch (NullPointerException npe) {
            this.setListaEventos(eventos.eventosProximos(new Long(idGrupo)));
        }
        //return "siryu.xhtml";
    }

    public List<Comentarios> doObtenerComentarios(Eventos e) {
//        switch (control.getUsuario().getRoles().getNombrerol()) {
//
//            case "ScouterTHA":
//            case "EducandoTHA":
//                return comentariosTHA.get(e);
//
//            case "ScouterKIM":
//            case "EducandoKIM":
//                return comentariosKIM.get(e);
//
//            case "ScouterSIRYU":
//            case "EducandoSIRYU":
//                return comentariosSIRYU.get(e);
//
//            case "ScouterALMOGAMA":
//            case "EducandoALMOGAMA":
//                return comentariosALMOGAMA.get(e);
//
//            default:
//                return new ArrayList<Comentarios>();
//        }
        return null;
    }

    public String doNuevoComentario(Eventos e) {
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        String cuerpo = request.getParameter("formComentarioEvento" + e.getId().toString() + ":textoComentario");
//        ComentariosUsuarioEventosDebil idComentario = crearIdComentario(e.getId(), control.getUsuario().getId());
//        Comentarios c = crearComentario(idComentario, cuerpo);
//        c.setUsuario(control.getUsuario());
//
//        switch (control.getUsuario().getRoles().getNombrerol()) {
//            case "EducandoTHA":
//            case "ScouterTHA":
//                comentariosTHA.get(e).add(c);
//                return "tha.xhtml?faces-redirect=true";
//            case "EducandoKIM":
//            case "ScouterKIM":
//                comentariosKIM.get(e).add(c);
//                return "kim.xhtml?faces-redirect=true";
//            case "EducandoSIRYU":
//            case "ScouterSIRYU":
//                comentariosSIRYU.get(e).add(c);
//                return "siryu.xhtml?faces-redirect=true";
//            case "EducandoALMOGAMA":
//            case "ScouterALMOGAMA":
//                comentariosALMOGAMA.get(e).add(c);
//                return "almogama.xhtml?faces-redirect=true";
//            default:
//                return "index.xhtml";
//        }
        return null;
    }

    public int tamañoListaComentarios(Eventos evento) {
//        switch (control.getUsuario().getRoles().getNombrerol()) {
//
//            case "ScouterTHA":
//            case "EducandoTHA":
//                return comentariosTHA.get(evento).size();
//
//            case "ScouterKIM":
//            case "EducandoKIM":
//                return comentariosKIM.get(evento).size();
//
//            case "ScouterSIRYU":
//            case "EducandoSIRYU":
//                return comentariosSIRYU.get(evento).size();
//
//            case "ScouterALMOGAMA":
//            case "EducandoALMOGAMA":
//                return comentariosALMOGAMA.get(evento).size();
//
//            default:
//                return 0;
//        }
        return 0;
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
            case "Scouter":
                try {
                    if (control.getGrupo().getNombre().equals("Unidad Esculta Siryu")) {
                        //userTransaction.begin();
                        eventos.crearEvento(evento, new Long(32));
                        //userTransaction.commit();
                    }
                } catch (Exception re) {
                    Logger.getLogger(NegocioEventos.class.getName()).log(Level.SEVERE, re.getMessage(), re.getCause());
                }
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
        Eventos evento = crearEventoId(idEvento, nombre, descripcion, new Date(), BigDecimal.valueOf(36.7147093), new BigDecimal(36.7147093).setScale(11, BigDecimal.ROUND_HALF_UP));
        switch (control.getUsuario().getRoles().getNombrerol()) {
            case "ScouterTHA":
                modificarEvento(eventosTHA, evento);
                return "tha.xhtml?faces-redirect=true";
            case "ScouterKIM":
                modificarEvento(eventosKIM, evento);
                return "kim.xhtml?faces-redirect=true";
            case "Scouter":
                try {
                    if (control.getGrupo().getNombre().equals("Unidad Esculta Siryu")) {
                        //userTransaction.begin();
                        eventos.modificarEvento(evento);
                        //userTransaction.commit();
                    }
                } catch (Exception re) {
                    Logger.getLogger(NegocioEventos.class.getName()).log(Level.SEVERE, re.getMessage(), re.getCause());
                }
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

//    private Comentarios crearComentario(ComentariosUsuarioEventosDebil idComentario, String cuerpo) {
//        Comentarios c = new Comentarios();
//        c.setIdComentarios(idComentario);
//        c.setCuerpo(cuerpo);
//        return c;
//    }
//    private ComentariosUsuarioEventosDebil crearIdComentario(Long idEvento, Long idUsuario) {
//        ComentariosUsuarioEventosDebil cId = new ComentariosUsuarioEventosDebil();
//        cId.setIdEvento(idEvento);
//        cId.setIdUsuario(idUsuario);
//        cId.setIdComentarios(new Random().nextLong());
//        return cId;
//    }
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

    public List<Eventos> getListaEventos() {
        Logger.getLogger(beanEventos.class.getName()).log(Level.WARNING, "Tamaño lista getter: " + this.listaEventos.size());
        return listaEventos;
    }

    public void setListaEventos(List<Eventos> listaEventos) {
        this.listaEventos = listaEventos;
        Logger.getLogger(beanEventos.class.getName()).log(Level.WARNING, "Tamaño lista setter: " + this.listaEventos.size());
    }

}
