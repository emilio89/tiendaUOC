/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiendaonline.model.lineapedido;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.pedido.Pedido;
import tiendaonline.model.ropa.Ropa;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author emilio
 */
@Repository("LineaPedidoDao")
public class LineaPedidoDaoHibernate extends GenericDaoHibernate<LineaPedido, Long> implements LineaPedidoDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void anadirLineaPedido(Pedido pedido, long idStockTalla, Ropa idRopa, double PrecioFinal, int cantidad) {
		LineaPedido lineaPedido = new LineaPedido(pedido, idStockTalla, idRopa, PrecioFinal, cantidad);
		sessionFactory.getCurrentSession().save(lineaPedido);
	}

	public List<LineaPedido> listarLineasPedido(long idPedido) {
		List<LineaPedido> listaPedido = new ArrayList<LineaPedido>();
		try {
			Query query = getSession().createQuery("SELECT a FROM LineaPedido a WHERE a.pedido.idPedido = :idPedido")
					.setParameter("idPedido", idPedido);
			listaPedido = query.list();
			return listaPedido;
		} catch (ObjectNotFoundException e) {
			listaPedido.clear();
			return listaPedido;
		}
	}

	public List<LineaPedido> listarLineasPedidoPorRopa(long idRopa) {
			Query query = getSession().createQuery("SELECT u FROM LineaPedido u WHERE u.idRopa.idRopa = :idRopa")
					.setParameter("idRopa", idRopa);
			List<LineaPedido> listaPedido = query.list();
			return listaPedido;
	}

	public void borrarLineaPedido(long idLineaPedido) {
		LineaPedido lineaPedido = (LineaPedido) getSession()
				.createQuery("SELECT u FROM LineaPedido u WHERE u.idLineaPedido = :idLineaPedido")
				.setParameter("idLineaPedido", idLineaPedido).uniqueResult();
		if (lineaPedido == null) {

		} else {
			sessionFactory.getCurrentSession().delete(lineaPedido);

		}
	}

}
