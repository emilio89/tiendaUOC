package tiendaonline.web.pages.etiqueta;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.web.pages.Index;
import tiendaonline.web.services.AuthenticationPolicy;
import tiendaonline.web.services.AuthenticationPolicyType;

/**
 * 
 * @author Emilio
 */
@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class CrearEtiqueta {

	@Property
	private String nombreEtiqueta;
	@Property
	private int porcentajeDescuento;
	@Component
	private Form crearEtiquetaForm;

	@Inject
	private AdministradorService administradorService;

	void onValidateFromcrearEtiquetaForm() {

		if (!crearEtiquetaForm.isValid()) {
			return;
		}

		Etiqueta etiqueta = administradorService.registrarEtiqueta(nombreEtiqueta, porcentajeDescuento);

	}

	Object onSuccess() {

		return Index.class;

	}

}