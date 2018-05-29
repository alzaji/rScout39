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
    public final static String COORDINADOR = "Coordinador";
    public final static String SCTHA = "ScouterTHA";
    public final static String SCKIM = "ScouterKIM";
    public final static String SCSIRYU = "ScouterSIRYU";
    public final static String SCALMOGAMA = "ScouterALMOGAMA";
    public final static String EDTHA = "EducandoTHA";
    public final static String EDKIM = "EducandoKIM";
    public final static String EDSIRYU = "EducandoSIRYU";
    public final static String EDALMOGAMA = "EducandoALMOGAMA";

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

    public String home() {

        switch (getUsuario().getRoles().getNombrerol()) {

            case "Coordinador":
                return "coordinador.xhtml";

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
