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
import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userservice.UserService;
import tiendaonline.web.services.Private;

@Private
public class MandarCorreo {

	@SessionState(create = false)
	UserProfile userProfile;
	long idUser1;
	@Inject
	UserService userService;
	@Property
	String asuntoCorreo;
	@Property
	String textoCorreo;

	@Component
	private Form mandarCorreoForm;

	void onActivate(long idUser) throws InstanceNotFoundException {
		idUser1 = idUser;
		userProfile = userService.findUserProfile(idUser);
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
			InternetAddress[] internetAddresses = { new InternetAddress(userProfile.getEmail()),
					new InternetAddress("tiendaonlinepfc@gmail.com") };

			// Agregar los destinatarios al mensaje
			mimeMessage.setRecipients(Message.RecipientType.TO, internetAddresses);

			// Agregar el asunto al correo
			mimeMessage.setSubject(asuntoCorreo);

			// Creo la parte del mensaje
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			MimeBodyPart htmlPart = new MimeBodyPart();

			mimeBodyPart.setContent(textoCorreo, "text/html");

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

	void onValidateFrommandarCorreoForm() throws InstanceNotFoundException {
		this.mandarCorreo2();

	}

	Object onSuccessFrommandarCorreoForm() {

		return AdministracionUsuarios.class;
	}

}
