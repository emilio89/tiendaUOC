/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.pages.categoria;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.categoria.Categoria;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.web.pages.Index;
import tiendaonline.web.services.Private;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author Emilio
 */
@Private
public class VerCategoria {

	@Property
	private long idCategoria;

	@Property
	private long idCategoria2;

	@Property
	private String nombreCategoria;
	@Property
	private long idCategoriaPadre;

	Categoria categoria;

	@Inject
	AdministradorService administradorService;

	void onActivate(long idCategoria) throws InstanceNotFoundException {
		categoria = administradorService.findCategoria(idCategoria);

	}

	void onPrepareForRender() {
		idCategoria2 = categoria.getIdCategoria();
		nombreCategoria = categoria.getNombreCategoria();
		idCategoriaPadre = categoria.getIdCategoriaPadre();
	}

	Object onSuccess() throws InstanceNotFoundException {
		administradorService.actualizarCategoria(idCategoria2, nombreCategoria, idCategoriaPadre);
		return Index.class;

	}
}
