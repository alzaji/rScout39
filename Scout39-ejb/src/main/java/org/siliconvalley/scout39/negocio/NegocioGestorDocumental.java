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
        List<Archivo> ar = aux.getArchivo();

        return ar;

    }

    @Override
    public String buscarPath(Archivo ar){
        Archivo aux = em.find(Archivo.class, ar.getId());
        return aux.getRuta();
    }
}
