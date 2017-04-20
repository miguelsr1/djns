package org.id.djns.controller;

import org.id.djns.model.Persona;
import org.id.djns.util.JsfUtil;
import org.id.djns.daos.PersonaFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import org.id.djns.daos.AsistenciaFacade;
import org.id.djns.daos.UsuarioFacade;
import org.id.djns.model.Alimentacion;
import org.id.djns.model.DetalleAsistente;
import org.id.djns.model.Iglesia;
import org.id.djns.model.Usuario;
import org.id.djns.util.VarSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

@ManagedBean(name = "personaController")
@SessionScoped
public class PersonaController implements Serializable {

    private Date fechaAlimentacion;
    private Character idTiempoAlimentacion;
    private Alimentacion alimentacion = new Alimentacion();
    private DetalleAsistente detalleAsistente = new DetalleAsistente();
    private String codigoPersona = "";
    private Persona persona;
    private Usuario usuario = new Usuario();
    @EJB
    private PersonaFacade ejbFacade;
    @EJB
    private AsistenciaFacade asistenciaFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    private Iglesia iglesia = new Iglesia();
    private DualListModel<Persona> asisModel;
    private List<Alimentacion> lstAlimentacion = new ArrayList<Alimentacion>();
    private List<Persona> lstAsistenciaSource = new ArrayList<Persona>();
    private List<Persona> lstAsistenciaTarget = new ArrayList<Persona>();

    public PersonaController() {
        asisModel = new DualListModel<Persona>();
    }

    @PostConstruct
    public void init() {
        usuario = usuarioFacade.find(VarSession.getVariableSession("usuario").toString());
    }

