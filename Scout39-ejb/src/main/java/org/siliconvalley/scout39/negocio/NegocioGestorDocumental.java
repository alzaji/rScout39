/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.siliconvalley.scout39.modelo.*;

/**
 *
 * @author hidden-process
 * @author pasantru
 */
@Stateless
public class NegocioGestorDocumental implements NegocioGestorDocumentalLocal {

    @PersistenceContext(unitName = "Scout39MPU")
    private EntityManager em;

    // Se sube un archivo a la BD.
    @Override
    public void subirArchivo(String ruta, String nombre, String tipo, Usuario u) {
        Archivo ar = new Archivo();
        Usuario aux = em.find(Usuario.class, u.getId());
        ar.setNombre(nombre);
        ar.setTipo(tipo);
        ar.setRuta(ruta);
        ar.setIdUsuario(aux);
        em.merge(ar);
    }

    @Override
    public void subirArchivoCSV(String ruta, String nombre, String tipo, Usuario u) {

        try {
            TypedQuery<Archivo> tq = em.createQuery("from Archivo where nombre=?", Archivo.class);
            Archivo arux = tq.setParameter(1, nombre).getSingleResult();

        } catch (NoResultException ex) {

            Archivo ar = new Archivo();
            Usuario aux = em.find(Usuario.class, u.getId());
            ar.setNombre(nombre);
            ar.setTipo(tipo);
            ar.setRuta(ruta);
            ar.setIdUsuario(aux);
            em.merge(ar);

        }
    }

    // Se buscan los archivos pertenecientes a un Usuario.
    @Override
    public List<Archivo> buscarArchivos(Usuario u) {

        Usuario aux = em.find(Usuario.class, u.getId());
        aux.getArchivo().size();
        List<Archivo> ar = aux.getArchivo();

        return ar;

    }

    @Override
    public List<Archivo> buscarArchivosScouter(Usuario u) {

        Usuario aux = em.find(Usuario.class, u.getId());
        aux.getArchivo().size();
        List<Archivo> ar = aux.getArchivo();

        return ar;

    }

    // Se obtiene el path para permitir descagar un archivo almacenado.
    @Override
    public String buscarPath(Usuario u, String ruta) {
        List<Archivo> ar = buscarArchivos(u);
        for (Archivo arch : ar) {
            if (arch.getRuta().equals(ruta)) {
                return arch.getRuta();
            }
        }

        return null;
    }

    // Se borra un archivo de la BD.
    @Override
    public void borrarArchivo(Usuario u, Archivo a) {
        Archivo aux = em.find(Archivo.class, a.getId());
        em.remove(aux);
    }

    // Muestra Todos los Archivos al coordinador
    @Override
    public List<Archivo> listarArchivos() {
        Query q = em.createQuery("SELECT a FROM Archivo a");
        List<Archivo> archivos = (List<Archivo>) q.getResultList();
        return archivos;
    }

    // Muestra los Archivos de los Educando de un grupo
    @Override
    public List<Archivo> listarArchivosScouter(Grupo g) {
        Query q = em.createQuery("Select a from Archivo a, Usuario u, AccesoGrupo ac where u = ac.Usuario_Grupo and :grupo = ac.grupo and ac.Fecha_Baja_Grupo IS NULL");
        q.setParameter("grupo", g);
        List<Archivo> archivos = (List<Archivo>) q.getResultList();
        return archivos;
    }

    // Filtrado por Ajax de los archivos segun el alias del usuario.
    @Override
    public List<Archivo> listaArchivosAJAX(String pal) {
        String cadena = "%" + pal.replace(" ", "%") + "%";
        Query q = em.createQuery("SELECT a from Archivo a,Usuario u WHERE u.alias LIKE :alias and a MEMBER of u.archivo");
        q.setParameter("alias", cadena);
        List<Archivo> archivos;
        archivos = (List<Archivo>) q.getResultList();
        return archivos;
    }

    // Filtrado por Ajax de los archivos segun el nombre de estos
    @Override
    public List<Archivo> listaArchivosNombreAJAX(String pal) {
        String cadena = "%" + pal.replace(" ", "%") + "%";
        Query q = em.createQuery("SELECT a from Archivo a, Usuario u WHERE a.nombre LIKE :archivo and a.fecha_limite > CURRENT_DATE");
        q.setParameter("archivo", cadena);
        List<Archivo> archivos;
        archivos = (List<Archivo>) q.getResultList();
        return archivos;
    }

    @Override
    public List<Usuario> generaCSVParticipantes(Eventos e) {

        Eventos epart = em.find(Eventos.class, e.getId());

        Query q = em.createQuery("SELECT u from Eventos e, Progresion p, Usuario u where p.usuarioP = u and e = :epart");
        q.setParameter("epart", epart);
        List<Usuario> participantes = q.getResultList();

        return participantes;

    }

    // El Scouter Registra una peticion de subida de Archivo a los educandos de su grupo.
    @Override
    public void registrarArchivo(Archivo ar, Grupo g) {
        try {
            Query q = em.createQuery("SELECT ac from AccesoGrupo ac where :grupo = ac.grupo and ac.Fecha_Baja_Grupo IS NULL");
            q.setParameter("grupo", g);
            List<AccesoGrupo> ac = q.getResultList();
            for (AccesoGrupo gr : ac) {
                Archivo a = new Archivo();
                a.setNombre(g.getNombre().replace(" ", "") + "_" + gr.getUsuario_Grupo().getAlias() + "_" + ar.getNombre());
                a.setRuta(ar.getRuta());
                a.setTipo(ar.getTipo());
                a.setEstado(ar.getEstado());
                a.setFecha_limite(ar.getFecha_limite());
                a.setIdUsuario(gr.getUsuario_Grupo());
                em.merge(a);
            }

        } catch (Exception re) {
            Logger.getLogger(NegocioEventos.class.getName()).log(Level.WARNING, re.getMessage(), re.getCause());
        }
    }

    @Override
    public List<Archivo> obtenerArchivos(Grupo gr) {
        Query q = em.createQuery("SELECT a FROM Archivo a, Objeto o, Grupo g  WHERE o.id = a.id and :g.id = o.id");
        q.setParameter("g", gr);
        List<Archivo> archivos = q.getResultList();
        return archivos;
    }

    // Validamos una subida de un archivo cambiando su estado a entregado.
    @Override
    public void validarArchivo(Archivo ar) {
        Archivo aux = em.find(Archivo.class, ar.getId());
        aux.setEstado('S');
        em.merge(aux);
    }

    @Override
    public List<Archivo> listaArchivosNombreAJAXScouter(Grupo g, String pal) {
        String cadena = "%" + pal.replace(" ", "%") + "%";

        Query q = em.createQuery("Select a from Archivo a, Usuario u, AccesoGrupo ac where a.nombre LIKE :archivo and ac.grupo = :grupo and u = ac.Usuario_Grupo and ac.Fecha_Baja_Grupo IS NULL and u.roles.nombrerol IS NOT :nombrerol");
        q.setParameter("archivo", cadena);
        q.setParameter("grupo", g);
        q.setParameter("nombrerol", "Scouter");
        List<Archivo> archivos;
        archivos = (List<Archivo>) q.getResultList();
        return archivos;
    }
    
    @Override
    public void subirArchivo(String ruta, String nombre, String tipo, Usuario u, Archivo o) {
        Archivo aux = em.find(Archivo.class, o.getId());
        aux.setNombre(nombre);
        aux.setRuta(ruta);
        aux.setTipo(tipo);
        em.merge(aux);
    }

}
