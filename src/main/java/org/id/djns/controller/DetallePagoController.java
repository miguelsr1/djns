package org.id.djns.controller;

import org.id.djns.model.DetallePago;
import org.id.djns.controller.util.JsfUtil;
import org.id.djns.daos.DetallePagoFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.id.djns.model.Asistencia;
import org.id.djns.model.Iglesia;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "detallePagoController")
@SessionScoped
public class DetallePagoController implements Serializable {

    private Iglesia iglesia = new Iglesia();
    private DetallePago detallePago;
    @EJB
    private org.id.djns.daos.DetallePagoFacade ejbFacade;

    public DetallePagoController() {
    }

    public Iglesia getIglesia() {
        if (iglesia == null) {
            iglesia = new Iglesia();
        }
        return iglesia;
    }

    public void setIglesia(Iglesia iglesia) {
        this.iglesia = iglesia;
    }

    public DetallePago getDetallePago() {
        if (detallePago == null) {
            detallePago = new DetallePago();
        }
        return detallePago;
    }

    public void setDetallePago(DetallePago detallePago) {
        this.detallePago = detallePago;
    }

    private DetallePagoFacade getFacade() {
        return ejbFacade;
    }

    public void abrirDialogo() {
        Map<String, Object> opt = new HashMap<String, Object>();
        opt.put("modal", true);
        opt.put("draggable", false);
        opt.put("resizable", false);
        opt.put("contentHeight", 260);
        opt.put("contentWidth", 420);
        RequestContext.getCurrentInstance().openDialog("/app/pagos/Edit", opt, null);
    }

    public void prepareCreate() {
        if (getIglesia().getIdIglesia() != null) {
            detallePago = new DetallePago();
            detallePago.setIdAsistencia(new Asistencia(1));
            abrirDialogo();
        } else {
            org.id.djns.util.JsfUtil.mensajeAlerta("Debe de seleccionar una Iglesia");
        }
    }

    public String update() {
        try {
            getFacade().edit(detallePago);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DetallePagoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public void onRefrescarLstDetallePago(SelectEvent event) {
        if (event.getObject() instanceof DetallePago) {
            detallePago = (DetallePago) event.getObject();
        }
    }

    @FacesConverter(forClass = DetallePago.class)
    public static class DetallePagoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DetallePagoController controller = (DetallePagoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "detallePagoController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof DetallePago) {
                DetallePago o = (DetallePago) object;
                return getStringKey(o.getIdDetallePago());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + DetallePago.class.getName());
            }
        }
    }

    public List<DetallePago> getLstDetallePagoByIglesia() {
        if (getIglesia() != null) {
            return ejbFacade.getLstDetallePagoByIglesia(getIglesia().getIdIglesia());
        } else {
            return new ArrayList<DetallePago>();
        }
    }
}