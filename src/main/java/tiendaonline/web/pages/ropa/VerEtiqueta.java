/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.pages.ropa;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.administradorservice.AdministradorService;
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
public class VerEtiqueta {
	@Inject
	private AdministradorService administradorService;
	@Inject
	private RopaService ropaService;
	@Property
	Etiqueta etiqueta;
	@Property
	Ropa ropa;

	void onActivate(long idEtiqueta) throws InstanceNotFoundException {
		etiqueta = administradorService.findEtiqueta(idEtiqueta);
	}

	public List<Ropa> getListaRopaConEtiqueta() {

		return ropaService.recuperarListaRopaPorEtiqueta(etiqueta.getIdEtiqueta());
	}

	@Inject
	private AssetSource assetSource;

	@Property
	private ListadoIndex listado;

	public Asset getSignImage() {
		final String path = "Users/Emilio/Dropbox/Facultad/PFC/pojo-app/ ";

		return assetSource.getContextAsset(path, null);
	}

	public boolean getHayDescuento (){
		if(etiqueta.getPorcentajeDescuento()!=0)
			return true;
		else
			return false;
			
	}

	@Inject
	private PageRenderLinkSource pageLink;

	@Property
	private List<ListadoIndex> listadoRopaPorEtiqueta;

	public List<ListadoIndex> getListadosEtiqueta() throws InstanceNotFoundException {
		List<Ropa> ropaLista = getListaRopaConEtiqueta();
		listadoRopaPorEtiqueta = new ArrayList<ListadoIndex>();

		for (int i = 0; i < ropaLista.size(); i++) {
			long id = ropaLista.get(i).getIdRopa();

			Ropa ropa = ropaService.findRopa(id);

			String nuevo = pageLink.createPageRenderLinkWithContext(MiImagen.class, id).toString();
			ListadoIndex listado2 = new ListadoIndex(id, nuevo, ropa.getNombre(), ropa.getDescripcion());
			listadoRopaPorEtiqueta.add(listado2);
		}

		this.numeroElementos = listadoRopaPorEtiqueta.size();
		return listadoRopaPorEtiqueta;
	}

	public void setListadosEtiqueta(List<ListadoIndex> listadoRopaPorEtiqueta) {
		this.listadoRopaPorEtiqueta = listadoRopaPorEtiqueta;
	}

	long onPassivate() {
		return etiqueta.getIdEtiqueta();
	}

	private int numeroElementos;

	public boolean getIsMultiPaged() {
		if (this.numeroElementos > 12)
			return true;
		else
			return false;

	}

}
