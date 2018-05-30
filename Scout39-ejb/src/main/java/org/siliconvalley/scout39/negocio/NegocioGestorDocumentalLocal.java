/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import java.util.List;
import javax.ejb.Local;
import org.siliconvalley.scout39.modelo.*;

/**
 *
 * @author hidden-process
 */
@Local
public interface NegocioGestorDocumentalLocal {
    
    public void subirArchivo(String ruta,String nombre, String tipo,Usuario u);
    public List<Archivo> buscarArchivos(Usuario u);
}
