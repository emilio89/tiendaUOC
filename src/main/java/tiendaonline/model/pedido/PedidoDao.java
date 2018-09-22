/********************* EMILIO DOMÍNGUEZ SACHEZ *********************/

package tiendaonline.model.pedido;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.lineapedido.LineaPedido;
import tiendaonline.model.adjunto.Adjunto;

import java.util.List;

public interface PedidoDao extends GenericDao<Pedido, Long> {

	/**
	 * Returns an Pedido by idPedido ( Ropa identifier)
	 *
	 * @param idPedido
	 *            the pedido identifier
	 * @return the Pedido
	 */

	public Pedido findByidPedido(long idPedido) throws InstanceNotFoundException;

	public void anadirPedido(Pedido pedido);

	public List<Pedido> listaPedidosUsuario(long userProfileId);


	public List<Pedido> listaPedidosUsuarioEstado(long userProfileId, String estado);

	public List<Pedido> listaPedidosEstado(String estado);

	public void borrarPedido(long idPedido);

}
