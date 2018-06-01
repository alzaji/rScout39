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
        try {
            List<Archivo> ar = buscarArchivos(u);
            for (Archivo arch : ar) {
                if (arch.getRuta().equals(ruta)) {
                    return arch.getRuta();
                }
            }
        } catch (Exception e) {
            Logger.getLogger(NegocioGestorDocumental.class.getName()).log(Level.WARNING, e.getMessage(), e.getCause());
        }

        return null;
    }
    
    @Override
    public void borrarArchivo(Usuario u,Archivo a){
          try {
            Archivo aux = em.find(Archivo.class, a.getId());
            em.remove(aux);
        } catch (Exception e) {
            Logger.getLogger(NegocioGestorDocumental.class.getName()).log(Level.WARNING, e.getMessage(), e.getCause());
        }
    }
}
