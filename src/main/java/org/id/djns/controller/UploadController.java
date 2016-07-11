/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.id.djns.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.id.djns.controller.util.JsfUtil;
import org.id.djns.model.DetallePago;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author DesarrolloPc
 */
@ManagedBean(name = "uploadController")
@ViewScoped
public class UploadController {

    private String destino = "D:\\fotos\\";
    @EJB
    private org.id.djns.daos.DetallePagoFacade ejbFacade;
    private UploadedFile file;
    private DetallePago detallePago = new DetallePago();

    /**
     * Creates a new instance of UploadController
     */
    public UploadController() {
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void copyFile(String fileName, InputStream in) {
        try {
            OutputStream out = new FileOutputStream(new File(destino + fileName));

            int read;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void fileUploadListener(FileUploadEvent e) {
        this.file = e.getFile();
        System.out.println("Uploaded File Name Is :: " + file.getFileName() + " :: Uploaded File Size :: " + file.getSize());
    }

    public void uploadFile() {
        if (file != null) {
            try {
                detallePago.setIdIglesia(((DetallePagoController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                        getValue(FacesContext.getCurrentInstance().getELContext(), null, "detallePagoController")).getIglesia());

                detallePago.setPathImg(file.getFileName());
                ejbFacade.guardarDetallePago(detallePago);

                copyFile(file.getFileName(), file.getInputstream());

                RequestContext.getCurrentInstance().closeDialog(detallePago);
            } catch (IOException ex) {
                Logger.getLogger(UploadController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JsfUtil.addErrorMessage("Debe de seleccionar una imagen");
        }
    }

    public void cerrar() {
        RequestContext.getCurrentInstance().closeDialog("/app/pagos/Edit");
    }

    public DetallePago getDetallePago() {
        return detallePago;
    }

    public void setDetallePago(DetallePago detallePago) {
        this.detallePago = detallePago;
    }
}
