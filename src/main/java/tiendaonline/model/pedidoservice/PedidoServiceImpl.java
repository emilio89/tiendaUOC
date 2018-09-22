/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.model.pedidoservice;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.etiqueta.EtiquetaDao;
import tiendaonline.model.lineapedido.LineaPedido;
import tiendaonline.model.lineapedido.LineaPedidoDao;
import tiendaonline.model.pedido.Pedido;
import tiendaonline.model.pedido.PedidoDao;
import tiendaonline.model.recomendacion.Recomendacion;
import tiendaonline.model.recomendacion.RecomendacionDao;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropa.RopaDao;
import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.web.util.Carrito;
import tiendaonline.web.util.LineaCarrito;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Emilio
 */
@Service("PedidoService")
@Transactional
public class PedidoServiceImpl implements PedidoService {
	@Autowired
	LineaPedidoDao lineaPedidoDao;
	@Autowired
	private PedidoDao pedidoDao;
	@Autowired
	private RecomendacionDao recomendacionDao;
	@Autowired
	RopaDao ropaDao;
	@Autowired
	EtiquetaDao etiquetaDao;

	public Pedido registrarPedido(Date ahora, double precioFinal, UserProfile userProfile, String creado) {
		Pedido pedido = new Pedido(ahora, precioFinal, userProfile, creado);
		pedidoDao.anadirPedido(pedido);
		return pedido;
	}

	@Transactional
	public List<Pedido> listaPedidosEstado(String estado) {

		return pedidoDao.listaPedidosEstado(estado);

	}

	@Transactional
	public List<Pedido> listaPedidosUsuarioEstado(long idUser, String estado) {

		return pedidoDao.listaPedidosUsuarioEstado(idUser, estado);

	}

	@Transactional
	public List<Pedido> listaPedidosPorUser(long idUser) {

		return pedidoDao.listaPedidosUsuario(idUser);

	}

	@Transactional(readOnly = true)
	public Pedido buscarPedido(long idPedido) throws InstanceNotFoundException {

		return pedidoDao.find(idPedido);
	}

	public void actualizarEstado(long idPedido, String estado) throws InstanceNotFoundException {
		Pedido ped = pedidoDao.find(idPedido);
		ped.setEstado(estado);
	}

	public List<Recomendacion> listaRecomendaciones() {

		return recomendacionDao.listaTodasRecomendaciones();

	}

