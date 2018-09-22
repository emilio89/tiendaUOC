/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiendaonline.model.adjunto;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

/**
 *
 * @author emilio
 */
@Repository("AdjuntoDao")
public class AdjuntoDaoHibernate extends GenericDaoHibernate<Adjunto, Long> implements AdjuntoDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Adjunto findByIdRopa(Long idRopa) throws InstanceNotFoundException {
		
		Adjunto adjunto = (Adjunto) getSession().createQuery("SELECT u FROM Adjunto u WHERE u.ropa.idRopa = :idRopa")
				.setParameter("idRopa", idRopa).uniqueResult();
		return adjunto;
	}

	public void anadirAdjunto(Adjunto adjunto) {

		sessionFactory.getCurrentSession().save(adjunto);
	}

	public void borrarAdjunto(Adjunto a) {
		sessionFactory.getCurrentSession().delete(a);
	}

}
