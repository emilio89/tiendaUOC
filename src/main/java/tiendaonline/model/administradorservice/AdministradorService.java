/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.model.administradorservice;

import java.util.List;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.categoria.Categoria;
import tiendaonline.model.correo.Correo;
import tiendaonline.model.entradablog.EntradaBlog;
import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.userprofile.UserProfile;

/**
 *
 * @author Emilio
 */
public interface AdministradorService {

	/* CATEGORIA */
	public Categoria crearCategoria(String nombreCategoria, int idCategoriaPadre);

	public Categoria findCategoria(Long idCategoria) throws InstanceNotFoundException;

	public void borrarCategoria(Categoria categoria);

	public List<Categoria> listaCategoria();

	public List<Categoria> listaSubCategoria();

	public void actualizarCategoria(long idCategoria, String nombreCategoria, long idCategoriaPadre)
			throws InstanceNotFoundException;

	/* ETIQUETA */
	public Etiqueta registrarEtiqueta(String nombreEtiqueta, int porcentajeDescuento);

	public Etiqueta actualizarEtiqueta(Long idEtiqueta, String nombreEtiqueta) throws InstanceNotFoundException;

	public Etiqueta findEtiqueta(Long idEtiqueta) throws InstanceNotFoundException;

	public Etiqueta findEtiqueta(String nombreEtiqueta) throws InstanceNotFoundException;

	public List<Etiqueta> listaEtiqueta();

	public List<Etiqueta> listaEtiquetasMenu();

	public List<Etiqueta> listaEtiquetasColecciones();

	public List<Etiqueta> listaEtiquetasDescuento();

	public void borrarEtiqueta(Etiqueta etiqueta);

	public List<UserProfile> getListaUsuarios() throws InstanceNotFoundException;

	public void setPrivilegios(String privilegio, long idUser) throws InstanceNotFoundException;

	/* BLOG */
	public EntradaBlog anadirEntradaBlog(long idUser, String titulo, String resumen, String texto)
			throws InstanceNotFoundException;

	public List<EntradaBlog> listaEntradasBlog();

	public EntradaBlog findEntradaBlog(long idEntradaBlog) throws InstanceNotFoundException;

	public List<EntradaBlog> listaEntradasBlog3();

	public void borrarEntradaBlog(long idEntrada) throws InstanceNotFoundException;

	/* CORREO */
	public List<Correo> listaCorreos();

	public void modificarCorreo(long idCorreo, String asuntoCorreo, String correo) throws InstanceNotFoundException;

	public Correo findCorreo(long idCorreo) throws InstanceNotFoundException;

}
