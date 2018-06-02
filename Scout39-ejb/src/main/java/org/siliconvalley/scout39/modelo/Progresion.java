/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

/**
 *
 * @author Fernandez
 */
@Entity
public class Progresion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private ProgresionUsuarioEventosDebil idProgresion;

    private Integer integracion;
    private Integer participacion;
    private Integer animacion;
    @MapsId("idUsuario")
    @JoinColumn(name = "idUsuario", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuarioP;
    @MapsId("idEventos")
    @JoinColumn(name = "idEventos", referencedColumnName = "id")
    @ManyToOne
    private Eventos eventoP;

    public Progresion() {
        this.idProgresion = new ProgresionUsuarioEventosDebil(); // Único workaround valido para la solucion a setter/getter de ids
    }

    public ProgresionUsuarioEventosDebil getIdProgresion() {
        return idProgresion;
    }

    public void setIdProgresion(ProgresionUsuarioEventosDebil idProgresion) {
        this.idProgresion = idProgresion;
    }

    public Integer getIntegracion() {
        return integracion;
    }

    public void setIntegracion(Integer integracion) {
        this.integracion = integracion;
    }

    public Integer getParticipacion() {
        return participacion;
    }

    public void setParticipacion(Integer participacion) {
        this.participacion = participacion;
    }

    public Integer getAnimacion() {
        return animacion;
    }

    public void setAnimacion(Integer animacion) {
        this.animacion = animacion;
    }

    public Usuario getUsuarioP() {
        return usuarioP;
    }

    public void setUsuarioP(Usuario usuarioP) {
        this.usuarioP = usuarioP;
    }

    public Eventos getEventoP() {
        return eventoP;
    }

    public void setEventoP(Eventos eventoP) {
        this.eventoP = eventoP;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.idProgresion);
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
        final Progresion other = (Progresion) obj;
        if (!Objects.equals(this.idProgresion, other.idProgresion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Progresion{" + "idProgresion=" + idProgresion + '}';
    }

}
