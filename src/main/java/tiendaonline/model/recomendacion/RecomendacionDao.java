/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiendaonline.model.recomendacion;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.pedido.Pedido;
import tiendaonline.model.ropa.Ropa;

import java.util.List;

/**
 *
 * @author emilio
 */
public interface RecomendacionDao extends GenericDao<Recomendacion, Long> {

	/**
	 * Returns an Recomendacion by idRecomendacion ( Recomendacion identifier)
	 *
	 * @param idRecomentadion
	 *            the Recomendacion identifier
	 * @return the Recomendacion
	 */

	public Recomendacion findByIdsRopa(Ropa idRopa1, Ropa idRopa2) throws InstanceNotFoundException;

	public List<Recomendacion> listaTodasRecomendaciones();

	public void insertarRecomendacion(Recomendacion recomendacion);

	public List<Ropa> listadeIds2(Ropa idRopa1);

	public List<Ropa> listadeIds1(Ropa idRopa2);

	public List<Recomendacion> findidRopaRecomendacion(long idRopa1) throws InstanceNotFoundException;

	public void borrarRecomendacion(long idRecomendacion);

}
