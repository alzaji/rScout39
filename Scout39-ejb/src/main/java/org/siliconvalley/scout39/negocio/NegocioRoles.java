/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import javax.persistence.EntityManager;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.siliconvalley.scout39.modelo.AccesoRecurso;
import javax.persistence.Query;
import org.siliconvalley.scout39.modelo.Objeto;
import org.siliconvalley.scout39.modelo.Privilegios;
import org.siliconvalley.scout39.modelo.Roles;

@Stateless
public class NegocioRoles implements NegocioRolesLocal {

    @PersistenceContext(unitName = "Scout39MPU")
    private EntityManager em;

    @Override
    public List<Roles> listaRoles() {
        Query q = em.createQuery("SELECT r FROM Roles r WHERE NOT r.nombrerol='Coordinador'");
        List<Roles> roles = (List<Roles>) q.getResultList();
        return roles;
    }

    @Override
    public List<Objeto> listaObjetos() {
        Query q = em.createQuery("select o from Objeto o order by o.id asc");
        List<Objeto> objetos = (List<Objeto>) q.getResultList();
        return objetos;
    }

    @Override
    public Long hasP(Roles r, Objeto o) {

        Query q = em.createQuery("select count(ar) from AccesoRecurso ar where ar.idObjeto = :ob and ar.idRol = :ro");
        q.setParameter("ob", o);
        q.setParameter("ro", r);
        Long i = (Long) q.getSingleResult();
        return i;
    }

    @Override
    public void crearRol(Roles rol) throws ScoutException{
        try {
            em.merge(rol);
        } catch (Exception r) {
            throw new ScoutException("Ya existe ese rol");
        }
    }

    @Override
    public void modificarRol(AccesoRecurso ar) {
        em.merge(ar);
    }

    @Override
    public String getPCreate(Roles r, Objeto o) {
        Query q = em.createQuery("select p.crear from Privilegios p, AccesoRecurso ac where ac.idObjeto=:obj and ac.idRol=:rol");
        q.setParameter("rol", r);
        q.setParameter("obj", o);
        String cad = (String) q.getSingleResult();
        return cad;
    }
    @Override
     public AccesoRecurso findAr(Roles rol, Objeto o){
        try {
            Query q = em.createQuery("select ar from AccesoRecurso ar where ar.idObjeto=:ob and ar.idRol = :r ");
            q.setParameter("r", rol);
            q.setParameter("ob", o);
            AccesoRecurso a = (AccesoRecurso) q.getSingleResult();
            return a;
        } catch (NoResultException e) {
            
            AccesoRecurso a = new AccesoRecurso();
            a.setIdObjeto(o);
            a.setIdRol(rol);
            return a;
        }
     }

    @Override
    public Objeto findObj(String obj) {
     
        Query q = em.createQuery("Select o from Objeto o where o.nombre = :obj");
        q.setParameter("obj", obj);
        Objeto o = (Objeto) q.getSingleResult();
        return o;
    }
     
     

    @Override
    public String getPRead(Roles r, Objeto o) {
        Query q = em.createQuery("select p.leer from Privilegios p, AccesoRecurso ac where ac.idObjeto=:obj and ac.idRol=:rol");
        q.setParameter("rol", r);
        q.setParameter("obj", o);
        String cad = (String) q.getSingleResult();
        return cad;
    }

    @Override
    public String getPUpdate(Roles r, Objeto o) {
        Query q = em.createQuery("select p.modificar from Privilegios p, AccesoRecurso ac where ac.idObjeto=:obj and ac.idRol=:rol");
        q.setParameter("rol", r);
        q.setParameter("obj", o);
        String cad = (String) q.getSingleResult();
        return cad;
    }

    @Override
    public String getPDelete(Roles r, Objeto o) {
        Query q = em.createQuery("select p.borrar from Privilegios p, AccesoRecurso ac where ac.idObjeto=:obj and ac.idRol=:rol");
        q.setParameter("rol", r);
        q.setParameter("obj", o);
        String cad = (String) q.getSingleResult();
        return cad;
    }

    @Override
    public Privilegios find(String c, String r, String u, String d) {
        try {
            Query q = em.createQuery("select p from Privilegios p where p.crear = :cr and p.leer = :re and p.modificar = :up and p.borrar = :de");
            q.setParameter("cr", c.charAt(0));
            q.setParameter("re", r.charAt(0));
            q.setParameter("up", u.charAt(0));
            q.setParameter("de", d.charAt(0));
            Privilegios p = (Privilegios) q.getSingleResult();
            return p;
        } catch (NoResultException e) {
            
            Privilegios p = new Privilegios();
            p.setCrear(c.charAt(0));
            p.setLeer(r.charAt(0));
            p.setModificar(u.charAt(0));
            p.setBorrar(d.charAt(0));
            em.merge(p);
            
            return em.find(Privilegios.class, p);
        }
    }

    @Override
    public void borrarRol(Roles rol) throws ScoutException {
        try {
            Roles r = em.find(Roles.class, rol);
            em.remove(r);
        } catch (Exception e) {
            throw new ScoutException("Error al borrar el rol");
        }
    }
    
}
