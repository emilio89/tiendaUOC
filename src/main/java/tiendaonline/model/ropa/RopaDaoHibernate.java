/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.model.ropa;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Emilio
 */
@Repository("RopaDao")
public class RopaDaoHibernate extends GenericDaoHibernate<Ropa, Long> implements RopaDao {

	public Ropa findByidRopa(long idRopa) throws InstanceNotFoundException {

		Ropa ropa = (Ropa) getSession().createQuery("SELECT u FROM Ropa u WHERE u.idRopa = :idRopa")
				.setParameter("idRopa", idRopa).uniqueResult();
		if (ropa == null) {
			throw new InstanceNotFoundException(idRopa, Ropa.class.getName());
		} else {
			return ropa;
		}

	}

	public void actualizarEtiqueta(Ropa ropa) throws InstanceNotFoundException {

		Ropa ropaAux = findByidRopa(ropa.getIdRopa());
		ropaAux.setIdEtiqueta(ropa.getIdEtiqueta());
		save(ropaAux);
	}

	@Autowired
	private SessionFactory sessionFactory;

	public List<Ropa> listaRopaBusqueda(String aBuscar) {
		Query query = getSession()
				.createQuery("SELECT u FROM Ropa u WHERE u.descripcion LIKE :aBuscar OR u.nombre LIKE :aBuscar")
				.setParameter("aBuscar", "%" + aBuscar + "%");
		List<Ropa> lista = query.list();

		return lista;
	}

	public List<Ropa> listaRopa() {

		Query query = getSession().createQuery("SELECT u FROM Ropa u");
		List<Ropa> lista = query.list();
		return lista;

	}

	public List<Ropa> listaRopaImagen() {

		Query query = getSession().createQuery("SELECT u FROM Ropa u ");
		List<Ropa> lista = query.list();
		return lista;

	}

	public List<Ropa> listaRopaPorCategoria(long idCategoria) {

		Query query = getSession().createQuery("SELECT u FROM Ropa u WHERE u.idCategoria = :idCategoria")
				.setParameter("idCategoria", idCategoria);
		List<Ropa> lista = query.list();
		return lista;

	}

	public List<Ropa> listaRopaPorEtiqueta(long idEtiqueta) {

		Query query = getSession().createQuery("SELECT u FROM Ropa u WHERE u.idEtiqueta = :idEtiqueta")
				.setParameter("idEtiqueta", idEtiqueta);
		List<Ropa> lista = query.list();
		return lista;

	}

	public void anadirRopa(Ropa ropa) {
		sessionFactory.getCurrentSession().save(ropa);

	}

	public List<Ropa> buscarConCategoria(String aBuscar, long idCategoria) {
		Query query = getSession()
				.createQuery(
						"SELECT u FROM Ropa u WHERE (u.descripcion LIKE :aBuscar OR u.nombre LIKE :aBuscar) AND u.idCategoria = :idCategoria")
				.setParameter("aBuscar", "%" + aBuscar + "%").setParameter("idCategoria", idCategoria);
		List<Ropa> lista = query.list();

		return lista;

	}

	public List<Ropa> listaRopaUltima() {
		Query query = getSession().createQuery("SELECT u FROM Ropa u ORDER BY u.fechaRegistro DESC");
		List<Ropa> lista = query.list();

		return lista;

	}

	public void deleteRopa(Ropa ropa) {
		sessionFactory.getCurrentSession().delete(ropa);
	}

}
