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
 * @author Dani
 */
@Entity
public class Comentarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private ComentariosUsuarioEventosDebil idComentarios;
    @Column(nullable = false, length = 500)
    private String cuerpo;

    @MapsId("idUsuario")
    @JoinColumn(name = "idUsuario", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuario;

    @MapsId("idEvento")
    @JoinColumn(name = "idEvento", referencedColumnName = "id")
    @ManyToOne
    private Eventos eventoC;

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public ComentariosUsuarioEventosDebil getIdComentarios() {
        return idComentarios;
    }

    public void setIdComentarios(ComentariosUsuarioEventosDebil idComentarios) {
        this.idComentarios = idComentarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Eventos getEventoC() {
        return eventoC;
    }

    public void setEventoC(Eventos eventoC) {
        this.eventoC = eventoC;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.idComentarios);
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
        final Comentarios other = (Comentarios) obj;
        if (!Objects.equals(this.idComentarios, other.idComentarios)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Comentarios{" + "idComentarios=" + idComentarios + '}';
    }

}
