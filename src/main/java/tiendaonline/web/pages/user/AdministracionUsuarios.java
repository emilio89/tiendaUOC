package tiendaonline.web.pages.user;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.categoria.Categoria;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.model.stocktalla.StockTalla;
import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userservice.UserService;
import tiendaonline.web.pages.Administracion;
import tiendaonline.web.pages.Index;
import tiendaonline.web.services.Private;
import tiendaonline.model.adjunto.Adjunto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.tapestry5.SelectModel;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;
import org.apache.tapestry5.upload.services.UploadedFile;

/**
 *
 * @author emilio
 */
@Private
public class AdministracionUsuarios {

	@Property
	private UserProfile userProfile;
	/*
	 * @Inject
	 * 
	 * @Path
	 * ("context:/home/emilio/Dropbox/Facultad/PFC/pojo-app/img/Pantalon_Depor1.png")
	 * private Asset imagen1;
	 */

	@Inject
	private AdministradorService administradorService;

	public List<UserProfile> getListaUsuarios() throws InstanceNotFoundException {
		return administradorService.getListaUsuarios();
	}

	Object onActionFromdarPrivilegios(long userId) throws InstanceNotFoundException {
		administradorService.setPrivilegios("Administrador", userId);

		return AdministracionUsuarios.class;
	}

	Object onActionFromquitarPrivilegios(long userId) throws InstanceNotFoundException {
		administradorService.setPrivilegios(" ", userId);

		return AdministracionUsuarios.class;
	}

}
