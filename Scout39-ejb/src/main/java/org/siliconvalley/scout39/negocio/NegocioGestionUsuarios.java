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
 * @author fernandez
 */
@Local
public interface NegocioGestionUsuarios {
    
    
    public void crearUsuario(Usuario u) throws ScoutException;
    
    public void borrarUsuario(int idUsuario) throws ScoutException;
    
    public void editarUsuario(int idUsuario) throws ScoutException;
    
    
}
