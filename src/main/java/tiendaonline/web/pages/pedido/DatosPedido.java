/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.pages.pedido;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.correo.Correo;
import tiendaonline.model.lineapedido.LineaPedido;
import tiendaonline.model.pedido.Pedido;
import tiendaonline.model.pedidoservice.PedidoService;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userservice.UserService;
import tiendaonline.model.util.ListadoIndex;
import tiendaonline.web.pages.Index;
import tiendaonline.web.pages.ropa.MiImagen;
import tiendaonline.web.services.AuthenticationPolicy;
import tiendaonline.web.services.AuthenticationPolicyType;
import tiendaonline.web.util.UserSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.PageRenderLinkSource;

/**
 *
 * @author Emilio
 */
@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class DatosPedido {

	String firstName;
	String lastName;
	String email;
	String dni;
	long telefono;
	int numeroPuntos;
	int precioTotal;

	@SessionState(create = false)
	private UserSession userSession;

	@Inject
	private UserService userService;
	private Pedido pedido;
	private long idPedido;

	@Property
	LineaPedido lineaPedido;
	@Inject
	private PedidoService pedidoService;
	@Component
	Form borrarPedidoForm;
	@Inject
	AdministradorService administradorService;
	@Component
	Form formalizarPedidoForm;

	public void setIdPedido(long idPedido) {
		this.idPedido = idPedido;
	}

	public long getIdPedido() {
		return this.idPedido;
	}

	void onActivate(Long idPedido) {

		this.idPedido = idPedido;
	}

	Long onPassivate() {
		return idPedido;
	}

	public List<LineaPedido> getListaProductosPedido() {

		return pedidoService.listarLineasPorIdPedido(this.idPedido);

	}

	public double getPrecioTotal() throws InstanceNotFoundException {
		return pedidoService.buscarPedido(idPedido).getPrecioTotal();
	}

	public String getFirstName() throws InstanceNotFoundException {
		UserProfile userProfile = userService.findUserProfile(userSession.getUserProfileId());
		return userProfile.getFirstName();
	}

	public void setFirstName(String firstName) throws InstanceNotFoundException {
		UserProfile userProfile = userService.findUserProfile(userSession.getUserProfileId());
		this.firstName = userProfile.getFirstName();
	}

	public String getLastName() throws InstanceNotFoundException {
		UserProfile userProfile = userService.findUserProfile(userSession.getUserProfileId());
		return userProfile.getLastName();
	}

	public void setLastName(String lastName) throws InstanceNotFoundException {
		UserProfile userProfile = userService.findUserProfile(userSession.getUserProfileId());
		this.firstName = userProfile.getLastName();
	}

	public String getProvincia() throws InstanceNotFoundException {
		UserProfile userProfile = userService.findUserProfile(userSession.getUserProfileId());
		return userProfile.getProvincia();
	}

	public String getLocalidad() throws InstanceNotFoundException {
		UserProfile userProfile = userService.findUserProfile(userSession.getUserProfileId());
		return userProfile.getLocalidad();
	}

	public String getDireccion() throws InstanceNotFoundException {
		UserProfile userProfile = userService.findUserProfile(userSession.getUserProfileId());
		return userProfile.getDireccion();
	}

	public String getNumero() throws InstanceNotFoundException {
		UserProfile userProfile = userService.findUserProfile(userSession.getUserProfileId());
		return userProfile.getNumero();
	}

	public String getEmail() throws InstanceNotFoundException {
		UserProfile userProfile = userService.findUserProfile(userSession.getUserProfileId());
		return userProfile.getEmail();
	}

	public String getDni() throws InstanceNotFoundException {
		UserProfile userProfile = userService.findUserProfile(userSession.getUserProfileId());
		return userProfile.getDni();
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public long getTelefono() throws InstanceNotFoundException {
		UserProfile userProfile = userService.findUserProfile(userSession.getUserProfileId());
		return userProfile.getTelefono();
	}

	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}

	public int getNumeroPuntos() throws InstanceNotFoundException {
		UserProfile userProfile = userService.findUserProfile(userSession.getUserProfileId());
		return userProfile.getNumeroPuntos();
	}

	public void setNumeroPuntos(int numeroPuntos) {
		this.numeroPuntos = numeroPuntos;
	}

	@Inject
	RopaService ropaService;

	@Inject
	private AssetSource assetSource;

	public Asset getSignImage() {
		final String path = "Users/Emilio/Dropbox/Facultad/PFC/pojo-app/ ";

		return assetSource.getContextAsset(path, null);
	}

	@Property
	private ListadoIndex listado;

	public boolean getAcceso() throws InstanceNotFoundException {
		long idUser = userSession.getUserProfileId();
		long idUserEnPedido = pedidoService.buscarPedido(idPedido).getUsuario().getUserProfileId();
		if (idUser == idUserEnPedido)
			return true;
		else
			return false;
	}

	public List<ListadoIndex> getListados() throws InstanceNotFoundException {
		List<LineaPedido> listaAdj = this.getListaProductosPedido();
		List<ListadoIndex> auxiliar = new ArrayList<ListadoIndex>();
		for (LineaPedido listaAdj1 : listaAdj) {
			Ropa ropa2 = listaAdj1.getIdRopa();
			long id2 = ropa2.getIdRopa();
			String nuevo = pageLink.createPageRenderLinkWithContext(MiImagen.class, id2).toString();
			ListadoIndex listado2 = new ListadoIndex(id2, nuevo, ropa2.getNombre(), ropa2.getDescripcion());
			auxiliar.add(listado2);
		}
		return auxiliar;
	}

	/* ENVIAR CORREO */
	public void mandarCorreo() {
		// El correo gmail de envío
		String correoEnvia = "tiendaonlinepfc@gmail.com";
		String claveCorreo = "tiendaonlineemilio";

		// La configuración para enviar correo
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");

		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.user", correoEnvia);
		properties.put("mail.password", claveCorreo);

		// Obtener la sesion
		Session session = Session.getInstance(properties, null);

		try {
			// Crear el cuerpo del mensaje
			MimeMessage mimeMessage = new MimeMessage(session);

			// Agregar quien envía el correo
			mimeMessage.setFrom(new InternetAddress(correoEnvia, "Tienda online PFC"));

			// Los destinatarios
			InternetAddress[] internetAddresses = { new InternetAddress(this.getEmail()),
					new InternetAddress("tiendaonlinepfc@gmail.com") };

			// Agregar los destinatarios al mensaje
			mimeMessage.setRecipients(Message.RecipientType.TO, internetAddresses);
			Correo c = administradorService.findCorreo((long) 1);
			String correo = c.getCorreo();

			// Agregar el asunto al correo
			mimeMessage.setSubject(c.getAsuntoCorreo());
			String cambio = correo.replace("(1)", this.getFirstName());
			cambio = cambio.replace("(2)", this.getProvincia());
			cambio = cambio.replace("(3)", this.getLocalidad());
			cambio = cambio.replace("(4)", this.getDireccion());
			cambio = cambio.replace("(5)", this.getNumero());
			cambio = cambio.replace("(6)", this.getEmail());
			cambio = cambio.replace("(7)", Long.toString(this.getTelefono()));
			cambio = cambio.replace("(8)", Double.toString(this.getPrecioTotal()));

			// Creo la parte del mensaje
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			mimeBodyPart.setContent(cambio, "text/html");
			// Crear el multipart para agregar la parte del mensaje anterior
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);

			// Agregar el multipart al cuerpo del mensaje
			mimeMessage.setContent(multipart);

			// Enviar el mensaje
			Transport transport = session.getTransport("smtp");
			transport.connect(correoEnvia, claveCorreo);
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			transport.close();

		} catch (Exception ex) {
			System.out.println("Fallo en el envio!");
			ex.printStackTrace();
		}
		System.out.println("Correo enviado");
	}

	Object onSuccessFromborrarPedidoForm() throws InstanceNotFoundException {
		pedidoService.actualizarEstado(idPedido, "Borrado");
		// BORRA EL PEDIDO!!
		return Index.class;

	}

	Object onSuccessFromformalizarPedidoForm() throws InstanceNotFoundException {
		pedidoService.actualizarEstado(idPedido, "Tramitación");
		this.mandarCorreo();
		return Index.class;

	}
	/*
	 * Object onSuccessFromtramitarPedidoForm2() throws
	 * InstanceNotFoundException { pedidoService.actualizarEstado(idPedido,
	 * "Tramitación"); this.mandarCorreo(); return Index.class;
	 * 
	 * }
	 */

	/* ver imagen */

	@Inject
	private PageRenderLinkSource pageLink;

	public Link getImageLink() {
		return pageLink.createPageRenderLinkWithContext(MiImagen.class, lineaPedido.getIdRopa().getIdRopa());
	}

	public boolean getTramitar() throws InstanceNotFoundException {
		if (pedidoService.buscarPedido(idPedido).getEstado().equals("Creado"))
			return true;
		else
			return false;
	}
}
