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
 * @author alzaji
 */
@Embeddable
public class AccesoRecursoId implements Serializable {
    
    Long idRol;
    Long idPrivilegio;
    Long idObjeto;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.idRol);
        hash = 97 * hash + Objects.hashCode(this.idPrivilegio);
        hash = 97 * hash + Objects.hashCode(this.idObjeto);
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
        final AccesoRecursoId other = (AccesoRecursoId) obj;
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
