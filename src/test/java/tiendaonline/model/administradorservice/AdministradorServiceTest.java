package tiendaonline.model.administradorservice;

import static tiendaonline.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static tiendaonline.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;

import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.categoria.Categoria;
import tiendaonline.model.categoria.CategoriaDao;
import tiendaonline.model.comentario.Comentario;
import tiendaonline.model.correo.Correo;
import tiendaonline.model.entradablog.EntradaBlog;
import tiendaonline.model.entradablog.EntradaBlogDao;
import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userprofile.UserProfileDao;
import tiendaonline.model.userservice.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class AdministradorServiceTest {

	@Autowired
	private UserService userService;
	@Autowired
	private AdministradorService administradorService;

	@Test
	public void testSetPrivilegios() throws InstanceNotFoundException {
		UserProfile userProfile2 = userService.findUserProfile(7L);
		administradorService.setPrivilegios("privilegio", userProfile2.getUserProfileId());
		assertEquals("privilegio", userProfile2.getTipoUsuario());

	}

	@Test
	public void testanadirEntradaBlog() throws InstanceNotFoundException {
		Random rand = new Random();
		String titulo = "TituloEntrada" + rand.nextInt(999);
		String resumen = "ResumenEntrada" + rand.nextInt(999);
		String texto = "TextoEntrada" + rand.nextInt(999);
		EntradaBlog entrada = administradorService.anadirEntradaBlog(7L, titulo, resumen, texto);
		assertEquals(administradorService.findEntradaBlog(entrada.getIdEntradaBlog()), entrada);
		administradorService.borrarEntradaBlog(entrada.getIdEntradaBlog());
	}

	@Test
	public void testListaEntradasBlog() {
		List<EntradaBlog> entradasList = administradorService.listaEntradasBlog();
		assertTrue(entradasList.size() >= 0);

	}

	@Test
	public void testlistaEntradasBlog3() {
		List<EntradaBlog> entradasList = administradorService.listaEntradasBlog3();
		assertTrue(entradasList.size() > 0);

	}

	@Test
	public void testlistaEtiquetasMenu() {

		List<Etiqueta> etiq = administradorService.listaEtiquetasMenu();
		assertTrue(etiq.size() > 0);
	}

	@Test
	public void testlistaEtiquetasColecciones() {

		List<Etiqueta> etiq = administradorService.listaEtiquetasColecciones();
		assertTrue(etiq.size() > 0);
	}

	@Test
	public void testlistaEtiquetasDescuento() {

		List<Etiqueta> etiq = administradorService.listaEtiquetasDescuento();
		assertTrue(etiq.size() > 0);
	}

	@Test
	public void testgetListaUsuarios() throws InstanceNotFoundException {

		List<UserProfile> users = administradorService.getListaUsuarios();
		assertTrue(users.size() > 0);
	}

	@Test
	public void testregistrarEtiqueta() throws InstanceNotFoundException {

		Random rand = new Random();
		String nombreEtiqueta = "nombreEtiqueta-" + rand.nextInt(9999);
		Etiqueta etiqueta = administradorService.registrarEtiqueta(nombreEtiqueta, rand.nextInt(9999));
		Etiqueta etiqueta2 = administradorService.findEtiqueta(etiqueta.getIdEtiqueta());
		assertEquals(etiqueta, etiqueta2);
		administradorService.borrarEtiqueta(etiqueta);

	}

	@Test
	public void testregistrarEtiqueta2() throws InstanceNotFoundException {
		Random rand = new Random();
		int porcentaje = rand.nextInt(9999);
		String nombreEtiqueta = "nombreEtiqueta-" + porcentaje;
		Etiqueta eti = new Etiqueta(nombreEtiqueta, rand.nextInt(9999));
		Etiqueta etiqueta = administradorService.registrarEtiqueta(nombreEtiqueta, porcentaje);
		Etiqueta etiqueta2 = administradorService.findEtiqueta(etiqueta.getIdEtiqueta());
		assertEquals(etiqueta, etiqueta2);
		administradorService.borrarEtiqueta(etiqueta);

	}

	@Test
	public void testactualizarEtiqueta() throws InstanceNotFoundException {

		Random rand = new Random();
		String nombreEtiqueta = "nombreEtiqueta-" + rand.nextInt(9999);
		Etiqueta etiqueta = administradorService.registrarEtiqueta(nombreEtiqueta, rand.nextInt(9999));
		administradorService.actualizarEtiqueta(etiqueta.getIdEtiqueta(), nombreEtiqueta);
		Etiqueta etiqueta2 = administradorService.findEtiqueta(etiqueta.getIdEtiqueta());
		assertEquals(etiqueta, etiqueta2);
		administradorService.borrarEtiqueta(etiqueta);

	}

	@Test
	public void testfindEtiqueta() throws InstanceNotFoundException {

		Random rand = new Random();
		String nombreEtiqueta = "nombreEtiquetaFind-" + rand.nextInt(9999);
		Etiqueta etiqueta = administradorService.registrarEtiqueta(nombreEtiqueta, rand.nextInt(9999));
		Etiqueta etiqueta2 = administradorService.findEtiqueta(etiqueta.getIdEtiqueta());
		assertEquals(etiqueta, etiqueta2);
		administradorService.borrarEtiqueta(etiqueta);

	}

	@Test
	public void testfindEtiqueta2() throws InstanceNotFoundException {
		Random rand = new Random();
		String nombreEtiqueta = "nombreEtiquetaFind2-" + rand.nextInt(9999);
		Etiqueta etiqueta = administradorService.registrarEtiqueta(nombreEtiqueta, rand.nextInt(9999));
		etiqueta.setNombreEtiqueta(nombreEtiqueta);
		Etiqueta etiqueta2 = administradorService.findEtiqueta(etiqueta.getIdEtiqueta());
		assertEquals(etiqueta, etiqueta2);
		administradorService.borrarEtiqueta(etiqueta);

	}

	@Test
	public void testlistaEtiqueta() {

		List<Etiqueta> etiquetas = administradorService.listaEtiqueta();
		assertTrue(etiquetas.size() > 0);

	}

	@Test
	public void testcrearCategoria() throws InstanceNotFoundException {
		Random rand = new Random();
		String nombreCategoria = "nombreCategoriaFind2-" + rand.nextInt(9999);
		Categoria categoria = administradorService.crearCategoria(nombreCategoria, rand.nextInt(9999));
		Categoria categoria2 = administradorService.findCategoria(categoria.getIdCategoria());
		assertEquals(categoria, categoria2);
		administradorService.borrarCategoria(categoria);

	}

	@Test
	public void findCategoria() throws InstanceNotFoundException {
		Random rand = new Random();
		String nombreCategoria = "nombreCategoriaFind2-" + rand.nextInt(9999);
		Categoria categoria = administradorService.crearCategoria(nombreCategoria, rand.nextInt(9999));
		Categoria categoria2 = administradorService.findCategoria(categoria.getIdCategoria());
		assertEquals(categoria, categoria2);
		administradorService.borrarCategoria(categoria);

	}

	@Test
	public void listaCategoria() {

		assertTrue(administradorService.listaCategoria().size() > 0);

	}

	@Test
	public void listaSubCategoria() {
		assertTrue(administradorService.listaCategoria().size() > 0);
	}

	@Test
	public void actualizarCategoria() throws InstanceNotFoundException {
		Random rand = new Random();
		String nombreCategoria = "nombreCategoriaFind2-" + rand.nextInt(9999);
		Categoria categoria = administradorService.crearCategoria(nombreCategoria, rand.nextInt(9999));
		categoria.setNombreCategoria(nombreCategoria);
		categoria.setIdCategoriaPadre(2);
		Categoria categoria2 = administradorService.findCategoria(categoria.getIdCategoria());
		assertEquals(categoria, categoria2);
		administradorService.borrarCategoria(categoria);

	}
	@Test
	public void listaCorreos() {
		assertTrue(administradorService.listaCorreos().size() > 0);
	}
	
	@Test
	public void modificarCorreo() throws InstanceNotFoundException {
		Random rand = new Random();
		String nombreCategoria = "nombreCategoriaFind2-" + rand.nextInt(9999);
		administradorService.modificarCorreo(1L, nombreCategoria, nombreCategoria);
		assertTrue(administradorService.findCorreo(1L).getAsuntoCorreo() == nombreCategoria);

	}


}
