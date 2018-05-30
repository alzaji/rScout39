/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.siliconvalley.scout39.modelo.AccesoGrupo;
import org.siliconvalley.scout39.modelo.Grupo;
import org.siliconvalley.scout39.modelo.Objeto;
import org.siliconvalley.scout39.modelo.Privilegios;
import org.siliconvalley.scout39.modelo.Roles;
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

        } catch (RuntimeException e) {

            throw new ScoutException("No se ha encontrado el usuario en la BD");
        }

    }

    @Override
    public Grupo grupoActualUsuario(Usuario u) throws ScoutException {

        try {
            Query q = em.createQuery("SELECT ac from AccesoGrupo ac where :u = ac.Usuario_Grupo and ac.Fecha_Baja_Grupo IS NULL");
            q.setParameter("u", u);
            AccesoGrupo ac = (AccesoGrupo) q.getSingleResult();

            return ac.getGrupo();

        } catch (RuntimeException e) {

            throw new ScoutException("No hay resultados para ese usuario");
        }
    }

    @Override
    public Privilegios checkPrivilegios(Objeto o, Usuario u) throws ScoutException {

        try {
            Usuario uaux = em.find(Usuario.class, u.getId());
            Roles r = uaux.getRoles();

            Query q = em.createQuery("Select p "
                    + "from Objeto o, Privilegios p, Roles ra "
                    + "where :a = o.nombre "
                    + "AND :rp = ra.nombrerol "
                    + "AND p MEMBER of o.listaPrivilegios "
                    + "AND p MEMBER of ra.privilegios");
            q.setParameter("a", o.getNombre());
            q.setParameter("rp", r.getNombrerol());

            Privilegios paux = (Privilegios) q.getSingleResult();
            return paux;

        } catch (RuntimeException e) {

            throw new ScoutException(e.getMessage());
        }

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
