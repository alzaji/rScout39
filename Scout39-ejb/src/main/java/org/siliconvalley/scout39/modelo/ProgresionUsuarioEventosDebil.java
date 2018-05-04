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
 * @author Fernandez
 */
@Embeddable
public class ProgresionUsuarioEventosDebil implements Serializable {

    private Long idEventos;
    private Long idUsuario;

    public Long getIdEventos() {
        return idEventos;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdEventos(Long idEventos) {
        this.idEventos = idEventos;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.idEventos);
        hash = 17 * hash + Objects.hashCode(this.idUsuario);
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
        final ProgresionUsuarioEventosDebil other = (ProgresionUsuarioEventosDebil) obj;
        if (!Objects.equals(this.idEventos, other.idEventos)) {
            return false;
        }
        if (!Objects.equals(this.idUsuario, other.idUsuario)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProgresionUsuarioEventosDebil{" + "idEventos=" + idEventos + ", idUsuario=" + idUsuario + '}';
    }

}
