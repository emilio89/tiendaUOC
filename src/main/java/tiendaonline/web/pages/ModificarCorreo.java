package tiendaonline.web.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.correo.Correo;
import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userservice.UserService;
import tiendaonline.web.services.Private;

@Private
public class ModificarCorreo {
	@SessionState(create = false)
	UserProfile userProfile;
	long idUser1;
	@Inject
	UserService userService;
	@Property
	String titulo;
	@Property
	String asuntoCorreo;

	@Property
	String textoCorreo;

	@SessionState(create = false)
	Correo correo;

	long idFinal;

	@Inject
	AdministradorService administradorService;

	void onActivate(long idCorreo) throws InstanceNotFoundException {
		idFinal = idCorreo;
		correo = administradorService.findCorreo(idCorreo);
	}

	void onPrepareForRender() {
		textoCorreo = correo.getCorreo();
		asuntoCorreo = correo.getAsuntoCorreo();

	}

	Object onSuccess() throws InstanceNotFoundException {
		correo.setAsuntoCorreo(asuntoCorreo);
		correo.setCorreo(textoCorreo);
		administradorService.modificarCorreo(correo.getIdCorreo(), asuntoCorreo, textoCorreo);
		return GestionCorreo.class;
	}

	public Correo getCorreo() {
		return correo;
	}

	public void setCorreo(Correo correo) {
		this.correo = correo;
	}

}
