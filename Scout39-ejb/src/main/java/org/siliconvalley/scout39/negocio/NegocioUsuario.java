/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.siliconvalley.scout39.modelo.*;

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
        String cadena = "%" + pal.replace(" ", "%") + "%";
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
        Usuario user = em.find(Usuario.class, u.getId());
        user.setAlias(u.getAlias());
        user.setApellidos(u.getApellidos());
        user.setNombre(u.getNombre());
        user.setEmail(u.getEmail());
        user.setRoles(u.getRoles());
        em.merge(user);
    }

    @Override
    public void cambiarPromesa(Usuario u, String promesa) {
        Query q = em.createQuery("SELECT ag FROM AccesoGrupo ag WHERE ag.Usuario_Grupo IS :usuario AND ag.Fecha_Baja_Grupo IS NULL");
        q.setParameter("usuario", u);
        AccesoGrupo ag = (AccesoGrupo) q.getSingleResult();
        ag.setPromesa(promesa);
        em.merge(ag);
    }

    @Override
    public List<AccesoGrupo> listaPromesas(Usuario u) {
        Query q = em.createQuery("SELECT ag FROM AccesoGrupo ag WHERE ag.Usuario_Grupo IS :usuario");
        q.setParameter("usuario", u);
        List<AccesoGrupo> lista = q.getResultList();
        return lista;
    }

    @Override
    public void cambiarPassword(Usuario u, String nueva) {
        String digestNuevo = sha256(nueva);
        Usuario user = em.find(Usuario.class, u.getId());
        user.setDigest(digestNuevo);
        em.merge(user);
        
    }

    @Override
    public String sha256(String rawString) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawString.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte hashByte : hash) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
