/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.pages.pedido;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.pedido.Pedido;
import tiendaonline.model.pedidoservice.PedidoService;
import java.util.List;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author Emilio
 */
public class VerPedidosTramitado {

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

	public List<Pedido> getPedidosCreado() {
		return pedidoService.listaPedidosEstado("Creado");

	}

	public List<Pedido> getPedidosTramitacion() {
		return pedidoService.listaPedidosEstado("Tramitación");

	}

	public List<Pedido> getPedidosEnviado() {
		return pedidoService.listaPedidosEstado("Enviado");

	}

	public List<Pedido> getPedidosBorrado() {
		return pedidoService.listaPedidosEstado("Borrado");

	}

	public List<Pedido> getPedidosSinStock() {
		return pedidoService.listaPedidosEstado("SIN STOCK");

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
		} else if (valueForMyBoolean.equals("S")) {
			salida = "SIN STOCK";
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

	public boolean getSinStock() {
		if (salida == "SIN STOCK")
			return true;
		else
			return false;
	}

}
