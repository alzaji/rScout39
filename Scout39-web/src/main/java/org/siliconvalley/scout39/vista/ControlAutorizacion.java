/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import org.siliconvalley.scout39.modelo.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;

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

    public boolean hasCreate() {

        return privilegio.getCrear() == 'S';
    }

    public boolean hasRead() {

        return privilegio.getLeer() == 'S';
    }
    
    public boolean hasUpdate() {
        
        return privilegio.getModificar() == 'S';
    }
    
    public boolean hasDelete(){
        
        return privilegio.getBorrar() == 'S';
    }

    public String home() {

        switch (getUsuario().getRoles().getNombrerol()) {

            case "Coordinador":
                return "editarUsuarios.xhtml";

            case "Scouter":
            case "Educando":
                if (getGrupo().getNombre().equals("Manada de Tha")) {
                    return "tha.xhtml";
                } else if (getGrupo().getNombre().equals("Tropa de Kim")) {
                    return "kim.xhtml";
                } else if (getGrupo().getNombre().equals("Unidad Esculta Siryu")) {
                    return "siryu.xhtml";
                } else if (getGrupo().getNombre().equals("Clan Rover Almogama")) {
                    return "almogama.xhtml";
                }

            default:
                return "index.xthml";

        }

    }

    public String logout() {
        // Destruye la sesión (y con ello, el ámbito de este bean)
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.getExternalContext().invalidateSession();
        usuario = null;
        return "index.xhtml";
    }

    /**
     * Creates a new instance of ControlAutorizacion
     */
    public ControlAutorizacion() {
    }
}
