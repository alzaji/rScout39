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
 * @author pasantru
 */
@Local
public interface NegocioGestorDocumentalLocal {
    
    public void subirArchivo(String ruta,String nombre, String tipo,Usuario u);
    public void subirArchivoCSV(String ruta,String nombre, String tipo,Usuario u);
    public List<Archivo> buscarArchivos(Usuario u);
    public String buscarPath(Usuario u,String ruta);
    public void borrarArchivo(Usuario u,Archivo a );
    public List<Archivo> listarArchivos();
    public List<Archivo> listarArchivosScouter(Grupo g);
    public List<Archivo> listaArchivosAJAX(String pal);
    public List<Archivo> listaArchivosNombreAJAX(String pal);
    public List<Usuario> generaCSVParticipantes(Eventos e);
    public void registrarArchivo(Archivo ar,Grupo g);
    public List<Archivo> obtenerArchivos(Grupo gr);
    public void validarArchivo(Archivo ar);
    public void listarPorGrupo();
}

