/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.siliconvalley.scout39.modelo.*;

/**
 *
 * @author alzaji
 */
@Stateless
public class NegocioCuotas implements NegocioCuotasLocal {

    @PersistenceContext(unitName = "Scout39MPU")
    private EntityManager em;

    @Override
    public void crearCuota(Cuotas c) {

        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.fecha_baja IS null");
        List<Usuario> usuarios = (List<Usuario>) q.getResultList();

        for (Usuario u : usuarios) {

            Cuotas caux = new Cuotas();
            caux.setPrecio(c.getPrecio());
            caux.setFecha_cuota(c.getFecha_cuota());
            caux.setEstado(c.getEstado());
            caux.setUsuario(u);
            em.merge(caux);

        }
    }

    @Override
    public void borrarCuota(String concepto) {

        Query q = em.createQuery("SELECT c from Cuotas c where c.concepto = :concepto");
        q.setParameter("concepto", concepto);
        List<Cuotas> cuotas = (List<Cuotas>) q.getResultList();

        for (Cuotas c : cuotas) {

            em.remove(c);
        }

    }

    @Override
    public List<Cuotas> listaCuotas() {

        Query q = em.createQuery("SELECT c FROM Cuotas c, Usuario u WHERE u.fecha_baja IS null");
        List<Cuotas> cuotas = (List<Cuotas>) q.getResultList();
        
        return cuotas;

    }

    @Override
    public List<Cuotas> listaCuotasAJAX(String concepto) {
    
        String cadena = "%" + concepto.replace(" ", "%") + "%" ;
        Query q = em.createQuery("SELECT c FROM Cuotasc, Usuario u WHERE c.concepto LIKE :concepto AND u.fecha_baja IS null");
        q.setParameter("concepto", concepto);
        List<Cuotas> cuotas = (List<Cuotas>) q.getResultList();        
        return cuotas;
    }

    @Override
    public void cambiarEstadoCuota(Character c, Cuotas cu) {
    
        Cuotas caux = em.find(Cuotas.class, cu.getId());
        caux.setEstado(c);
        em.merge(caux);
    
    }

}
