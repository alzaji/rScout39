/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.siliconvalley.scout39.modelo.*;

/**
 *
 * @author alzaji
 */
@Startup
@Singleton
public class DBPopulate {

    @PersistenceContext(name = "Scout39MPU")
    private EntityManager em;

    @PostConstruct
    private void initdb() {

        // Roles
        Roles coordinador = new Roles();
        Roles scouter = new Roles();
        Roles educando = new Roles();

        coordinador.setNombrerol("Coordinador");
        scouter.setNombrerol("Scouter");
        educando.setNombrerol("nombrerol");

        // Privilegios
        Privilegios sss = new Privilegios();
        Privilegios nss = new Privilegios();
        Privilegios nns = new Privilegios();

        sss.setBorrado('S');
        sss.setEscritura('S');
        sss.setLectura('S');

        nss.setBorrado('N');
        nss.setEscritura('S');
        nss.setLectura('S');

        nns.setBorrado('N');
        nns.setEscritura('N');
        nns.setLectura('S');

        em.persist(sss);
        em.persist(nss);
        em.persist(nns);

        // Roles y privilegios
        List<Privilegios> priv = new ArrayList<>();
        priv.add(sss);

        coordinador.setPrivilegios(priv);

        em.persist(coordinador);

        priv = new ArrayList<>();
        priv.add(sss);
        priv.add(nss);

        scouter.setPrivilegios(priv);

        em.persist(scouter);

        priv = new ArrayList<>();
        priv.add(nss);

        educando.setPrivilegios(priv);

        em.persist(educando);

        // Grupos
        Grupo tha = new Grupo();
        Grupo kim = new Grupo();
        Grupo siryu = new Grupo();
        Grupo almogama = new Grupo();

        tha.setNombre("Manada de Tha");
        tha.setDescripcion("");

        kim.setNombre("Tropa de Kim");
        kim.setDescripcion("");

        siryu.setNombre("Unidad Esculta Siryu");
        siryu.setDescripcion("");

        almogama.setNombre("Clan Rover Almogama");
        almogama.setDescripcion("");

        em.persist(tha);
        em.persist(kim);
        em.persist(siryu);
        em.persist(almogama);

        // Usuarios
        Usuario ucoord = newUsuario("Coord", "1234", "Coordinador", "1", "coordinador@scout39.org", new Date(), coordinador);
        Usuario uscouttha = newUsuario("ScouterTHA", "1234", "Scouter", "THA", "scoutertha@scout39.org", new Date(), scouter);
        Usuario uscoutkim = newUsuario("ScouterKIM", "1234", "Scouter", "KIM", "scouterkim@scout39.org", new Date(), scouter);
        Usuario uscoutsiryu = newUsuario("ScouterSIRYU", "1234", "Scouter", "SIRYU", "scoutersiryu@scout39.org", new Date(), scouter);
        Usuario uscoutalmogama = newUsuario("ScouterALMOGAMA", "1234", "Scouter", "ALMOGAMA", "scouteralmogama@scout39.org", new Date(), scouter);
        Usuario ueductha = newUsuario("EducandoTHA", "1234", "Educando", "THA", "educandotha@scout39.org", new Date(), educando);
        Usuario ueduckim = newUsuario("EducandoKIM", "1234", "Educando", "KIM", "educandokim@scout39.org", new Date(), educando);
        Usuario ueducsiryu = newUsuario("EducandoSIRYU", "1234", "Educando", "SIRYU", "educandosiryu@scout39.org", new Date(), educando);
        Usuario ueducalmogama = newUsuario("EducandoALMOGAMA", "1234", "Educando", "ALMOGAMA", "educandoalmogama@scout39.org", new Date(), educando);

        em.persist(ucoord);
        em.persist(uscouttha);
        em.persist(uscoutkim);
        em.persist(uscoutsiryu);
        em.persist(uscoutalmogama);
        em.persist(ueductha);
        em.persist(ueduckim);
        em.persist(ueducsiryu);
        em.persist(ueducalmogama);

        // Roles y Usuarios
        List<Usuario> lru = new ArrayList<>();
        lru.add(ucoord);
        coordinador.setUsuarios(lru);

        em.merge(coordinador);

        lru = new ArrayList<>();
        lru.add(uscouttha);
        lru.add(uscoutkim);
        lru.add(uscoutsiryu);
        lru.add(uscoutalmogama);
        scouter.setUsuarios(lru);

        em.merge(scouter);

        lru = new ArrayList<>();
        lru.add(ueductha);
        lru.add(ueduckim);
        lru.add(ueducsiryu);
        lru.add(ueducalmogama);
        educando.setUsuarios(lru);

        em.merge(educando);

        //Acceso a Grupo
        AccesoGrupo acscouttha = newAcceso(new Date(), null, uscouttha, tha);
        AccesoGrupo acscoutkim = newAcceso(new Date(), null, uscoutkim, kim);
        AccesoGrupo acscoutsiryu = newAcceso(new Date(), null, uscoutsiryu, siryu);
        AccesoGrupo acscoutalmogama = newAcceso(new Date(), null, uscoutalmogama, almogama);
        AccesoGrupo aceductha = newAcceso(new Date(), null, ueductha, tha);
        AccesoGrupo aceduckim = newAcceso(new Date(), null, ueduckim, kim);
        AccesoGrupo aceducsiryu = newAcceso(new Date(), null, ueducsiryu, siryu);
        AccesoGrupo aceducalmogama = newAcceso(new Date(), null, ueducalmogama, almogama);

        em.persist(acscouttha);
        em.persist(acscoutkim);
        em.persist(acscoutsiryu);
        em.persist(acscoutalmogama);
        em.persist(aceductha);
        em.persist(aceduckim);
        em.persist(aceducsiryu);
        em.persist(aceducalmogama);

        List<AccesoGrupo> lac = new ArrayList<>();
        lac.add(acscouttha);
        uscouttha.setAcceso_Grupo(lac);

        lac = new ArrayList<>();
        lac.add(acscoutkim);
        uscoutkim.setAcceso_Grupo(lac);

        lac = new ArrayList<>();
        lac.add(acscoutsiryu);
        uscoutsiryu.setAcceso_Grupo(lac);

        lac = new ArrayList<>();
        lac.add(acscoutalmogama);
        uscoutalmogama.setAcceso_Grupo(lac);

        lac = new ArrayList<>();
        lac.add(aceductha);
        ueductha.setAcceso_Grupo(lac);

        lac = new ArrayList<>();
        lac.add(aceduckim);
        ueduckim.setAcceso_Grupo(lac);

        lac = new ArrayList<>();
        lac.add(aceducsiryu);
        ueducsiryu.setAcceso_Grupo(lac);

        lac = new ArrayList<>();
        lac.add(aceducalmogama);
        ueducalmogama.setAcceso_Grupo(lac);

        em.merge(ucoord);
        em.merge(uscouttha);
        em.merge(uscoutkim);
        em.merge(uscoutsiryu);
        em.merge(uscoutalmogama);
        em.merge(ueductha);
        em.merge(ueduckim);
        em.merge(ueducsiryu);
        em.merge(ueducalmogama);

        // Eventos
        String fevento1 = "03-6-2018 23:55:00";
        String fevento2 = "06-6-2018 10:45:00";
        String fevento3 = "01-7-2018 00:00:00";
        String fevento4 = "03-9-2018 00:00:00";

        Eventos evento1 = newEvento(
                "Deadline",
                "Día en el que se entrega esta aplicacion.",
                fevento1,
                BigDecimal.valueOf(36.71470930),
                BigDecimal.valueOf(-4.4751480));
        Eventos evento2 = newEvento(
                "Presentación",
                "Se realiza la presentacion en este día.",
                fevento2,
                BigDecimal.valueOf(36.71470930),
                BigDecimal.valueOf(-4.4751480));

        Eventos evento3 = newEvento(
                "Vacaciones",
                "¡Nos vamos de vacaciones!",
                fevento3,
                BigDecimal.valueOf(36.71470930),
                BigDecimal.valueOf(-4.4751480));

        Eventos evento4 = newEvento(
                "Vuelta a la rutina",
                "Comienzan los exámenes de septiembre",
                fevento4,
                BigDecimal.valueOf(36.71470930),
                BigDecimal.valueOf(-4.4751480));

        em.persist(evento1);
        em.persist(evento2);
        em.persist(evento3);
        em.persist(evento4);

        // Comentarios
        Comentarios c1 = newComentario("Ya hemos terminado y podemos descansar", ucoord, evento1);
        Comentarios c2 = newComentario("Este es un ejemplo de comentario dentro de un evento", ucoord, evento2);
        Comentarios c3 = newComentario("Acabamos por este curso, nos vemos en septiembre", ucoord, evento3);
        Comentarios c4 = newComentario("Nooooo", ucoord, evento4);
        Comentarios c5 = newComentario("Ojalá", ucoord, evento1);
        c5.setRespuesta(c1);
        
        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.persist(c4);
        em.persist(c5);
        
        List<Comentarios> luc = new ArrayList<>();
        luc.add(c1);
        luc.add(c2);
        luc.add(c3);
        luc.add(c4);
        luc.add(c5);
        ucoord.setComentarios(luc);
        
        em.merge(ucoord);
        
        // Objetos
        Objeto e1 = new Objeto();
        Objeto e2 = new Objeto();
        Objeto e3 = new Objeto();
        Objeto e4 = new Objeto();
        Objeto g1 = new Objeto();
        Objeto g2 = new Objeto();
        Objeto g3 = new Objeto();
        Objeto g4 = new Objeto();

        e1.setNombre("eventosTHA");
        e2.setNombre("eventosKIM");
        e3.setNombre("eventosSIRYU");
        e4.setNombre("eventosALMOGAMA");

        em.persist(e1);
        em.persist(e2);
        em.persist(e3);
        em.persist(e4);

        // Objetos y Eventos
        List<Eventos> le = new ArrayList<>();
        le.add(evento1);
        le.add(evento2);
        le.add(evento3);
        le.add(evento4);
        e3.setListaEventos(le);

        // Objetos y Privilegios
        priv = new ArrayList<>();
        priv.add(sss);
        priv.add(nss);
        e3.setListaPrivilegios(priv);

        List<Objeto> lo = new ArrayList<>();
        lo.add(e3);
        sss.setListaObjetos(lo);
        nss.setListaObjetos(lo);

        em.merge(e3);
        em.merge(sss);
        em.merge(nss);

        // Objetos y Grupos
        g1.setNombre("grupoTHA");
        g2.setNombre("grupoKIM");
        g3.setNombre("grupoSIRYU");
        g4.setNombre("grupoALMOGAMA");

        em.persist(g1);
        em.persist(g2);
        em.persist(g3);
        em.persist(g4);

        List<Grupo> lg = new ArrayList<>();
        lg.add(tha);
        g1.setListaGrupos(lg);

        em.merge(g1);

        lg = new ArrayList<>();
        lg.add(kim);
        g2.setListaGrupos(lg);

        em.merge(g2);

        lg = new ArrayList<>();
        lg.add(siryu);
        g3.setListaGrupos(lg);

        em.merge(g3);

        lg = new ArrayList<>();
        lg.add(almogama);
        g4.setListaGrupos(lg);

        em.merge(g4);

    }

    private Usuario newUsuario(
            String alias,
            String digest,
            String nombre,
            String apellidos,
            String email,
            Date fecha_alta,
            Roles rol
    ) {

        Usuario usuario = new Usuario();
        usuario.setAlias(alias);
        usuario.setDigest(sha256(digest));
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

    private Eventos newEvento(
            String nombre,
            String descripcion,
            String fecha,
            BigDecimal latitud,
            BigDecimal longitud
    ) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            Date date = sdf.parse(fecha);
            Eventos evento = new Eventos();
            evento.setNombre(nombre);
            evento.setDescripcion(descripcion);
            evento.setFecha(date);
            evento.setLatitud(latitud);
            evento.setLongitud(longitud);

            return evento;
        } catch (ParseException ex) {
            Logger.getLogger(DBPopulate.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getLocalizedMessage());
            return null;
        }
    }

    private Comentarios newComentario(
            String cuerpo,
            Usuario u,
            Eventos e
    ) {
        Comentarios c = new Comentarios();
        c.setCuerpo(cuerpo);
        c.setUsuario(u);
        c.setEventoC(e);

        return c;
    }

    private String sha256(String rawString) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawString.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte hashByte : hash) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
