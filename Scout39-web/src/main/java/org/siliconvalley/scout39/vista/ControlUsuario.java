/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.siliconvalley.scout39.modelo.AccesoGrupo;
import org.siliconvalley.scout39.modelo.Grupo;
import org.siliconvalley.scout39.modelo.Roles;
import org.siliconvalley.scout39.modelo.Usuario;
import org.siliconvalley.scout39.negocio.NegocioLogin;
import org.siliconvalley.scout39.negocio.NegocioUsuarioLocal;
import org.siliconvalley.scout39.negocio.ScoutException;

/**
 *
 * @author Dani
 */
@Named(value = "controlUsuario")
@ManagedBean
@RequestScoped
public class ControlUsuario implements Serializable {

    @Inject
    private Login login;
    @EJB
    private NegocioUsuarioLocal users;
    @EJB
    private NegocioLogin registrar;

    private Usuario usuario;
    private Grupo group = new Grupo();
    private Roles roles = new Roles();
    protected String pal;
    protected boolean update = false;
    @Inject
    private ControlAutorizacion controlAutorizacion;

    public ControlUsuario() {
    }

    public Grupo getGroup() {
        return group;
    }

    public void setGroup(Grupo group) {
        this.group = group;
    }

    public String getPal() {
        return pal;
    }

    public void setPal(String pal) {
        this.pal = pal;
    }

    public List<Usuario> listarUsuarios() {
        if (update) {
            return listarUsuariosAJAX();
        }
        return users.listaUsuarios();
    }

    public void searchListUser() {
        update = true;
        listarUsuarios();
    }

    public List<Usuario> listarUsuariosAJAX() {
        return users.listaUsuariosAJAX(pal);
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String removeUsuario(Usuario u) {

        users.borrarUsuario(u);

        return "editarUsuario.xhtml";
    }

    public void cambiarPass() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String pass = request.getParameter("cambiarContraseña:nuevapass");
        Usuario u = controlAutorizacion.getUsuario();

        users.cambiarPassword(u, pass);
    }

    public String doModificarUsuario(Usuario u, int index) {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String alias = request.getParameter("table:" + index + ":formModificarUsuario:modificarAlias");
            String nombr = request.getParameter("table:" + index + ":formModificarUsuario:modificarNombre");
            String apellidos = request.getParameter("table:" + index + ":formModificarUsuario:modificarApellidos");
            String correo = request.getParameter("table:" + index + ":formModificarUsuario:modificarEmail");
            String grupo = request.getParameter("table:" + index + ":formModificarUsuario:modificarGrupo");
            String rol = request.getParameter("table:" + index + ":formModificarUsuario:modificarRol");

            Roles r = registrar.getRolesfromString(rol);
            Grupo g = registrar.getGrupofromString(grupo);
            Usuario user = newUsuario(u.getId(), alias, u.getDigest(),
                    nombr, apellidos, correo, u.getFecha_alta(), r);
            AccesoGrupo ag = newAcceso(new Date(), null, null, g);
            users.modificarUsuario(user, ag);
            return "editarUsuarios.xhtml?faces-redirect=true";
        } catch (ScoutException ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            return "editarUsuarios.xhtml?faces-redirect=true";
        }

    }

    public List<Roles> getlistaRoles() {

        try {
            return registrar.getAllRoles();
        } catch (ScoutException ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            return new ArrayList<Roles>();
        }
    }

    public List<Grupo> getlistaGrupos() {

        try {
            return registrar.getAllGrupos();
        } catch (ScoutException ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getLocalizedMessage()));
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            return new ArrayList<Grupo>();
        }
    }

    private Roles newRol(String nombrerol) {

        Roles rol = new Roles();

        rol.setNombrerol(nombrerol);

        return rol;
    }

    private Usuario newUsuario(
            Long id,
            String alias,
            String digest,
            String nombre,
            String apellidos,
            String email,
            Date fecha_alta,
            Roles rol
    ) {

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setAlias(alias);
        usuario.setDigest(digest);
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setEmail(email);
        usuario.setFecha_alta(fecha_alta);
        usuario.setRoles(rol);

        return usuario;
    }
    
        private AccesoGrupo newAcceso(
            Date fecha_alta,
            Date fecha_baja,
            Usuario u,
            Grupo g
    ) {

        AccesoGrupo ac = new AccesoGrupo();
        ac.setFecha_Alta_Grupo(fecha_alta);
        ac.setFecha_Baja_Grupo(fecha_baja);
        ac.setUsuario_Grupo(u);
        ac.setGrupo(g);

        return ac;

    }

    public String doCrearUsuario() throws ScoutException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String nombre = request.getParameter("formCrearUsuario:crearNombre");
        String primerApellido = request.getParameter("formCrearUsuario:crearPrimerApellido");
        String segundoApellido = request.getParameter("formCrearUsuario:crearSegundoApellido");
        String apellidos = primerApellido + " " + segundoApellido;

        String grupo = request.getParameter("formCrearUsuario:crearGrupo.value");
        String email = request.getParameter("formCrearUsuario:crearEmail");
        String rol = request.getParameter("formCrearUsuario:crearRol.value");
        System.out.println("Rol--------------------------------------------------------------: " + rol);

        Usuario u = crearUsuario(nombre, apellidos, email, rol);
        group.setId(5L);
        group.setNombre(grupo);

        System.out.println("Usuario nuevo: " + u);
        System.out.println("Grupo del usuario: " + group);
        registrar.registrarUsuario(u, group);

        return "editarUsuarios.xhtml?faces-redirect=true";
    }

    public Usuario crearUsuario(String nombre, String apellidos, String email, String rol) {

        Usuario user = new Usuario();
        Random rnd = new Random();
        user.setId(rnd.nextLong() % 100);
        user.setNombre(nombre);
        user.setApellidos(apellidos);
        user.setEmail(email);
        user.setAlias(nombre); //inicialmente, el alias es igual al nombre        
        System.out.println("Usuario nuevo en crearUsuario: " + user);
        user.setDigest("asdja212"); //digest actual es el nombre----probando

        //prueba
        roles.setId(3L);
        roles.setNombrerol("Coordinador");

        user.setRoles(roles);

        return user;

    }

}
