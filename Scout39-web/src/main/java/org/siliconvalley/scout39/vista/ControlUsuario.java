/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.siliconvalley.scout39.modelo.Roles;
import org.siliconvalley.scout39.modelo.Usuario;

/**
 *
 * @author Dani
 */
@Named(value = "controlUsuario")
@SessionScoped
public class ControlUsuario implements Serializable{

    public ControlUsuario() {

    }
    @Inject
    private Login login;
    private Usuario u;
    private int contador;
    private String nombre;
    private String Apellidos;
    private String alias;
    private String email;
    private Roles roles;
    private String texto;

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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

    public void addUsuario() {
        if (!login.getUsuarios().add(u)) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario " + u.getAlias() + " no se encuentra en la lista de usuarios", "El usuario " + u.getAlias() + " no se encuentra en la lista de usuarios"));
        }
    }

    public String removeUsuario() {
        int cont=contador/6;
        login.getUsuarios().remove(cont);            
        return "editarUsuario.xhtml";
    }

    public String modificarUsuario() {
        int cont=contador/6;
        int au=contador%6;
        switch (au) {
            case 0:
                login.getUsuarios().get(cont).setNombre(texto);
                break;
            case 1:
                login.getUsuarios().get(cont).setApellidos(texto);
                break;
            case 2:
                login.getUsuarios().get(cont).setAlias(texto);
                break;
            case 3:
                login.getUsuarios().get(cont).setEmail(texto);
                break;
            case 4:
                login.getUsuarios().get(cont).setRoles(roles);
                break;
            default:
                break;
        }
        return "editarUsuarios.xhtml";

    }
    
    public String doCrearUsuario(String nombre, String primerApellido, String segundoApellido, String dni, String sexo,
            String email, String movil, String direccion, String localidad, String provincia, String codPostal, String rol){
        
        String apellidos = primerApellido + " " + segundoApellido;
        
        Usuario user = crearUsuario(nombre, apellidos, dni, sexo, email, movil, direccion, localidad, provincia, codPostal, rol);
        login.getUsuarios().add(user);                        
    
        return "editarUsuarios.xhtml";
    }
    
    public Usuario crearUsuario(String nombre, String apellidos, String dni, String sexo,
            String email, String movil, String direccion, String localidad, String provincia, String codPostal, String rol){
    
        Usuario user = new Usuario();
        user.setNombre(nombre);
        user.setApellidos(apellidos); 
        user.setEmail(email);
        
        
        return user;
        
    }
    
}
