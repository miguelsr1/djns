/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.id.djns.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author desarrollopc
 */
@ManagedBean
@ViewScoped
public class MenuController {

    private String app = "";

    /**
     * Creates a new instance of MenuController
     */
    public MenuController() {
    }

    public String selectOpcion() {
        String url = "";

        switch (Integer.parseInt(app)) {
            case 1:
                url = "/app/personas/List.xhtml";
                break;
            case 2:
                url = "/app/pagos/List.xhtml";
                break;
            case 3:
                url = "/app/personas/confirmarAsistencia.xhtml";
                break;
            case 4:
                url = "/app/personas/registrarAlimentacion.xhtml";
                break;
        }

        return url;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

}
