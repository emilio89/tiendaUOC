package tiendaonline.model.categoriaservice;

import static tiendaonline.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static tiendaonline.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.categoria.Categoria;
import tiendaonline.model.ropaservice.RopaService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class CategoriaServiceTest {


	@Autowired
	private AdministradorService categoriaService;		
	
	
	@Test
	public void testCrearCategoria() throws InstanceNotFoundException {

		Random rand = new Random();
		String categoriaName = "CategoriaPrueba-" + rand.nextInt(9999);
        Categoria categoria = categoriaService.crearCategoria(categoriaName, 1);

        Categoria aux = categoriaService.findCategoria(categoria.getIdCategoria());

        /* Check data. */
        assertEquals(categoria, aux);

	}

	@Test
	public void testFindCategoria() throws InstanceNotFoundException {
		Random rand = new Random();
		String categoriaName = "CategoriaPrueba-" + rand.nextInt(9999);
        Categoria categoria = categoriaService.crearCategoria(categoriaName, 1);

        Categoria aux = categoriaService.findCategoria(categoria.getIdCategoria());

        /* Check data. */
        assertEquals(categoria, aux);
	}


	@Test
	public void testActualizarCategoria() throws InstanceNotFoundException {
		Random rand = new Random();
		String categoriaName = "CategoriaPrueba-" + rand.nextInt(9999);
        Categoria categoria = categoriaService.crearCategoria(categoriaName, 1);

        categoriaService.actualizarCategoria(categoria.getIdCategoria(), categoria.getNombreCategoria()+"MOD", categoria.getIdCategoriaPadre());
        
        Categoria aux2 = categoriaService.findCategoria(categoria.getIdCategoria());
        
        aux2 = categoriaService.findCategoria(categoria.getIdCategoria());        
        
        /* Check data. */
        assertEquals(categoria.getNombreCategoria(), aux2.getNombreCategoria());
	}
	
}
