/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiendaonline.model.comentario;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author emilio
 */
@Repository("ComentarioDao")
public class ComentarioDaoHibernate extends GenericDaoHibernate<Comentario, Long> implements ComentarioDao {

	@Autowired
	private SessionFactory sessionFactory;

	public List<Comentario> listaComentario() {

		Query query = getSession().createQuery("SELECT u FROM Comentario u ");
		List<Comentario> listaComentario = query.list();
		return listaComentario;

	}

	public List<Comentario> listaComentario(long idRopa) {
		Query query = getSession().createQuery("SELECT a FROM Comentario a WHERE a.ropa.idRopa = :idRopa")
				.setParameter("idRopa", idRopa);
		List<Comentario> listaComentario = query.list();
		return listaComentario;

	}

	public void anadirComentario(Comentario comentario) {
		sessionFactory.getCurrentSession().save(comentario);

	}

	public void borrarComentario(Comentario comentario) {
		sessionFactory.getCurrentSession().delete(comentario);
	}
}
