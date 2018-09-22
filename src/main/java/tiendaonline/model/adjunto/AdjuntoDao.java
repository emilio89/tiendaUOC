/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiendaonline.model.adjunto;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

/**
 *
 * @author emilio
 */
public interface AdjuntoDao extends GenericDao<Adjunto, Long> {
	/**
	 * Returns an Adjunto by nombreAdjunto ( not identifier)
	 *
	 * @param nombreAdjunto
	 * @return Adjunto
	 */

	public Adjunto findByIdRopa(Long idRopa) throws InstanceNotFoundException;

	public void anadirAdjunto(Adjunto adjunto);

	public void borrarAdjunto(Adjunto a);
}