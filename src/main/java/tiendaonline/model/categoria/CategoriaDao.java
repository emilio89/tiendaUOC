
package tiendaonline.model.categoria;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.ropa.Ropa;

import java.util.List;

/**
 *
 * @author emilio
 */
public interface CategoriaDao extends GenericDao<Categoria, Long> {
	public Categoria findByIdCategoria(long idCategoria) throws InstanceNotFoundException;

	public List<Categoria> listaCategoria();

	public List<Categoria> listaSubCategoria();

	public List<Categoria> listaCategoriaPadre(long idCategoriaPadre);

	public void borrarCategoria(Categoria categoria);

}
