

package tiendaonline.web.pages.pedido;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.lineapedido.LineaPedido;
import tiendaonline.model.lineapedido.LineaPedidoDao;
import tiendaonline.model.pedido.Pedido;
import tiendaonline.model.pedidoservice.PedidoService;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userservice.UserService;
import tiendaonline.model.util.ListadoIndex;
import tiendaonline.web.pages.Index;
import tiendaonline.web.pages.ropa.MiImagen;
import tiendaonline.web.services.AuthenticationPolicy;
import tiendaonline.web.services.AuthenticationPolicyType;
import tiendaonline.web.util.Carrito;
import tiendaonline.web.util.LineaCarrito;
import tiendaonline.web.util.UserSession;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Emilio
 */

public class VerPedido {

	@Property
	@SessionState(create = false)
	private Carrito carrito;

	UserProfile userProfile;

	boolean verMensaje = false;

	@Component
	Form borrarCarritoForm;

	@Component
	Form tramitarPedidoForm;

	@Inject
	private PedidoService pedidoService;

	@Property
	@SessionState(create = false)
	private UserSession userSession;

	@Inject
	private UserService userService;
	@Inject
	private AdministradorService administradorService;

	@InjectPage
	private DatosPedido datosPedido;

	@Property
	@Persist(PersistenceConstants.FLASH)
	// We use a String, not a Boolean, in the radio group value so that we can
	// represent null. Boolean can't represent
	// null because Tapestry will coerce it to Boolean.FALSE. See
	// https://issues.apache.org/jira/browse/TAPESTRY-1928 .
	private String valueForMyBoolean;

	@Property
	private boolean myBoolean;

	@Inject
	Block bloque1;
	@Inject
	Block bloque2;
	@Inject
	Block bloque3;

	@Property
	private String string;

	@Inject
	private Messages messages;

	double variableDescuentoPuntos;
	int puntosRopa, puntosUsuario, descuentoPuntos;
	@Inject
	RopaService ropaService;
	
	
	public int getPuntosRopa() throws InstanceNotFoundException {
		List<LineaCarrito> list = getProductos();
		int aux = 0;
		int aux2 = 0;
		for (int i = 0; i < list.size(); i++) {
			aux = ropaService.findRopa(list.get(i).getRopa().getIdRopa()).getNumPuntos();
			System.out.println("aa"+aux );
			aux2= aux + aux2;
		}
		return aux2;
	}

	public int getPuntosUsuario() throws InstanceNotFoundException {
		return userService.findUserProfile(userSession.getUserProfileId()).getNumeroPuntos();
	}

