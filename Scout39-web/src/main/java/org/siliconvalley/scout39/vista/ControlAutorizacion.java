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

    public String home() {

        switch (getUsuario().getRoles().getNombrerol()) {

            case "Coordinador":
                return "coordinador.xhtml";

            case "ScouterTHA":
            case "EducandoTHA":
                return "tha.xhtml";

            case "ScouterKIM":
            case "EducandoKIM":
                return "kim.xhtml";

            case "ScouterSIRYU":
            case "EducandoSIRYU":
                return "siryu.xhtml";

            case "ScouterALMOGAMA":
            case "EducandoALMOGAMA":
                return "almogama.xhtml";

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
