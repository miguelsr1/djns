/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.id.djns.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author DesarrolloPc
 */
@Entity
@Table(name = "alimentacion")
@NamedQueries({
    @NamedQuery(name = "Alimentacion.findAll", query = "SELECT a FROM Alimentacion a")})
public class Alimentacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_alimentacion")
    private Integer idAlimentacion;
    @Column(name = "fecha_alimentacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlimentacion;
    @Column(name = "id_tiempo_alimenacion")
    private Character idTiempoAlimenacion;
    @JoinColumn(name = "id_detalle_asistente", referencedColumnName = "id_detalle_asistente")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private DetalleAsistente idDetalleAsistente;

    public Alimentacion() {
    }

    public Alimentacion(Integer idAlimentacion) {
        this.idAlimentacion = idAlimentacion;
    }

    public Integer getIdAlimentacion() {
        return idAlimentacion;
    }

    public void setIdAlimentacion(Integer idAlimentacion) {
        this.idAlimentacion = idAlimentacion;
    }

    public Date getFechaAlimentacion() {
        return fechaAlimentacion;
    }

    public void setFechaAlimentacion(Date fechaAlimentacion) {
        this.fechaAlimentacion = fechaAlimentacion;
    }

    public Character getIdTiempoAlimenacion() {
        return idTiempoAlimenacion;
    }

    public void setIdTiempoAlimenacion(Character idTiempoAlimenacion) {
        this.idTiempoAlimenacion = idTiempoAlimenacion;
    }

    public DetalleAsistente getIdDetalleAsistente() {
        return idDetalleAsistente;
    }

    public void setIdDetalleAsistente(DetalleAsistente idDetalleAsistente) {
        this.idDetalleAsistente = idDetalleAsistente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlimentacion != null ? idAlimentacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alimentacion)) {
            return false;
        }
        Alimentacion other = (Alimentacion) object;
        if ((this.idAlimentacion == null && other.idAlimentacion != null) || (this.idAlimentacion != null && !this.idAlimentacion.equals(other.idAlimentacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.id.djns.model.Alimentacion[ idAlimentacion=" + idAlimentacion + " ]";
    }
    
}
