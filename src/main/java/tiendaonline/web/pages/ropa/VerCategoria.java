/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.pages.ropa;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.categoria.Categoria;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.model.util.ListadoIndex;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.PageRenderLinkSource;

/**
 *
 * @author emilio
 */
public class VerCategoria {
	@Inject
	private RopaService ropaService;
	@Property
	Categoria categoria;
	@Property
	Ropa ropa;

	void onActivate(long categoriaId) throws InstanceNotFoundException {
		categoria = ropaService.recuperarCategoriaPorId(categoriaId);
	}

	public List<Ropa> getListaRopaConCategoria() {

		return ropaService.recuperarListaRopaPorCategoria(categoria.getIdCategoria());
	}

	@Inject
	private AssetSource assetSource;

	@Property
	private ListadoIndex listado;

	public Asset getSignImage() {
		final String path = "Users/Emilio/Dropbox/Facultad/PFC/pojo-app/ ";

		return assetSource.getContextAsset(path, null);
	}

	@Inject
	private PageRenderLinkSource pageLink;

	public List<ListadoIndex> getListadosCategoria() throws InstanceNotFoundException {
		List<Ropa> ropaLista = getListaRopaConCategoria();
		listadoPorCategoria = new ArrayList<ListadoIndex>();

		for (int i = 0; i < ropaLista.size(); i++) {
			long id = ropaLista.get(i).getIdRopa();

			Ropa ropa = ropaService.findRopa(id);

			String nuevo = pageLink.createPageRenderLinkWithContext(MiImagen.class, id).toString();
			ListadoIndex listado2 = new ListadoIndex(id, nuevo, ropa.getNombre(), ropa.getDescripcion());
			listadoPorCategoria.add(listado2);
		}
		this.numeroElementos = listadoPorCategoria.size();
		return listadoPorCategoria;
	}

	public void setListadosCategoria(List<ListadoIndex> listadoPorCategoria) {
		this.listadoPorCategoria = listadoPorCategoria;
	}

	@Property
	private List<ListadoIndex> listadoPorCategoria;

	long onPassivate() {
		return categoria.getIdCategoria();
	}

	private int numeroElementos;

	public boolean getIsMultiPaged() {
		if (this.numeroElementos > 12)
			return true;
		else
			return false;

	}
}
