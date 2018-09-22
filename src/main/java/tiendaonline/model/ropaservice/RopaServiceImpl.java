
package tiendaonline.model.ropaservice;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.categoria.Categoria;
import tiendaonline.model.categoria.CategoriaDao;
import tiendaonline.model.comentario.Comentario;
import tiendaonline.model.comentario.ComentarioDao;
import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.etiqueta.EtiquetaDao;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropa.RopaDao;
import tiendaonline.model.stocktalla.StockTalla;
import tiendaonline.model.stocktalla.StockTallaDao;
import tiendaonline.model.adjunto.Adjunto;
import tiendaonline.model.adjunto.AdjuntoDao;

import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author emilio
 */
@Service("RopaService")
@Transactional
public class RopaServiceImpl implements RopaService {

	@Autowired
	private StockTallaDao stockTallaDao;
	@Autowired
	EtiquetaDao etiquetaDao;
	@Autowired
	private RopaDao ropaDao;
	@Autowired
	private AdjuntoDao adjuntoDao;
	@Autowired
	private CategoriaDao categoriaDao;

	@Autowired
	private ComentarioDao comentarioDao;

	public Ropa registrarRopa(String nombre, int precio, String color, String marca, String descripcion,
			long idCategoria)

	{
		Date dateHoy = new Date();
		Ropa ropa = new Ropa(nombre, precio, color, marca, descripcion, idCategoria, dateHoy);
		// ropaDao.save(ropa);
		ropaDao.anadirRopa(ropa);
		return ropa;
	}

	public Adjunto registrarAdjunto(String nombre, Blob imagen, Ropa ropa) {
		Adjunto adjunto = new Adjunto(nombre, imagen, ropa);
		ropa.addAdjunto(adjunto);
		adjuntoDao.anadirAdjunto(adjunto);
		return adjunto;
	}

	public byte[] verImagen(long idRopa) {
		byte[] devolver = null;

		return devolver;

	}

	public Ropa registrarRopa(Ropa ropa) {
		ropaDao.anadirRopa(ropa);
		ropaDao.save(ropa);
		return ropa;

	}

	@Transactional(readOnly = true)
	public Ropa findRopa(Long idRopa) throws InstanceNotFoundException {
		return ropaDao.find(idRopa);
	}

	@Transactional
	public List<Ropa> listaRopa() {
		return ropaDao.listaRopa();
	}

	public void borrarRopa(Ropa r) {
		ropaDao.deleteRopa(r);

	}

	public Adjunto recuperarAdjunto(Long idRopa) throws InstanceNotFoundException {

		return adjuntoDao.findByIdRopa(idRopa);
	}

	public Blob recuperarImagen(Long idRopa) throws InstanceNotFoundException {

		return recuperarAdjunto(idRopa).getImagen();
	}

	@Transactional(readOnly = true)
	public List<Ropa> listaRopaBusqueda(String aBuscar) {
		return ropaDao.listaRopaBusqueda(aBuscar);
	}

	@Transactional(readOnly = true)
	public List<Ropa> listaRopaBusqueda(String aBuscar, Long idCategoria) throws InstanceNotFoundException {
		return ropaDao.buscarConCategoria(aBuscar, idCategoria);
	}

	@Transactional(readOnly = true)
	public List<Ropa> listaRopaUltima() {
		return ropaDao.listaRopaUltima();
	}

	public void actualizarRopa(long idRopa, String nombre, double precio, String color, String marca,
			String descripcion, int numPuntos, long idCategoria, long idEtiqueta) throws InstanceNotFoundException {
		Ropa ropa = ropaDao.find(idRopa);
		ropa.setNombre(nombre);
		ropa.setPrecio(precio);
		ropa.setColor(color);
		ropa.setMarca(marca);
		ropa.setDescripcion(descripcion);
		ropa.setNumPuntos(numPuntos);
		Date dateHoy = new Date();
		ropa.setFechaRegistro(dateHoy);
		ropa.setIdCategoria(idCategoria);
		ropa.setIdEtiqueta(idEtiqueta);

	}

	public void actualizarEtiquetaRopa(Ropa ropa) throws InstanceNotFoundException {
		ropaDao.actualizarEtiqueta(ropa);
	}

	@Transactional
	public List<Categoria> listaCategoriasPadre(long idCategoriaPadre) {
		return categoriaDao.listaCategoriaPadre(idCategoriaPadre);
	}

	@Transactional
	public Categoria recuperarCategoriaPorId(long id) throws InstanceNotFoundException {

		return categoriaDao.findByIdCategoria(id);
	}

	@Transactional
	public List<Ropa> recuperarListaRopaPorCategoria(long idCategoria) {
		return ropaDao.listaRopaPorCategoria(idCategoria);
	}

	@Transactional
	public List<Ropa> recuperarListaRopaPorEtiqueta(long idEtiqueta) {
		return ropaDao.listaRopaPorEtiqueta(idEtiqueta);
	}

	public Etiqueta findEtiqueta(long idEtiqueta) throws InstanceNotFoundException {

		return etiquetaDao.findById(idEtiqueta);
	}

	/* STOCK TALLA */

	public StockTalla registrarStockTalla(String talla, int stock, Ropa ropa) {

		StockTalla stocktalla = new StockTalla(talla, stock, ropa);
		stockTallaDao.anadirStockTalla(stocktalla);
		ropa.addStockTalla(stocktalla);
		return stocktalla;
	}

	public StockTalla registrarStockTalla(StockTalla stockTalla) {
		stockTallaDao.save(stockTalla);
		return stockTalla;

	}

	@Transactional(readOnly = true)
	public StockTalla findStockTalla(Long idStockTalla) throws InstanceNotFoundException {
		return stockTallaDao.find(idStockTalla);

	}

	@Transactional
	public List<StockTalla> listaStockTalla(long idRopa) {

		return stockTallaDao.listaStockTalla(idRopa);

	}

	public void actualizarStock(long idStockTalla, int unidades) throws InstanceNotFoundException {

		StockTalla sto = stockTallaDao.find(idStockTalla);
		sto.setStock(unidades);

	}

	public void borrarStock(StockTalla stock) {

		stockTallaDao.borrarStock(stock);
	}

	/* ADJUNTO */

	public void borrarAdjunto(Adjunto a) {
		adjuntoDao.borrarAdjunto(a);
	}

	/* COMENTARIO */
	public void borrarComentario(long iDcomentario) throws InstanceNotFoundException {
		comentarioDao.borrarComentario(comentarioDao.find(iDcomentario));
	}

	@Transactional
	public List<Comentario> listaComentario(long idRopa) {
		return comentarioDao.listaComentario(idRopa);

	}

	public Adjunto getAdjunto(long idRopa) throws InstanceNotFoundException {
		return adjuntoDao.findByIdRopa(idRopa);
	}

	public void actualizarAdjunto(long idRopa, String nombre, Blob blob) throws InstanceNotFoundException {
		Adjunto a = adjuntoDao.findByIdRopa(idRopa);
		System.out.println("idRopa " + idRopa);
		System.out.println("adjunto? " + a.getIdAdjunto());
		a.setNombreAdjunto(nombre);
		a.setImagen(blob);
		adjuntoDao.save(a);
	}
}
