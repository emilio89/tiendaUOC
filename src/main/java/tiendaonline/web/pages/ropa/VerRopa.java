/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.pages.ropa;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.comentario.Comentario;
import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.categoria.Categoria;
import tiendaonline.model.pedidoservice.PedidoService;
import tiendaonline.model.recomendacion.Recomendacion;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.model.stocktalla.StockTalla;
import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userservice.UserService;
import tiendaonline.model.util.ListadoIndex;
import tiendaonline.web.pages.Index;
import tiendaonline.model.adjunto.Adjunto;
import tiendaonline.web.util.Carrito;
import tiendaonline.web.util.LineaCarrito;
import tiendaonline.web.util.UserSession;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.SelectModelFactory;

/**
 *
 * @author Emilio
 */
public class VerRopa {

	@Property
	private Adjunto adjunto;
	long id;
	Ropa ropa;
	Etiqueta etiqueta;
	UserProfile userProfile;
	@Property
	Comentario comentario;
	@Property
	Recomendacion recomendacion;
	@Property
	StockTalla stockTalla;

	@Property
	private long idStockTalla;

	@Inject
	private RopaService ropaService;

	@Property
	private SelectModel stockTallaSelectModel;

	@Inject
	SelectModelFactory selectModelFactory;

	@Inject
	private AdministradorService administradorService;

	@Component
	Form anadirCarritoForm;

	@SessionState(create = false)
	private UserSession userSession;
	@Inject
	private UserService userService;

	@Inject
	private PedidoService pedidoService;

	@Property
	@Persist(PersistenceConstants.FLASH)
	private String textAreaValue;

	@Component
	private Form comentarioForm;

	@SessionState(create = false)
	private Carrito carrito = new Carrito();

	private InputStream blob1;
	// private Blob blob2;

	public Ropa getRopa() {
		return ropa;
	}

	public void setRopa(Ropa ropa) {
		this.ropa = ropa;
	}

	public Etiqueta getEtiqueta() {

		return etiqueta;
	}

	public boolean gethayEtiqueta() {
		if (etiqueta == null)
			return false;
		if (etiqueta.getIdEtiqueta() == 0)
			return false;
		else
			return true;
	}

	public void setEtiqueta(Etiqueta etiqueta) {
		this.etiqueta = etiqueta;
	}

	public long getAccountContext() {

		return ropa.getIdRopa();

	}

	void onActivate(long ropaId) throws InstanceNotFoundException {
		ropa = ropaService.findRopa(ropaId);
		etiqueta = administradorService.findEtiqueta(ropa.getIdEtiqueta());
	}

	void onActivate(String ropaId) throws InstanceNotFoundException {
	}

	long onPassivate() {

		return ropa.getIdRopa();
	}

	public boolean getLogueado() {

		if (userSession != null)
			return true;
		else
			return false;
	}

	Object onValidateFromcomentarioForm() throws InstanceNotFoundException {
		if (!comentarioForm.isValid()) {
			return VerRopa.class;
		}
		userProfile = userService.findUserProfile(userSession.getUserProfileId());
		userService.registrarComentario(textAreaValue, userProfile, ropa);
		return VerRopa.class;

	}

	void onValidateFromanadirCarritoForm() throws InstanceNotFoundException {
		if (!anadirCarritoForm.isValid()) {
			return;
		}
		LineaCarrito lineaCarrito2 = new LineaCarrito(ropa.getPrecio(), ropa, idStockTalla);
		carrito.anadirProducto(lineaCarrito2);
	}

	Object onSuccess() {
		return Index.class;
	}

	void setupRender() {
		List<StockTalla> stockTallas = ropaService.listaStockTalla(ropa.getIdRopa());
		stockTallaSelectModel = selectModelFactory.create(stockTallas, "talla");
	}

	public List<Comentario> getListaComentarios() throws InstanceNotFoundException {
		long idrop = ropa.getIdRopa();
		return ropaService.listaComentario(idrop);
	}

	public List<Recomendacion> getListaRecomendaciones() throws InstanceNotFoundException {
		return pedidoService.listaRecomendaciones();
	}

	public Blob getBlob1() throws InstanceNotFoundException {
		Blob a = ropaService.recuperarImagen(ropa.getIdRopa());
		return a;
	}

	public StreamResponse onReturnStreamResponse() {
		return new StreamResponse() {
			InputStream inputStream;

			@Override
			public void prepareResponse(Response response) {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				try {
					inputStream = getBlob1().getBinaryStream();
				} catch (InstanceNotFoundException ex) {
					Logger.getLogger("FALLA inputStream1" + VerRopa.class.getName()).log(Level.SEVERE, null, ex);
				} catch (SQLException ex) {
					Logger.getLogger("FALLA inputStream1" + VerRopa.class.getName()).log(Level.SEVERE, null, ex);
				}
				try {
					response.setHeader("Content-Length", "" + inputStream.available());
				} catch (IOException e) {
					// Ignore the exception in this simple example.
				}
			}

			@Override
			public String getContentType() {
				return "png/Image";
			}

			@Override
			public InputStream getStream() throws IOException {
				return inputStream;

			}
		};
	}

	@Inject
	private PageRenderLinkSource pageLink;

	public Link getImageLink() {
		return pageLink.createPageRenderLinkWithContext(MiImagen.class, ropa.getIdRopa());
	}

	public List<Recomendacion> getListaRecomendacionesPorId() throws InstanceNotFoundException {

		return pedidoService.listaRecomendaciones(ropa.getIdRopa());

	}

	@Property
	private ListadoIndex listado;

	public List<ListadoIndex> getListados() throws InstanceNotFoundException {
		List<Recomendacion> listaRecom = this.getListaRecomendacionesPorId();
		List<ListadoIndex> auxiliar = new ArrayList<ListadoIndex>();
		for (int i = 0; i < listaRecom.size(); i++) {
			Ropa id2 = listaRecom.get(i).getIdRopa1();
			Ropa id3 = listaRecom.get(i).getIdRopa2();
			if (id2 != ropa) {
				String nuevo = pageLink.createPageRenderLinkWithContext(MiImagen.class, id2.getIdRopa()).toString();
				ListadoIndex listado2 = new ListadoIndex(id2.getIdRopa(), nuevo, this.getRopa().getNombre(), 0);
				auxiliar.add(listado2);
			}
			if (id3 != ropa) {
				String nuevo = pageLink.createPageRenderLinkWithContext(MiImagen.class, id3.getIdRopa()).toString();
				ListadoIndex listado2 = new ListadoIndex(id3.getIdRopa(), nuevo, this.getRopa().getNombre(), 0);
				auxiliar.add(listado2);

			}
		}
		return auxiliar;
	}

	public boolean getHayPuntos() {
		if (ropa.getNumPuntos() > 0)
			return true;
		else
			return false;
	}

	public Categoria getCategoria() throws InstanceNotFoundException {
		return administradorService.findCategoria(ropa.getIdCategoria());
	}

}
