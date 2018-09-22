/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.pages.ropa;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.categoria.Categoria;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.web.pages.Index;
import tiendaonline.web.services.Private;
import tiendaonline.model.adjunto.Adjunto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;
import org.apache.tapestry5.upload.services.UploadedFile;

/**
 *
 * @author Emilio
 */
@Private

public class ModificarRopa {

	@Property
	private long idRopa;

	@Property
	private String nombre;

	@Property
	private double precio;

	@Property
	private String color;
	@Property
	private String marca;

	@Property
	private String descripcion;
	@Property
	private int numPuntos;

	@Property
	private long idCategoria;

	@SessionState(create = false)
	Ropa ropa;

	@Inject
	RopaService ropaService;

	long idFinal;

	@Property
	long idEtiqueta;

	@Property
	private SelectModel categoriaSelectModel;
	@Inject
	SelectModelFactory selectModelFactory;
	@Property
	SelectModel etiquetaSelectModel;
	@Inject
	private AdministradorService administradorService;

	@Property
	private UploadedFile file;

	private Adjunto adjunto;

	@Inject
	SelectModelFactory selectModelFactory2;

	public List<Categoria> getListaCategoriaRopa() {

		return administradorService.listaSubCategoria();

	}

	void onActivate(long idRopa) throws InstanceNotFoundException {
		idFinal = idRopa;
		ropa = ropaService.findRopa(idRopa);
	}

	void onPrepareForRender() {
		idRopa = ropa.getIdRopa();
		nombre = ropa.getNombre();
		precio = ropa.getPrecio();
		color = ropa.getColor();
		marca = ropa.getMarca();
		descripcion = ropa.getDescripcion();
		numPuntos = ropa.getNumPuntos();

	}

	Object onSuccess() throws InstanceNotFoundException, IOException, SQLException {
		if (file != null) {
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
			if (blob1 != null) {
				blob1.setBytes(1, imageData);
				ropaService.actualizarAdjunto(ropa.getIdRopa(), file.getFileName(), blob1);
			}
		}
		ropaService.actualizarRopa(ropa.getIdRopa(), nombre, precio, color, marca, descripcion, numPuntos, idCategoria,
				idEtiqueta);

		return Index.class;

	}

	void setupRender() {

		List<Categoria> categorias = administradorService.listaSubCategoria();
		categoriaSelectModel = selectModelFactory.create(categorias, "nombreCategoria");
		List<Etiqueta> etiquetas = administradorService.listaEtiqueta();
		etiquetaSelectModel = selectModelFactory2.create(etiquetas, "nombreEtiqueta");

	}

}
