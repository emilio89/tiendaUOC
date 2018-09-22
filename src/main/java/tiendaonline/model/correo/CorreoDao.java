package tiendaonline.model.correo;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public interface CorreoDao extends GenericDao<Correo, Long> {

	public Correo findByIdCorreo(long idCorreo) throws InstanceNotFoundException;

	public void modificarCorreo(Correo correo);

	public List<Correo> listaCorreo();

}
