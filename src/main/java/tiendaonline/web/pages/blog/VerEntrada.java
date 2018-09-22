package tiendaonline.web.pages.blog;

import java.util.Date;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.entradablog.EntradaBlog;
import tiendaonline.model.userservice.UserService;
import tiendaonline.web.util.UserSession;

public class VerEntrada {
	@Property
	private String titulo;
	@Property
	private String resumen;
	@Property
	private String texto;
	@Property
	private String usuario;
	@Property
	private Date fechaCreacion;
	EntradaBlog entradaBlog;

	@Inject
	AdministradorService administradorService;
	@Inject
	UserService userService;

	void onActivate(long idEntradaBlog) throws InstanceNotFoundException {
		entradaBlog = administradorService.findEntradaBlog(idEntradaBlog);

	}

	void onPrepareForRender() throws InstanceNotFoundException {
		usuario = entradaBlog.getIdUser().getFirstName();
		titulo = entradaBlog.getTitulo();
		resumen = entradaBlog.getResumen();
		texto = entradaBlog.getTexto();
		fechaCreacion = entradaBlog.getFechaCreacion();
	}

}
