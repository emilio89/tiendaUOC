
package tiendaonline.model.etiqueta;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

import java.util.List;

/**
 *
 * @author Emilio
 */
public interface EtiquetaDao extends GenericDao<Etiqueta, Long> {

	/**
	 * Returns an Etiqueta by nombreEtiqueta ( not Etiqueta identifier)
	 *
	 * @param nombreEtiqueta
	 *            nomber de la etiqueta
	 * @param porcentajeDescuento
	 *            porcentaje del descuento asociado
	 * @return the Etiqueta
	 */

	public Etiqueta findByNombreEtiqueta(String nombreEtiqueta) throws InstanceNotFoundException;

	public Etiqueta findById(Long id) throws InstanceNotFoundException;

	public List<Etiqueta> listaEtiqueta();

	public List<Etiqueta> listaEtiquetasMenu();

	public List<Etiqueta> listaEtiquetasColecciones();

	public List<Etiqueta> listaEtiquetasDescuento();

	public void actualizaEtiqueta(Etiqueta etiqueta) throws InstanceNotFoundException;

	public void anadirEtiqueta(Etiqueta etiqueta);

	public int insertEtiqueta(Etiqueta etiqueta);

	public void borrarEtiqueta(Etiqueta etiqueta);

}
