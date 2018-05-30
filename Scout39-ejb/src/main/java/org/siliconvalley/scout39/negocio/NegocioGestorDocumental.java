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
       try{
           Archivo ar = new Archivo();
       
       Usuario aux = em.find(Usuario.class, u.getId());
        ar.setNombre(nombre);
        ar.setTipo(tipo);
        ar.setRuta(ruta);
        ar.setUsuario(aux);
        em.merge(ar);
       } catch(Exception re){
           Logger.getLogger(NegocioGestorDocumental.class.getName()).log(Level.WARNING,re.getMessage(),re.getCause());
       }
//        if (aux.getArchivo().size()== 0) {
//            List<Archivo> arc = new ArrayList<>();
//            arc.add(ar);
//            u.setArchivo(arc);
//            em.merge(arc);
//            em.merge(ar);
//        } else {
//            List<Archivo> archi = aux.getArchivo();
//            archi.add(ar);
//            aux.setArchivo(archi);
//            em.merge(aux);
//            em.merge(ar);
//        }
//

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    }
    
    @Override
    public List<Archivo> buscarArchivos(Usuario u){
        
        Usuario aux = em.find(Usuario.class, u.getId());
        List<Archivo> ar = aux.getArchivo();
        
        return ar;
        
    }
}
