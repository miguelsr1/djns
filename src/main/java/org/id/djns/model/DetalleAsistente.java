/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.id.djns.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author DesarrolloPc
 */
@Entity
@Table(name = "detalle_asistente")
@NamedQueries({
    @NamedQuery(name = "DetalleAsistente.findAll", query = "SELECT d FROM DetalleAsistente d")})
public class DetalleAsistente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_asistente")
    private Integer idDetalleAsistente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inscripcion")
    @Temporal(TemporalType.DATE)
    private Date fechaInscripcion;
    @JoinColumn(name = "id_asistencia", referencedColumnName = "id_asistencia")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Asistencia idAsistencia;
    @JoinColumn(name = "usuario", referencedColumnName = "usuario")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Usuario usuario;
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Persona idPersona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDetalleAsistente", fetch = FetchType.LAZY)
    private List<Alimentacion> alimentacionList;

    public DetalleAsistente() {
    }

    public DetalleAsistente(Integer idDetalleAsistente) {
        this.idDetalleAsistente = idDetalleAsistente;
    }

    public DetalleAsistente(Integer idDetalleAsistente, Date fechaInscripcion) {
        this.idDetalleAsistente = idDetalleAsistente;
        this.fechaInscripcion = fechaInscripcion;
    }

    public Integer getIdDetalleAsistente() {
        return idDetalleAsistente;
    }

    public void setIdDetalleAsistente(Integer idDetalleAsistente) {
        this.idDetalleAsistente = idDetalleAsistente;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Asistencia getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(Asistencia idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public List<Alimentacion> getAlimentacionList() {
        return alimentacionList;
    }

    public void setAlimentacionList(List<Alimentacion> alimentacionList) {
        this.alimentacionList = alimentacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleAsistente != null ? idDetalleAsistente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleAsistente)) {
            return false;
        }
        DetalleAsistente other = (DetalleAsistente) object;
        if ((this.idDetalleAsistente == null && other.idDetalleAsistente != null) || (this.idDetalleAsistente != null && !this.idDetalleAsistente.equals(other.idDetalleAsistente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.id.djns.model.DetalleAsistente[ idDetalleAsistente=" + idDetalleAsistente + " ]";
    }
    
}
