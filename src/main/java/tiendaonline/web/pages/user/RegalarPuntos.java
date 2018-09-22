package tiendaonline.web.pages.user;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.correo.Correo;
import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userservice.UserService;
import tiendaonline.web.pages.Index;
import tiendaonline.web.services.Private;

@Private
public class RegalarPuntos {
	@SessionState(create = false)
	UserProfile userProfile;
	long idUser1;
	@Inject
	UserService userService;

	@Property
	int numeroPuntos;
	@Inject
	private AdministradorService administradorService;

	@Component
	private Form regalarPuntosForm;

	void onActivate(long idUser) throws InstanceNotFoundException {
		idUser1 = idUser;
		userProfile = userService.findUserProfile(idUser);
		System.out.println(userProfile.getFirstName());
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public void mandarCorreo2(int numeroPuntos) {
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
			InternetAddress[] internetAddresses = { new InternetAddress(userProfile.getEmail()),
					new InternetAddress("tiendaonlinepfc@gmail.com") };
			// Agregar los destinatarios al mensaje
			mimeMessage.setRecipients(Message.RecipientType.TO, internetAddresses);

			// Creo la parte del mensaje
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			Correo c = administradorService.findCorreo((long) 3);
			String correo = c.getCorreo();

			// Agregar el asunto al correo
			mimeMessage.setSubject(c.getAsuntoCorreo());
			String cambio = correo.replace("(1)", userProfile.getFirstName());
			cambio = cambio.replace("(2)", Integer.toString(numeroPuntos));

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

	void onValidateFromregalarPuntosForm() throws InstanceNotFoundException {
		this.mandarCorreo2(numeroPuntos);
		userService.setNumeroPuntos(userProfile, numeroPuntos + userProfile.getNumeroPuntos());

	}

	Object onSuccessFromregalarPuntosForm() {

		return AdministracionUsuarios.class;
	}

}
