/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.pages.categoria;

import tiendaonline.model.categoria.Categoria;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.web.pages.Index;
import tiendaonline.web.services.AuthenticationPolicy;
import tiendaonline.web.services.AuthenticationPolicyType;
import tiendaonline.web.services.Private;

import java.util.List;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

/**
 *
 * @author Emilio
 */
@Private
public class CrearCategoria {

	@Property
	private String nombreCategoria;

	@Property
	private int idCategoriaPadre;

	@Component
	private Form crearCategoriaForm;

	@Inject
	private AdministradorService administradorService;

	void onValidateFromcrearCategoriaForm() {

		if (!crearCategoriaForm.isValid()) {
			return;
		}

		Categoria categoria = administradorService.crearCategoria(nombreCategoria, idCategoriaPadre);

	}

	Object onSuccess() {

		return Index.class;

	}

	@Property
	private SelectModel categoriaSelectModel;

	@Inject
	SelectModelFactory selectModelFactory;

	void setupRender() {
		// invoke my service to find all descuentos, e.g. in the database
		List<Categoria> categorias = administradorService.listaCategoria();

		// create a SelectModel from my list of descuentos
		categoriaSelectModel = selectModelFactory.create(categorias, "nombreCategoria");
	}

}