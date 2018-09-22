/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.pages.ropa;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.model.stocktalla.StockTalla;
import tiendaonline.web.pages.Index;
import tiendaonline.web.services.Private;

import java.util.List;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author Emilio
 */
@Private

public class ModificarStock {

	@Property
	private long idRopa;
	@Property
	private String talla;
	@Property
	private int stock;

	@Property
	private List<StockTalla> listStockTalla;

	@SessionState(create = false)
	Ropa ropa;

	@Inject
	RopaService ropaService;

	@Property
	long idEtiqueta;

	@Component
	Form actualizarStockForm;

	void onActivate(long idRopa) throws InstanceNotFoundException {
		ropa = ropaService.findRopa(idRopa);
	}

	@Property
	StockTalla stockTalla;

	void onPrepareForRender() {
		idRopa = ropa.getIdRopa();
		listStockTalla = ropaService.listaStockTalla(idRopa);

	}

	Object onSuccessfromactualizarStockForm() throws InstanceNotFoundException {
		System.out.println("entra aqui3333");
		return Index.class;

	}

	@Property
	private int columnIndex;

	public int getColumnIndexMasUno() {
		return columnIndex + 1;
	}

}
