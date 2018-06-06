/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import java.util.List;
import javax.ejb.Local;
import javax.persistence.PersistenceContext;
import org.siliconvalley.scout39.modelo.AccesoRecurso;
import org.siliconvalley.scout39.modelo.Objeto;
import org.siliconvalley.scout39.modelo.Privilegios;
import org.siliconvalley.scout39.modelo.Roles;

/**
 *
 * @author Dani
 */
@Local
public interface NegocioRolesLocal {



    public List<Roles> listaRoles();

    public List<Objeto> listaObjetos();
    
    public Long hasP(Roles r, Objeto o);
    
    public String getPCreate(Roles r, Objeto o);
    
    public String getPRead(Roles r, Objeto o);
    
    public String getPUpdate(Roles r, Objeto o);
    
    public String getPDelete(Roles r, Objeto o);
    
    public void crearRol(Roles rol) throws ScoutException;
    
    public void borrarRol(Roles rol) throws ScoutException;
        
    public Privilegios find(String c, String r, String u, String d);
    
    public AccesoRecurso findAr(Roles rol, Objeto o);
    
    public Objeto findObj(String obj);
    
    public void modificarRol(AccesoRecurso ar);

}
