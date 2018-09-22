/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiendaonline.model.ropaservice;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.categoria.Categoria;
import tiendaonline.model.comentario.Comentario;
import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.stocktalla.StockTalla;
import tiendaonline.model.adjunto.Adjunto;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emilio
 */
public interface RopaService {

	public Ropa registrarRopa(String nombre, int precio, String color, String marca, String descripcion,
			long idCategoria);

	public Ropa findRopa(Long idRopa) throws InstanceNotFoundException;

	public void actualizarRopa(long idRopa, String nombre, double precio, String color, String marca,
			String descripcion, int numPuntos, long idCategoria, long idEtiqueta) throws InstanceNotFoundException;

	public void actualizarEtiquetaRopa(Ropa ropa) throws InstanceNotFoundException;

	public List<Ropa> listaRopa();

	public Adjunto recuperarAdjunto(Long idRopa) throws InstanceNotFoundException;

	public void borrarRopa(Ropa r);

	public Adjunto registrarAdjunto(String nombre, Blob imagen, Ropa ropa);

	public Blob recuperarImagen(Long idRopa) throws InstanceNotFoundException;

	public List<Ropa> listaRopaBusqueda(String aBuscar);

	public List<Ropa> listaRopaBusqueda(String aBuscar, Long idCategoria) throws InstanceNotFoundException;

	public Categoria recuperarCategoriaPorId(long id) throws InstanceNotFoundException;

	public List<Ropa> recuperarListaRopaPorCategoria(long idCategoria);

	public List<Ropa> recuperarListaRopaPorEtiqueta(long idEtiqueta);

	public Etiqueta findEtiqueta(long idEtiqueta) throws InstanceNotFoundException;

	public List<Categoria> listaCategoriasPadre(long idCategoriaPadre);

	public List<Ropa> listaRopaUltima();

	/* STOCK TALLA */
	public StockTalla registrarStockTalla(String talla, int stock, Ropa ropa);

	public StockTalla findStockTalla(Long idStockTalla) throws InstanceNotFoundException;

	public List<StockTalla> listaStockTalla(long idRopa);

	public void actualizarStock(long idStockTalla, int unidades) throws InstanceNotFoundException;

	public void borrarStock(StockTalla stock);

	/* ADJUNTO */

	public void borrarAdjunto(Adjunto a);

	/* BORRAR COMENTARIOS */
	public void borrarComentario(long iDcomentario) throws InstanceNotFoundException;

	public List<Comentario> listaComentario(long idRopa);

	public void actualizarAdjunto(long idRopa, String nombre, Blob blob) throws InstanceNotFoundException;

}
