package tiendaonline.web.pages.blog;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.userservice.UserService;
import tiendaonline.web.pages.Administracion;
import tiendaonline.web.services.Private;
import tiendaonline.web.util.UserSession;

@Private
public class CrearEntradaBlog {

	@Property
	private String tituloEntrada;
	@Property
	private String resumenEntrada;
	@Property
	private String textoEntrada;

	@Component
	private Form crearEntradaForm;
	@Inject
	private AdministradorService administradorService;
	@SessionState(create = false)
	private UserSession userSession;

	void onValidateFromcrearEntradaForm() throws InstanceNotFoundException {
		administradorService.anadirEntradaBlog(userSession.getUserProfileId(), tituloEntrada, resumenEntrada,
				textoEntrada);
	}

	Object onSuccessFromcrearEntradaForm() {

		return Administracion.class;
	}

}
