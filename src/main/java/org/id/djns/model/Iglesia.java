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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author DesarrolloPc
 */
@Entity
@Table(name = "iglesia")
@NamedQueries({
    @NamedQuery(name = "Iglesia.findAll", query = "SELECT i FROM Iglesia i")})
public class Iglesia implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idIglesia", fetch = FetchType.LAZY)
    private List<Usuario> usuarioList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_iglesia")
    private Integer idIglesia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_iglesia")
    private String nombreIglesia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idIglesia", fetch = FetchType.LAZY)
    private List<DetallePago> detallePagoList;
    @JoinColumn(name = "id_distrito", referencedColumnName = "id_distrito")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Distrito idDistrito;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idIglesia", fetch = FetchType.LAZY)
    private List<Persona> personaList;

    public Iglesia() {
    }

    public Iglesia(Integer idIglesia) {
        this.idIglesia = idIglesia;
    }

    public Iglesia(Integer idIglesia, String nombreIglesia) {
        this.idIglesia = idIglesia;
        this.nombreIglesia = nombreIglesia;
    }

    public Integer getIdIglesia() {
        return idIglesia;
    }

    public void setIdIglesia(Integer idIglesia) {
        this.idIglesia = idIglesia;
    }

    public String getNombreIglesia() {
        return nombreIglesia;
    }

    public void setNombreIglesia(String nombreIglesia) {
        this.nombreIglesia = nombreIglesia;
    }

    public List<DetallePago> getDetallePagoList() {
        return detallePagoList;
    }

    public void setDetallePagoList(List<DetallePago> detallePagoList) {
        this.detallePagoList = detallePagoList;
    }

    public Distrito getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(Distrito idDistrito) {
        this.idDistrito = idDistrito;
    }

    public List<Persona> getPersonaList() {
        return personaList;
    }

    public void setPersonaList(List<Persona> personaList) {
        this.personaList = personaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIglesia != null ? idIglesia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Iglesia)) {
            return false;
        }
        Iglesia other = (Iglesia) object;
        if ((this.idIglesia == null && other.idIglesia != null) || (this.idIglesia != null && !this.idIglesia.equals(other.idIglesia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreIglesia;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }
}