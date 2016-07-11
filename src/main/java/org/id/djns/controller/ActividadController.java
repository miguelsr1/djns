package org.id.djns.controller;

import org.id.djns.model.Actividad;
import org.id.djns.util.JsfUtil;
import org.id.djns.controller.util.PaginationHelper;
import org.id.djns.daos.ActividadFacade;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "actividadController")
@SessionScoped
public class ActividadController implements Serializable {

    private Actividad actividad;
    private DataModel items = null;
    @EJB
    private org.id.djns.daos.ActividadFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ActividadController() {
    }

    public Actividad getActividad() {
        if (actividad == null) {
            actividad = new Actividad();
        }
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    private ActividadFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        actividad = (Actividad) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public void prepareCreate() {
        actividad = new Actividad();
        abrirDialogo();
    }

    public void abrirDialogo() {
        Map<String, Object> opt = new HashMap<String, Object>();
        opt.put("modal", true);
        opt.put("draggable", false);
        opt.put("resizable", false);
        opt.put("contentHeight", 240);
        opt.put("contentWidth", 410);
        RequestContext.getCurrentInstance().openDialog("/app/actividades/Edit", opt, null);
    }

    public void aceptarActividad() {
        RequestContext.getCurrentInstance().closeDialog(actividad);
    }
    
    public void guardar() {
        try {
            if (actividad.getIdActividad() == null) {
            getFacade().create(actividad);
                JsfUtil.mensajeInsert();
            } else {
                getFacade().edit(actividad);
                JsfUtil.mensajeUpdate();
            }
        } catch (Exception e) {
            JsfUtil.mensajeError(e.getMessage());
        }
    }

    public void prepareEdit() {
        abrirDialogo();
    }

    public String update() {
        try {
            getFacade().edit(actividad);
            JsfUtil.mensajeUpdate();
            return "View";
        } catch (Exception e) {
            JsfUtil.mensajeError(e.getMessage());
            return null;
        }
    }
    
    public void onRefrescarLstActividad(SelectEvent event) {
        actividad = (Actividad) event.getObject();
        guardar();
        items = getPagination().createPageDataModel();
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Actividad.class)
    public static class ActividadControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ActividadController controller = (ActividadController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "actividadController");
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
            if (object instanceof Actividad) {
                Actividad o = (Actividad) object;
                return getStringKey(o.getIdActividad());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Actividad.class.getName());
            }
        }

    }

}
