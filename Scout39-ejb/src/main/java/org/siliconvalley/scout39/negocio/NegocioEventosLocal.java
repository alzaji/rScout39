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
    
    public Eventos buscarEvento(Eventos evento) throws ScoutException; 
    public void crearEvento(Eventos e, Long idGrupo) throws ScoutException;
    public void borrarEvento(Eventos e) throws ScoutException ;
    public Eventos modificarEvento(Eventos e) throws ScoutException;
    public List<Eventos> eventosProximos(Long idGrupo);
    public List<Eventos> eventosPasados(Long idGrupo);
    public List<Comentarios> listaComentarios(Long idGrupo, Eventos e);
    public List<Comentarios> listaRespuestasComentarios(Comentarios c); 
    public void nuevoComentario(Comentarios c) throws ScoutException;
    public void respuestaComentario(Comentarios c) throws ScoutException;
    public List<Progresion> obtenerParticipantes(Eventos e);
    public void asistirEvento(Usuario u, Eventos e) throws ScoutException;
    public void noAsistirEvento(Usuario u, Eventos e) throws ScoutException;
    public boolean comprobarAsistencia(Usuario u, Eventos e);
    public void rellenarProgresion(Progresion p);
    public List<Progresion> mediaProgresion(Usuario u);
}
