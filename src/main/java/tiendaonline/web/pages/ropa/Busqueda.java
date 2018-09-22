package tiendaonline.web.pages.ropa;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.categoria.Categoria;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.model.util.ListadoIndex;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.SelectModelFactory;

/**
 *
 * @author emilio
 */
public class Busqueda {
	@Inject
	RopaService ropaService;
	String buscar;
	@Property
	Ropa ropa;
	@Property
	private long idCategoria;
	@Inject
	private AdministradorService administradorService;
	@Property
	private SelectModel categoriaSelectModel;
	@Inject
	SelectModelFactory selectModelFactory;
	@Component(id = "filtrarPorCategoriaForm")
	private Form form;
	@Property
	private String aBuscar;
	@Component(id = "aBuscar")
	private TextField aBuscar2;
	boolean porCategorias = false;
	@InjectPage
	private Busqueda page2;
	@Property
	private List<ListadoIndex> listadoBuscado;
	private int numeroElementos;
	@Inject
	private AssetSource assetSource;
	@Property
	private ListadoIndex listado;
	@Inject
	private PageRenderLinkSource pageLink;
	boolean siEncuentra;

	public void set(String busqueda) {
		this.buscar = busqueda;
	}

	public void set(String busqueda, long idCategoria) {
		this.buscar = busqueda;
		this.idCategoria = idCategoria;
	}

	Object[] onPassivate() {
		String id = Long.toString(idCategoria);
		if (idCategoria == 0)
			return new String[] { buscar };
		else
			return new String[] { buscar, id };
	}

	void onActivate(String buscar) {
		this.buscar = buscar;
	}

	void onActivate(String buscar, long idCategoria) {
		this.buscar = buscar;
		if (idCategoria != 0)
			this.idCategoria = idCategoria;
	}

	public List<Ropa> getRopaABuscar() throws InstanceNotFoundException {
		if (idCategoria != 0)
			return ropaService.listaRopaBusqueda(buscar, idCategoria);
		else
			return ropaService.listaRopaBusqueda(buscar);
	}

	// Muestra el SELECT de la lista de categorias, para seleccionar una.
	void setupRender() {
		List<Categoria> categorias = administradorService.listaSubCategoria();
		categoriaSelectModel = selectModelFactory.create(categorias, "nombreCategoria");
	}

	void onValidateFromfiltrarPorCategoriaForm() {
		if (aBuscar == null || aBuscar.trim().equals("")) {
			form.recordError(aBuscar2, "Inserte algo para buscar.");
		}
		porCategorias = true;
	}

	Object onSuccess() {
		page2.set(aBuscar, idCategoria);
		return page2;
	}

	public Asset getSignImage() {
		final String path = "Users/Emilio/Dropbox/Facultad/PFC/pojo-app/ ";

		return assetSource.getContextAsset(path, null);
	}

	public List<ListadoIndex> getListadosBuscar() throws InstanceNotFoundException {
		List<Ropa> ropaLista = getRopaABuscar();
		listadoBuscado = new ArrayList<ListadoIndex>();

		for (int i = 0; i < ropaLista.size(); i++) {
			long id2 = ropaLista.get(i).getIdRopa();
			String nuevo = "";
			nuevo = pageLink.createPageRenderLinkWithContext(MiImagen.class, id2).toString();

			ListadoIndex listado2 = new ListadoIndex(id2, nuevo, ropaLista.get(i).getNombre(),
					ropaLista.get(i).getDescripcion());
			listadoBuscado.add(listado2);
		}
		numeroElementos = listadoBuscado.size();
		return listadoBuscado;
	}

	public String getResultado() {
		return buscar;
	}

	public long getCategoriaresultado() {
		return idCategoria;
	}

	public void getSiEncuentra() throws InstanceNotFoundException {

		if (getListadosBuscar().isEmpty()) {
			siEncuentra = false;
		} else {
			siEncuentra = true;
		}

	}

	public boolean isSiEncuentra() throws InstanceNotFoundException {
		if (getListadosBuscar().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public void setSiEncuentra(boolean siHayCarrito) throws InstanceNotFoundException {

		if (getListadosBuscar().isEmpty()) {
			this.siEncuentra = false;
		} else {
			this.siEncuentra = true;
		}
	}

	public void setListadosBuscar(List<ListadoIndex> listadoBuscado) {
		this.listadoBuscado = listadoBuscado;
	}

	public boolean getIsMultiPaged() {
		if (this.numeroElementos > 12)
			return true;
		else
			return false;

	}

}
