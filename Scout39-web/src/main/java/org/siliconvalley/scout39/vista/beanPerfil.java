/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import org.siliconvalley.scout39.modelo.*;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author pasantru
 */
@Named(value = "beanPerfil")
@RequestScoped
public class beanPerfil {
    
    private Usuario usuario;
    private Map<Usuario,List<Archivo>> archivos;
    @Inject
    private ControlAutorizacion ctrl;
    private Login login;
    
    public beanPerfil() {
        login = new Login();
        archivos = new HashMap<>();
        List<Archivo> listas03;
                
        for(Usuario u: login.getUsuarios()){
            listas03 = new ArrayList<Archivo>();
            Archivo d = new Archivo();
            d.setNombre("S03" + u.getAlias() + ".pdf");
            d.setRuta("../Archivos/S03" + u.getAlias() +".pdf");
            d.setTipo("pdf");
            listas03.add(d);
            archivos.put(u, listas03);
        }
    }
    public boolean insertFile(Usuario u){
        boolean ret = false;
        if(ret = archivos.containsKey(u)){
            for(Usuario a: archivos.keySet()){
            if(a.equals(u)){
                Archivo d = new Archivo();
                d.setNombre("archivoPrueba.pdf");
                d.setRuta("../Archivos/archivoPrueba.pdf");
                d.setTipo("pdf");
                archivos.get(a).add(d);
                break;
            }
        }
        }
        return ret;
    }
    public List<Archivo> getUserFiles(Usuario u){
        if(archivos.containsKey(u)){
            for(Usuario a: archivos.keySet()){
                if(u.equals(a)) return archivos.get(a);
            }
        }
        return null;
    }
    
    
}
