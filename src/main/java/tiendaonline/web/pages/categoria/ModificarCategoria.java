/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.pages.categoria;

import tiendaonline.model.categoria.Categoria;
import tiendaonline.model.categoria.CategoriaDao;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.web.services.AuthenticationPolicy;
import tiendaonline.web.services.AuthenticationPolicyType;
import tiendaonline.web.services.Private;

import java.util.List;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Emilio
 */
@Private
public class ModificarCategoria {

	@Property
	Categoria categoria;

	@Inject
	private AdministradorService administradorService;

	public List<Categoria> getListaCategoria() {
		return administradorService.listaCategoria();
	}

	public List<Categoria> getCategorias() {
		return administradorService.listaCategoria();

	}

}
