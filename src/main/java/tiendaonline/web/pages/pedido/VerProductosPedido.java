/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.pages.pedido;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.correo.Correo;
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
import tiendaonline.web.pages.user.MisPedidos;
import tiendaonline.web.services.Private;
import tiendaonline.web.util.UserSession;

import java.util.ArrayList;
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
@Private

public class VerProductosPedido {
	String firstName;
	String lastName;
	String email;
	String dni;
	long telefono;
	int numeroPuntos;
	int precioTotal;

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
	@Inject
	private AdministradorService administradorService;

	@Inject
	private RopaService ropaService;

	@Component
	private Form comprobarStockForm;

	@Component
	Form EnviarCorreoForm;
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String textAreaValue;

	@Property
	private String fechaRecepcion;
	@Property
	private String numeroSeguimiento;

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
	private PageRenderLinkSource pageLink;

	public Link getImageLink() {
		return pageLink.createPageRenderLinkWithContext(MiImagen.class, lineaPedido.getIdRopa().getIdRopa());
	}

	public Ropa getRopa() throws InstanceNotFoundException {
		return ropaService.findRopa(lineaPedido.getIdRopa().getIdRopa());
	}

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
		return pedidoService.listarLineasPorIdPedido(pedido.getIdPedido());

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
			descuento = administradorService.findEtiqueta(ropa.getIdEtiqueta()).getPorcentajeDescuento();
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

	@Property
	String string;

	public List<String> getPorcentajeDescuentos() throws InstanceNotFoundException {
		
		return pedidoService.getPorcentajeDescuentos(pedido);
	}

	void anadirComprarRecomendaciones(ArrayList<Ropa> listaIds) throws InstanceNotFoundException {
		pedidoService.anadirComprarRecomendaciones(listaIds);
	}

	void onValidateFromcomprobarStockForm() throws InstanceNotFoundException {

		// Esto hai que Hacerlo con EL DAO es decir un metodo UPDATE!!
		List<LineaPedido> lineas = pedido.getLineaPedidos();
		ArrayList<Ropa> listaIds = new ArrayList<Ropa>();
		Iterator i = lineas.iterator();
		int count = 0;
		while (i.hasNext()) {
			count++;
			LineaPedido lineaActual = (LineaPedido) i.next();
			listaIds.add(ropaService.findRopa(lineaActual.getIdRopa().getIdRopa()));
			StockTalla stock = ropaService.findStockTalla(lineaActual.getIdStockTalla());
			if (stock.getStock() < lineaActual.getNumeroUnidades()) {
				Ropa ropa = ropaService.findRopa(lineaActual.getIdRopa().getIdRopa());
				pedidoService.actualizarEstado(pedido.getIdPedido(), ropa.getNombre() + " SIN STOCK");

			} else {
				ropaService.actualizarStock(stock.getIdStockTalla(),
						stock.getStock() - lineaActual.getNumeroUnidades());
				pedidoService.actualizarEstado(pedido.getIdPedido(), "Enviado");
			}

		}
		anadirComprarRecomendaciones(listaIds);

	}

	/* METODOS PARA ENVIAR CORREO AL USUARIO CON EL NUMERO DE USUARIO */
	public String getFirstName() throws InstanceNotFoundException {
		UserProfile userProfile = (pedido.getUsuario());
		return userProfile.getFirstName();
	}

	public void setFirstName(String firstName) throws InstanceNotFoundException {

		UserProfile userProfile = (pedido.getUsuario());
		this.firstName = userProfile.getFirstName();
	}

	public String getLastName() throws InstanceNotFoundException {
		UserProfile userProfile = (pedido.getUsuario());
		return userProfile.getLastName();
	}

	public void setLastName(String lastName) throws InstanceNotFoundException {
		UserProfile userProfile = (pedido.getUsuario());
		this.firstName = userProfile.getLastName();
	}

	public String getProvincia() throws InstanceNotFoundException {
		UserProfile userProfile = (pedido.getUsuario());
		return userProfile.getProvincia();
	}

	public String getLocalidad() throws InstanceNotFoundException {
		UserProfile userProfile = (pedido.getUsuario());
		return userProfile.getLocalidad();
	}

	public String getDireccion() throws InstanceNotFoundException {
		UserProfile userProfile = (pedido.getUsuario());
		return userProfile.getDireccion();
	}

	public String getNumero() throws InstanceNotFoundException {
		UserProfile userProfile = (pedido.getUsuario());
		return userProfile.getNumero();
	}

	public double getPrecioTotal() throws InstanceNotFoundException {
		return pedidoService.buscarPedido(pedido.getIdPedido()).getPrecioTotal();
	}

	public String getEmail() throws InstanceNotFoundException {
		UserProfile userProfile = (pedido.getUsuario());
		return userProfile.getEmail();
	}

	public String getDni() throws InstanceNotFoundException {
		UserProfile userProfile = (pedido.getUsuario());
		return userProfile.getDni();
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public long getTelefono() throws InstanceNotFoundException {
		UserProfile userProfile = (pedido.getUsuario());
		return userProfile.getTelefono();
	}

	public void setTelefono(long telefono) {
		this.telefono = telefono;
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
			InternetAddress[] internetAddresses = { new InternetAddress(this.getEmail()),
					new InternetAddress("tiendaonlinepfc@gmail.com") };

			// Agregar los destinatarios al mensaje
			mimeMessage.setRecipients(Message.RecipientType.TO, internetAddresses);

			// Agregar el asunto al correo
			mimeMessage.setRecipients(Message.RecipientType.TO, internetAddresses);
			Correo c = administradorService.findCorreo((long) 2);
			String correo = c.getCorreo();

			// Agregar el asunto al correo
			mimeMessage.setSubject(c.getAsuntoCorreo().replace("(1)", Long.toString(this.getIdpe())));
			String cambio = correo.replace("(1)", this.getFirstName());
			cambio = cambio.replace("(2)", this.fechaRecepcion);
			cambio = cambio.replace("(3)", this.numeroSeguimiento);
			cambio = cambio.replace("(4)", this.getProvincia());
			cambio = cambio.replace("(5)", this.getLocalidad());
			cambio = cambio.replace("(6)", this.getDireccion());
			cambio = cambio.replace("(7)", this.getNumero());
			cambio = cambio.replace("(8)", this.getEmail());
			cambio = cambio.replace("(9)", Long.toString(this.getTelefono()));
			cambio = cambio.replace("(10)", Double.toString(this.getPrecioTotal()));

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

	Object onSuccessFromEnviarCorreoForm() throws InstanceNotFoundException {
		this.mandarCorreo2();
		return VerPedidosTramitado.class;

	}

}
