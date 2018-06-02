/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siliconvalley.scout39.vista;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
    private Archivo infoArchivo;

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

    public String deleteFile(Archivo ar) {
        gestor.borrarArchivo(control.getUsuario(), ar);
        return "Perfil.xhtml?faces-redirect=true";
    }

    public String CSVGeneration(Eventos e) {

        List<Usuario> participantes = gestor.generaCSVParticipantes(e);
        String fileName = control.getUsuario().getAlias() + "_Evento" + e.getId() + "_Participantes.csv";
        String ruta = File.separator + "resources" + File.separator + "archivos" + File.separator + fileName;
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
        String path = servletContext.getRealPath("") + ruta;

        try (Writer writer = Files.newBufferedWriter(Paths.get(path));) {

            String[] mapeoColumnas  = new String[]{"nombre","apellidos","alias"};
            
            ColumnPositionMappingStrategy<Usuario> strategy = new ColumnPositionMappingStrategy<Usuario>();
            strategy.setType(Usuario.class);
            strategy.setColumnMapping(mapeoColumnas);

            StatefulBeanToCsv<Usuario> beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withMappingStrategy(strategy).build();
            beanToCsv.write(participantes);

            gestor.subirArchivo(ruta, fileName, "csv", control.getUsuario());
            return "evento.xhtml?faces-redirect=true";

        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException ex) {
            Logger.getLogger(FileUploadMBean.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getCause());
            return null;
        }
    }
    public String crearArchivo(){
        try{
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String nombre = request.getParameter("crearArchivo:nombreArchivo");
            String fecha = request.getParameter("crearArchivo:crearFecha");
            if (fecha != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = sdf.parse(fecha);
                infoArchivo.setFecha_limite(date);
            }
            infoArchivo.setEstado('N');
        } catch (ParseException ex) {
            Logger.getLogger(FileUploadMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "documentos.xhtml?faces-redirect=true";
    }
}
