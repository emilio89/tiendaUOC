package tiendaonline.web.pages;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.adjunto.Adjunto;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.model.util.ListadoIndex;
import tiendaonline.web.pages.ropa.MiImagen;

public class Index {

	long id;
	@Property
	private Ropa ropa;

	@Property
	private Adjunto adjunto;

	@Property
	String nombreAdjunto;

	String direccion;

	@Inject
	private RopaService ropaService;

	public String getDireccion() {
		return System.getProperty("user.dir");
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public Blob getBlob1() throws InstanceNotFoundException {
		Blob a = ropaService.recuperarImagen(id);

		return a;
	}

	@Property
	private ListadoIndex listado;

	public String getNombre2(long idRopa) throws InstanceNotFoundException {
		return ropaService.findRopa(idRopa).getNombre();
	}

	public String getDescripcion(long idRopa) throws InstanceNotFoundException {
		return ropaService.findRopa(idRopa).getDescripcion();
	}

	@Inject
	private PageRenderLinkSource pageLink;

	public List<Ropa> listaRopaUltima() {

		return ropaService.listaRopaUltima();
	}

	public List<ListadoIndex> getListados() throws InstanceNotFoundException {
		List<Ropa> ropaLista = listaRopaUltima();
		List<ListadoIndex> auxiliar = new ArrayList<ListadoIndex>();

		for (int i = 0; i < 3; i++) {
			long id = ropaLista.get(i).getIdRopa();

			Ropa ropa = ropaService.findRopa(id);

			String nuevo = pageLink.createPageRenderLinkWithContext(MiImagen.class, id).toString();
			ListadoIndex listado2 = new ListadoIndex(id, nuevo, ropa.getNombre(), Double.toString(ropa.getPrecio()));
			auxiliar.add(listado2);
		}
		return auxiliar;
	}

}
