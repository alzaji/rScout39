/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

/**
 *
 * @author alzaji
 */
@Entity
public class AccesoRecurso implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AccesoRecursoId idAccRec;
    
    @MapsId("idRol")
    @JoinColumn (name = "idRol", referencedColumnName = "id")
    @ManyToOne
    private Roles rol;
    
    @MapsId("idPrivilegio")
    @JoinColumn (name = "idPrivilegios", referencedColumnName = "id")
    @ManyToOne
    private Privilegios priv;
    
    @MapsId("idObjeto")
    @JoinColumn (name = "idObjeto")
    @ManyToOne
    private Objeto o;

    public AccesoRecursoId getIdAccRec() {
        return idAccRec;
    }

    public void setIdAccRec(AccesoRecursoId idAccRec) {
        this.idAccRec = idAccRec;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public Privilegios getPriv() {
        return priv;
    }

    public void setPriv(Privilegios priv) {
        this.priv = priv;
    }

    public Objeto getO() {
        return o;
    }

    public void setO(Objeto o) {
        this.o = o;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.idAccRec);
        hash = 89 * hash + Objects.hashCode(this.rol);
        hash = 89 * hash + Objects.hashCode(this.priv);
        hash = 89 * hash + Objects.hashCode(this.o);
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
        final AccesoRecurso other = (AccesoRecurso) obj;
        if (!Objects.equals(this.idAccRec, other.idAccRec)) {
            return false;
        }
        if (!Objects.equals(this.rol, other.rol)) {
            return false;
        }
        if (!Objects.equals(this.priv, other.priv)) {
            return false;
        }
        if (!Objects.equals(this.o, other.o)) {
            return false;
        }
        return true;
    }
    
}
