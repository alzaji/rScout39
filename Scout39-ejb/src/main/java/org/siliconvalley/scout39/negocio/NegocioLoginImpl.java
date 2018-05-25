/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.siliconvalley.scout39.modelo.Usuario;

/**
 *
 * @author alzaji
 */
@Stateless
public class NegocioLoginImpl implements NegocioLogin {
    
    @PersistenceContext(unitName = "Scout39MPU")
    private EntityManager em;

    
    @Override
    public void registrarUsuario(Usuario u) throws ScoutException{
        
        comprobarUsuario(u);
        
        em.persist(u);
               
    }

    @Override
    public void comprobarUsuario(Usuario u) throws ScoutException {
        
         Usuario us = em.find(Usuario.class, u.getAlias());
        
        if (us != null){
            
            throw new ScoutException("Ya existe el usuario: " + u.getAlias());
        }
        
    }

}
