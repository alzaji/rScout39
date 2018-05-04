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
 * @author Dani
 */
@Embeddable
public class ComentariosUsuarioEventosDebil implements Serializable {

    private Long idComentarios;
    private Long idUsuario;
    private Long idEvento;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public Long getIdComentarios() {
        return idComentarios;
    }

    public void setIdComentarios(Long idComentarios) {
        this.idComentarios = idComentarios;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.idComentarios);
        hash = 47 * hash + Objects.hashCode(this.idUsuario);
        hash = 47 * hash + Objects.hashCode(this.idEvento);
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
        final ComentariosUsuarioEventosDebil other = (ComentariosUsuarioEventosDebil) obj;
        if (!Objects.equals(this.idComentarios, other.idComentarios)) {
            return false;
        }
        if (!Objects.equals(this.idUsuario, other.idUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idEvento, other.idEvento)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ComentariosUsuarioEventosDebil{" + "idComentarios=" + idComentarios + ", idUsuario=" + idUsuario + ", idEvento=" + idEvento + '}';
    }

}
