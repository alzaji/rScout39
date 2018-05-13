/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import java.io.Serializable;
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
public class Login implements Serializable{

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
        usuarios.add(newUsuario(Long.parseLong("0"),"Coord1", "1234", "Coordinador1", ".", "coordinador@scout39.org", new Date(), newRol("Coordinador")));
        usuarios.add(newUsuario(Long.parseLong("1"),"ScouterTHA", "1234", "Scouter", "THA", "scoutertha@scout39.org", new Date(), newRol("ScouterTHA")));
        usuarios.add(newUsuario(Long.parseLong("2"),"ScouterKIM", "1234", "Scouter", "KIM", "scouterkim@scout39.org", new Date(), newRol("ScouterKIM")));
        usuarios.add(newUsuario(Long.parseLong("3"),"ScouterSIRYU", "1234", "Scouter", "SIRYU", "scoutersiryu@scout39.org", new Date(), newRol("ScouterSIRYU")));
        usuarios.add(newUsuario(Long.parseLong("4"),"ScouterALMOGAMA", "1234", "Scouter", "ALMOGAMA", "scouteralmogama@scout39.org", new Date(), newRol("ScouterALMOGAMA")));
        usuarios.add(newUsuario(Long.parseLong("11"),"EducandoTHA", "1234", "Educando", "THA", "educandotha@scout39.org", new Date(), newRol("EducandoTHA")));
        usuarios.add(newUsuario(Long.parseLong("12"),"EducandoKIM", "1234", "Educando", "KIM", "educandokim@scout39.org", new Date(), newRol("EducandoKIM")));
        usuarios.add(newUsuario(Long.parseLong("13"),"EducandoSIRYU", "1234", "Educando", "SIRYU", "educandosiryu@scout39.org", new Date(), newRol("EducandoSIRYU")));
        usuarios.add(newUsuario(Long.parseLong("14"),"EducandoALMOGAMA", "1234", "Educando", "ALMOGAMA", "educandoalmogama@scout39.org", new Date(), newRol("EducandoALMOGAMA")));

        roles = new ArrayList<Roles>();
        roles.add(newRol("Coordinador"));
        roles.add(newRol("ScouterTHA"));
        roles.add(newRol("ScouterKIM"));
        roles.add(newRol("ScouterSIRYU"));
        roles.add(newRol("ScouterALMOGAMA"));
        roles.add(newRol("EducandoKIM"));
        roles.add(newRol("EducandoSIRYU"));
        roles.add(newRol("EducandoTHA"));
        roles.add(newRol("EducandoTHA"));

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

    public Integer isinList(String alias) {
        int posicion = -1;
        boolean esta = false;
        Iterator<Usuario> it = usuarios.iterator();
        while (!esta && it.hasNext()) {
            if (it.next().getAlias().equals(alias)) {
                esta = true;
            }
            posicion++;
        }
        if (posicion == usuarios.size() - 1) {
            posicion = -1;
        }
        return posicion;
    }

    public String autenticar() {
        // Implementar este método
        FacesContext ctx = FacesContext.getCurrentInstance();
        int posicion = isinList(getUsuario());
        //boolean isPasswdCorrect = false;

        if (posicion == -1) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario " + getUsuario() + " no se encuentra en la lista de usuarios", "El usuario " + getUsuario() + " no se encuentra en la lista de usuarios"));
            return null;
        } else {
            Usuario aux = usuarios.get(posicion);
            if (!aux.getDigest().equals(getContrasenia())) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña incorrecta", "Contraseña incorrecta"));
                return null;
            } else {
                ctrl.setUsuario(aux);
                return ctrl.home();
            }
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
        usuario.setDigest(digest);
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setEmail(email);
        usuario.setFecha_alta(fecha_alta);
        usuario.setRoles(rol);

        return usuario;
    }

    private Roles newRol(String nombrerol) {

        Roles rol = new Roles();

        rol.setNombrerol(nombrerol);

        return rol;
    }
}
