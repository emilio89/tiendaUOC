/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiendaonline.model.stocktalla;

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
@Repository("StockTallaDao")
public class StockTallaHibernate extends GenericDaoHibernate<StockTalla, Long> implements StockTallaDao {

	@Autowired
	private SessionFactory sessionFactory;

	public List<StockTalla> listaStockTalla() {

		Query query = getSession().createQuery("SELECT u FROM StockTalla u");
		List<StockTalla> lista = query.list();
		return lista;

	}

	public List<StockTalla> listaStockTalla(long idRopa) {

		Query query = getSession().createQuery("SELECT u FROM StockTalla u WHERE u.ropa.idRopa = :idRopa")
				.setParameter("idRopa", idRopa);
		List<StockTalla> lista = query.list();
		return lista;

	}

	public void anadirStockTalla(StockTalla stocktalla) {
		sessionFactory.getCurrentSession().save(stocktalla);

	}

	public void borrarStock(StockTalla stock) {
		sessionFactory.getCurrentSession().delete(stock);
	}

}
