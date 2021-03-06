/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import org.siliconvalley.scout39.modelo.*;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.siliconvalley.scout39.negocio.NegocioLogin;
import org.siliconvalley.scout39.negocio.ScoutException;

/**
 *
 * @author alzaji
 */
@Named(value = "controlAutorizacion")
@SessionScoped
public class ControlAutorizacion implements Serializable {

    private Usuario usuario;
    private Grupo grupo;
    private Objeto lastobjeto;
    private Privilegios privilegio;

    @EJB
    private NegocioLogin login;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Objeto getLastobjeto() {
        return lastobjeto;
    }

    public void setLastobjeto(Objeto lastobjeto) {
        this.lastobjeto = lastobjeto;
    }

    public Privilegios getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(Privilegios privilegio) {
        this.privilegio = privilegio;
    }

    public boolean hasCreate(Objeto o) {

        privsobreobj(o);
        return privilegio.getCrear() == 'S';
    }

    public boolean hasRead(Objeto o) {

        privsobreobj(o);
        return privilegio.getLeer() == 'S';
    }

    public boolean hasUpdate(Objeto o) {

        privsobreobj(o);
        return privilegio.getModificar() == 'S';
    }

    public boolean hasDelete(Objeto o) {

        privsobreobj(o);
        return privilegio.getBorrar() == 'S';
    }

    public Objeto checkObjeto(String nombreobj, long id) {

        try {

            return login.getObjetoActual(nombreobj, id);
        } catch (ScoutException ex) {
            Logger.getLogger(ControlAutorizacion.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            return null;
        }
    }

    public void privsobreobj(Objeto o) {

        if ((null == lastobjeto) || (!lastobjeto.equals(o))) {

            try {

                Privilegios p = login.checkPrivilegios(o, usuario);
                setLastobjeto(o);
                setPrivilegio(p);

            } catch (ScoutException ex) {
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            }
        }
    }
    
    public String adminVeEventos(Grupo g){
        
        if(grupo == null) setGrupo(g);
       
        return "listaeventos.xhtml?faces-redirect=true";
    }
    
    public String adminRegresaHome(){
        
        setGrupo(null);
        
        return home();
    }

    public String home() {

        switch (getUsuario().getRoles().getNombrerol()) {

            case "Coordinador":
                return "editarUsuarios.xhtml";

            case "Scouter":
            case "Educando":
                return "listaeventos.xhtml";

            default:
                return "index.xthml";

        }

    }

    public String logout() {

        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.getExternalContext().invalidateSession();
        usuario = null;
        grupo = null;
        privilegio = null;
        lastobjeto = null;
        return "index.xhtml";
    }

    /**
     * Creates a new instance of ControlAutorizacion
     */
    public ControlAutorizacion() {
    }
}