	public Ropa findIdRopa1(Ropa idRopa) {

		List<Recomendacion> lista = listaRecomendaciones();
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getIdRopa1() == idRopa) {
				return lista.get(i).getIdRopa1();
			}

		}
		return null;
	}

	public Ropa findIdRopa2(Ropa idRopa) {

		List<Recomendacion> lista = listaRecomendaciones();
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getIdRopa2() == idRopa) {
				return lista.get(i).getIdRopa2();
			}
		}
		return null;
	}

	public List<Ropa> ids1(Ropa idRopa2) {
		return recomendacionDao.listadeIds1(idRopa2);
	}

	public List<Ropa> ids2(Ropa idRopa1) {
		return recomendacionDao.listadeIds2(idRopa1);
	}

	public void actualizarNumVeces(Ropa id1, Ropa id2) throws InstanceNotFoundException {

		Recomendacion re = recomendacionDao.findByIdsRopa(id1, id2);
		re.setNumVeces(re.getNumVeces() + 1);
	}

	public void insertarPedidoService(Ropa id1, Ropa id2) {
		Recomendacion re = new Recomendacion(id1, id2, 1);
		recomendacionDao.insertarRecomendacion(re);
	}

	public List<Recomendacion> listaRecomendaciones(long idRopa1) throws InstanceNotFoundException {
		List<Recomendacion> aux = recomendacionDao.findidRopaRecomendacion(idRopa1);

		return aux; // . recomendacionDao.findidRopa2(idRopa1);

	}

	public List<Recomendacion> listaRecomendacionesTodas(long idRopa1) throws InstanceNotFoundException {
		List<Recomendacion> aux = recomendacionDao.findidRopaRecomendacion(idRopa1);

		return aux; // . recomendacionDao.findidRopa2(idRopa1);

	}

	public void anadirLineaPedido(long idPedido, long idStockTalla, Ropa idRopa, double PrecioFinal, int cantidad)
			throws InstanceNotFoundException {

		Pedido pedido = pedidoDao.find(idPedido);
		lineaPedidoDao.anadirLineaPedido(pedido, idStockTalla, idRopa, PrecioFinal, cantidad);

	}

	public List<LineaPedido> listarLineasPorIdPedido(long idPedido) {
		return lineaPedidoDao.listarLineasPedido(idPedido);

	}

	public boolean getHayDescuentosEtiqueta(long idPedido) throws InstanceNotFoundException {
		boolean devolver = false;
		Pedido pedido = this.buscarPedido(idPedido);
		for (int i = 0; i < pedido.getLineaPedidos().size(); i++) {
			double precioUnitario = pedido.getLineaPedidos().get(i).getPrecioUnitario();
			Ropa ropa = ropaDao.findByidRopa(pedido.getLineaPedidos().get(i).getIdRopa().getIdRopa());
			if (precioUnitario == (ropa.getPrecio() * pedido.getLineaPedidos().get(i).getNumeroUnidades()))
				devolver = false;
			else
				return true;
		}
		return false;
	}


	public double calculaPrecio(Carrito carrito) throws InstanceNotFoundException {

		double precio = 0;
		double precio2 = 0;
		List<LineaCarrito> productos = carrito.getProductos();

		Iterator i = productos.iterator();
		double precioFinal = 0;
		while (i.hasNext()) {

			LineaCarrito linea = (LineaCarrito) i.next();
			if (etiquetaDao.findById(linea.getRopa().getIdEtiqueta()) != null) {
				Etiqueta et = etiquetaDao.findById(linea.getRopa().getIdEtiqueta());
				precio2 = linea.getPrecio() - (linea.getPrecio() * et.getPorcentajeDescuento()) / 100;
				precio = precio + precio2;
			} else

				precio = precio + linea.getPrecio();

		}
		return precio;
	}

	public List<String> getPorcentajeDescuento(Carrito carrito) throws InstanceNotFoundException {

		List<String> descuento = new ArrayList();
		List<LineaCarrito> productos = carrito.getProductos();
		Iterator i = productos.iterator();
		LineaCarrito lineaCarrito = null;
		while (i.hasNext()) {
			LineaCarrito linea = (LineaCarrito) i.next();
			Etiqueta et = etiquetaDao.findById(linea.getRopa().getIdEtiqueta());
			if (et.getPorcentajeDescuento() != 0) {
				double precioFinal = linea.getPrecio() - (linea.getPrecio() * et.getPorcentajeDescuento()) / 100;
				descuento.add("El producto " + linea.getRopa().getNombre() + " tiene un descuento de "
						+ et.getPorcentajeDescuento() + "% por lo que su precio pasa de " + linea.getPrecio() + " a "
						+ precioFinal);
			}
		}
		return descuento;
	}

	public void anadirComprarRecomendaciones(ArrayList<Ropa> listaIds) throws InstanceNotFoundException {
		if (listaIds.size() > 1) {
			for (int i = 0; i < listaIds.size(); i++) {
				Ropa id = this.findIdRopa1(listaIds.get(i));
				Ropa id2 = this.findIdRopa2(listaIds.get(i));
				if (id != null) {
					List<Ropa> ids2 = this.ids2(listaIds.get(i));
					// lo encuentra en idRopa1
					for (int j = i; j < listaIds.size(); j++) {
						// recorrer la lista buscando solo en idRopa2
						if (ids2.contains(listaIds.get(j))) {
							this.actualizarNumVeces(listaIds.get(i), listaIds.get(j));
						} else {
							if (listaIds.get(i) != listaIds.get(j))
								this.insertarPedidoService(listaIds.get(i), listaIds.get(j));
						}

					}
				} else if (id2 != null) {
					// lo encuentra en idRopa2
					List<Ropa> ids1 = this.ids1(id2);
					for (int z = i; z < listaIds.size(); z++) {
						// Recorrer la lista buscando solo en idRopa1
						if (ids1.contains(listaIds.get(z))) {
							this.actualizarNumVeces(listaIds.get(z), id2);
						} else {
							if (listaIds.get(z) != listaIds.get(i))
								this.insertarPedidoService(listaIds.get(z), listaIds.get(i));
						}
					}
				} else if (id == null & id2 == null) {
					// no está en idropa1 ni en idropa2
					// por lo que hai q añadir ese id, con el resto a
					// recomendaciones.
					// añadir listaIds.get(i) en id1 con
					// HACER YA, PARA INSERTAR RECOMENDACION Y PARA ACUALIZAR
					// NUMVECES
					// Ropa ropa1 = ropaService.findRopa(id);
					for (int k = 0; k < listaIds.size(); k++) {
						if (listaIds.get(i) != listaIds.get(k)) {
							this.insertarPedidoService(listaIds.get(i), listaIds.get(k));
						}
					}
				}
			}

		}
	}

	public List<String> getPorcentajeDescuentos(Pedido pedido) throws InstanceNotFoundException {
		List<String> descuentos = new ArrayList();
		if (this.getHayDescuentosEtiqueta(pedido.getIdPedido())) {
			for (int i = 0; i < pedido.getLineaPedidos().size(); i++) {
				Ropa ropa = pedido.getLineaPedidos().get(i).getIdRopa();
				Etiqueta et = etiquetaDao.findById(ropa.getIdEtiqueta());
				if (et.getPorcentajeDescuento() != 0)
					descuentos.add("El producto " + ropa.getNombre() + " tiene un descuento del "
							+ et.getPorcentajeDescuento() + " % ");

			}

		}

		return descuentos;
	}

	public List<LineaPedido> listarLineasPedidoPorRopa(long idRopa) {
		return lineaPedidoDao.listarLineasPedidoPorRopa(idRopa);
	}

	public void borrarPedido(long idPedido) {
		pedidoDao.borrarPedido(idPedido);
	}

	public void borrarLineaPedido(long idLineaPedido) {
		lineaPedidoDao.borrarLineaPedido(idLineaPedido);

	}

	public void borrarRecomendacion(long idRecomendacion) {
		recomendacionDao.borrarRecomendacion(idRecomendacion);
	}
	

	
	
}
