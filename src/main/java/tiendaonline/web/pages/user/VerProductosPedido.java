/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.pages.user;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.etiqueta.Etiqueta;
import tiendaonline.model.lineapedido.LineaPedido;
import tiendaonline.model.pedido.Pedido;
import tiendaonline.model.pedidoservice.PedidoService;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.model.stocktalla.StockTalla;
import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userservice.UserService;
import tiendaonline.web.pages.Index;
import tiendaonline.web.pages.ropa.MiImagen;
import tiendaonline.web.services.AuthenticationPolicy;
import tiendaonline.web.services.AuthenticationPolicyType;
import tiendaonline.web.services.Private;
import tiendaonline.web.util.File;
import tiendaonline.web.util.UserSession;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

/**
 *
 * @author Emilio
 */
@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)

public class VerProductosPedido {

	String firstName;
	String lastName;
	String email;
	String dni;
	long telefono;
	int numeroPuntos;
	int precioTotal;
	@Property
	private String asuntoCorreo;
	@Property
	private String textoCorreo;

	@Property
	@Persist(PersistenceConstants.FLASH)
	// We use a String, not a Boolean, in the radio group value so that we can
	// represent null. Boolean can't represent
	// null because Tapestry will coerce it to Boolean.FALSE. See
	// https://issues.apache.org/jira/browse/TAPESTRY-1928 .
	private String valueForMyBoolean;

	long idpe;
	@Property
	Pedido pedido;
	Pedido pedido2;

	@Property
	LineaPedido lineaPedido;

	@SessionState(create = false)
	private UserSession userSession;
	@Inject
	private UserService userService;

	@Inject
	private PedidoService pedidoService;
	@Property
	String salida;
	@Component
	Form tramitarPedidoForm;
	@Component
	Form EnviarCorreoForm;
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String textAreaValue;

	public boolean getCreado() {
		if (pedido.getEstado().equals("Creado"))
			return true;
		else
			return false;
	}

	public boolean getTramitacion() {
		if (pedido.getEstado().equals("Tramitación"))
			return true;
		else
			return false;
	}

	public boolean getEnviado() {
		if (pedido.getEstado().equals("Enviado"))
			return true;
		else
			return false;
	}

	public boolean getBorrado() {
		if (pedido.getEstado().equals("Borrado"))
			return true;
		else
			return false;
	}

	@Inject
	private RopaService ropaService;

	public long getAccountContext() {

		return pedido.getIdPedido();
	}

	public double getPrecioPedido() {
		return pedido.getPrecioTotal();
	}

	public long getIdpe() {
		return idpe;
	}

	public void setIdpe(long idpe) {
		this.idpe = idpe;
	}

	void onActivate(long pedidoId) throws InstanceNotFoundException {
		this.idpe = pedidoId;
		this.pedido = pedidoService.buscarPedido(pedidoId);

	}

	long onPassivate() {

		return pedido.getIdPedido();
	}

	public List<LineaPedido> getListaProductosPedido() {
		List<LineaPedido> l = new ArrayList<LineaPedido>();
		try {
			l= pedidoService.listarLineasPorIdPedido(pedido.getIdPedido());
			return l;
		}catch 
			(Exception e){
			return l;
		}

	}

	public boolean getHayDescuentosEtiqueta() throws InstanceNotFoundException {

		return pedidoService.getHayDescuentosEtiqueta(pedido.getIdPedido());
	}

	public boolean getHayDescuentosPuntos() throws InstanceNotFoundException {
		double precioTotalRopa2 = 0;
		double descuento = 1;
		double precioRopa = 0;
		int ultimo = 0;
		for (int i = 0; i < pedido.getLineaPedidos().size(); i++) {
			Ropa ropa = ropaService.findRopa(pedido.getLineaPedidos().get(i).getIdRopa().getIdRopa());
			descuento = ropaService.findEtiqueta(ropa.getIdEtiqueta()).getPorcentajeDescuento();
			if (ropaService.findEtiqueta(ropa.getIdEtiqueta()).getPorcentajeDescuento() != 0)
				precioRopa = (ropa.getPrecio() - (ropa.getPrecio() * (descuento / 100)))
						* pedido.getLineaPedidos().get(i).getNumeroUnidades();
			else
				precioRopa = ropa.getPrecio();
			precioTotalRopa2 = precioRopa + precioTotalRopa2;
		}
		ultimo = (int) (Math.round(precioTotalRopa2));
		if (ultimo != pedido.getPrecioTotal())
			return true;
		return false;

	}

