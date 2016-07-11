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
import org.id.djns.model.Usuario;

/**
 *
 * @author desarrollopc
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {
    @PersistenceContext(unitName = "org.id_djns_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public List<Usuario> getLstUsuario(String usuario, String clave) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuarioActivo=1 and u.usuario=:usuario and u.claveAcceso=:clave", Usuario.class);
            q.setParameter("usuario", usuario);
            q.setParameter("clave", clave.toUpperCase());
            return q.getResultList();
        } finally {
        }
    }
}
