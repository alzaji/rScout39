/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.siliconvalley.scout39.modelo.*;
import org.siliconvalley.scout39.negocio.NegocioRolesLocal;
import org.siliconvalley.scout39.negocio.ScoutException;

/**
 *
 * @author Fernandez
 */
@Named(value = "beanRoles")
@SessionScoped
public class beanRoles implements Serializable {

    @EJB
    private NegocioRolesLocal roles;
    private String nombreRol;
    private String priv;
    private boolean peine;
    private String c;
    private String re;
    private String u;
    private String d;
    private String cI;
    private String reI;
    private String uI;
    private String dI;

    public beanRoles() {
    }

    public void setCRUD(Roles r, Objeto o) {
        pCreate(r, o);
        pDelete(r, o);
        pRead(r, o);
        pUpdate(r, o);
    }

    public void setInverso(Roles r, Objeto o) {
        setcI(pInverso(c));
        setReI(pInverso(re));
        setuI(pInverso(u));
        setdI(pInverso(d));

    }

    public List<Roles> obtenerRoles() {
        return roles.listaRoles();
    }

    public List<Objeto> obtenerObjetos() {
        return roles.listaObjetos();
    }

    public void hasPriv(Roles rol, Objeto o) {
        setPeine(roles.hasP(rol, o) == 1);
    }

    public void pCreate(Roles rol, Objeto o) {
        String cad = roles.getPCreate(rol, o);
        setPriv(cad);
        setC(cad);
    }

    public void pRead(Roles rol, Objeto o) {
        String cad = roles.getPRead(rol, o);
        setPriv(cad);
        setRe(cad);
    }

    public void pUpdate(Roles rol, Objeto o) {
        String cad = roles.getPUpdate(rol, o);
        setPriv(cad);
        setU(cad);
    }

    public void pDelete(Roles rol, Objeto o) {
        String cad = roles.getPDelete(rol, o);
        setPriv(cad);
        setD(cad);
    }

    public String pInverso(String s) {
        if (s.equals("S")) {
            return "N";
        } else {
            return "S";
        }
    }

    public String borrarRol(Roles rol) {
        try {
            roles.borrarRol(rol);
            return null;
        } catch (ScoutException ex) {

            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            return null;

        }
    }

    public String crearRol() {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String nombre = request.getParameter("formCrearRol:nombreR");
            Roles r = new Roles();
            r.setNombrerol(nombre);
            roles.crearRol(r);
        } catch (ScoutException ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            return null;
        }
        return null;
    }

    public void updateRol(Roles rol, int idxr) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String cre = request.getParameter("table:" + idxr + ":formModificarRoles:Crear");
        String rea = request.getParameter("table:" + idxr + ":formModificarRoles:Leer");
        String upd = request.getParameter("table:" + idxr + ":formModificarRoles:Modificar");
        String del = request.getParameter("table:" + idxr + ":formModificarRoles:Borrar");
        String obj = request.getParameter("table:" + idxr + ":formModificarRoles:Objeto");
        Objeto o = roles.findObj(obj);
        AccesoRecurso ar = roles.findAr(rol, o);
        Privilegios p = roles.find(cre, rea, upd, del);
        ar.setIdPrivilegio(p);
        roles.modificarRol(ar);
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getPriv() {
        return priv;
    }

    public void setPriv(String priv) {
        this.priv = priv;
    }

    public boolean isPeine() {
        return peine;
    }

    public void setPeine(boolean peine) {
        this.peine = peine;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getRe() {
        return re;
    }

    public void setRe(String re) {
        this.re = re;
    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getcI() {
        return cI;
    }

    public void setcI(String cI) {
        this.cI = cI;
    }

    public String getReI() {
        return reI;
    }

    public void setReI(String reI) {
        this.reI = reI;
    }

    public String getuI() {
        return uI;
    }

    public void setuI(String uI) {
        this.uI = uI;
    }

    public String getdI() {
        return dI;
    }

    public void setdI(String dI) {
        this.dI = dI;
    }

}