	public double getDescuentosPorPuntos() throws InstanceNotFoundException {
		double precioTotalRopa = 0;
		double precioTotalRopa2 = 0;

		if (this.getHayDescuentosPuntos()) {

			for (int i = 0; i < pedido.getLineaPedidos().size(); i++) {
				precioTotalRopa = pedido.getLineaPedidos().get(i).getPrecioUnitario()
						* pedido.getLineaPedidos().get(i).getNumeroUnidades();
				precioTotalRopa2 = precioTotalRopa2 + precioTotalRopa;
			}
			return (precioTotalRopa2 - pedido.getPrecioTotal());
		} else
			return 0;

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

	public double getPrecioTotal() throws InstanceNotFoundException {
		return pedidoService.buscarPedido(pedido.getIdPedido()).getPrecioTotal();
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
	private PageRenderLinkSource pageLink;

	public Link getImageLink() {
		return pageLink.createPageRenderLinkWithContext(MiImagen.class, lineaPedido.getIdRopa().getIdRopa());
	}

	public Ropa getRopa() throws InstanceNotFoundException {
		return ropaService.findRopa(lineaPedido.getIdRopa().getIdRopa());
	}

	@Property
	String string;

	public List<String> getPorcentajeDescuentos() throws InstanceNotFoundException {
		List<String> descuentos = new ArrayList();
		if (this.getHayDescuentosEtiqueta()) {
			for (int i = 0; i < pedido.getLineaPedidos().size(); i++) {
				Ropa ropa = ropaService.findRopa(pedido.getLineaPedidos().get(i).getIdRopa().getIdRopa());
				Etiqueta et = ropaService.findEtiqueta(ropa.getIdEtiqueta());
				if (et.getPorcentajeDescuento() != 0)
					descuentos.add("El producto " + ropa.getNombre() + " tiene un descuento del "
							+ et.getPorcentajeDescuento() + " % ");

			}

		}

		return descuentos;
	}

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

			// Agregar el asunto al correo
			mimeMessage.setSubject("CONFIRMACIÓN DE COMPRA EN LA TIENDA ONLINE.");

			// Creo la parte del mensaje
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			MimeBodyPart htmlPart = new MimeBodyPart();

			mimeBodyPart.setContent("<h1> Hola " + this.getFirstName() + "</h1>"
					+ " has comprado en nuestra tienda online el envío,<b> muchas gracias por ello </b>. El envio del pedido se realizará a la siguiente dirección por contrarrembolso :"
					+ "<p>Provincia: " + this.getProvincia() + "</p>" + "<p>Localidad: " + this.getLocalidad() + "</p>"
					+ "<p>Direccion: " + this.getDireccion() + "</p>" + "<p>Numero: " + this.getNumero() + "</p>"
					+ "<p>Email: " + this.getEmail() + "</p>" + "<p>Telefono: " + this.getTelefono() + "</p>"
					+ "<p> Recordarle que el total de su pedido asciende a<b> " + this.getPrecioTotal()
					+ "euros </b></p>"
					+ "</p>Si tiene cualquier problema o sugerencia puede enviarnos un correo a info@tiendaonline.com o llamarnos a nuestro numero de teléfono 981 981 981"
					+ "<p>" + "Muchas gracias por confiar en nosotros, no le defraudaremos.</p>"

					, "text/html");

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

	public void mandarCorreo2() {
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
			InternetAddress[] internetAddresses = { new InternetAddress("tiendaonlinepfc@gmail.com") };

			// Agregar los destinatarios al mensaje
			mimeMessage.setRecipients(Message.RecipientType.TO, internetAddresses);

			// Agregar el asunto al correo
			mimeMessage.setSubject("PEDIDO " + pedido.getIdPedido() + ": DUDA SOBRE COMPRA EN LA TIENDA ONLINE.");

			// Creo la parte del mensaje
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			MimeBodyPart htmlPart = new MimeBodyPart();

			mimeBodyPart.setContent("<h1> El usuario " + this.getFirstName() + "</h1>"
					+ "<p> ha comprado en nuestra tienda y tiene la siguiente duda </p>" + "<p>Asunto: "
					+ this.asuntoCorreo + "</p>" + "<p>Texto : " + this.textAreaValue + "</p>"
					+ "<p> Sobre el pedido con id<b> " + pedido.getIdPedido() + " </b></p>" + "<p>"
					+ "El correo del usuario es el siguiente, por favor contestar cuanto antes.</p>" + this.getEmail(),
					"text/html");

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
		pedidoService.actualizarEstado(pedido.getIdPedido(), "Borrado");
		// BORRA EL PEDIDO!!
		return MisPedidos.class;

	}

	Object onSuccessFromtramitarPedidoForm() throws InstanceNotFoundException {
		pedidoService.actualizarEstado(pedido.getIdPedido(), "Tramitación");
		this.mandarCorreo();
		return Index.class;

	}

	Object onSuccessFromEnviarCorreoForm() throws InstanceNotFoundException {
		this.mandarCorreo2();
		return Index.class;

	}
	

}
