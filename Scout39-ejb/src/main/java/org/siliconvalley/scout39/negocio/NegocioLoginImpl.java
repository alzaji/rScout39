/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
    public void registrarUsuario(Usuario u, Grupo g) throws ScoutException {

       // Me traigo el usuario ya con su id y su rol
        Usuario aux = em.merge(u);
        Roles r = aux.getRoles();
        
        //Actualizo la lista de usuarios en el rol
        List<Usuario> ru = r.getUsuarios();
        ru.add(aux);
        em.merge(ru);

        // Creo sus objetos
        Objeto archivos = new Objeto();
        archivos.setNombre("archivos" + aux.getId());
        em.persist(archivos);

        // Le asigno a un grupo
        AccesoGrupo ac = newAcceso(new Date(), null, aux, g);
        em.persist(ac);

        List<AccesoGrupo> lac = new ArrayList<>();
        lac.add(ac);
        aux.setAcceso_Grupo(lac);
        
        em.merge(aux);
        
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
    public Grupo getGrupofromString(String nombre) throws ScoutException {
        
        try{
            
            Query q = em.createQuery("Select g from Grupo g where g.nombre = :nombre");
            q.setParameter("nombre", nombre);
            Grupo g = (Grupo) q.getSingleResult();
            return g;
            
        }catch (NoResultException ex){
            throw new ScoutException("No se encontro el grupo");
        }
    }

    
    @Override
    public Privilegios checkPrivilegios(Objeto o, Usuario u) throws ScoutException {

        try {
            Usuario uaux = em.find(Usuario.class, u.getId());
            Roles r = uaux.getRoles();

            Query q = em.createQuery("Select ar.idPrivilegio from AccesoRecurso ar where ar.idObjeto = :o and ar.idRol = :r");
            q.setParameter("o", o);
            q.setParameter("r", r);

            Privilegios paux = (Privilegios) q.getSingleResult();
            return paux;

        } catch (RuntimeException e) {

            throw new ScoutException(e.getMessage());
        }

    }
    
    @Override
    public Objeto getObjetoActual(String nombreobj, long id) throws ScoutException {
        try {
            Query q = em.createQuery("SELECT o FROM Objeto o WHERE o.nombre = :nombre");
            q.setParameter("nombre", nombreobj + id);
            Objeto o = (Objeto) q.getSingleResult();
            return o;
        } catch (RuntimeException e) {
            throw new ScoutException("No se ha encontrado esta colección en la BD");
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

    private AccesoGrupo newAcceso(
            Date fecha_alta,
            Date fecha_baja,
            Usuario u,
            Grupo g
    ) {

        AccesoGrupo ac = new AccesoGrupo();
        ac.setFecha_Alta_Grupo(fecha_alta);
        ac.setFecha_Baja_Grupo(fecha_baja);
        ac.setUsuario_Grupo(u);
        ac.setGrupo(g);

        return ac;

    }
}
