/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.siliconvalley.scout39.modelo.Usuario;

/**
 *
 * @author Dani
 */
@Named(value = "controlUsuario")
@RequestScoped
public class ControlUsuario {

    public ControlUsuario() {

    }
    @Inject
    private Login login;
    private int posicion;

    public void addUsuario(Usuario u) {
        if (!login.getUsuarios().add(u)) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario " + u.getAlias() + " no se encuentra en la lista de usuarios", "El usuario " + u.getAlias() + " no se encuentra en la lista de usuarios"));
        }
    }

    public void removeUsuario(Usuario u) {
        if (!login.getUsuarios().remove(u)) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario " + u.getAlias() + " no se encuentra en la lista de usuarios", "El usuario " + u.getAlias() + " no se encuentra en la lista de usuarios"));
        }
    }

    public void modificarUsuario(Usuario original, Usuario nuevo) {
       

        posicion = login.isinList(original.getAlias());
        if (posicion==-1) {
             FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario " + original.getAlias() + " no se encuentra en la lista de usuarios", "El usuario " + original.getAlias() + " no se encuentra en la lista de usuarios"));
        } else {
            login.getUsuarios().set(posicion, nuevo);
        }

    }
}
