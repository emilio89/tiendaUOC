/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.pages.ropa;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.web.pages.Index;
import tiendaonline.web.services.Private;

import java.sql.SQLException;
import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

/**
 *
 * @author emilio
 */
@Private

public class AsignarEtiqueta {
	@Inject
	private RopaService ropaService;

	@Inject
	private AdministradorService administradorService;

	@Component
	private Form asignarEtiquetaForm;

	Ropa ropa;

	@Property
	SelectModel etiquetaSelectModel;

	@Inject
	SelectModelFactory selectModelFactory;

	// Property
	// Etiqueta etiqueta;

	@Property
	long idEtiqueta;

	public Ropa getRopa() {
		return ropa;
	}

	public void setRopa(Ropa ropa) {
		this.ropa = ropa;
	}

	void onActivate(long ropaId) throws InstanceNotFoundException {
		ropa = ropaService.findRopa(ropaId);
	}

	long onPassivate() {

		return ropa.getIdRopa();
	}

	void onValidateFromAsignarEtiquetaForm() throws InstanceNotFoundException, SQLException {

		if (!asignarEtiquetaForm.isValid()) {
			return;
		}

		Ropa ropaAux = ropaService.findRopa(ropa.getIdRopa());
		ropaAux.setIdEtiqueta(idEtiqueta);
		ropaService.actualizarEtiquetaRopa(ropaAux);

	}

	Object onSuccess() {

		return Index.class;

	}

	void setupRender() {
		List<Etiqueta> etiquetas = administradorService.listaEtiqueta();
		etiquetaSelectModel = selectModelFactory.create(etiquetas, "nombreEtiqueta");
	}

}
