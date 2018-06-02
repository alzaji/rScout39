/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import org.siliconvalley.scout39.modelo.*;
import org.siliconvalley.scout39.negocio.NegocioGestorDocumentalLocal;

/**
 *
 * @author hidden-process
 */
@Named(value = "fileUploadMBean")
@RequestScoped
public class FileUploadMBean implements Serializable {

    @Inject
    private ControlAutorizacion control;

    @EJB
    private NegocioGestorDocumentalLocal gestor;

    private static final long serialVersionUID = 1L;
    private Part file;
    private String message;

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String uploadFile() throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String fileName = control.getUsuario().getAlias() + "_" + Utils.getFileNameFromPart(file);
        String ruta = File.separator + "resources" + File.separator + "archivos" + File.separator + fileName;
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
        String path = servletContext.getRealPath("") + ruta;
        boolean file1Success = false;

        String extension = "";

        if (file.getSize() > 0) {

            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i + 1);
            }

            File outputFile = new File(path);

            inputStream = file.getInputStream();
            outputStream = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            file1Success = true;
        }

        if (file1Success) {
            gestor.subirArchivo(ruta, fileName, extension, control.getUsuario());
        } else {

            setMessage("Error, select atleast one file!");
        }
        return null;
    }

    public String getPath(String ruta) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + gestor.buscarPath(control.getUsuario(), ruta);
    }
    
    public String deleteFile(Archivo ar){
        gestor.borrarArchivo(control.getUsuario(), ar);
        return null;
    }
}