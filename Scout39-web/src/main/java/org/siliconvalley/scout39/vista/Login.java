/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import java.io.Serializable;
import org.siliconvalley.scout39.modelo.*;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.siliconvalley.scout39.negocio.NegocioLogin;
import org.siliconvalley.scout39.negocio.ScoutException;

/**
 *
 * @author alzaji
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    private String usuario;
    private String contrasenia;
    private List<Usuario> usuarios;
    private List<Roles> roles;
    private Usuario u;
    @Inject
    private ControlAutorizacion ctrl;
    @EJB
    private NegocioLogin negociologin;

    /**
     * Creates a new instance of Login
     */
    public Login() {

    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String autenticar() {

        FacesContext ctx = FacesContext.getCurrentInstance();

        try {
            Usuario aux = negociologin.comprobarUsuario(getUsuario());

            if (!aux.getDigest().equals(negociologin.sha256(getContrasenia()))) {

                throw new ScoutException("La contraseña proporcionada no coincide con la BD");
            } else {

                if (!aux.getRoles().getNombrerol().equals("Coordinador")) {
                    Grupo g = negociologin.grupoActualUsuario(aux);
                    ctrl.setGrupo(g);
                }
                ctrl.setUsuario(aux);
                Objeto o = new Objeto();
                o.setNombre("eventosSIRYU");
                Privilegios p = negociologin.checkPrivilegios(o, aux);
                return ctrl.home();
            }

        } catch (ScoutException ex) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            return null;
        }

    }

    private Usuario newUsuario(
            Long id,
            String alias,
            String digest,
            String nombre,
            String apellidos,
            String email,
            Date fecha_alta,
            Roles rol
    ) {

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setAlias(alias);
        usuario.setDigest(negociologin.sha256(digest));
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setEmail(email);
        usuario.setFecha_alta(fecha_alta);
        usuario.setRoles(rol);

        return usuario;
    }

    private Roles newRol(long id, String nombrerol) {

        Roles rol = new Roles();

        rol.setNombrerol(nombrerol);
        rol.setId(id);

        return rol;
    }
}
