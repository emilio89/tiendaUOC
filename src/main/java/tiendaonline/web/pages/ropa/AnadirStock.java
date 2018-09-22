/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.pages.ropa;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.model.stocktalla.StockTalla;
import tiendaonline.web.services.Private;

import java.sql.SQLException;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author emilio
 */
@Private

public class AnadirStock {

	Ropa ropa;
	@Inject
	private RopaService ropaService;

	@Property
	private int stock;

	@Property
	private String talla;

	void onActivate(long ropaId) throws InstanceNotFoundException {
		ropa = ropaService.findRopa(ropaId);
	}

	long onPassivate() {

		return ropa.getIdRopa();
	}

	void onValidateFromAnadirStockForm() throws InstanceNotFoundException, SQLException {

		StockTalla stockTalla = ropaService.registrarStockTalla(talla, stock, ropa);

	}

}
