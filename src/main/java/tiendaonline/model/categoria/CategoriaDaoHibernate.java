package tiendaonline.model.categoria;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

/**
 *
 * @author emilio
 */
@Repository("CategoriaDao")
public class CategoriaDaoHibernate extends GenericDaoHibernate<Categoria, Long> implements CategoriaDao {
	@Autowired
	private SessionFactory sessionFactory;

	public Categoria findByIdCategoria(long idCategoria) throws InstanceNotFoundException {

		Categoria categoria = (Categoria) getSession()
				.createQuery("SELECT u FROM Categoria u WHERE u.idCategoria = :idCategoria")
				.setParameter("idCategoria", idCategoria).uniqueResult();
		if (categoria == null) {
			throw new InstanceNotFoundException(idCategoria, Categoria.class.getName());
		} else {
			return categoria;
		}

	}

	public List<Categoria> listaCategoria() {
		Query query = getSession().createQuery("SELECT u FROM Categoria u");
		List<Categoria> lista = query.list();
		return lista;

	}

	public List<Categoria> listaSubCategoria() {
		Query query = getSession().createQuery("SELECT u FROM Categoria u WHERE u.idCategoriaPadre != 0");
		List<Categoria> lista = query.list();
		return lista;

	}

	public List<Categoria> listaCategoriaPadre(long idCategoriaPadre) {
		Query query = getSession().createQuery("SELECT u FROM Categoria u WHERE u.idCategoriaPadre = :idCategoriaPadre")
				.setParameter("idCategoriaPadre", idCategoriaPadre);
		List<Categoria> lista = query.list();
		return lista;

	}

	public void borrarCategoria(Categoria categoria) {
		sessionFactory.getCurrentSession().delete(categoria);

	}

}