	public int getDescuentoPuntos() throws InstanceNotFoundException, IOException {
		
		    BufferedReader br = new BufferedReader(new FileReader("variableDescuento.txt"));
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();

		        while (line != null) {
		            sb.append(line);
		            sb.append("\n");
		            line = br.readLine();
		        }
		        variableDescuentoPuntos = Double.valueOf(sb.toString());
		    } finally{
		        br.close();	    
		    }
		return (int) ((getPuntosRopa() + getPuntosUsuario()) * variableDescuentoPuntos);
	}

	public Block getBloque1() {
		return bloque1;
	}

	public void setBloque1(Block bloque1) {
		this.bloque1 = bloque1;
	}

	public double calculaPrecio2() throws InstanceNotFoundException {

		return pedidoService.calculaPrecio(carrito);
	}

	public boolean getHay() {
		if (carrito != null)
			if (!carrito.getProductos().isEmpty())
				return true;
			else
				return false;
		else
			return false;
	}

	public boolean isVerMensaje() {

		return verMensaje;
	}

	public void setVerMensaje(boolean verMensaje) {
		this.verMensaje = verMensaje;
	}

	public List getProductos() {
		if (carrito == null)
			carrito = new Carrito();
		return carrito.getProductos();
	}

	public double getPrecio(int i) throws InstanceNotFoundException {
		return this.calculaPrecio2();
	}

	// CADA LINEA DEL CARRITO es una linea De pedido en la persistencia
	// Despues todas esas lineas se meten en un PEDIDO (una vez tramitado....a)

	Object onSuccessFromborrarCarritoForm() {

		// BORRA EL CARRITO!!
		carrito.vaciarCarrito();
		return Index.class;

	}

	public void registrarLineaPedido(List productos, Pedido pedido) throws InstanceNotFoundException {
		Iterator i = productos.iterator();
		double precioFinal = 0;
		LineaPedido lineaPedido;
		while (i.hasNext()) {
			LineaCarrito linea = (LineaCarrito) i.next();
			if (administradorService.findEtiqueta(linea.getRopa().getIdEtiqueta()) != null) {

				Etiqueta et = administradorService.findEtiqueta(linea.getRopa().getIdEtiqueta());

				precioFinal = linea.getPrecio() - (linea.getPrecio() * et.getPorcentajeDescuento()) / 100;
				lineaPedido = new LineaPedido(pedido, linea.getIdStockTalla(), linea.getRopa(), precioFinal,
						linea.getCantidad());
				pedidoService.anadirLineaPedido(pedido.getIdPedido(), linea.getIdStockTalla(), linea.getRopa(), precioFinal,
						linea.getCantidad());
				pedido.addLineaPedido(lineaPedido);


			} else {
				lineaPedido = new LineaPedido(pedido, linea.getIdStockTalla(), linea.getRopa(),
						linea.getPrecio(), linea.getCantidad());
				pedidoService.anadirLineaPedido(pedido.getIdPedido(), linea.getIdStockTalla(), linea.getRopa(), precioFinal,
						linea.getCantidad());
				pedido.addLineaPedido(lineaPedido);


			}

		}

	}

	Object onSuccessFromtramitarPedidoForm() throws InstanceNotFoundException, IOException {

		userProfile = userService.findUserProfile(userSession.getUserProfileId());
		Date ahora = new Date();
		int puntosUser = userProfile.getNumeroPuntos();
		double precioAntes = this.calculaPrecio2();
		double precioFinal;
		if (!myBoolean) {
			precioFinal = precioAntes - getDescuentoPuntos();
			userProfile.setNumeroPuntos(0);
		} else {
			precioFinal = precioAntes;
			userProfile.setNumeroPuntos(puntosUser + getPuntosRopa());

		}
		Pedido pedido = pedidoService.registrarPedido(ahora, precioFinal, userProfile, (String) "Creado");
		datosPedido.setIdPedido(pedido.getIdPedido());
		this.registrarLineaPedido(carrito.getProductos(), pedido);
		carrito.vaciarCarrito();

		return datosPedido;

	}

	void setupRender() {
		if (valueForMyBoolean == null) {
			valueForMyBoolean = "T";
		}
		if (valueForMyBoolean.equals("T")) {
			myBoolean = Boolean.TRUE;
		} else if (valueForMyBoolean.equals("F")) {
			myBoolean = Boolean.FALSE;
		} else {
			throw new IllegalStateException(valueForMyBoolean);
		}
	}

	@Component
	private Form actualizarCantidadForm;

	private boolean actualizadoItem;
	@Property
	private long identificadorRopa;

	Object onSuccessFromactualizarCantidadForm() throws InstanceNotFoundException {
		actualizadoItem = true;
		return VerPedido.class;
	}

	void onActivate(boolean actualizadoItem, int identificadorRopa, int cantidad) {
		// Actualizamos la linea del carrito correspondiente
		if (actualizadoItem) {
			List<LineaCarrito> productos = carrito.getProductos();
			Iterator i = productos.iterator();
			LineaCarrito lineaCarrito = null;
			while (i.hasNext()) {
				LineaCarrito linea = (LineaCarrito) i.next();
				if (linea.getRopa().getIdRopa() == identificadorRopa) {
					if (cantidad==0) i.remove();
					if (cantidad < 0) break;
					linea.inicializaCantidad(cantidad);
				}
				
			}
			// Actualizar carrito
			carrito.setProductos(productos);
		}
	}

	void onPrepareForRender() {
		// Actualizamos los datos de la linea del carrito
		if (lineaCarrito != null) {
			identificadorRopa = lineaCarrito.getRopa().getIdRopa();
			cantidad = lineaCarrito.getCantidad();
		}
	}

	Object onPassivate() {
		return new Object[] { actualizadoItem, identificadorRopa, cantidad };
	}

	/*********** transformar a imagen **************/

	@Inject
	private AssetSource assetSource;

	@Property
	private LineaCarrito lineaCarrito;

	@Property
	private int cantidad;

	public String getRootPath() {
	    return assetSource.getClasspathAsset("/").toClientURL();
	}

	public List<ListadoIndex> getListadosBuscar() throws InstanceNotFoundException {
		List<Ropa> ropaLista = new ArrayList<Ropa>();
		List<ListadoIndex> auxiliar = new ArrayList<ListadoIndex>();
		for (int i = 0; i < this.getProductos().size(); i++) {
			LineaCarrito l = (LineaCarrito) this.getProductos().get(i);
			Ropa r = l.getRopa();
			String nuevo = "";
			nuevo = pageLink.createPageRenderLinkWithContext(MiImagen.class, r.getIdRopa()).toString();
			//

		}

		return auxiliar;
	}

	@Inject
	private PageRenderLinkSource pageLink;

	public Link getImageLink() {
		return pageLink.createPageRenderLinkWithContext(MiImagen.class, lineaCarrito.getRopa().getIdRopa());
	}

	public List<String> getPorcentajeDescuento() throws InstanceNotFoundException {

		return pedidoService.getPorcentajeDescuento(carrito);
	}

}
