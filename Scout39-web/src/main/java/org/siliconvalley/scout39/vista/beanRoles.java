/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.siliconvalley.scout39.modelo.*;

/**
 *
 * @author Fernandez
 */
@Named(value = "beanRoles")
@SessionScoped
public class beanRoles implements Serializable {

    private String nombreRol;
    private Map<Roles, List<Privilegios>> roles;

    public beanRoles() {
        roles = new HashMap<>();
    }

    private Roles crearRol(Long idRol, String nombreRol) {
        Roles r = new Roles();
        r.setIdrol(idRol);
        r.setNombrerol(nombreRol);
        return r;
    }

    private Privilegios crearPrivilegios(Long id) {
        Privilegios p = new Privilegios();
        p.setId(id);
        p.setLectura('N');
        p.setBorrado('N');
        p.setEscritura('N');
        return p;
    }

    public void añadirNuevoRol(Roles rol) {

        Roles r = crearRol(rol.getId(), rol.getNombrerol());

        FacesContext ctx = FacesContext.getCurrentInstance();

        if (!roles.containsKey(r)) {
            roles.put(r, new ArrayList<Privilegios>());
        } else {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El rol " + rol.getNombrerol() + " ya se encuentra en la lista de roles", "El rol " + rol.getNombrerol() + " ya se encuentra en la lista de roles"));
        }

    }

    public void eliminarRole(Roles rol) {

        Roles r = crearRol(rol.getId(), rol.getNombrerol());

        FacesContext ctx = FacesContext.getCurrentInstance();

        if (roles.containsKey(r)) {
            roles.remove(r);
        } else {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El rol " + rol.getNombrerol() + " no se encuentra en la lista de roles", "El rol " + rol.getNombrerol() + " no se encuentra en la lista de roles"));
        }
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}
