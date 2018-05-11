/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.siliconvalley.scout39.modelo.Roles;
import org.siliconvalley.scout39.modelo.Usuario;

/**
 *
 * @author Fernandez
 */
@Named(value = "beanRoles")
@RequestScoped
public class beanRoles implements Serializable {

    private String nombreRol;
    @Inject
    private Login login;

    public beanRoles() {

    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public void crearNuevoRole(Roles rol) {

        if (!login.getRoles().add(rol)) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El rol " + rol.getNombrerol() + " no se encuentra en la lista de roles", "El rol " + rol.getNombrerol() + " no se encuentra en la lista de roles"));
        }
    }

    public void eliminarRole(Roles rol) {
        if (login.getRoles().contains(rol)) {
            for (Usuario u : login.getUsuarios()) {
                if (u.getRoles().equals(rol)) {
                    u.setRoles(login.getRoles().get(0));
                }
            }
        }
    }
}
