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
    private Roles idRol;
    
    @MapsId("idPrivilegio")
    @JoinColumn (name = "idPrivilegio", referencedColumnName = "id")
    @ManyToOne
    private Privilegios idPrivilegio;
    
    @MapsId("idObjeto")
    @JoinColumn (name = "idObjeto", referencedColumnName = "id")
    @ManyToOne
    private Objeto idObjeto;
    
    public AccesoRecurso(){
        this.idAccRec = new AccesoRecursoId(); // Único workaround valido para la solucion a setter/getter de ids
    }

    public AccesoRecursoId getIdAccRec() {
        return idAccRec;
    }

    public void setIdAccRec(AccesoRecursoId idAccRec) {
        this.idAccRec = idAccRec;
    }

    public Roles getIdRol() {
        return idRol;
    }

    public void setIdRol(Roles idRol) {
        this.idRol = idRol;
    }

    public Privilegios getIdPrivilegio() {
        return idPrivilegio;
    }

    public void setIdPrivilegio(Privilegios idPrivilegio) {
        this.idPrivilegio = idPrivilegio;
    }

    public Objeto getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(Objeto idObjeto) {
        this.idObjeto = idObjeto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.idAccRec);
        hash = 47 * hash + Objects.hashCode(this.idRol);
        hash = 47 * hash + Objects.hashCode(this.idPrivilegio);
        hash = 47 * hash + Objects.hashCode(this.idObjeto);
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
        if (!Objects.equals(this.idRol, other.idRol)) {
            return false;
        }
        if (!Objects.equals(this.idPrivilegio, other.idPrivilegio)) {
            return false;
        }
        if (!Objects.equals(this.idObjeto, other.idObjeto)) {
            return false;
        }
        return true;
    }

   
}
