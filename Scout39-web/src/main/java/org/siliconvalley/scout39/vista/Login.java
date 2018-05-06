/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import org.siliconvalley.scout39.modelo.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author alzaji
 */
@Named(value = "login")
@RequestScoped
public class Login {

    private String usuario;
    private String contrasenia;
    private List<Usuario> usuarios;
    private List<Roles> roles;

    @Inject
    private ControlAutorizacion ctrl;

    /**
     * Creates a new instance of Login
     */
    public Login() {
        usuarios = new ArrayList<Usuario>();
        usuarios.add(newUsuario("Coord1", "1234", "Coordinador1", ".", "coordinador@scout39.org", new Date(), newRol("Coordinador")));
        usuarios.add(newUsuario("ScouterTHA", "1234", "Scouter", "THA", "scoutertha@scout39.org", new Date(),newRol("ScouterTHA")));
        usuarios.add(newUsuario("ScouterKIM", "1234", "Scouter", "KIM", "scouterkim@scout39.org", new Date(),newRol("ScouterKIM")));
        usuarios.add(newUsuario("ScouterSIRYU", "1234", "Scouter", "SIRYU", "scoutersiryu@scout39.org", new Date(),newRol("ScouterSIRYU")));
        usuarios.add(newUsuario("ScouterALMOGAMA", "1234", "Scouter", "ALMOGAMA", "scouteralmogama@scout39.org", new Date(),newRol("ScouterALMOGAMA")));
        
        roles = new ArrayList<Roles>();
        roles.add(newRol("Coordinador"));
        roles.add(newRol("ScouterTHA"));
        roles.add(newRol("ScouterKIM"));
        roles.add(newRol("ScouterSIRYU"));
        roles.add(newRol("ScouterALMOGAMA"));
        

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
        // Implementar este método
        FacesContext ctx = FacesContext.getCurrentInstance();
        Iterator<Usuario> it = usuarios.iterator();

        boolean isinList = false;
        boolean isPasswdCorrect = false;
        Usuario aux = null;

        while (it.hasNext() && !isinList) {

            aux = it.next();

            if (aux.getAlias().equals(getUsuario())) {

                isinList = true;
                if (aux.getDigest().equals(getContrasenia())) {

                    isPasswdCorrect = true;
                }
            }
        }
        if (!isinList) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario " + getUsuario() + " no se encuentra en la lista de usuarios", "El usuario " + getUsuario() + " no se encuentra en la lista de usuarios"));
            return null;
        } else {

            if (!isPasswdCorrect) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña incorrecta", "Contraseña incorrecta"));
                return null;
            } else {
                ctrl.setUsuario(aux);
                return ctrl.home();
            }
        }

    }

    private Usuario newUsuario(String alias,
            String digest,
            String nombre,
            String apellidos,
            String email,
            Date fecha_alta,
            Roles rol
    ) {

        Usuario usuario = new Usuario();
        usuario.setAlias(alias);
        usuario.setDigest(digest);
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setEmail(email);
        usuario.setFecha_alta(fecha_alta);
        usuario.setRoles(rol);

        return usuario;
    }
    
    private Roles newRol(String nombrerol){
        
        Roles rol = new Roles();
        
        rol.setNombrerol(nombrerol);
        
        return rol; 
    }
}
