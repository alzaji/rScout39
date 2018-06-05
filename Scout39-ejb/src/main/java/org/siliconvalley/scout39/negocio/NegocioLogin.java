/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import java.util.List;
import javax.ejb.Local;
import org.siliconvalley.scout39.modelo.*;

/**
 *
 * @author alzaji
 */
@Local
public interface NegocioLogin {
    
    public void registrarUsuario(Usuario u, Grupo g) throws ScoutException;
    public void registrarS03(Usuario u, S03 s03, Archivo a) throws ScoutException;
    public Usuario comprobarUsuario(String alias) throws ScoutException;
    public Grupo grupoActualUsuario(Usuario u) throws ScoutException;
    public Grupo getGrupofromString(String nombre) throws ScoutException;
    public Privilegios checkPrivilegios (Objeto o, Usuario u) throws ScoutException;
    public Objeto getObjetoActual(String nombreobj, long id) throws ScoutException;
    public Roles getRolesfromString(String nombrerol) throws ScoutException;
    public List<Roles> getAllRoles() throws ScoutException;
    public List<Grupo> getAllGrupos() throws ScoutException;
    public String sha256(String rawString);
}
