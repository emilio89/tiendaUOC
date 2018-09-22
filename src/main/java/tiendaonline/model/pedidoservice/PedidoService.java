/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.model.pedidoservice;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.lineapedido.LineaPedido;
import tiendaonline.model.pedido.Pedido;
import tiendaonline.model.recomendacion.Recomendacion;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.web.util.Carrito;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.StreamResponse;

/**
 *
 * @author Emilio
 */
public interface PedidoService {

	public Pedido registrarPedido(Date ahora, double precioFinal, UserProfile userProfile, String creado);

	public List<Pedido> listaPedidosPorUser(long idUser);

	public Pedido buscarPedido(long idPedido) throws InstanceNotFoundException;

	public void actualizarEstado(long idPedido, String estado) throws InstanceNotFoundException;

	public void actualizarNumVeces(Ropa id1, Ropa id2) throws InstanceNotFoundException;

	public List<Recomendacion> listaRecomendaciones();

	public List<Recomendacion> listaRecomendaciones(long idRopa1) throws InstanceNotFoundException;

	public List<Recomendacion> listaRecomendacionesTodas(long idRopa1) throws InstanceNotFoundException;

	public List<Pedido> listaPedidosEstado(String estado);

	public List<Pedido> listaPedidosUsuarioEstado(long idUser, String estado);

	/* LINEA PEDIDO */

	public void anadirLineaPedido(long idPedido, long idStockTalla, Ropa idRopa, double PrecioFinal, int cantidad)
			throws InstanceNotFoundException;

	public List<LineaPedido> listarLineasPorIdPedido(long idPedido);

	public boolean getHayDescuentosEtiqueta(long idPedido) throws InstanceNotFoundException;

	public List<String> getPorcentajeDescuento(Carrito carrito) throws InstanceNotFoundException;

	public double calculaPrecio(Carrito carrito) throws InstanceNotFoundException;

	public void anadirComprarRecomendaciones(ArrayList<Ropa> listaIds) throws InstanceNotFoundException;

	public List<String> getPorcentajeDescuentos(Pedido pedido) throws InstanceNotFoundException;

	public List<LineaPedido> listarLineasPedidoPorRopa(long idRopa);

	public void borrarPedido(long idPedido);

	public void borrarLineaPedido(long idLineaPedido);

	public void borrarRecomendacion(long idRecomendacion);

}
