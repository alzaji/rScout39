/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.siliconvalley.scout39.modelo.Archivo;
import org.siliconvalley.scout39.modelo.Grupo;
import org.siliconvalley.scout39.modelo.Roles;
import org.siliconvalley.scout39.modelo.S03;
import org.siliconvalley.scout39.modelo.Usuario;
import org.siliconvalley.scout39.negocio.NegocioLogin;
import org.siliconvalley.scout39.negocio.NegocioUsuarioLocal;
import org.siliconvalley.scout39.negocio.ScoutException;

/**
 *
 * @author hidden-process
 * @author pasantru
 */
@Named(value = "beanS03Usuario")
@SessionScoped
public class beanS03Usuario implements Serializable {

    @EJB
    private NegocioLogin usr;

    @EJB
    private NegocioUsuarioLocal negocioU;

    @Inject
    private ControlAutorizacion ctrl;

    private S03 s;
    private Usuario user;

    public String createUser() {
        try {
            Usuario aux = new Usuario();
            S03 s03 = new S03();
            Archivo dummy = new Archivo();

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String nombre = request.getParameter("crearUsuarioS03:nombre");
            String apellido1 = request.getParameter("crearUsuarioS03:apellido1");
            String apellido2 = request.getParameter("crearUsuarioS03:apellido2");
            String alias = request.getParameter("crearUsuarioS03:alias");
            String email = request.getParameter("crearUsuarioS03:email");
            // String grupo = request.getParameter("crearUsuarioS03:grupo");
            String sexo = request.getParameter("crearUsuarioS03:sexo");
            String dni = request.getParameter("crearUsuarioS03:dni");
            String fechaNacimiento = request.getParameter("crearUsuarioS03:fechaNacimiento");
            String direccion = request.getParameter("crearUsuarioS03:direccion");
            String localidad = request.getParameter("crearUsuarioS03:localidad");
            String codigopostal = request.getParameter("crearUsuarioS03:codigopostal");
            String provincia = request.getParameter("crearUsuarioS03:provincia");
            String telefono = request.getParameter("crearUsuarioS03:telefono");
            String movil = request.getParameter("crearUsuarioS03:movil");
            String seccion = request.getParameter("crearUsuarioS03:seccion");

            String cargo = request.getParameter("crearUsuarioS03:cargo");
            String federal = request.getParameter("crearUsuarioS03:federal");
            String asociativo = request.getParameter("crearUsuarioS03:asociativo");
            String provincial = request.getParameter("crearUsuarioS03:provincial");
            String nombreResponsable = request.getParameter("crearUsuarioS03:nombreResponsable");
            String apellido1Responsable = request.getParameter("crearUsuarioS03:apellido1Responsable");
            String apellido2Responsable = request.getParameter("crearUsuarioS03:apellido2Responsable");
            String dniResponsable = request.getParameter("crearUsuarioS03:dniResponsable");

            //Usuario
            Grupo g = usr.getGrupofromString(seccion);
            Roles r = usr.getRolesfromString("Educando");
            aux.setNombre(nombre);
            aux.setAlias(alias);
            aux.setDigest(alias);
            aux.setApellidos(apellido1 + " " + apellido2);
            aux.setEmail(email);
            aux.setFecha_alta(new Date());
            aux.setRoles(r);

            //Archivo
            //Obligatorios
            s03.setNombre(nombre);
            s03.setApellido1(apellido1);
            s03.setApellido2(apellido2);
            s03.setSeccion(seccion);
            s03.setDni(dni);
            s03.setFnacimiento(new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(fechaNacimiento));
            s03.setCalleynumero(direccion);
            s03.setLocalidad(localidad);
            s03.setCodPostal(Integer.parseInt(codigopostal));
            s03.setProvincia(provincia);
            s03.setSexo(sexo.charAt(0));
            s03.setGrupo("Scout 39 Santo Ángel");

            //Nulos
            if ((telefono.length() > 0) && (telefono.matches("[0-9]{9}"))) {
                s03.setTelefono(Integer.parseInt(telefono));
            }
            if ((movil.length() > 0) && (movil.matches("[0-9]{9}"))) {
                s03.setMovil(Integer.parseInt(movil));
            }
            if (seccion.length() > 0) {
                s03.setSeccion(seccion);
            }
            if (cargo.length() > 0) {
                s03.setCargo(cargo);
            }
            if ((federal.length() > 0) && (federal.matches("[X]{1}"))) {
                s03.setFederal(federal.charAt(0));
            }
            if ((asociativo.length() > 0) && (asociativo.matches("[X]{1}"))) {
                s03.setAsociativo(asociativo.charAt(0));
            }
            if ((provincial.length() > 0) && (provincial.matches("[X]{1}"))) {
                s03.setProvincial(provincial.charAt(0));
            }
            if (nombreResponsable.length() > 0) {
                s03.setNombreResponsableLegal(nombreResponsable);
            }
            if (apellido1Responsable.length() > 0) {
                s03.setApellido1ResponsableLegal(apellido1Responsable);
            }
            if (apellido1Responsable.length() > 0) {
                s03.setApellido2ResponsableLegal(apellido2Responsable);
            }
            if (dniResponsable.length() > 0) {
                s03.setDniResponsableLegal(dniResponsable);
            }
            usr.registrarUsuario(aux, g);
            aux = usr.comprobarUsuario(aux.getAlias());
            dummy.setNombre(alias + "_S03");
            dummy.setTipo("pdf");
            dummy.setRuta("null");
            dummy.setEstado('P');
            usr.registrarS03(aux, s03, dummy);

        } catch (ParseException ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            return null;
        } catch (ScoutException ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            return null;

        }

        return "editarUsuarios.xhtml?faces-redirect=true";
    }

    public String getGrupoFromUser(Usuario u) {
        try {
            return usr.grupoActualUsuario(u).getNombre();
        } catch (ScoutException ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            return null;
        }
    }

    public String obtenerS03(Usuario u) {
        S03 datos = negocioU.buscarS03Usuario(u);
        setUser(u);
        setS(datos);   
        return "s03.xhtml";
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public S03 getS() {
        return s;
    }

    public void setS(S03 s) {
        this.s = s;
    }
  

}
