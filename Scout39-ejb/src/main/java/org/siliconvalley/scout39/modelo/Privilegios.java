/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

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
    private Character crear;
    @Column(nullable = false)
    private Character leer;
    @Column(nullable = false)
    private Character modificar;
    @Column(nullable = false)
    private Character borrar;
    @OneToMany(mappedBy = "idPrivilegio", cascade = CascadeType.ALL)
    private List<AccesoRecurso> accesorec;
    @ManyToMany(mappedBy = "privilegios", cascade = CascadeType.ALL)
    private List<Roles> listaRoles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getCrear() {
        return crear;
    }

    public void setCrear(Character crear) {
        this.crear = crear;
    }

    public Character getLeer() {
        return leer;
    }

    public void setLeer(Character leer) {
        this.leer = leer;
    }

    public Character getModificar() {
        return modificar;
    }

    public void setModificar(Character modificar) {
        this.modificar = modificar;
    }

    public Character getBorrar() {
        return borrar;
    }

    public void setBorrar(Character borrar) {
        this.borrar = borrar;
    }

    public List<AccesoRecurso> getAccesorec() {
        return accesorec;
    }

    public void setAccesorec(List<AccesoRecurso> accesorec) {
        this.accesorec = accesorec;
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
