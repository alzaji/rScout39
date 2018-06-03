/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import java.io.Serializable;
import java.util.Iterator;
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
    private Archivo requeridos;
    private Usuario usuario;
    private Archivo archivo;
    private List<Archivo> listaArchivos;
    protected String pal;
    protected boolean update = false;

    @Inject
    private ControlAutorizacion ctrl;

    @EJB
    private NegocioGestorDocumentalLocal gestorArchivos;

    @EJB
    private NegocioUsuarioLocal user;

    public Archivo getRequeridos() {
        return requeridos;
    }

    public void setRequeridos(Archivo requeridos) {
        this.requeridos = requeridos;
    }

    public beanPerfil() {

    }

    public List<Archivo> getListaArchivos() {
        return listaArchivos;
    }

    public void setListaArchivos(List<Archivo> listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    public void listarEstadoArchivo() {
        listaArchivos = gestorArchivos.obtenerArchivos(ctrl.getGrupo());
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

    // Para Scouter
    public List<Archivo> listarArchivosScouter() {
        if (update) {
            return listarArchivosAJAXScouter();
        }
        return gestorArchivos.listarArchivosScouter(ctrl.getGrupo());
    }

    public void searchListFilesNombre() {
        update = true;
        listarArchivosNombre();
    }

    public void searchListFilesScouter() {
        update = true;
        listarArchivosScouter();
    }

    public List<Archivo> listarArchivosAJAXNombre() {
        return gestorArchivos.listaArchivosNombreAJAX(pal);
    }

    public List<Archivo> listarArchivosAJAXScouter() {
        return gestorArchivos.listaArchivosNombreAJAXScouter(ctrl.getGrupo(), pal);
    }

    public void validarArchivo(Archivo ar) {
        gestorArchivos.validarArchivo(ar);
    }

    public void nuevaPromesa() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String nPromesa = request.getParameter("cambiarPromesa:promesa");
        user.cambiarPromesa(ctrl.getUsuario(), nPromesa);
    }

    public List<AccesoGrupo> listarPromesas() {
        return user.listaPromesas(ctrl.getUsuario());
    }

    public Archivo getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }           
}
