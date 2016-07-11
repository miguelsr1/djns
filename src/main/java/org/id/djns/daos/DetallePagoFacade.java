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
import org.id.djns.model.DetallePago;

/**
 *
 * @author desarrollopc
 */
@Stateless
public class DetallePagoFacade extends AbstractFacade<DetallePago> {
    @PersistenceContext(unitName = "org.id_djns_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DetallePagoFacade() {
        super(DetallePago.class);
    }
    
    public void guardarDetallePago(DetallePago detallePago){
        detallePago.setIdAsistencia(em.find(Asistencia.class, 1));
        
        em.getTransaction().begin();
        em.persist(detallePago);
        em.getTransaction().commit();
    }
    
    public List<DetallePago> getLstDetallePagoByIglesia(Integer iglesia) {
        EntityManager emm = getEntityManager();
        Query q = emm.createQuery("SELECT d FROM DetallePago d WHERE d.idIglesia.idIglesia =:iglesia", DetallePago.class);
        q.setParameter("iglesia", iglesia);
        return q.getResultList();
    }
}
