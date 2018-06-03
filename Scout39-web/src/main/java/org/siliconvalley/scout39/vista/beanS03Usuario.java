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
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.siliconvalley.scout39.modelo.Archivo;
import org.siliconvalley.scout39.modelo.Grupo;
import org.siliconvalley.scout39.modelo.S03;
import org.siliconvalley.scout39.modelo.Usuario;
import org.siliconvalley.scout39.negocio.NegocioLogin;


/**
 *
 * @author hidden-process
 * @author pasantru
 */

@Named(value = "beanS03Usuario")
@SessionScoped
public class beanS03Usuario implements Serializable{
    
    @EJB
    private NegocioLogin usr;
    
    @Inject 
    private ControlAutorizacion ctrl;
    
    public String createUser(){
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
            String grupo = request.getParameter("crearUsuarioS03:grupo");
            String dni = request.getParameter("crearUsuarioS03:dni");
            String fechaNacimiento = request.getParameter("crearUsuarioS03:fechaNacimiento");
            String direccion = request.getParameter("crearUsuarioS03:direccion");
            String localidad = request.getParameter("crearUsuarioS03:localidad");
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
            aux.setNombre(nombre);
            aux.setAlias(alias);
            aux.setDigest(alias);
            aux.setApellidos(apellido1 + "," + apellido2);
            aux.setEmail(email);
            aux.setFecha_alta(new Date());
            
            //Archivo
            s03.setNombre(nombre);
            s03.setApellido1(apellido1);
            s03.setApellido2(apellido2);
            s03.setGrupo(grupo);
            s03.setDni(dni);
            s03.setFnacimiento(new SimpleDateFormat("dd/MM/yyyy").parse(fechaNacimiento));
            s03.setCalleynumero(direccion);
            s03.setLocalidad(localidad);
            s03.setProvincia(provincia);
            s03.setTelefono(Integer.parseInt(telefono));
            s03.setMovil(Integer.parseInt(movil));
            s03.setSeccion(seccion);
            s03.setCargo(cargo);
            s03.setFederal(federal.charAt(0));
            s03.setAsociativo(asociativo.charAt(0));
            s03.setProvincial(provincial.charAt(0));
            s03.setNombreResponsableLegal(nombreResponsable);
            s03.setApellido1ResponsableLegal(apellido1Responsable);
            s03.setApellido2ResponsableLegal(apellido2Responsable);
            s03.setDniResponsableLegal(dniResponsable);

            
            dummy.setS03(s03);
            List<Archivo> listArch = new ArrayList<>();
            listArch.add(dummy);
            aux.setArchivo(listArch);
            
            
            
            
            
        } catch (ParseException ex) {
            Logger.getLogger(beanS03Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "index.xhtml?faces-redirect=true";
    }
}