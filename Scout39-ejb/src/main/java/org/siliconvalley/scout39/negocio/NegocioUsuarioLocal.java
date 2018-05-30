/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import java.util.List;
import javax.ejb.Local;
import org.siliconvalley.scout39.modelo.Usuario;

/**
 *
 * @author fernandez
 */
@Local
public interface NegocioUsuarioLocal {
    
    public List<Usuario> listaUsuarios();
    public List<Usuario> listaUsuariosAJAX(String pal);
}
