/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author aruizdlt
 */
@Embeddable
public class UsuarioArchivoDebil implements Serializable {

    private Long idUsuario;
    private Long idArchivo;
    
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Long idArchivo) {
        this.idArchivo = idArchivo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idUsuario);
        hash = 97 * hash + Objects.hashCode(this.idArchivo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioArchivoDebil other = (UsuarioArchivoDebil) obj;
        if (!Objects.equals(this.idUsuario, other.idUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idArchivo, other.idArchivo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UsuarioArchivoDebil{" + "idUsuario=" + idUsuario + ", idArchivo=" + idArchivo + '}';
    }

}
