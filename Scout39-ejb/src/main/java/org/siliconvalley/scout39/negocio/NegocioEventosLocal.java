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
 * @author aruizdlt
 */
@Local
public interface NegocioEventosLocal {
    
    public Eventos buscarEvento(Eventos evento); 
    public void crearEvento(Eventos e, Long idGrupo);
    public void borrarEvento(Eventos e);
    public Eventos modificarEvento(Eventos e);
    public List<Eventos> eventosProximos(Long idGrupo);
    public List<Eventos> eventosPasados(Long idGrupo);
    public List<Comentarios> listaComentarios(Long idGrupo, Eventos e);
    public List<Comentarios> listaRespuestasComentarios(Comentarios c); 
    public void nuevoComentario(Eventos e, Comentarios c);
    public void respuestaComentario(Eventos e, Comentarios c);
}
