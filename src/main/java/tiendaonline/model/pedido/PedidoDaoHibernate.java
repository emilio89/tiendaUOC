/********************* EMILIO DOMÍNGUEZ SACHEZ *********************/
package tiendaonline.model.pedido;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.lineapedido.LineaPedido;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("PedidoDao")
public class PedidoDaoHibernate extends GenericDaoHibernate<Pedido, Long> implements PedidoDao {
	@Autowired
	private SessionFactory sessionFactory;

	public Pedido findByidPedido(long idPedido) throws InstanceNotFoundException {

		Pedido pedido = (Pedido) getSession().createQuery("SELECT u FROM Pedido u WHERE u.idPedido = :idPedido")
				.setParameter("idPedido", idPedido).uniqueResult();
		if (pedido == null) {
			throw new InstanceNotFoundException(idPedido, Pedido.class.getName());
		} else {
			return pedido;
		}
	}

	public void anadirPedido(Pedido pedido) {

		sessionFactory.getCurrentSession().save(pedido);

	}


	
	public List<Pedido> listaPedidosUsuario(long userProfileId) {

		Query query = getSession().createQuery("SELECT a FROM Pedido a WHERE a.usuario.userProfileId = :userProfileId")
				.setParameter("userProfileId", userProfileId);
		List<Pedido> listaPedido = query.list();
		return listaPedido;

	}

	public List<Pedido> listaPedidosUsuarioEstado(long userProfileId, String estado) {
		Query query = getSession()
				.createQuery(
						"SELECT a FROM Pedido a WHERE a.usuario.userProfileId = :userProfileId AND a.estado LIKE :estado ORDER BY a.fechaPedido DESC")
				.setParameter("userProfileId", userProfileId).setParameter("estado", "%" + estado + "%");
		List<Pedido> listaPedido = query.list();
		return listaPedido;

	}

	public List<Pedido> listaPedidosEstado(String estado) {

		Query query = getSession()
				.createQuery("SELECT a FROM Pedido a WHERE a.estado LIKE :estado ORDER BY a.fechaPedido DESC")
				.setParameter("estado", "%" + estado + "%");
		List<Pedido> listaPedido = query.list();
		return listaPedido;

	}

	public void borrarPedido(long idPedido) {
		Pedido pedido = (Pedido) getSession().createQuery("SELECT u FROM Pedido u WHERE u.idPedido = :idPedido")
				.setParameter("idPedido", idPedido).uniqueResult();
		if (pedido == null) {

		} else {
			sessionFactory.getCurrentSession().delete(pedido);
		}

	}

}
