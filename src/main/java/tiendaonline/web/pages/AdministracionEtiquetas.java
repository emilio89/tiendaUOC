package tiendaonline.web.pages;

/**
 *
 * @author Emilio
 */

import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.web.services.AuthenticationPolicy;
import tiendaonline.web.services.AuthenticationPolicyType;
import tiendaonline.web.services.Private;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

@Private
public class AdministracionEtiquetas {

	@Property
	Etiqueta etiqueta;

	@Inject
	private AdministradorService administradorService;

	public List<Etiqueta> getListaEtiqueta() {
		return administradorService.listaEtiqueta();
	}

	public List<Etiqueta> getEtiquetas() {
		return administradorService.listaEtiqueta();

	}

}
