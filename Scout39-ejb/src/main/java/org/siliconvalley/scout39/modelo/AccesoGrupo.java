package org.siliconvalley.scout39.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class AccesoGrupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date Fecha_Alta_Grupo;
    @Temporal(TemporalType.DATE)
    private Date Fecha_Baja_Grupo;
    @Column(length = 500)
    private String promesa;
    @ManyToOne
    private Usuario Usuario_Grupo;
    @ManyToOne
    private Grupo grupo;

    public String getPromesa() {
        return promesa;
    }

    public void setPromesa(String promesa) {
        this.promesa = promesa;
    }

    public Date getFecha_Alta_Grupo() {
        return Fecha_Alta_Grupo;
    }

    public void setFecha_Alta_Grupo(Date Fecha_Alta_Grupo) {
        this.Fecha_Alta_Grupo = Fecha_Alta_Grupo;
    }

    public Date getFecha_Baja_Grupo() {
        return Fecha_Baja_Grupo;
    }

    public void setFecha_Baja_Grupo(Date Fecha_Baja_Grupo) {
        this.Fecha_Baja_Grupo = Fecha_Baja_Grupo;
    }

    public Usuario getUsuario_Grupo() {
        return Usuario_Grupo;
    }

    public void setUsuario_Grupo(Usuario Usuario_Grupo) {
        this.Usuario_Grupo = Usuario_Grupo;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof AccesoGrupo)) {
            return false;
        }
        AccesoGrupo other = (AccesoGrupo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "scout39jpa.AccesoGrupo[ id=" + id + " ]";
    }

}
