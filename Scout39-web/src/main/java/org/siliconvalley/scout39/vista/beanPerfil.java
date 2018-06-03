/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import java.io.Serializable;
import org.siliconvalley.scout39.modelo.*;

import java.util.Map;
import org.siliconvalley.scout39.negocio.*;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pasantru
 */
@Named(value = "beanPerfil")
@ManagedBean
@SessionScoped
public class beanPerfil implements Serializable {

    private AccesoGrupo promesa;
    private Usuario usuario;
    private Archivo archivo;
    private Map<Usuario, List<Archivo>> archivos;
    private Map<String, String> promesas;
    protected String pal;
    protected boolean update = false;

    @Inject
    private ControlAutorizacion ctrl;

    @EJB
    private NegocioGestorDocumentalLocal gestorArchivos;

    @EJB
    private NegocioUsuarioLocal user;

    public beanPerfil() {

    }

    public AccesoGrupo getPromesa() {
        return promesa;
    }

    public void setPromesa(AccesoGrupo promesa) {
        this.promesa = promesa;
    }

    public String getPal() {
        return pal;
    }

    public void setPal(String pal) {
        this.pal = pal;
    }

    public List<Archivo> getUserFiles(Usuario u) {
        return gestorArchivos.buscarArchivos(u);
    }

    // Para Coordinador.
    public List<Archivo> listarArchivos() {
        if (update) {
            return listarArchivosAJAX();
        }
        return gestorArchivos.listarArchivos();
    }

    public void searchListFiles() {
        update = true;
        listarArchivos();
    }

    public List<Archivo> listarArchivosAJAX() {
        return gestorArchivos.listaArchivosAJAX(pal);
    }

    // Para Educando
    public List<Archivo> listarArchivosNombre() {
        if (update) {
            return listarArchivosAJAXNombre();
        }
        return gestorArchivos.listarArchivos();
    }

    public void searchListFilesNombre() {
        update = true;
        listarArchivosNombre();
    }

    public List<Archivo> listarArchivosAJAXNombre() {
        return gestorArchivos.listaArchivosNombreAJAX(pal);
    }

    public void nuevaPromesa() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String nPromesa = request.getParameter("cambiarPromesa:promesa");
        user.cambiarPromesa(ctrl.getUsuario(), nPromesa);
    }

    public List<AccesoGrupo> listarPromesas() {
        return user.listaPromesas(ctrl.getUsuario());
    }

}
