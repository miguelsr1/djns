/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.id.djns.daos;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.id.djns.model.Asistencia;
import org.id.djns.model.DetalleAsistente;
import org.id.djns.model.Persona;

/**
 *
 * @author desarrollopc
 */
@Stateless
public class AsistenciaFacade extends AbstractFacade<Asistencia> {

    @PersistenceContext(unitName = "org.id_djns_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AsistenciaFacade() {
        super(Asistencia.class);
    }

    public List<Persona> getLstPersonaNotAsistencia(Integer iglesia) {
        EntityManager emm = getEntityManager();
        Query q2 = emm.createQuery("select  d.idPersona.idPersona from DetalleAsistente d", DetalleAsistente.class);
        Query q;
        if (q2.getResultList().isEmpty()) {
            q = emm.createQuery("select p from Persona p where p.idIglesia.idIglesia=:idIglesia", Persona.class);
            q.setParameter("idIglesia", iglesia);
        } else {
            q = emm.createQuery("select p from Persona p where p.idIglesia.idIglesia=:idIglesia and p.idPersona not in :lstDatos", Persona.class);
            q.setParameter("idIglesia", iglesia);
            q.setParameter("lstDatos", q2.getResultList());
        }
        return q.getResultList();
    }

    public List<Persona> getLstPersonaByAsistencia(Integer iglesia) {
        EntityManager emm = getEntityManager();
        Query q = emm.createQuery("select d.idPersona from DetalleAsistente d where d.idPersona.idIglesia.idIglesia=:idIglesia", Persona.class);
        q.setParameter("idIglesia", iglesia);
        return q.getResultList();
    }

    public void agregarAsistente(DetalleAsistente detalleAsistente) {
        em.getTransaction().begin();
        em.persist(detalleAsistente);
        em.getTransaction().commit();
    }

    public void removerAsistente(Integer idPersona) {
        Query q = em.createQuery("SELECT d FROM DetalleAsistente d WHERE d.idPersona.idPersona=:idPersona", DetalleAsistente.class);
        q.setParameter("idPersona", idPersona);

        DetalleAsistente det = (DetalleAsistente) q.getSingleResult();

        em.getTransaction().begin();
        em.remove(det);
        em.getTransaction().commit();
    }
}
