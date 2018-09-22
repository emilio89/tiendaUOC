/********************* EMILIO DOMÍNGUEZ SACHEZ *********************/

package tiendaonline.model.ropa;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

import java.util.List;

/**
 *
 * @author Emilio
 */
public interface RopaDao extends GenericDao<Ropa, Long> {

	/**
	 * Returns an Ropa by idRopa ( Ropa identifier)
	 *
	 * @param idRopa
	 *            the user identifier
	 * @return the Ropa
	 */

	public Ropa findByidRopa(long idRopa) throws InstanceNotFoundException;

	public List<Ropa> listaRopa();

	public void anadirRopa(Ropa ropa);

	public void actualizarEtiqueta(Ropa ropa) throws InstanceNotFoundException;

	public List<Ropa> listaRopaBusqueda(String aBuscar);

	public List<Ropa> listaRopaPorCategoria(long idCategoria);

	public List<Ropa> listaRopaPorEtiqueta(long idEtiqueta);

	public List<Ropa> buscarConCategoria(String aBuscar, long idCategoria);

	public List<Ropa> listaRopaUltima();

	public void deleteRopa(Ropa ropa);

}
