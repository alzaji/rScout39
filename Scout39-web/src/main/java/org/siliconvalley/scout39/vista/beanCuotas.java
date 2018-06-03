/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.siliconvalley.scout39.modelo.*;
import org.siliconvalley.scout39.negocio.NegocioCuotas;

/**
 *
 * @author alzaji
 */
@Named(value = "beanCuotas")
@RequestScoped
public class beanCuotas {

    protected String concepto;
    protected boolean update = false;

    @EJB
    private NegocioCuotas cuotas;

    public beanCuotas() {
    }

    public void crearCuota() {

        //Falta lógica del formulario aquí
        //cuotas.crearCuota(c);

    }
    
    public void borrarCuota(){
        
        
    }
    
    public void cambiarEstadoCuota(){
        
        
    }

    public List<Cuotas> listarCuotas() {
        if (update) {
            return listarCuotasAJAX();
        }
        return cuotas.listaCuotas();
    }

    public void searchListUser() {
        update = true;
        listarCuotas();
    }

    public List<Cuotas> listarCuotasAJAX() {
        return cuotas.listaCuotasAJAX(concepto);
    }

    private Cuotas newCuotas(
            String tipo,
            String precio,
            String fecha_cuota,
            Character estado,
            String concepto) {

        Cuotas c = new Cuotas();
        c.setTipo(tipo);
        c.setPrecio(BigDecimal.valueOf(Long.valueOf(precio)));
        c.setFecha_cuota(parseFecha(fecha_cuota));
        c.setEstado(estado);
        c.setConcepto(concepto);
        
        return c;
    }

    private Date parseFecha(String fecha) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            Date date = sdf.parse(fecha);
            return date;
        } catch (ParseException e) {
            Logger.getLogger(beanCuotas.class.getName()).log(Level.WARNING, e.getMessage(), e.getCause());
            return null;
        }
    }

}
