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
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.siliconvalley.scout39.modelo.*;

/**
 *
 * @author hidden-process
 */
@Stateless
public class NegocioGestorDocumental implements NegocioGestorDocumentalLocal {

    @PersistenceContext(unitName = "Scout39MPU")
    private EntityManager em;

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
    public List<Archivo> buscarArchivos(Usuario u) {

        Usuario aux = em.find(Usuario.class, u.getId());
        aux.getArchivo().size();
        List<Archivo> ar = aux.getArchivo();

        return ar;

    }

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

    @Override
    public void borrarArchivo(Usuario u, Archivo a) {
        Archivo aux = em.find(Archivo.class, a.getId());
        em.remove(aux);
    }

    @Override
    public List<Archivo> listarArchivos() {
        Query q = em.createQuery("SELECT a FROM Archivo a");
        List<Archivo> archivos = (List<Archivo>) q.getResultList();
        return archivos;
    }

    @Override
    public List<Archivo> listaArchivosAJAX(String pal) {
        String cadena = "%" + pal.replace(" ", "%") + "%";
        Query q = em.createQuery("SELECT a from Archivo a,Usuario u WHERE u.alias LIKE :alias and a MEMBER of u.archivo");
        q.setParameter("alias", cadena);
        System.out.println(q.getResultList());
        List<Archivo> archivos;
        archivos = (List<Archivo>) q.getResultList();
        return archivos;
    }

    @Override
    public List<Archivo> listaArchivosNombreAJAX(String pal) {
        String cadena = "%" + pal.replace(" ", "%") + "%";
        Query q = em.createQuery("SELECT a from Archivo a,Usuario u WHERE a.nombre LIKE :archivo and a.Fecha_limite < CURRENT_DATE");
        q.setParameter("archivo", cadena);
        System.out.println(q.getResultList());
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

    @Override
    public void registrarArchivo(Archivo ar, Grupo g) {
        try {
            Query q = em.createQuery("SELECT ac from AccesoGrupo ac where :grupo = ac.grupo and ac.Fecha_Baja_Grupo IS NULL");
            q.setParameter("grupo", g);
            List<AccesoGrupo> ac = q.getResultList();
            for (AccesoGrupo gr : ac) {
                Archivo a = new Archivo();
                a.setNombre(gr.getUsuario_Grupo().getAlias() + "_" + ar.getNombre());
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
        Query q = em.createQuery("SELECT a FROM Archivo a, Objeto o, Grupo g  WHERE o.id = a.id and g.id = o.id");
        q.setParameter("grupo",  gr);
        List<Archivo> archivos= q.getResultList();
        return archivos;
    }
    
    @Override
    public void validarArchivo(Archivo ar){
        ar.setEstado('S');
        em.merge(ar);
    }
    
    @Override
    public void listarPorGrupo(){
        // Query para mostrar los archivos de los educando de tu grupo
    }

}
