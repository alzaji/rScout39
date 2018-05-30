/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.siliconvalley.scout39.modelo.Roles;
import org.siliconvalley.scout39.modelo.Usuario;
import org.siliconvalley.scout39.negocio.NegocioUsuarioLocal;



/**
 *
 * @author Dani
 */
@Named(value = "controlUsuario")
@ManagedBean
@RequestScoped
public class ControlUsuario implements Serializable {

    @Inject
    private Login login;
    @EJB
    private NegocioUsuarioLocal users;

    private Usuario usuario;    
    private Roles roles;
    protected String pal;
    protected boolean update = false;
    
    public ControlUsuario() {
    }

    public String getPal() {
        return pal;
    }

    public void setPal(String pal) {
        this.pal = pal;
    }
    
    public List<Usuario> listarUsuarios() {
        if (update) {
            return listarUsuariosAJAX();
        }
        return users.listaUsuarios();
    } 
    
    public void searchListUser(){
        update = true;
        listarUsuarios();
    }
    
    public List<Usuario> listarUsuariosAJAX() {
        return users.listaUsuariosAJAX(pal);
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
    public String removeUsuario(Usuario u) {

        login.getUsuarios().remove(u);

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
        Usuario u = crearUsuario(nombre, apellidos, email, movil, direccion, localidad, provincia, codPostal, rol);
        login.getUsuarios().add(u);
        System.out.println("Nombre: " + login.getUsuarios().get(login.getUsuarios().indexOf(u)).getNombre() + " Size: " + login.getUsuarios().size());

        return "editarUsuarios.xhtml?faces-redirect=true";
    }

    public Usuario crearUsuario(String nombre, String apellidos, String email, String movil, String direccion, String localidad, String provincia, String codPostal, String rol) {

        Usuario user = new Usuario();
        Random rnd = new Random();
        user.setId(rnd.nextLong() % 100);
        user.setNombre(nombre);
        user.setApellidos(apellidos);
        user.setEmail(email);

        return user;

    }
    

}
