/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiendaonline.model.comentario;

import es.udc.pojo.modelutil.dao.GenericDao;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.ropa.Ropa;
import java.util.List;

/**
 *
 * @author emilio
 */
public interface ComentarioDao extends GenericDao<Comentario, Long> {
	/**
	 * Returns an Comentario by idComentario ( not identifier)
	 *
	 * @param idComentario
	 * @return Comentario
	 */
	
	public void anadirComentario(Comentario comentario);

	public List<Comentario> listaComentario();

	public List<Comentario> listaComentario(long idRopa);

	public void borrarComentario(Comentario comentario);

}
