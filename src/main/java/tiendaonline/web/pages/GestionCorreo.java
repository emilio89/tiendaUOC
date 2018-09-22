package tiendaonline.web.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.correo.Correo;
import tiendaonline.web.services.Private;

@Private
public class GestionCorreo {
	@Property
	private Correo correo;

	@Inject
	private AdministradorService administradorService;

	public List<Correo> getListaCorreos() {
		return administradorService.listaCorreos();

	}

}
