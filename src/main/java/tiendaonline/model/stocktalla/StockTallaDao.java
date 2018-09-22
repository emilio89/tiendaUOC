/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiendaonline.model.stocktalla;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.ropa.Ropa;
import java.util.List;

/**
 *
 * @author emilio
 */
public interface StockTallaDao extends GenericDao<StockTalla, Long> {
	/**
	 * Returns an StockTalla by StockTalla ( StockTalla identifier)
	 *
	 * @param idStockTalla
	 *            the StockTalla identifier
	 * @return the StockTalla
	 */

	public void anadirStockTalla(StockTalla stocktalla);

	public List<StockTalla> listaStockTalla();

	public List<StockTalla> listaStockTalla(long idRopa);

	public void borrarStock(StockTalla stock);
}
