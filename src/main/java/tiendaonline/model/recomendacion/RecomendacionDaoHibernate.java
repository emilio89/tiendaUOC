/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiendaonline.model.recomendacion;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.ropa.Ropa;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author emilio
 */
@Repository("RecomendacionDao")
public class RecomendacionDaoHibernate extends GenericDaoHibernate<Recomendacion, Long> implements RecomendacionDao {
	@Autowired
	private SessionFactory sessionFactory;

	public Recomendacion findByIdsRopa(Ropa idRopa1, Ropa idRopa2) throws InstanceNotFoundException {
		Recomendacion recomendacion = (Recomendacion) getSession()
				.createQuery("SELECT u FROM Recomendacion u WHERE (u.idRopa1 = :idRopa1) and (u.idRopa2 = :idRopa2)")
				.setParameter("idRopa1", idRopa1).setParameter("idRopa2", idRopa2).uniqueResult();
		if (recomendacion == null) {
			throw new InstanceNotFoundException(idRopa1, Recomendacion.class.getName());
		} else {
			return recomendacion;
		}

	}

	public List<Recomendacion> findidRopaRecomendacion(long idRopa3) throws InstanceNotFoundException {
		List<Recomendacion> recomendacion = (List<Recomendacion>) getSession()
				.createQuery(
						"SELECT u FROM Recomendacion u WHERE (u.idRopa1.idRopa = :idRopa3) or (u.idRopa2.idRopa = :idRopa3)   ORDER BY u.numVeces DESC")
				.setMaxResults(5).setParameter("idRopa3", idRopa3).list();
		if (recomendacion == null) {
			throw new InstanceNotFoundException(idRopa3, Recomendacion.class.getName());
		} else {
			return recomendacion;
		}

	}

	public List<Ropa> listadeIds2(Ropa idRopa1) {
		List<Ropa> devolver = (List<Ropa>) getSession()
				.createQuery(
						"SELECT u.idRopa2 FROM Recomendacion u WHERE (u.idRopa1 = :idRopa1) ORDER BY u.numVeces DESC")
				.setMaxResults(5).setParameter("idRopa1", idRopa1).list();
		return devolver;
	}

	public List<Ropa> listadeIds1(Ropa idRopa2) {
		List<Ropa> devolver = (List<Ropa>) getSession()
				.createQuery(
						"SELECT u.idRopa1 FROM Recomendacion u WHERE (u.idRopa2 = :idRopa2) ORDER BY u.numVeces DESC")
				.setMaxResults(5).setParameter("idRopa2", idRopa2).list();
		return devolver;
	}

	public List<Recomendacion> listaTodasRecomendaciones() {
		Query query = getSession().createQuery("SELECT u FROM Recomendacion u");
		List<Recomendacion> listaRecomendaciones = query.list();
		return listaRecomendaciones;

	}

	public void insertarRecomendacion(Recomendacion recomendacion) {

		sessionFactory.getCurrentSession().save(recomendacion);
	}

	public void borrarRecomendacion(long idRecomendacion) {
		Recomendacion recomendacion = (Recomendacion) getSession()
				.createQuery("SELECT u FROM Recomendacion u WHERE (u.idRecomendacion = :idRecomendacion)")
				.setParameter("idRecomendacion", idRecomendacion).setParameter("idRecomendacion", idRecomendacion)
				.uniqueResult();
		if (recomendacion == null) {
		} else {
			sessionFactory.getCurrentSession().delete(recomendacion);
		}

	}
}
