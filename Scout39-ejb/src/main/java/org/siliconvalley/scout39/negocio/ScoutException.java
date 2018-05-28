/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.negocio;

import javax.ejb.ApplicationException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author alzaji
 */
@ApplicationException (rollback = true)
public class ScoutException extends Exception {

    public ScoutException(String msg) {
           super(msg);
    }
    
}
