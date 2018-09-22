/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.model.administradorservice;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.categoria.Categoria;
import tiendaonline.model.categoria.CategoriaDao;
import tiendaonline.model.correo.Correo;
import tiendaonline.model.correo.CorreoDao;
import tiendaonline.model.entradablog.EntradaBlog;
import tiendaonline.model.entradablog.EntradaBlogDao;
import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.etiqueta.EtiquetaDao;
import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userprofile.UserProfileDao;

/**
 *
 * @author Emilio
 */
@Service("AdministradorService")
@Transactional
public class AdministradorServiceImpl implements AdministradorService {

	@Autowired
	private CategoriaDao categoriaDao;
	@Autowired
	private EntradaBlogDao entradaBLogDao;
	@Autowired
	private UserProfileDao userProfileDao;
	@Autowired
	private CorreoDao correoDao;
	@Autowired
	private EtiquetaDao etiquetaDao;

	public Categoria crearCategoria(String nombreCategoria, int idCategoriaPadre) {

		Categoria categoria = new Categoria(nombreCategoria, idCategoriaPadre);
		categoriaDao.save(categoria);
		return categoria;

	}

	@Transactional(readOnly = true)
	public Categoria findCategoria(Long idCategoria) throws InstanceNotFoundException {
		return categoriaDao.find(idCategoria);

	}

	public void borrarCategoria(Categoria categoria) {
		categoriaDao.borrarCategoria(categoria);

	}

	@Transactional
	public List<Categoria> listaCategoria() {

		return categoriaDao.listaCategoria();

	}

	@Transactional
	public List<Categoria> listaSubCategoria() {

		return categoriaDao.listaSubCategoria();

	}

	public void actualizarCategoria(long idCategoria, String nombreCategoria, long idCategoriaPadre)
			throws InstanceNotFoundException {

		Categoria categoria = categoriaDao.find(idCategoria);
		categoria.setNombreCategoria(nombreCategoria);
		categoria.setIdCategoriaPadre(idCategoriaPadre);

	}

	public Etiqueta registrarEtiqueta(String nombreEtiqueta, int porcentajeDescuento) {

		try {

			Etiqueta etiqueta = new Etiqueta(nombreEtiqueta, porcentajeDescuento);
			etiquetaDao.anadirEtiqueta(etiqueta);
			// etiquetaDao.insertEtiqueta(etiqueta);

			return etiqueta;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public void borrarEtiqueta(Etiqueta etiqueta) {
		etiquetaDao.borrarEtiqueta(etiqueta);
	}

	public Etiqueta registrarEtiqueta(Etiqueta etiqueta) {
		etiquetaDao.save(etiqueta);
		return etiqueta;

	}

	public Etiqueta actualizarEtiqueta(Long idEtiqueta, String nombreEtiqueta) throws InstanceNotFoundException {

		Etiqueta etiqueta = etiquetaDao.findById(idEtiqueta);
		etiqueta.setNombreEtiqueta(nombreEtiqueta);
		etiquetaDao.actualizaEtiqueta(etiqueta);
		return etiqueta;
	}

	@Transactional(readOnly = true)
	public Etiqueta findEtiqueta(Long idEtiqueta) throws InstanceNotFoundException {
		return etiquetaDao.findById(idEtiqueta);

	}

	@Transactional(readOnly = true)
	public Etiqueta findEtiqueta(String nombreEtiqueta) throws InstanceNotFoundException {
		return etiquetaDao.findByNombreEtiqueta(nombreEtiqueta);

	}

	@Transactional
	public List<Etiqueta> listaEtiqueta() {

		return etiquetaDao.listaEtiqueta();

	}

	@Transactional
	public List<Etiqueta> listaEtiquetasMenu() {

		return etiquetaDao.listaEtiquetasMenu();

	}

	@Transactional
	public List<Etiqueta> listaEtiquetasColecciones() {

		return etiquetaDao.listaEtiquetasColecciones();

	}

	@Transactional
	public List<Etiqueta> listaEtiquetasDescuento() {

		return etiquetaDao.listaEtiquetasDescuento();

	}

	@Transactional
	public List<UserProfile> getListaUsuarios() throws InstanceNotFoundException {

		return userProfileDao.getListaUsuarios();

	}

	public void setPrivilegios(String privilegio, long idUser) throws InstanceNotFoundException {
		UserProfile user = userProfileDao.find(idUser);
		user.setTipoUsuario(privilegio);
		userProfileDao.save(user);

	}

	public EntradaBlog anadirEntradaBlog(long idUser, String titulo, String resumen, String texto) throws InstanceNotFoundException {
		Date hoy = new Date();
		EntradaBlog entrada = new EntradaBlog(userProfileDao.find(idUser), hoy, titulo, resumen, texto);
		entradaBLogDao.save(entrada);
		return entrada;
	}

	public void borrarEntradaBlog(long idEntrada) throws InstanceNotFoundException {
		entradaBLogDao.borrarEntradaBlog(entradaBLogDao.findByIdEntradaBlog(idEntrada));
	}

	public List<EntradaBlog> listaEntradasBlog() {
		return entradaBLogDao.listaEntradaBlog();
	}

	public EntradaBlog findEntradaBlog(long idEntradaBlog) throws InstanceNotFoundException {
		return entradaBLogDao.findByIdEntradaBlog(idEntradaBlog);
	}

	public List<EntradaBlog> listaEntradasBlog3() {
		return entradaBLogDao.listaEntradaBlog3();
	}

	public List<Correo> listaCorreos() {
		return correoDao.listaCorreo();
	}

	public void modificarCorreo(long idCorreo, String asuntoCorreo, String correo) throws InstanceNotFoundException {
		Correo c = correoDao.find(idCorreo);
		c.setAsuntoCorreo(asuntoCorreo);
		c.setCorreo(correo);
		correoDao.modificarCorreo(c);
	}

	public Correo findCorreo(long idCorreo) throws InstanceNotFoundException {
		Correo c = new Correo ( "asuntoCorreo"," nombreCorreo", " instruccionesCorreo", " correo");
		return correoDao.findByIdCorreo(idCorreo);
	}
}
