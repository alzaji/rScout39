package org.siliconvalley.scout39.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String alias;
    @Column(nullable = false, length = 255)
    private String nombre;
    @Column(nullable = false, length = 255)
    private String apellidos;
    @Column(nullable = false, length = 64)
    private String digest;
    @Column(nullable = false, length = 128)
    private String email;
    @Lob
    private byte[] avatar;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha_alta;
    @Temporal(TemporalType.DATE)
    private Date fecha_baja;
    @ManyToOne
    private Roles roles;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Comentarios> comentarios;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Cuotas> cuota;
    @OneToMany(mappedBy = "idUsuario", cascade = CascadeType.ALL)
    private List<Archivo> archivo;
    @OneToMany(mappedBy = "usuarioP", cascade = CascadeType.ALL)
    private List<Progresion> progresion;
    @OneToMany(mappedBy = "Usuario_Grupo", cascade = CascadeType.ALL)
    private List<AccesoGrupo> Acceso_Grupo;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public Date getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(Date fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public List<Comentarios> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentarios> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Cuotas> getCuota() {
        return cuota;
    }

    public void setCuota(List<Cuotas> cuota) {
        this.cuota = cuota;
    }

    public List<Archivo> getArchivo() {
        return archivo;
    }

    public void setArchivo(List<Archivo> archivo) {
        this.archivo = archivo;
    }

    public List<Progresion> getProgresion() {
        return progresion;
    }

    public void setProgresion(List<Progresion> progresion) {
        this.progresion = progresion;
    }

    public List<AccesoGrupo> getAcceso_Grupo() {
        return Acceso_Grupo;
    }

    public void setAcceso_Grupo(List<AccesoGrupo> Acceso_Grupo) {
        this.Acceso_Grupo = Acceso_Grupo;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "scout39jpa.Usuario[ id=" + id + " ]";
    }

}
