/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.util;

import java.net.URL;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;
import org.springframework.beans.factory.annotation.Autowired;

import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.stocktalla.StockTalla;

/**
 *
 * @author Emilio
 */
public class LineaCarrito {

	private double precio;
	private int cantidad = 1;
	private Ropa ropa;
	private long idStockTalla;

	public LineaCarrito(double precio, Ropa ropa, long idStockTalla) {
		this.precio = precio;
		this.ropa = ropa;
		this.idStockTalla = idStockTalla;
	}

	public double getPrecio() {

		return precio * cantidad;
	}

	public void setPrecio(long precio) {
		this.precio = precio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = this.cantidad + cantidad;
	}

	public void inicializaCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Ropa getRopa() {
		return ropa;
	}

	public void setRopa(Ropa ropa) {
		this.ropa = ropa;
	}

	public long getIdStockTalla() {
		return idStockTalla;
	}

	public void setIdStockTalla(long idStockTalla) {
		this.idStockTalla = idStockTalla;
	}

}
