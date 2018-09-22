package tiendaonline.model.etiqueta;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.comentario.Comentario;
import tiendaonline.model.stocktalla.StockTalla;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

/**
 * 
 * @author Emilio
 */
@Repository("EtiquetaDao")
public class EtiquetaDaoHibernate extends GenericDaoHibernate<Etiqueta, Long>
		implements EtiquetaDao {

	public Etiqueta findByNombreEtiqueta(String nombreEtiqueta)
			throws InstanceNotFoundException {

		Query query = getSession().createQuery(
						"SELECT u.idEtiqueta, u.nombreEtiqueta,  u.porcentajeDescuento FROM Etiqueta u WHERE u.nombreEtiqueta = :nombreEtiqueta")
				.setParameter("nombreEtiqueta", nombreEtiqueta);
		List<Etiqueta> lista = convertToEtiquetaList(query.list());
		if (lista.isEmpty()) {
			throw new InstanceNotFoundException(nombreEtiqueta,
					Etiqueta.class.getName());
		} else {
			return lista.get(0);
		}

	}

	public Etiqueta findById(Long idEtiqueta)
			throws InstanceNotFoundException {

    	Etiqueta etiqueta = (Etiqueta) getSession().createQuery(
    			"SELECT u FROM Etiqueta u WHERE u.idEtiqueta = :idEtiqueta")
    			.setParameter("idEtiqueta", idEtiqueta)
    			.uniqueResult();
    	if (etiqueta == null) {   			
			//throw new InstanceNotFoundException(idEtiqueta,
			//		Etiqueta.class.getName());
			return findById(1L);
    	} else {
    		return etiqueta;
    	}
	}	
	
	@Autowired
	private SessionFactory sessionFactory;

	public List<Etiqueta> listaEtiqueta() {

		Query query = getSession().createQuery(
				"SELECT  u.idEtiqueta, u.nombreEtiqueta, u.porcentajeDescuento FROM Etiqueta u");
		List<Etiqueta> lista = convertToEtiquetaList(query.list());
		return lista;

	}


	public List<Etiqueta> listaEtiquetasColecciones() {
																		
		Query query = getSession().createQuery( 						
				"SELECT u.idEtiqueta, u.nombreEtiqueta, u.porcentajeDescuento FROM Etiqueta u where u.idEtiqueta <> 0 AND u.porcentajeDescuento <= 0");
		List<Etiqueta> lista = convertToEtiquetaList(query.list());
		return lista;

	}	

	public List<Etiqueta> listaEtiquetasDescuento() {
																	
		Query query = getSession().createQuery( 					
				"SELECT u.idEtiqueta, u.nombreEtiqueta, u.porcentajeDescuento FROM Etiqueta u where u.idEtiqueta <> 0 AND u.porcentajeDescuento > 0");
		List<Etiqueta> lista = convertToEtiquetaList(query.list());
		return lista;

	}	
	
	public List<Etiqueta> listaEtiquetasMenu() {

		Query query = getSession().createQuery( 
				"SELECT u.idEtiqueta, u.nombreEtiqueta, u.porcentajeDescuento FROM Etiqueta u WHERE u.idEtiqueta > 1");
		List<Etiqueta> lista = convertToEtiquetaList(query.list());
		return lista;

	}	
	
	public void anadirEtiqueta(Etiqueta etiqueta) {

		sessionFactory.getCurrentSession().save(etiqueta);

	}

	public int insertEtiqueta(Etiqueta etiqueta) {

		Query query = getSession().createSQLQuery(
				"insert into Etiqueta(nombreEtiqueta,porcentajeDescuento) values " + "('"
						+ etiqueta.getNombreEtiqueta() +etiqueta.getPorcentajeDescuento()+ "')");
		return query.executeUpdate();
	}

	private List<Etiqueta> convertToEtiquetaList(List<Object[]> lista) {
		List<Etiqueta> nuevaLista = new ArrayList<Etiqueta>();
		for (int i = 0; i < lista.size(); i++) {
			nuevaLista.add(new Etiqueta(Long.parseLong(lista.get(i)[0]
					.toString()),(String) lista.get(i)[1], Integer.parseInt(lista.get(i)[2]
							.toString())));
		}
		return nuevaLista;
	}

	@Override
	public void actualizaEtiqueta(Etiqueta etiqueta) throws InstanceNotFoundException {
		Query query = getSession().createQuery("update Etiqueta set nombreEtiqueta = '"+etiqueta.getNombreEtiqueta()+"' " +
		 " where idEtiqueta = "+etiqueta.getIdEtiqueta()+"");
		int result = query.executeUpdate();
		if (result!=1) {
			throw new InstanceNotFoundException(etiqueta.getIdEtiqueta(),Etiqueta.class.getName());
		} 	
	}

	
	public void borrarEtiqueta (Etiqueta etiqueta) {
		sessionFactory.getCurrentSession().delete(etiqueta);

	}
	

}
