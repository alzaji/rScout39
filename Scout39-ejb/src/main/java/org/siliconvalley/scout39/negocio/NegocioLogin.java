/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import javax.ejb.Local;
import org.siliconvalley.scout39.modelo.Usuario;

/**
 *
 * @author alzaji
 */
@Local
public interface NegocioLogin {
    
    public void registrarUsuario(Usuario u) throws ScoutException;
    public void comprobarUsuario(Usuario u) throws ScoutException;
    
}
