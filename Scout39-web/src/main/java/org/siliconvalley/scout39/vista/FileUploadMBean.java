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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import org.siliconvalley.scout39.negocio.NegocioGestorDocumentalLocal;

/**
 *
 * @author hidden-process
 */
@Named(value = "fileUploadMBean")
@ViewScoped
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

    public void setFile(Part f) {
        this.file = f;
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
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context
                .getExternalContext().getContext();
        String path = servletContext.getRealPath("");
        boolean file1Success = false;

        String fileName = control.getUsuario().getAlias() + "_" + Utils.getFileNameFromPart(file);
        String ruta = path + File.separator + "resources" + File.separator + "archivos" + File.separator + fileName;
        String extension = "";

        if (file.getSize() > 0) {

            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i + 1);
            }

            File outputFile = new File(ruta);

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
            System.out.println("File uploaded to : " + path);
            /**
             * set the success message when the file upload is successful
             */
            setMessage("File successfully uploaded to " + path);

            gestor.subirArchivo(ruta, fileName, extension,control.getUsuario());
        } else {
            /**
             * set the error message when error occurs during the file upload
             */
            setMessage("Error, select atleast one file!");
        }
        /**
         * return to the same view
         */
        return null;
    }
}
