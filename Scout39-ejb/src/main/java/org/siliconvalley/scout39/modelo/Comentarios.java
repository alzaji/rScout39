/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.modelo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Dani
 */
@Entity
public class Comentarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 500)
    private String cuerpo;

    @ManyToOne (cascade = CascadeType.ALL)
    private Usuario usuario;

    @ManyToOne
    private Eventos eventoC;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "respuesta")
    private List<Comentarios> respuestas;
    
    @ManyToOne
    private Comentarios respuesta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
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

    public List<Comentarios> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Comentarios> respuestas) {
        this.respuestas = respuestas;
    }

    public Comentarios getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Comentarios respuesta) {
        this.respuesta = respuesta;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Comentarios{" + "id=" + id + ", cuerpo=" + cuerpo + ", usuario=" + usuario + ", eventoC=" + eventoC + '}';
    }

}
