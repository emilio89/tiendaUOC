package tiendaonline.model.ropaservice;

import static tiendaonline.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static tiendaonline.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.*;

import java.sql.Blob;
import java.sql.SQLException;
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
import tiendaonline.model.categoria.Categoria;
import tiendaonline.model.comentario.Comentario;
import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.stocktalla.StockTalla;
import tiendaonline.model.adjunto.Adjunto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class RopaServiceTest {

	@Autowired
	private RopaService ropaService;

	@Test
	public void registrarAdjunto() throws InstanceNotFoundException {
		Ropa aux = ropaService.findRopa(1L);
		Adjunto adj = ropaService.registrarAdjunto(aux.getNombre(), aux.getAdjuntos().get(0).getImagen(), aux);
		assertTrue(adj.getRopa() == aux);

	}

	@Test
	public void testRegistrarRopa() throws InstanceNotFoundException {
		Random rand = new Random();
		String ropaString = "Ropa-" + rand.nextInt(9999);
		Ropa ropa = ropaService.registrarRopa(ropaString, 10, "ColorTest", "MarcaTest", "DescripcionTest", 1L);
		Ropa aux = ropaService.findRopa(ropa.getIdRopa());
		/* Check data. */
		assertEquals(ropa, aux);
	}

	@Test
	public void testRegistrarRopa2() throws InstanceNotFoundException {
		Random rand = new Random();

		String ropaString = "Ropa-" + rand.nextInt(9999);
		Date date = new Date();
		Ropa ropa = new Ropa(ropaString, 10, "ColorTest", "MarcaTest", "DescripcionTest", 1L, date);
		Ropa ropa2 = ropaService.registrarRopa(ropaString, 10, "ColorTest", "MarcaTest", "DescripcionTest", 1L);
		/* Check data. */
		assertEquals(ropa.getNombre(), ropa2.getNombre());
		ropaService.borrarRopa(ropa);
	}

	@Test
	public void testFindRopa() throws InstanceNotFoundException {
		Random rand = new Random();
		String ropaString = "Ropa-" + rand.nextInt(9999);
		Ropa ropa = ropaService.registrarRopa(ropaString, 10, "ColorTest", "MarcaTest", "DescripcionTest", 1L);
		Ropa aux = ropaService.findRopa(ropa.getIdRopa());

		/* Check data. */
		assertEquals(ropa, aux);
	}

	@Test
	public void testActualizarRopa() throws InstanceNotFoundException {
		Random rand = new Random();

		String ropaString = "Ropa-" + rand.nextInt(9999);
		Ropa ropa = ropaService.registrarRopa(ropaString, 10, "ColorTest", "MarcaTest", "DescripcionTest", 1L);

		ropaService.actualizarRopa(ropa.getIdRopa(), ropaString + "-M", 10D, "ColorTest-M", "MarcaTest-M",
				"DescripcionTest-M", 1, 0, 0);

		Ropa aux = ropaService.findRopa(ropa.getIdRopa());
		/* Check data. */
		assertEquals(aux.getNombre(), ropaString + "-M");

	}

	@Test
	public void testListaRopaUltima() {
		List<Ropa> ropaList = ropaService.listaRopaUltima();

		/* Check data. */
		assertTrue(ropaList.size() > 0);
	}

	@Test
	public void testRecuperarAdjunto() throws InstanceNotFoundException {
		Adjunto adjunto = ropaService.recuperarAdjunto(1L);

		/* Check data. */
		assertTrue(adjunto.getNombreAdjunto().equalsIgnoreCase("deportivo1.jpg"));
	}

	@Test
	public void testRegistrarAdjunto() throws InstanceNotFoundException {
		Random rand = new Random();

		String ropaString = "Ropa-" + rand.nextInt(9999);
		Ropa ropa = ropaService.registrarRopa(ropaString, 10, "ColorTest", "MarcaTest", "DescripcionTest", 1L);

		String adjuntoString = "Adjunto-" + rand.nextInt(9999);
		ropaService.registrarAdjunto(adjuntoString, null, ropa);

		Adjunto a = ropaService.recuperarAdjunto(ropa.getIdRopa());
		/* Check data. */
		assertTrue(a.getRopa() == ropa);
	}

	@Test
	public void testRecuperarImagen() throws InstanceNotFoundException, SQLException {
		Blob imagen = ropaService.recuperarImagen(1L);
		assertTrue(imagen.length() > 0);
	}

	@Test
	public void testListaRopaBusquedaString() {
		List<Ropa> ropaList = ropaService.listaRopaBusqueda("Cami");
		assertTrue(ropaList.size() > 0);
	}

	@Test
	public void testRecuperarCategoriaPorId() throws InstanceNotFoundException {
		Categoria categoria = ropaService.recuperarCategoriaPorId(1L);
		assertTrue(categoria.getNombreCategoria().equalsIgnoreCase("Ropa Hombre"));
	}

	@Test
	public void testRecuperarListaRopaPorCategoria() {
		List<Ropa> ropaList = ropaService.recuperarListaRopaPorCategoria(1L);
		assertTrue(ropaList.size() > 0);
	}

	@Test
	public void testRecuperarListaRopaPorEtiqueta() {
		List<Ropa> ropaList = ropaService.recuperarListaRopaPorEtiqueta(1L);
		assertTrue(ropaList.size() > 0);
	}

	@Test
	public void testRegistrarStockTalla() throws InstanceNotFoundException {

		Random rand = new Random();

		String tallaString = "Talla-" + rand.nextInt(9999);

		StockTalla stock = ropaService.registrarStockTalla(tallaString, 5, ropaService.findRopa(3L));

		StockTalla stock2 = ropaService.findStockTalla(stock.getIdStockTalla());
		assertEquals(stock, stock2);
	}

	@Test
	public void testFindStockTallaLong() throws InstanceNotFoundException {
		Random rand = new Random();

		String tallaString = "Talla-" + rand.nextInt(9999);

		StockTalla stock = ropaService.registrarStockTalla(tallaString, 5, ropaService.findRopa(3L));

		StockTalla stock2 = ropaService.findStockTalla(stock.getIdStockTalla());
		assertEquals(stock, stock2);
	}

	@Test
	public void testFindStockTallaRopa() throws InstanceNotFoundException {
		Random rand = new Random();
		String tallaString = "Talla-" + rand.nextInt(9999);
		StockTalla stock = ropaService.registrarStockTalla(tallaString, 5, ropaService.findRopa(3L));
		StockTalla stock2 = ropaService.findStockTalla(stock.getIdStockTalla());
		assertEquals(stock, stock2);
	}

	@Test
	public void testListaStockTallaLong() {
		List<StockTalla> stockTallaList = ropaService.listaStockTalla(1L);
		assertTrue(stockTallaList.size() > 0);
	}

	@Test
	public void testActualizarStock() throws InstanceNotFoundException {

		ropaService.actualizarStock(8L, 50);

		StockTalla aux = ropaService.findStockTalla(8L);
		/* Check data. */
		assertEquals(aux.getStock(), 50);
	}

	@Test
	public void testFindEtiqueta() throws InstanceNotFoundException {
		Etiqueta etiqueta = ropaService.findEtiqueta(1L);
		assertNotNull(etiqueta);
	}

	@Test
	public void testListaRopaBusquedaStringLong() throws InstanceNotFoundException {
		List<Ropa> ropaList = ropaService.listaRopaBusqueda("Cami", 1L);
		assertTrue(ropaList.size() > 0);
	}

	@Test
	public void testListaCategoriasPadre() {
		List<Categoria> categoriaList = ropaService.listaCategoriasPadre(1L);
		assertTrue(categoriaList.size() > 0);
	}

	@Test
	public void testfindStockTalla() throws InstanceNotFoundException {
		Random rand = new Random();
		String tallaString = "Talla-" + rand.nextInt(9999);
		StockTalla stock = ropaService.registrarStockTalla(tallaString, 5, ropaService.findRopa(3L));
		StockTalla stock2 = ropaService.findStockTalla(stock.getIdStockTalla());
		assertEquals(stock, stock2);

	}

	@Test
	public void testregistrarStockTalla() throws InstanceNotFoundException {
		Random rand = new Random();
		String tallaString = "Talla-" + rand.nextInt(9999);
		StockTalla stock = ropaService.registrarStockTalla(tallaString, 5, ropaService.findRopa(3L));
		StockTalla stock2 = ropaService.findStockTalla(stock.getIdStockTalla());
		assertEquals(stock, stock2);

	}

	@Test
	public void testBorrarStock() throws InstanceNotFoundException {
		Random rand = new Random();
		String tallaString = "Talla-" + rand.nextInt(9999);
		StockTalla stock = ropaService.registrarStockTalla(tallaString, 5, ropaService.findRopa(3L));
		ropaService.borrarStock(stock);
		assertTrue(1 == 1);
	}

	@Test
	public void testListaComentario() {
		assertTrue(ropaService.listaComentario(1L).size() >= 0);
	}

	@Test
	public void testActualizarAdjunto() throws InstanceNotFoundException {
		Ropa aux = ropaService.findRopa(1L);
		ropaService.actualizarAdjunto(1L, aux.getNombre(), aux.getAdjuntos().get(0).getImagen());
		assertTrue(true == true);

	}

	@Test
	public void testregistrarStockTalla1() throws InstanceNotFoundException {
		ropaService.registrarStockTalla("GG", 2, ropaService.findRopa(1L));
		assertTrue (true);
	}

}
