/*
*/

package tiendaonline.web.pages.ropa;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.comentario.Comentario;
import tiendaonline.model.lineapedido.LineaPedido;
import tiendaonline.model.pedidoservice.PedidoService;
import tiendaonline.model.recomendacion.Recomendacion;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropa.RopaDao;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.model.stocktalla.StockTalla;
import tiendaonline.model.userservice.UserService;
import tiendaonline.web.pages.user.AdministracionUsuarios;
import tiendaonline.web.services.AuthenticationPolicy;
import tiendaonline.web.services.AuthenticationPolicyType;
import tiendaonline.web.services.Private;
import tiendaonline.model.adjunto.Adjunto;

import java.util.List;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author emilio
 */

@Private
public class ActualizarRopa {

	/*
	 * @Property private Adjunto adjunto;
	 */

	@Property
	Ropa ropa;

	@Property
	StockTalla stockTalla;
	@Property
	Comentario comentario;

	@Inject
	private RopaService ropaService;
	@Inject
	private AdministradorService administradorService;
	@Inject
	private UserService userService;
	@Inject
	private PedidoService pedidoService;

	public List<Ropa> getListaRopa() {
		return ropaService.listaRopa();
	}

	Object onActionFromBorrarRopa(long idRopa) throws InstanceNotFoundException {
		Ropa r = ropaService.findRopa(idRopa);
		List<StockTalla> stock = ropaService.listaStockTalla(idRopa);
		Adjunto adjunto = null;

		if (ropaService.recuperarAdjunto(idRopa) != null)
			adjunto = ropaService.recuperarAdjunto(idRopa);
		List<Comentario> comentarios = ropaService.listaComentario(idRopa);
		List<Recomendacion> recomendaciones = pedidoService.listaRecomendacionesTodas(idRopa);
		//SE BORRA EN CASCADA TODAS LAS DEPENDENCIAS DE LA ROPA HASTA BORRAR LA PROPIA ROPA
		for (int i = 0; i < recomendaciones.size(); i++)
			pedidoService.borrarRecomendacion(recomendaciones.get(i).getIdRecomendacion());

		for (int i = 0; i < stock.size(); i++)
			ropaService.borrarStock(stock.get(i));

		for (int i = 0; i < comentarios.size(); i++)
			ropaService.borrarComentario(comentarios.get(i).getIdComentario());

		if (ropaService.recuperarAdjunto(idRopa) != null)
			ropaService.borrarAdjunto(adjunto);
		List<LineaPedido> lineasPedidos = null;
		if (pedidoService.listarLineasPedidoPorRopa(idRopa) != null) {
			lineasPedidos = pedidoService.listarLineasPedidoPorRopa(idRopa);
			for (int i = 0; i < lineasPedidos.size(); i++) {
				pedidoService.borrarPedido(lineasPedidos.get(i).getPedido().getIdPedido());
				pedidoService.borrarLineaPedido(lineasPedidos.get(i).getIdLineaPedido());
			}
		}
		ropaService.borrarRopa(r);

		return ActualizarRopa.class;
	}

}
