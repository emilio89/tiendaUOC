/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.pages.pedido;

import tiendaonline.model.pedido.Pedido;
import tiendaonline.model.pedidoservice.PedidoService;
import tiendaonline.web.services.AuthenticationPolicy;
import tiendaonline.web.services.AuthenticationPolicyType;
import tiendaonline.web.services.Private;
import tiendaonline.web.util.UserSession;

import java.util.List;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author emilio
 */
@Private
public class MisPedidos {

	@SessionState(create = false)
	private UserSession userSession;
	@Property
	Pedido pedido;

	@Inject
	private PedidoService pedidoService;

	public List<Pedido> getPedidosTramitados() {
		return pedidoService.listaPedidosPorUser(userSession.getUserProfileId());

	}

}
