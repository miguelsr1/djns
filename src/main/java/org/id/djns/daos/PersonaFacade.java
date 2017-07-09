/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.id.djns.daos;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.id.djns.model.Alimentacion;
import org.id.djns.model.DetalleAsistente;
import org.id.djns.model.Persona;

/**
 *
 * @author desarrollopc
 */
@Stateless
public class PersonaFacade extends AbstractFacade<Persona> {

    @PersistenceContext(unitName = "org.id_djns_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonaFacade() {
        super(Persona.class);
    }

    public List<Persona> getLstPersonasByIglesia(Integer iglesia) {
        EntityManager emm = getEntityManager();
        Query q = emm.createQuery("SELECT p FROM Persona p WHERE p.idIglesia.idIglesia =:iglesia", Persona.class);
        q.setParameter("iglesia", iglesia);
        return q.getResultList();
    }

    public Persona getPersonasByCod(String codigo) {
        EntityManager emm = getEntityManager();
        Query q = emm.createQuery("SELECT p FROM Persona p WHERE p.codigoPersona =:codigo", Persona.class);
        q.setParameter("codigo", codigo);
        if (q.getResultList().isEmpty()) {
            return new Persona();
        } else {
            return (Persona) q.getSingleResult();
        }
    }
    

    public DetalleAsistente getDetalleAsistenciaByCod(String codigo) {
        Query q = em.createQuery("SELECT d FROM DetalleAsistente d WHERE d.idPersona.codigoPersona =:codigo", DetalleAsistente.class);
        q.setParameter("codigo", codigo);
        if (q.getResultList().isEmpty()) {
            return new DetalleAsistente();
        } else {
            return (DetalleAsistente) q.getSingleResult();
        }
    }

    public Boolean isAlimentoPersona(String codEmpleado, Date fecha, Character tiempoAlimentacion) {
        Query q = em.createQuery("SELECT a FROM Alimentacion a WHERE a.idDetalleAsistente.idPersona.codigoPersona=:codPersona and a.fechaAlimentacion=:date and a.idTiempoAlimenacion=:tiempoAlimentacion", Persona.class);
        q.setParameter("codPersona", codEmpleado);
        q.setParameter("date", fecha);
        q.setParameter("tiempoAlimentacion", tiempoAlimentacion);

        return q.getResultList().isEmpty();
    }

    public List<Alimentacion> guardarAlimentacion(Alimentacion alimentacion) {
        em.getTransaction().begin();
        em.persist(alimentacion);
        em.getTransaction().commit();

        Query q = em.createQuery("SELECT a FROM Alimentacion a WHERE a.fechaAlimentacion=:fecha and a.idTiempoAlimenacion=:idTipoAli", Alimentacion.class);
        q.setParameter("fecha", alimentacion.getFechaAlimentacion());
        q.setParameter("idTipoAli", alimentacion.getIdTiempoAlimenacion());
        return q.getResultList();
    }

    public List<Alimentacion> getLstAlimentacion(Date fecha, Character tipoAlimentacion) {
        Query q = em.createQuery("SELECT a FROM Alimentacion a WHERE a.fechaAlimentacion=:date and a.idTiempoAlimenacion=:tipoAli", Alimentacion.class);
        q.setParameter("date", fecha);
        q.setParameter("tipoAli", tipoAlimentacion);
        return q.getResultList();
    }
}
