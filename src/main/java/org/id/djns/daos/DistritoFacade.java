/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.id.djns.daos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import org.id.djns.model.Distrito;

/**
 *
 * @author desarrollopc
 */
@Stateless
public class DistritoFacade extends AbstractFacade<Distrito> {
    @PersistenceContext(unitName = "org.id_djns_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DistritoFacade() {
        super(Distrito.class);
    }
    
}
