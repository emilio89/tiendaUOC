package tiendaonline.web.components;

import java.util.List;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.categoria.Categoria;
import tiendaonline.model.entradablog.EntradaBlog;
import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.pedidoservice.PedidoService;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.web.pages.Index;
import tiendaonline.web.pages.pedido.VerPedido;
import tiendaonline.web.pages.ropa.Busqueda;
import tiendaonline.web.services.AuthenticationPolicy;
import tiendaonline.web.services.AuthenticationPolicyType;
import tiendaonline.web.util.Carrito;
import tiendaonline.web.util.CookiesManager;
import tiendaonline.web.util.UserSession;

public class Layout {

	@Import(stylesheet = "context:css/stylesTapestry.css")
	void afterRender() {
	}

	@Property
	@SessionState(create = false)
	private Carrito carrito;

	@Component
	Form carritoForm;

	@Property
	@Parameter(required = false, defaultPrefix = "message")
	private String menuExplanation;

	@Property
	@Parameter(required = true, defaultPrefix = "message")
	private String pageTitle;

	@Property
	@SessionState(create = false)
	private UserSession userSession;

	private boolean siHayCarrito = false;

	@Inject
	private Cookies cookies;

	@Inject
	private RopaService ropaService;

	@Inject
	private AdministradorService administradorService;
	@Inject
	private PedidoService pedidoService;

	@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)

	Object onActionFromLogout() {
		userSession = null;
		CookiesManager.removeCookies(cookies);
		return Index.class;
	}

	private List<Categoria> listaCategoria;
	@Property
	Categoria categoria;
	@Property
	Etiqueta etiqueta;

	/* AQUI VA TODO LO DEL FORMULARIO BUSCAR */
	@Property
	private String aBuscar;
	@InjectPage
	private Busqueda page2;

	// Generally useful bits and pieces

	@Component(id = "fomularioBuscar")
	private Form form;
	@Component(id = "aBuscar")
	private TextField aBuscar2;

	// The code

	void onValidateFromfomularioBuscar() {
		if (aBuscar == null || aBuscar.trim().equals("")) {
			form.recordError(aBuscar2, "Inserte algo para buscar.");
		}
	}

	Object onSuccessFromfomularioBuscar() {
		page2.set(aBuscar);
		return page2;
	}

	public List<Categoria> getListaCategoriaHombre() {
		return ropaService.listaCategoriasPadre(1);
	}

	public List<Categoria> getListaCategoriaMujer() {
		return ropaService.listaCategoriasPadre(2);
	}

	public List<Etiqueta> getListaEtiquetasMenu() {
		return administradorService.listaEtiquetasMenu();
	}

	public List<Etiqueta> getListaEtiquetasEspeciales() {
		List<Etiqueta> a = administradorService.listaEtiquetasColecciones();
		for(int i=1; i<a.size(); i++){
			if(a.get(i).getNombreEtiqueta().equals("N/A"))
				a.remove(i);
		}
		return a;
	}

	public List<Etiqueta> getListaEtiquetasDescuento() {
		return administradorService.listaEtiquetasDescuento();
	}

	public double getPrecioCarrito() throws InstanceNotFoundException {
		if (carrito != null)
			return carrito.calculaPrecio();
		else
			return 0;
	}

	/*
	 * public void setPrecioCarrito(int precioCarrito) { this.precioCarrito =
	 * precioCarrito; }
	 */
	public int getElementosCarrito() {
		if (carrito != null)
			return carrito.getProductos().size();
		else
			return 0;
	}

	public boolean isSiHayCarrito() {
		if (carrito.getProductos().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public boolean getPedidosPendientes() {
		if ((pedidoService.listaPedidosUsuarioEstado(userSession.getUserProfileId(), "Creado").isEmpty())
				&& (pedidoService.listaPedidosUsuarioEstado(userSession.getUserProfileId(), "Tramitación").isEmpty()))
			return false;
		else
			return false;

	}

	public void setSiHayCarrito(boolean siHayCarrito) {

		if (carrito.getProductos().isEmpty()) {
			this.siHayCarrito = false;
		} else {
			this.siHayCarrito = true;
		}
	}

	Object onSuccessFromCarritoForm() {
		return VerPedido.class;

	}

	Object onSuccessFromborrarCarritoForm() {
		// BORRA EL CARRITO!!
		carrito.vaciarCarrito();
		return Index.class;

	}

	@Property
	EntradaBlog entradaBlog;

	public List<EntradaBlog> getListEntradasBlog3() {
		return administradorService.listaEntradasBlog3();

	}
}
