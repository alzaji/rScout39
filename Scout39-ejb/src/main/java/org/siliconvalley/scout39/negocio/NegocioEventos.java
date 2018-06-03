/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    public void crearEvento(Eventos e, Long idGrupo) throws ScoutException {

        try {
            Eventos e1 = insertarEvento(e);
            Logger.getLogger(NegocioEventos.class.getName()).log(Level.WARNING, e1.getId().toString(), e1);
            Query q = em.createQuery("SELECT o FROM Objeto o WHERE o.nombre = :nombre");
            q.setParameter("nombre", "eventos" + idGrupo);
            Objeto o = (Objeto) q.getSingleResult();
            List<Eventos> eventosObjeto = o.getListaEventos();
            eventosObjeto.add(e1);
            o.setListaEventos(eventosObjeto);
            em.merge(o);
        } catch (Exception ex) {
            throw new ScoutException("No se pudo crear el evento en la BD");
        }

    }

    @Override
    public void borrarEvento(Eventos e) throws ScoutException {
        try {
            Query q = em.createQuery("SELECT o FROM Objeto o WHERE :evento MEMBER OF o.listaEventos");
            q.setParameter("evento", e);
            Objeto o = (Objeto) q.getSingleResult();
            List<Eventos> eventos = o.getListaEventos();
            eventos.remove(e);
            em.merge(o);
            em.remove(em.find(Eventos.class, e.getId()));
        } catch (Exception ex) {
            throw new ScoutException("Error al borrar el evento de la BD");
        }
    }

    @Override
    public Eventos modificarEvento(Eventos e) throws ScoutException {
        try {
            Eventos mEvento = em.find(Eventos.class, e.getId());
            mEvento.setNombre(e.getNombre());
            mEvento.setDescripcion(e.getDescripcion());
            mEvento.setFecha(e.getFecha());
            mEvento.setLatitud(e.getLatitud().stripTrailingZeros());
            mEvento.setLongitud(e.getLongitud().stripTrailingZeros());
            em.merge(mEvento);
            return em.find(Eventos.class, e.getId());
        } catch (Exception ex) {

            throw new ScoutException("No se pudo modificar el evento en la BD");
        }
    }

    @Override
    public List<Eventos> eventosProximos(Long idGrupo) {
        Query q = em.createQuery("SELECT o FROM Objeto o WHERE o.nombre = :nombre");
        q.setParameter("nombre", "eventos" + idGrupo);
        Objeto o = (Objeto) q.getSingleResult();
        List<Eventos> eventosObjeto = o.getListaEventos();
        List<Eventos> eventosProximos = new ArrayList<>();
        for (Eventos evento : eventosObjeto) {
            if (evento.getFecha().after(new Date())) {
                eventosProximos.add(evento);
            }
        }
        return eventosProximos;
    }

    @Override
    public List<Eventos> eventosPasados(Long idGrupo) {
        Query q = em.createQuery("SELECT o FROM Objeto o WHERE o.nombre = :nombre");
        q.setParameter("nombre", "eventos" + idGrupo);
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
        Eventos e1 = em.find(Eventos.class, e.getId());
        e1.getComentariosE().size();
        List<Comentarios> listaComentarios = e1.getComentariosE();

        return listaComentarios;
    }

    @Override
    public Eventos buscarEvento(Eventos evento) throws ScoutException {
        try {

            return em.find(Eventos.class, evento.getId());
        } catch (Exception e) {
            throw new ScoutException("No se ha encontrado el evento pedido");
        }
    }

    private Eventos insertarEvento(Eventos e) {
        e.setComentariosE(new ArrayList<Comentarios>());
        e.setProgresionesE(new ArrayList<Progresion>());
        return em.merge(e);
    }

    @Override
    public List<Comentarios> listaRespuestasComentarios(Comentarios c) {
        Comentarios resp = em.find(Comentarios.class, c.getId());
        resp.getRespuestas().size();
        List<Comentarios> respuestas = resp.getRespuestas();
        return respuestas;
    }

    @Override
    public void nuevoComentario(Comentarios c) throws ScoutException {
        try {
            em.merge(c);
        } catch (Exception ex) {
            throw new ScoutException("Error al insertar el comentario en la BD");
        }
    }

    @Override
    public void respuestaComentario(Comentarios c) throws ScoutException {
        try {
            em.merge(c);
        } catch (Exception e) {
            throw new ScoutException("Error al insertar la respuesta al comentario en la BD");
        }
    }

    @Override
    public List<Progresion> obtenerParticipantes(Eventos e) {
        Query q = em.createQuery("SELECT p FROM Progresion p WHERE p.eventoP = :evento");
        q.setParameter("evento", e);
        List<Progresion> participantes = q.getResultList();
        return participantes;
    }

    @Override
    public void asistirEvento(Usuario u, Eventos e) throws ScoutException {
        try {
            Progresion p = new Progresion();
            p.setAnimacion(0);
            p.setIntegracion(0);
            p.setParticipacion(0);
            p.setEventoP(e);
            p.setUsuarioP(u);
            em.merge(p);
        } catch (Exception ex) {
            throw new ScoutException("Error al insertar la progresión en la BD");
        }
    }

    @Override
    public void noAsistirEvento(Usuario u, Eventos e) throws ScoutException {

        try {
            Query q = em.createQuery("SELECT p FROM Progresion p WHERE p.eventoP = :evento AND p.usuarioP = :usuario");
            q.setParameter("usuario", u);
            q.setParameter("evento", e);
            Progresion p = (Progresion) q.getSingleResult();
            em.remove(p);
        } catch (Exception ex) {
            throw new ScoutException("Error al quitar la progresion de la BD");
        }

    }

    @Override
    public boolean comprobarAsistencia(Usuario u, Eventos e) {
        try {
            Query q = em.createQuery("SELECT p FROM Progresion p WHERE p.eventoP = :evento AND p.usuarioP = :usuario");
            q.setParameter("usuario", u);
            q.setParameter("evento", e);
            Progresion p = (Progresion) q.getSingleResult();
            return true;
        } catch (NoResultException nrw) {
            return false;
        }

    }

    @Override
    public void rellenarProgresion(Progresion p) {
        Progresion p1 = em.find(Progresion.class, p.getIdProgresion());
        p1.setAnimacion(p.getAnimacion());
        p1.setIntegracion(p.getIntegracion());
        p1.setParticipacion(p.getParticipacion());
        em.merge(p1);
    }

    @Override
    public List<Progresion> mediaProgresion(Usuario u) {
        Query q = em.createQuery("SELECT p FROM Progresion p WHERE p.usuarioP = :usuario AND p.animacion > 0 AND p.integracion > 0 AND p.participacion > 0");
        q.setParameter("usuario", u);
        return q.getResultList();
    }

}
