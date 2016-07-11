package org.id.djns.controller;

import org.id.djns.model.Iglesia;
import org.id.djns.controller.util.JsfUtil;
import org.id.djns.controller.util.PaginationHelper;
import org.id.djns.daos.IglesiaFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
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
import org.id.djns.daos.AsistenciaFacade;
import org.id.djns.model.Distrito;
import org.id.djns.model.Persona;

@ManagedBean(name = "iglesiaController")
@SessionScoped
public class IglesiaController implements Serializable {

    private Iglesia current;
    private DataModel items = null;
    @EJB
    private IglesiaFacade ejbFacade;
    @EJB
    private AsistenciaFacade asistenciaFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Persona> lstPersona = new ArrayList<Persona>();
    private BigDecimal montoTotal = BigDecimal.ZERO;
    private Distrito distrito = new Distrito();

    public IglesiaController() {
    }

    @PostConstruct
    public void init() {
        distrito.setIdDistrito(1);
    }

    public void actualizarListaConfirmados() {
        montoTotal = BigDecimal.ZERO;
        lstPersona = asistenciaFacade.getLstPersonaByAsistencia(getSelected().getIdIglesia());
        montoTotal = new BigDecimal((lstPersona.size() - 1) * 34);
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public Iglesia getSelected() {
        if (current == null) {
            current = new Iglesia();
            selectedItemIndex = -1;
        }
        return current;
    }

    private IglesiaFacade getFacade() {
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

    public String prepareCreate() {
        current = new Iglesia();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("IglesiaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("IglesiaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getIglesiasByDistrito() {
        if (distrito.getIdDistrito() == null) {
            return JsfUtil.getSelectItems(ejbFacade.findIglesiaByDistrito(0), true);
        } else {
            return JsfUtil.getSelectItems(ejbFacade.findIglesiaByDistrito(distrito.getIdDistrito()), true);
        }
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Iglesia.class)
    public static class IglesiaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            IglesiaController controller = (IglesiaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "iglesiaController");
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
            if (object instanceof Iglesia) {
                Iglesia o = (Iglesia) object;
                return getStringKey(o.getIdIglesia());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Iglesia.class.getName());
            }
        }
    }
}
