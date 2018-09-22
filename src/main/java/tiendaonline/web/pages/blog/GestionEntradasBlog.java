package tiendaonline.web.pages.blog;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.entradablog.EntradaBlog;
import tiendaonline.web.services.Private;

@Private
public class GestionEntradasBlog {
	@Property
	private EntradaBlog entradablog;

	@Inject
	private AdministradorService administradorService;

	public List<EntradaBlog> getListaEntradasBlog() {
		return administradorService.listaEntradasBlog();

	}

}
