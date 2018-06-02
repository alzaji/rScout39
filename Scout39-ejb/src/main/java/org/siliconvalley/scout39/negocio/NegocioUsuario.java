/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.siliconvalley.scout39.modelo.Usuario;

/**
 *
 * @author fernandez
 */
@Stateless
public class NegocioUsuario implements NegocioUsuarioLocal {
    
    @PersistenceContext(unitName = "Scout39MPU")
    private EntityManager em;
    
    @Override
    public List<Usuario> listaUsuarios() {
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.fecha_baja IS null");                
        List<Usuario> usuarios = (List<Usuario>) q.getResultList();        
        return usuarios;
    }

    @Override
    public List<Usuario> listaUsuariosAJAX(String pal) {
        String cadena = "%" + pal.replace(" ", "%") + "%" ;
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.alias LIKE :alias AND u.fecha_baja IS null");
        q.setParameter("alias", cadena);
        System.out.println(q.getResultList());
        List<Usuario> usuarios;
        usuarios = (List<Usuario>) q.getResultList();        
        return usuarios;
    }

    @Override
    public void borrarUsuario(Usuario u) {            
        Usuario usuario = em.find(Usuario.class, u.getId());
        Date f = new Date();        
        usuario.setFecha_baja(f);
        em.merge(usuario);        
    }

    @Override
    public void modificarUsuario(Usuario u) {
        System.out.println("Alias: ------------------------------------------------>"+ u.getAlias());
        Usuario user = em.find(Usuario.class, u.getId());
        user.setAlias(u.getAlias());
        user.setApellidos(u.getApellidos());
        user.setNombre(u.getNombre());
        user.setEmail(u.getEmail());
        user.setRoles(u.getRoles());
        em.merge(user);
    }

   
    
}
