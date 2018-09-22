package tiendaonline.model.entradablog;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@Repository("EntradaBlogDao")
public class EntradaBlogDaoHibernate extends GenericDaoHibernate<EntradaBlog, Long> implements EntradaBlogDao {

	@Autowired
	private SessionFactory sessionFactory;

	public EntradaBlog findByIdEntradaBlog(long idEntradaBlog) throws InstanceNotFoundException {

		EntradaBlog entradaBlog = (EntradaBlog) getSession()
				.createQuery("SELECT u FROM EntradaBlog u WHERE u.idEntradaBlog = :idEntradaBlog")
				.setParameter("idEntradaBlog", idEntradaBlog).uniqueResult();
		if (entradaBlog == null) {
			throw new InstanceNotFoundException(idEntradaBlog, EntradaBlog.class.getName());
		} else {
			return entradaBlog;
		}
	}

	public List<EntradaBlog> listaEntradaBlog() {

		Query query = getSession().createQuery("SELECT u FROM EntradaBlog u ORDER BY u.fechaCreacion DESC");
		List<EntradaBlog> listaEntradaBlog = query.list();
		return listaEntradaBlog;

	}

	public List<EntradaBlog> listaEntradaBlog3() {
		Query query = getSession().createQuery("SELECT a FROM EntradaBlog a ORDER BY a.fechaCreacion DESC")
				.setMaxResults(3);
		List<EntradaBlog> listaEntradaBlog = query.list();
		return listaEntradaBlog;

	}

	public void anadirEntradaBlog(EntradaBlog entradaBlog) {
		sessionFactory.getCurrentSession().save(entradaBlog);

	}

	public void borrarEntradaBlog(EntradaBlog entrada) {
		sessionFactory.getCurrentSession().delete(entrada);

	}

}
