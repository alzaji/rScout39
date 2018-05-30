/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import javax.ejb.Local;
import org.siliconvalley.scout39.modelo.*;

/**
 *
 * @author alzaji
 */
@Local
public interface NegocioLogin {
    
    public void registrarUsuario(Usuario u) throws ScoutException;
    public Usuario comprobarUsuario(String alias) throws ScoutException;
    public Grupo grupoActualUsuario(Usuario u) throws ScoutException;
    public Privilegios checkPrivilegios (Objeto o, Usuario u) throws ScoutException;
    public String sha256(String rawString);
}
