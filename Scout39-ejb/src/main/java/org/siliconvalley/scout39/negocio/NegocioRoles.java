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
    public void crearRol(Roles rol) {
        try {
            em.merge(rol);
        } catch (RuntimeException r) {

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
         Query q= em.createQuery("select ar from AccesoRecurso ar where ar.idObjeto=:ob and ar.idRol = :r ");
         q.setParameter("r", rol);
         q.setParameter("ob",o);
         AccesoRecurso a= (AccesoRecurso)q.getSingleResult();
         return a;
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
        Query q = em.createQuery("select p from privilegios p where ");
        q.setParameter("cr", c);
        q.setParameter("re", r);
        q.setParameter("up", u);
        q.setParameter("de", d);
        Privilegios p = (Privilegios) q.getSingleResult();
        return p;
    }

    @Override
    public void borrarRol(Roles rol) {
        em.remove(rol);
    }
    /*
     @Override
     public Privilegios obtenerP(Roles rol, Objeto o){       
         Query q= em.createQuery("select p from Privilegios p,AccesoRecurso ac where p.id=ac.idPrivilegio and ac.idObjeto = :obj and ac.idRol = :r");
         q.setParameter("obj", o.getId());
         q.setParameter("r", rol.getId());
         Privilegios p=(Privilegios) q.getSingleResult();
         return p;
     }
     */
}
