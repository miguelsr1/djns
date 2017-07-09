/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.id.djns.model;

import java.io.Serializable;
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

/**
 *
 * @author DesarrolloPc
 */
@Entity
@Table(name = "asistencia")
@NamedQueries({
    @NamedQuery(name = "Asistencia.findAll", query = "SELECT a FROM Asistencia a")})
public class Asistencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_asistencia")
    private Integer idAsistencia;
//   // @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAsistencia", fetch = FetchType.LAZY)
//    private List<DetallePago> detallePagoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAsistencia", fetch = FetchType.LAZY)
    private List<DetalleAsistente> detalleAsistenteList;
    @JoinColumn(name = "id_actividad", referencedColumnName = "id_actividad")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Actividad idActividad;

    public Asistencia() {
    }
//
    public Asistencia(Integer idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public Integer getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(Integer idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

//    public List<DetallePago> getDetallePagoList() {
//        return detallePagoList;
//    }
//
//    public void setDetallePagoList(List<DetallePago> detallePagoList) {
//        this.detallePagoList = detallePagoList;
//    }

    public List<DetalleAsistente> getDetalleAsistenteList() {
        return detalleAsistenteList;
    }

    public void setDetalleAsistenteList(List<DetalleAsistente> detalleAsistenteList) {
        this.detalleAsistenteList = detalleAsistenteList;
    }

    public Actividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Actividad idActividad) {
        this.idActividad = idActividad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsistencia != null ? idAsistencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asistencia)) {
            return false;
        }
        Asistencia other = (Asistencia) object;
        if ((this.idAsistencia == null && other.idAsistencia != null) || (this.idAsistencia != null && !this.idAsistencia.equals(other.idAsistencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.id.djns.model.Asistencia[ idAsistencia=" + idAsistencia + " ]";
    }
    
}
