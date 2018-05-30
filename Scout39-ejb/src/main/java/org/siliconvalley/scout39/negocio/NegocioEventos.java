/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.siliconvalley.scout39.modelo.*;

/**
 *
 * @author aruizdlt
 */
@Stateless
public class NegocioEventos implements NegocioEventosLocal {

    @PersistenceContext(unitName = "Scout39MPU")
    private EntityManager em;

    @Override
    public void crearEvento(Eventos e, Long idGrupo) {

        try {
            Eventos e1 = insertarEvento(e);
            Logger.getLogger(NegocioEventos.class.getName()).log(Level.WARNING, e1.getId().toString(), e1);
            Query q = em.createQuery("SELECT o FROM Objeto o WHERE o.nombre = :nombre");
            q.setParameter("nombre", "eventosSIRYU");
            Objeto o = (Objeto) q.getSingleResult();
            List<Eventos> eventosObjeto = o.getListaEventos();
            eventosObjeto.add(e1);
            o.setListaEventos(eventosObjeto);
            em.merge(o);
        } catch (Exception re) {
            Logger.getLogger(NegocioEventos.class.getName()).log(Level.SEVERE, re.getMessage(), re.getCause());
        }

    }

    @Override
    public void borrarEvento(Eventos e) {
        Query q = em.createQuery("SELECT o FROM Objeto o WHERE :evento MEMBER OF o.listaEventos");
        q.setParameter("evento", e);
        Objeto o = (Objeto) q.getSingleResult();
        List<Eventos> eventos = o.getListaEventos();
        eventos.remove(e);
        em.merge(o);
        em.remove(em.find(Eventos.class, e.getId()));
    }

    @Override
    public void modificarEvento(Eventos e) {
        Eventos mEvento = em.find(Eventos.class, e.getId());
        mEvento.setNombre(e.getNombre());
        mEvento.setDescripcion(e.getDescripcion());
        mEvento.setFecha(e.getFecha());
        mEvento.setLatitud(e.getLatitud().stripTrailingZeros());
        mEvento.setLongitud(e.getLongitud().stripTrailingZeros());
        em.merge(mEvento);
                
    }

    @Override
    public List<Eventos> eventosProximos(Long idGrupo) {
        Query q = em.createQuery("SELECT o FROM Objeto o WHERE o.nombre = :nombre");
        q.setParameter("nombre", "eventosSIRYU");
        Objeto o = (Objeto) q.getSingleResult();
        List<Eventos> eventosObjeto = o.getListaEventos();
        List<Eventos> eventosProximos = new ArrayList<>();
        for (Eventos evento : eventosObjeto) {            
            if (evento.getFecha().after(new Date())) {
                eventosProximos.add(evento);
            }
        }

//        }
        return eventosProximos;
//        Date now = new Date();
//        Query q = em.createQuery("SELECT e FROM Eventos e WHERE e.fecha > :today");
//        q.setParameter("today", now, TemporalType.DATE);        
//        return q.getResultList();
    }

    @Override
    public List<Eventos> eventosPasados(Long idGrupo) {
        Query q = em.createQuery("SELECT o FROM Objeto o WHERE o.nombre = :nombre");
        q.setParameter("nombre", "eventosSIRYU");
        Objeto o = (Objeto) q.getSingleResult();
        List<Eventos> eventosObjeto = o.getListaEventos(); 
        List<Eventos> eventosPasados = new ArrayList<>();
        for (Eventos evento : eventosObjeto) {
            if (evento.getFecha().before(new Date())) {
                eventosPasados.add(evento);
            }
        }
        return eventosPasados;
    }

    @Override
    public List<Comentarios> listaComentarios(Long idGrupo, Eventos e) {
        Objeto o = new Objeto();
        o.setNombre("evento" + idGrupo);
        List<Eventos> listaEventos = em.find(Objeto.class,
                o).getListaEventos();
        List<Comentarios> listaComentarios = listaEventos.get(listaEventos.indexOf(e)).getComentariosE();
        return listaComentarios;
    }

    private Eventos insertarEvento(Eventos e) {
        e.setComentariosE(new ArrayList<Comentarios>());
        e.setProgresionesE(new ArrayList<Progresion>());
        return em.merge(e);
    }
}
