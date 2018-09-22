package tiendaonline.web.pages.ropa;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.categoria.Categoria;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.model.stocktalla.StockTalla;
import tiendaonline.web.pages.Index;
import tiendaonline.web.services.Private;
import tiendaonline.model.adjunto.Adjunto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;
import org.apache.tapestry5.upload.services.UploadedFile;

/**
 *
 * @author emilio
 */
@Private
public class RegistrarRopa {

	@Property
	private String nombre;

	@Property
	private int precio;

	@Property
	private String color;

	@Property
	private String marca;

	@Property
	private String descripcion;

	@Property
	private String nombreAdjunto;

	@Property
	private long idCategoria;

	@Property
	private UploadedFile file;

	private File serverFile;

	/*
	 * @Property private int stock;
	 * 
	 * @Property private String talla;
	 */

	@Persist
	@Property
	private Integer numTallas;

	@Property
	private int columnIndex;

	@Persist
	@Property
	private List<String> inputNombresList;

	@Persist
	@Property
	private List<Integer> inputNumberList;

	@InjectComponent
	private Zone tallasZone;

	/**
	 * Este metodo se llama inmediatamente despues de que se le de valor a
	 * numTallas
	 */
	public Object onValueChanged(Integer numTallas) {
		initInputNumberList(numTallas);
		return tallasZone.getBody();

	}

	/**
	 * Inicializamos inputNumberList para coger los datos del formulario
	 */
	public void initInputNumberList(Integer nTallas) {

		if (inputNumberList == null) {
			inputNumberList = new ArrayList<Integer>();
			inputNombresList = new ArrayList<String>();
		} else {
			inputNumberList.clear();
			inputNombresList.clear();
		}

		for (int i = 0; i < nTallas; i++) {
			inputNumberList.add(new Integer(0));
			inputNombresList.add("Talla " + (i + 1));
			;
		}

	}

	public SelectModel getNumTallasModel() {
		OptionModelImpl uno = new OptionModelImpl("1", new Integer(1));
		OptionModelImpl dos = new OptionModelImpl("2", new Integer(2));
		OptionModelImpl tres = new OptionModelImpl("3", new Integer(3));
		OptionModelImpl cuatro = new OptionModelImpl("4", new Integer(4));
		OptionModelImpl cinco = new OptionModelImpl("5", new Integer(5));
		OptionModelImpl seis = new OptionModelImpl("6", new Integer(6));
		OptionModelImpl siete = new OptionModelImpl("7", new Integer(7));
		OptionModelImpl ocho = new OptionModelImpl("8", new Integer(8));
		OptionModelImpl nueve = new OptionModelImpl("9", new Integer(9));
		OptionModelImpl diez = new OptionModelImpl("10", new Integer(10));
		List<OptionModel> optionsModel = new ArrayList<OptionModel>();
		optionsModel.add(uno);
		optionsModel.add(dos);
		optionsModel.add(tres);
		optionsModel.add(cuatro);
		optionsModel.add(cinco);
		optionsModel.add(seis);
		optionsModel.add(siete);
		optionsModel.add(ocho);
		optionsModel.add(nueve);
		optionsModel.add(diez);
		return new SelectModelImpl(null, optionsModel);

	}

	public boolean beginRender() {

		if (numTallas == null) {
			numTallas = 1;
		}

		if (inputNumberList == null) {
			// Se llama la pantalla por primera vez
			initInputNumberList(numTallas);
		}

		return true;
	}

	public Integer getNumber() {
		return inputNumberList.get(columnIndex);
	}

	public void setNumber(Integer number) {

		// Durante el submit, el indice puede se mayor o igual que el tamaño de
		// la lista, en ese caso ignoramos esos numeros
		if (columnIndex < inputNumberList.size()) {
			inputNumberList.set(columnIndex, number);
		}

	}

	public String getNombreTalla() {
		return inputNombresList.get(columnIndex);
	}

	public void setNombreTalla(String nombreTalla) {

		// Durante el submit, el indice puede se mayor o igual que el tamaño de
		// la lista, en ese caso ignoramos esos numeros
		if (columnIndex < inputNumberList.size()) {
			inputNombresList.set(columnIndex, nombreTalla);
		}

	}

	public int getColumnIndexMasUno() {
		return columnIndex + 1;
	}

	/*
	 * @Inject
	 * 
	 * @Path
	 * ("context:/home/emilio/Dropbox/Facultad/PFC/pojo-app/img/Pantalon_Depor1.png")
	 * private Asset imagen1;
	 */

	@Inject
	private RopaService ropaService;

	@Component
	private Form registrarRopaForm;

	@Property
	Categoria categoria;
	@Inject
	private AdministradorService administradorService;

	@Property
	private SelectModel categoriaSelectModel;

	@Inject
	SelectModelFactory selectModelFactory;

	public List<Categoria> getListaCategoriaRopa() {

		return administradorService.listaSubCategoria();

	}

	void onValidateFromregistrarRopaForm() throws InstanceNotFoundException, SQLException, IOException {

		if (!registrarRopaForm.isValid()) {
			return;
		}
		// File copied = new
		// File("/Users/Emilio/Dropbox/Facultad/PFC/pojo-app/img/" +
		// file.getFileName());
		// file.write(copied);
		Ropa ropa = ropaService.registrarRopa(nombre, precio, color, marca, descripcion, idCategoria);
		File file2 = new File("nada.txt");
		FileUtils.copyInputStreamToFile(file.getStream(), file2);
		byte[] imageData = new byte[(int) file2.length()];
		file.write(file2);
		try {
			FileInputStream fileInputStream = new FileInputStream(file2);
			fileInputStream.read(imageData);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Blob blob1 = new javax.sql.rowset.serial.SerialBlob(imageData);
		blob1.setBytes(1, imageData);
		Adjunto adjunto = ropaService.registrarAdjunto(file.getFileName(), blob1, ropa);
		// Registramos todas las tallas con su stock correspondiente
		for (int i = 0; i < inputNumberList.size(); i++) {
			StockTalla stockTalla = ropaService.registrarStockTalla(inputNombresList.get(i), inputNumberList.get(i),
					ropa);
		}

	}

	Object onSuccessFromregistrarRopaForm() {

		return Index.class;
	}

	// Muestra el SELECT de la lista de categorias, para seleccionar una.
	void setupRender() {

		List<Categoria> categorias = administradorService.listaSubCategoria();
		categoriaSelectModel = selectModelFactory.create(categorias, "nombreCategoria");
	}

}
