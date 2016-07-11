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
import org.id.djns.model.Iglesia;

/**
 *
 * @author desarrollopc
 */
@Stateless
public class IglesiaFacade extends AbstractFacade<Iglesia> {

    @PersistenceContext(unitName = "org.id_djns_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IglesiaFacade() {
        super(Iglesia.class);
    }

    public List<Iglesia> findIglesiaByDistrito(Integer distrito) {
        EntityManager emm = getEntityManager();
        try {
            Query q = emm.createQuery("SELECT i FROM Iglesia i WHERE i.idDistrito.idDistrito=:distrito Order by i.nombreIglesia", Iglesia.class);
            q.setParameter("distrito", distrito);
            return q.getResultList();
        } finally {
        }
    }
}
