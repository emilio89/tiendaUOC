package tiendaonline.web.pages.blog;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.entradablog.EntradaBlog;
import tiendaonline.web.services.Private;

@Private
public class ModificarEntradaBlog {

	@Property
	private String titulo;
	@Property
	private String resumen;
	@Property
	private String texto;

	EntradaBlog entradaBlog;

	@Inject
	AdministradorService administradorService;

	void onActivate(long idEntradaBlog) throws InstanceNotFoundException {
		entradaBlog = administradorService.findEntradaBlog(idEntradaBlog);

	}

	void onPrepareForRender() {
		titulo = entradaBlog.getTitulo();
		resumen = entradaBlog.getResumen();
		texto = entradaBlog.getTexto();
	}

}
