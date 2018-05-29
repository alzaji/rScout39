/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import org.siliconvalley.scout39.modelo.Usuario;

/**
 *
 * @author fernandez
 */

@Stateless
public class NegocioGestionUsuariosImpl implements NegocioGestionUsuarios{

    private EntityManager em;
    
    
    
    @Override
    public void crearUsuario(Usuario user) throws ScoutException {
        em.persist(user);                               
    }

    @Override
    public void borrarUsuario(int idUsuario) throws ScoutException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void editarUsuario(int idUsuario) throws ScoutException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
