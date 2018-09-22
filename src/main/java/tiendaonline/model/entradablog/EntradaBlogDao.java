package tiendaonline.model.entradablog;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public interface EntradaBlogDao extends GenericDao<EntradaBlog, Long> {

	public EntradaBlog findByIdEntradaBlog(long idEntradaBlog) throws InstanceNotFoundException;

	public void anadirEntradaBlog(EntradaBlog entradaBlog);

	public List<EntradaBlog> listaEntradaBlog();

	public List<EntradaBlog> listaEntradaBlog3();

	public void borrarEntradaBlog(EntradaBlog entrada);
}
