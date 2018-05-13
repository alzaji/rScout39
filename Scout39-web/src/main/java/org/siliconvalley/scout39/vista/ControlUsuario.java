/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.siliconvalley.scout39.modelo.Roles;
import org.siliconvalley.scout39.modelo.Usuario;

/**
 *
 * @author Dani
 */
@Named(value = "controlUsuario")
@ManagedBean
@SessionScoped
public class ControlUsuario implements Serializable {

    @Inject
    private Login login;

    private Usuario usuario;
    private String nombre;
    private String Apellidos;
    private String alias;
    private String email;
    private Roles roles;
    
    public ControlUsuario() {
        login = new Login();
    }
        
    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String removeUsuario() {

        return "editarUsuario.xhtml";
    }

    public String doModificarUsuario(Usuario u) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String alia = request.getParameter("formModificarUsuario" + u.getId().toString() + ":modificarAlias");
        String nombr = request.getParameter("formModificarUsuario" + u.getId().toString() + ":modificarNombre");
        String apellidos = request.getParameter("formModificarUsuario" + u.getId().toString() + ":modificarApellidos");
        String correo = request.getParameter("formModificarUsuario" + u.getId().toString() + ":modificarEmail");
        Usuario user = newUsuario(u.getId(), alia, u.getDigest(),
                nombr, apellidos, correo, u.getFecha_alta(), u.getRoles());

        int pos = login.getUsuarios().indexOf(u);
        login.getUsuarios().toArray()[pos] = user;

        return "editarUsuarios.xhtml?faces-redirect=true";

    }

    private Roles newRol(String nombrerol) {

        Roles rol = new Roles();

        rol.setNombrerol(nombrerol);

        return rol;
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

    public String doCrearUsuario(String nombre, String primerApellido, String segundoApellido, String dni, String sexo,
            String email, String movil, String direccion, String localidad, String provincia, String codPostal, String rol) {

        String apellidos = primerApellido + " " + segundoApellido;
        login.getUsuarios().add(crearUsuario(nombre, apellidos, email, movil, direccion, localidad, provincia, codPostal, rol));

        return "editarUsuarios.xhtml?faces-redirect=true";
    }

    public Usuario crearUsuario(String nombre, String apellidos, String email, String movil, String direccion, String localidad, String provincia, String codPostal, String rol) {

        Usuario user = new Usuario();
        user.setNombre(nombre);
        user.setApellidos(apellidos);
        user.setEmail(email);

        return user;

    }

}
