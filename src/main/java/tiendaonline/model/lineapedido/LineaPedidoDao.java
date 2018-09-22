/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiendaonline.model.lineapedido;

import java.util.List;

import tiendaonline.model.pedido.Pedido;
import tiendaonline.model.ropa.Ropa;

/**
 *
 * @author emilio
 */
public interface LineaPedidoDao {
	/**
	 * Returns an LineaPedido by idLineaPedido ( not identifier)
	 *
	 * @param idLineaPedido
	 * @return LineaPedido
	 */
	public void anadirLineaPedido(Pedido pedido, long idStockTalla, Ropa idRopa, double PrecioFinal, int cantidad);

	public List<LineaPedido> listarLineasPedido(long idPedido);

	public List<LineaPedido> listarLineasPedidoPorRopa(long idRopa);
	
	public void borrarLineaPedido (long idLineaPedido);

}
