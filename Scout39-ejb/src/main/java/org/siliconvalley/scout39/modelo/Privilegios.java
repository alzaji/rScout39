/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author alzaj
 */
@Entity
public class Privilegios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Character lectura;
    @Column(nullable = false)
    private Character escritura;
    @Column(nullable = false)
    private Character borrado;
    @ManyToMany(mappedBy = "listaPrivilegios")
    private List<Objeto> listaObjetos;
    @ManyToMany (mappedBy = "privilegios")
    private List<Roles> listaRoles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getLectura() {
        return lectura;
    }

    public void setLectura(Character lectura) {
        this.lectura = lectura;
    }

    public Character getEscritura() {
        return escritura;
    }

    public void setEscritura(Character escritura) {
        this.escritura = escritura;
    }

    public Character getBorrado() {
        return borrado;
    }

    public void setBorrado(Character borrado) {
        this.borrado = borrado;
    }

    public List<Objeto> getListaObjetos() {
        return listaObjetos;
    }

    public void setListaObjetos(List<Objeto> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public List<Roles> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<Roles> listaRoles) {
        this.listaRoles = listaRoles;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Privilegios)) {
            return false;
        }
        Privilegios other = (Privilegios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "scout39jpa.Privilegios[ id=" + id + " ]";
    }

}
