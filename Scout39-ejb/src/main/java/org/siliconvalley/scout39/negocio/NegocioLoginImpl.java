/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    public void registrarUsuario(Usuario u) throws ScoutException {

        em.merge(u);

    }

    @Override
    public Usuario comprobarUsuario(String alias) throws ScoutException {

        Query q = em.createQuery("Select u from Usuario u where alias = :alias");
        q.setParameter("alias", alias);
        
        try {
            Usuario u = (Usuario) q.getSingleResult();
            return u;
            
        }catch (RuntimeException e) {
            
            throw new ScoutException("No se encontro el usuario con alias " + alias);
        }

    }

}
