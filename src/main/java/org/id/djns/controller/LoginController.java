/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.id.djns.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.id.djns.daos.UsuarioFacade;
import org.id.djns.model.Usuario;
import org.id.djns.util.JsfUtil;
import org.id.djns.util.VarSession;

/**
 *
 * @author desarrollopc
 */
@ManagedBean(name = "loginController")
@ViewScoped
public class LoginController {

    private String usuario;
    private String clave;
    @EJB
    private UsuarioFacade ejbFacade;

    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
    }

    public String isUsuarioValido() {
        List<Usuario> lstUsuarios = ejbFacade.getLstUsuario(usuario, getMD5(clave));
        if (lstUsuarios.isEmpty()) {
            JsfUtil.mensajeError("Error en las credenciales de acceso.");
            return "";
        } else {
            VarSession.setVariableSession("usuario", lstUsuarios.get(0).getUsuario());
            ((PersonaController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "personaController")).init();
            return "/app/principal?faces-redirect=true";
        }
    }

    public void logout() {
        try {
            VarSession.limpiarVariableSession();
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            externalContext.redirect(((ServletContext) externalContext.getContext()).getContextPath() + "/index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void dialogReasignarClave() {
    }

    private static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
