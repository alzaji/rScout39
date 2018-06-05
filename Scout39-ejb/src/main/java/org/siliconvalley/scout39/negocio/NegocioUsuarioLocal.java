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
 * @author fernandez
 */
@Local
public interface NegocioUsuarioLocal {
    
    public List<Usuario> listaUsuarios();
    public List<Usuario> listaUsuariosAJAX(String pal);
    public void borrarUsuario(Usuario u);
    public void modificarUsuario(Usuario u, AccesoGrupo ag);
    public void cambiarPromesa(Usuario u, String promesa);
    public List<AccesoGrupo> listaPromesas(Usuario u);
    public void cambiarPassword(Usuario u, String nueva);
    public String sha256(String rawString);
    
}
