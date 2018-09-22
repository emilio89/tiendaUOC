package tiendaonline.web.pages.user;

import java.util.List;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import tiendaonline.model.pedido.Pedido;
import tiendaonline.model.pedidoservice.PedidoService;
import tiendaonline.web.services.AuthenticationPolicy;
import tiendaonline.web.services.AuthenticationPolicyType;
import tiendaonline.web.util.UserSession;
@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class MisPedidos {

	@Property
	Pedido pedido;

	@Inject
	private PedidoService pedidoService;
	@Property
	@Persist(PersistenceConstants.FLASH)
	// We use a String, not a Boolean, in the radio group value so that we can
	// represent null. Boolean can't represent
	// null because Tapestry will coerce it to Boolean.FALSE. See
	// https://issues.apache.org/jira/browse/TAPESTRY-1928 .
	private String valueForMyBoolean;
	@Property
	String salida;
	@SessionState(create = false)
	private UserSession userSession;

	public List<Pedido> getPedidosCreado() {
		return pedidoService.listaPedidosUsuarioEstado(userSession.getUserProfileId(), "Creado");

	}

	public List<Pedido> getPedidosTramitacion() {
		return pedidoService.listaPedidosUsuarioEstado(userSession.getUserProfileId(), "Tramitación");

	}

	public List<Pedido> getPedidosEnviado() {
		return pedidoService.listaPedidosUsuarioEstado(userSession.getUserProfileId(), "Enviado");

	}

	public List<Pedido> getPedidosBorrado() {
		return pedidoService.listaPedidosUsuarioEstado(userSession.getUserProfileId(), "Borrado");

	}

	void setupRender() {
		if (valueForMyBoolean == null) {
			valueForMyBoolean = "T";
		}
		if (valueForMyBoolean.equals("T")) {
			salida = "Tramitacion";
		} else if (valueForMyBoolean.equals("C")) {
			salida = "Creado";
		} else if (valueForMyBoolean.equals("E")) {
			salida = "Enviado";
		} else if (valueForMyBoolean.equals("B")) {
			salida = "Borrado";
		} else {
			throw new IllegalStateException(valueForMyBoolean);
		}
	}

	public boolean getCreado() {
		if (salida == "Creado")
			return true;
		else
			return false;
	}

	public boolean getTramitacion() {
		if (salida == "Tramitacion")
			return true;
		else
			return false;
	}

	public boolean getEnviado() {
		if (salida == "Enviado")
			return true;
		else
			return false;
	}

	public boolean getBorrado() {
		if (salida == "Borrado")
			return true;
		else
			return false;
	}

}