    public String getLeyendaUsuario() {
        String valor = "";
        if (usuario.getIdIglesia() == null) {
            valor = "ADMINISTRADOR";
        } else {
            valor = usuario.getUsuario() + ", Iglesia de Dios en " + usuario.getIdIglesia().getNombreIglesia();
        }
        return valor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Persona> getLstPersonasByIglesia() {
        if (getIglesia().getIdIglesia() != null) {
            return ejbFacade.getLstPersonasByIglesia(iglesia.getIdIglesia());
        } else {
            return new ArrayList<Persona>();
        }
    }

    public Character getIdTiempoAlimentacion() {
        return idTiempoAlimentacion;
    }

    public void setIdTiempoAlimentacion(Character idTiempoAlimentacion) {
        this.idTiempoAlimentacion = idTiempoAlimentacion;
    }

    public List<Alimentacion> getLstAlimentacion() {
        return lstAlimentacion;
    }

    public Date getFechaAlimentacion() {
        return fechaAlimentacion;
    }

    public void setFechaAlimentacion(Date fechaAlimentacion) {
        this.fechaAlimentacion = fechaAlimentacion;
    }

    public void setLstAlimentacion(List<Alimentacion> lstAlimentacion) {
        if (lstAlimentacion != null) {
            this.lstAlimentacion = lstAlimentacion;
        }
    }

    public void actualizarAlimentacion() {
        alimentacion.setFechaAlimentacion(fechaAlimentacion);
        alimentacion.setIdTiempoAlimenacion(idTiempoAlimentacion);
        lstAlimentacion = ejbFacade.getLstAlimentacion(alimentacion.getFechaAlimentacion(), alimentacion.getIdTiempoAlimenacion());
    }

    public Alimentacion getAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(Alimentacion alimentacion) {
        if (alimentacion != null) {
            this.alimentacion = alimentacion;
        }
    }

    public String getCodigoPersona() {
        return codigoPersona;
    }

    public void setCodigoPersona(String codigoPersona) {
        this.codigoPersona = codigoPersona;
    }

    public DualListModel<Persona> getAsisModel() {
        return asisModel;
    }

    public void setAsisModel(DualListModel<Persona> asisModel) {
        this.asisModel = asisModel;
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

    public void setPersona(Persona current) {
        this.persona = current;
    }

    public Persona getPersona() {
        if (persona == null) {
            persona = new Persona();
        }
        return persona;
    }

    private PersonaFacade getFacade() {
        return ejbFacade;
    }

    public void prepareCreate() {
        if (getIglesia().getIdIglesia() != null) {
            persona = new Persona();
            persona.setIdIglesia(getIglesia());
            abrirDialogo();
        } else {
            JsfUtil.mensajeAlerta("Debe de seleccionar una Iglesia");
        }
    }

    public void abrirDialogo() {
        Map<String, Object> opt = new HashMap<String, Object>();
        opt.put("modal", true);
        opt.put("draggable", false);
        opt.put("resizable", false);
        opt.put("contentHeight", 300);
        opt.put("contentWidth", 550);
        RequestContext.getCurrentInstance().openDialog("/app/personas/Edit", opt, null);
    }

    public void prepareEdit() {
        abrirDialogo();
    }
    
    public void prepareDelete(){
        getFacade().remove(persona);
    }

    public void guardar() {
        try {
            if (persona.getIdPersona() == null) {
                getFacade().create(persona);
                JsfUtil.mensajeInsert();
            } else {
                getFacade().edit(persona);
                JsfUtil.mensajeUpdate();
            }
        } catch (Exception e) {
            JsfUtil.mensajeError(e.getMessage());
        }
    }

    public void aceptarPersona() {
        if (!persona.getNombres().isEmpty() && !persona.getApellidos().isEmpty() && persona.getFechaNacimiento() != null) {
            RequestContext.getCurrentInstance().closeDialog(persona);
        } else {
            JsfUtil.mensajeAlerta("Los campos NOMBRES, APELIDOS, FECHA DE NACIMIENTO, son requeridos");
        }
    }

    public void onRefrescarLstPersona(SelectEvent event) {
        persona = (Persona) event.getObject();
        persona.setIdIglesia(getIglesia());
        guardar();
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Persona.class)
    public static class PersonaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PersonaController controller = (PersonaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "personaController");
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
            if (object instanceof Persona) {
                Persona o = (Persona) object;
                return getStringKey(o.getIdPersona());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Persona.class.getName());
            }
        }
    }

    public void actualizarModelAsistencia() {
        lstAsistenciaSource = asistenciaFacade.getLstPersonaNotAsistencia(getIglesia().getIdIglesia());
        lstAsistenciaTarget = asistenciaFacade.getLstPersonaByAsistencia(getIglesia().getIdIglesia());
        asisModel = new DualListModel<Persona>(lstAsistenciaSource, lstAsistenciaTarget);
    }

    public void onTranfer(TransferEvent event) {
        List lst = event.getItems();
        if (event.isAdd()) {
            for (Object object : lst) {
                DetalleAsistente detAsistente = new DetalleAsistente();
                detAsistente.setIdAsistencia(asistenciaFacade.find(1));
                detAsistente.setFechaInscripcion(new Date());
                detAsistente.setIdPersona(ejbFacade.find(Integer.parseInt(object.toString())));
                detAsistente.setUsuario(new Usuario(VarSession.getVariableSessionUsuario()));
                asistenciaFacade.agregarAsistente(detAsistente);
            }
        } else if (event.isRemove()) {
            for (Object object : lst) {
                asistenciaFacade.removerAsistente(new Integer(object.toString()));
            }
        }
    }

    public void buscarPersonaByCod() {
        if (!codigoPersona.trim().isEmpty() && codigoPersona.length() == 11) {
            persona = ejbFacade.getPersonasByCod(codigoPersona);
            detalleAsistente = ejbFacade.getDetalleAsistenciaByCod(codigoPersona);
            if (detalleAsistente.getIdAsistencia() == null) {
                JsfUtil.mensajeAlerta("Esta persona no ha confirmado su asistencia");
                codigoPersona = "";
                persona = new Persona();
            } else {
                alimentacion.setIdDetalleAsistente(detalleAsistente);
                RequestContext.getCurrentInstance().update("pnlDatos");
            }
        }
    }

    public void validarAlimentacionByPersona() {
        if (detalleAsistente.getIdAsistencia() != null) {
            alimentacion.setFechaAlimentacion(fechaAlimentacion);
            alimentacion.setIdTiempoAlimenacion(idTiempoAlimentacion);
            if (ejbFacade.isAlimentoPersona(codigoPersona, alimentacion.getFechaAlimentacion(), alimentacion.getIdTiempoAlimenacion())) {
                lstAlimentacion = ejbFacade.guardarAlimentacion(alimentacion);
                alimentacion = new Alimentacion();
                detalleAsistente = new DetalleAsistente();
                persona = new Persona();
                codigoPersona = "";
            } else {
                //JsfUtil.mensajeAlerta("Ya se registro este joven para el tiempo de alimentación seleccionado");
            }
        }
    }
}
