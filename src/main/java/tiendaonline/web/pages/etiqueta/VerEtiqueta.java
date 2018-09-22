
package tiendaonline.web.pages.etiqueta;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.web.pages.Index;
import tiendaonline.web.services.Private;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author Emilio
 */
@Private
public class VerEtiqueta {

	@Property
	private long idEtiqueta;

	@Property
	private long idEtiqueta2;

	@Property
	private String nombreEtiqueta;

	@SessionState(create = false)
	Etiqueta etiqueta = new Etiqueta();

	@Inject
	AdministradorService administradorService;

	void onActivate(long idEtiqueta) throws InstanceNotFoundException {
		etiqueta = administradorService.findEtiqueta(idEtiqueta);

	}
	
	
	public boolean getHayDescuento (){
		if(etiqueta.getPorcentajeDescuento()!=0)
			return true;
		else
			return false;
			
	}

	void onPrepareForRender() {
		idEtiqueta2 = etiqueta.getIdEtiqueta();
		nombreEtiqueta = etiqueta.getNombreEtiqueta();
	}

	Object onSuccess() throws InstanceNotFoundException {
		administradorService.actualizarEtiqueta(idEtiqueta2, nombreEtiqueta);
		return Index.class;

	}
}
