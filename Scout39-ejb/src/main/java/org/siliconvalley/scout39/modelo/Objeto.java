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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author aruizdlt
 */
@Entity
public class Objeto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 100, unique = true)
    private String nombre;
    @OneToMany(mappedBy = "idObjeto", cascade = CascadeType.ALL)
    private List<AccesoRecurso> listaAcceso;
    @OneToMany (cascade = CascadeType.ALL)
    private List<Grupo> listaGrupos;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Eventos> listaEventos;
    @OneToMany (cascade = CascadeType.ALL)
    private List<Archivo> listaArchivos;
    @OneToMany (cascade = CascadeType.ALL)
    private List<Cuotas> listaCuotas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<AccesoRecurso> getListaAcceso() {
        return listaAcceso;
    }

    public void setListaAcceso(List<AccesoRecurso> listaAcceso) {
        this.listaAcceso = listaAcceso;
    }

    public List<Grupo> getListaGrupos() {
        return listaGrupos;
    }

    public void setListaGrupos(List<Grupo> listaGrupos) {
        this.listaGrupos = listaGrupos;
    }

    public List<Eventos> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(List<Eventos> listaEventos) {
        this.listaEventos = listaEventos;
    }

    public List<Archivo> getListaArchivos() {
        return listaArchivos;
    }

    public void setListaArchivos(List<Archivo> listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    public List<Cuotas> getListaCuotas() {
        return listaCuotas;
    }

    public void setListaCuotas(List<Cuotas> listaCuotas) {
        this.listaCuotas = listaCuotas;
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
        if (!(object instanceof Objeto)) {
            return false;
        }
        Objeto other = (Objeto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "scout39jpa.Objeto[ id=" + id + " ]";
    }

}
