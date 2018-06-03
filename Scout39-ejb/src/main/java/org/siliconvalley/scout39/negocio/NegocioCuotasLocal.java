/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import java.util.List;
import javax.ejb.Local;
import org.siliconvalley.scout39.modelo.*;

/**
 *
 * @author alzaji
 */
@Local
public interface NegocioCuotasLocal {
    
    public void crearCuota(Cuotas c);
    public void borrarCuota(String concepto);
    public List<Cuotas> listaCuotas();
    public List<Cuotas> listaCuotasAJAX(String concepto);
    public void cambiarEstadoCuota(Character c, Cuotas cu);
    
}
