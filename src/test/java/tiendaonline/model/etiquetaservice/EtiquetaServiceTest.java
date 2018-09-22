package tiendaonline.model.etiquetaservice;

import static tiendaonline.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static tiendaonline.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.ropaservice.RopaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class EtiquetaServiceTest {

	@Autowired
	private AdministradorService etiquetaService;
	
	@Test
	public void testRegistrarEtiqueta() throws InstanceNotFoundException {
		Random rand = new Random();

		String etiquetaString = "EtiquetaPrueba-" + rand.nextInt(9999);
		Etiqueta etiqueta = etiquetaService.registrarEtiqueta(etiquetaString,1);

		Etiqueta aux = etiquetaService.findEtiqueta(etiquetaString);

        /* Check data. */
        assertEquals(etiqueta.getNombreEtiqueta(), aux.getNombreEtiqueta());

	}

	@Test
	public void testFindEtiquetaLong() throws InstanceNotFoundException {
		Random rand = new Random();
		
		String etiquetaString = "EtiquetaPrueba-" + rand.nextInt(9999);
		Etiqueta etiqueta = etiquetaService.registrarEtiqueta(etiquetaString,1);

		Etiqueta aux = etiquetaService.findEtiqueta(etiquetaString);

        /* Check data. */
        assertEquals(etiqueta.getNombreEtiqueta(), aux.getNombreEtiqueta());
	}

	@Test
	public void testFindEtiquetaString() throws InstanceNotFoundException {

		Random rand = new Random();
		
		String etiquetaString = "EtiquetaPrueba-" + rand.nextInt(9999);
		Etiqueta etiqueta = etiquetaService.registrarEtiqueta(etiquetaString,1);
		
		Etiqueta aux = etiquetaService.findEtiqueta(etiquetaString);
		
		assertEquals(etiqueta.getNombreEtiqueta(),aux.getNombreEtiqueta());
	}

	@Test
	public void testListaEtiqueta() {
		List<Etiqueta> etiquetaList = etiquetaService.listaEtiqueta();
		
		assertTrue(etiquetaList.size()>0);
	}

}
