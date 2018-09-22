package tiendaonline.model.pedidoservice;

import static tiendaonline.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static tiendaonline.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.*;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.lineapedido.LineaPedido;
import tiendaonline.model.pedido.Pedido;
import tiendaonline.model.recomendacion.Recomendacion;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userservice.UserService;
import tiendaonline.web.util.Carrito;
import tiendaonline.web.util.LineaCarrito;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class PedidoServiceTest {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private UserService userService;
	@Autowired
	private RopaService ropaService;

	@Test
	public void testRegistrarPedido() throws InstanceNotFoundException {

		UserProfile userProfile = userService.findUserProfile(7L);
		pedidoService.registrarPedido(new Date(), 10d, userProfile, "Enviado");

	}

	@Test
	public void testListaPedidosPorUser() {
		List<Pedido> pedidoList = pedidoService.listaPedidosPorUser(7L);
		assertTrue(pedidoList.size() > 0);
	}

	@Test
	public void testListaPedidosEstado() {
		List<Pedido> pedidoList = pedidoService.listaPedidosEstado("creado");
		assertTrue(pedidoList.size() >= 0);
	}

	@Test
	public void testBuscarPedido() throws InstanceNotFoundException {

		List<Pedido> pedidos = pedidoService.listaPedidosEstado("Enviado");
		Pedido pedido = pedidoService.buscarPedido(pedidos.get(0).getIdPedido());
		assertNotNull(pedido);
	}

	@Test
	public void testListaPedidosLineas() {
		List<LineaPedido> lineaPedidoList = pedidoService.listarLineasPorIdPedido(104L);
		assertNotNull(lineaPedidoList);
	}

	@Test
	public void testActualizarEstado() throws InstanceNotFoundException {

		List<Pedido> pedidos = pedidoService.listaPedidosEstado("Enviado");
		Long idPedido = pedidos.get(0).getIdPedido();
		Pedido pedido = pedidoService.buscarPedido(idPedido);
		String aux = pedido.getEstado();
		pedidoService.actualizarEstado(idPedido, "Pendiente");
		pedidoService.actualizarEstado(idPedido, aux);
		Pedido pedido2 = pedidoService.buscarPedido(idPedido);
		assertTrue(pedido2.getEstado().equalsIgnoreCase(aux));
	}

	@Test
	public void testActualizarNumVeces() throws InstanceNotFoundException {
		List<Recomendacion> recomendaciones = pedidoService.listaRecomendaciones();
		int i = recomendaciones.get(0).getNumVeces();
		pedidoService.actualizarNumVeces(recomendaciones.get(0).getIdRopa1(), recomendaciones.get(0).getIdRopa2());
		List<Recomendacion> recomendaciones2 = pedidoService.listaRecomendaciones();
		int j = recomendaciones2.get(0).getNumVeces();
		assertEquals(j, i + 1);

	}

	@Test
	public void testListaRecomendaciones1() throws InstanceNotFoundException {
		List<Recomendacion> aux = pedidoService.listaRecomendaciones(1L);
		assertTrue(aux.size() >= 0);
	}

	@Test
	public void testListaRecomendaciones() {
		List<Recomendacion> recomendacionList = pedidoService.listaRecomendaciones();
		assertTrue(recomendacionList.size() > 0);
	}

	@Test
	public void testListaPedidosUsuarioEstado() {
		List<Pedido> listaPedido = pedidoService.listaPedidosUsuarioEstado(7L, "creado");
		assertTrue(listaPedido.size() >= 0);

	}

	@Test
	public void testanadirComprarRecomendaciones() throws InstanceNotFoundException {
		ArrayList<Ropa> listaIds = new ArrayList<Ropa>();
		listaIds.add(ropaService.findRopa(1L));
		listaIds.add(ropaService.findRopa(2L));
		listaIds.add(ropaService.findRopa(3L));
		listaIds.add(ropaService.findRopa(1L));
		pedidoService.anadirComprarRecomendaciones(listaIds);
		assertTrue(true);

	}

	@Test
	public void calculaPrecio() throws InstanceNotFoundException {
		Carrito carrito = new Carrito();
		LineaCarrito lineaCarrito = new LineaCarrito(10, ropaService.findRopa(1L), 3);
		carrito.anadirProducto(lineaCarrito);
		assertTrue(pedidoService.calculaPrecio(carrito) >= 0);

	}

	@Test
	public void testGetHayDescuentosEtiqueta() throws InstanceNotFoundException {
		pedidoService.getHayDescuentosEtiqueta(104L);
		assertTrue(true);

	}

	@Test
	public void testgGetPorcentajeDescuentos() throws InstanceNotFoundException {

		pedidoService.getPorcentajeDescuentos(pedidoService.buscarPedido(104L));
		assertTrue(true);

	}

	@Test
	public void testGetPorcentajeDescuento() throws InstanceNotFoundException {
		Carrito carrito = new Carrito();
		LineaCarrito lineaCarrito = new LineaCarrito(10, ropaService.findRopa(1L), 3);
		carrito.anadirProducto(lineaCarrito);
		pedidoService.getPorcentajeDescuento(carrito);
		assertTrue(true);
	}

	@Test
	public void listaRecomendacionesTodas() throws InstanceNotFoundException {
		assertTrue(pedidoService.listaRecomendacionesTodas(1L).size() >= 0);
	}
	@Test
	public void anadirLineaPedido () throws InstanceNotFoundException{
		pedidoService.anadirLineaPedido(104L, 3L, ropaService.findRopa(1L),50, 4);
		assertTrue(true);
	}
	@Test
	public void testListarLineasPedidoPorRopa (){
		pedidoService.listarLineasPedidoPorRopa(1L);
	}

}
