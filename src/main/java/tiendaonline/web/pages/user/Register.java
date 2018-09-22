package tiendaonline.web.pages.user;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userservice.UserProfileDetails;
import tiendaonline.model.userservice.UserService;
import tiendaonline.web.pages.Index;
import tiendaonline.web.services.AuthenticationPolicy;
import tiendaonline.web.services.AuthenticationPolicyType;
import tiendaonline.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import java.util.Date;

@AuthenticationPolicy(AuthenticationPolicyType.NON_AUTHENTICATED_USERS)
public class Register {

	@Property
	private String loginName;

	@Property
	private String password;

	@Property
	private String retypePassword;

	@Property
	private String firstName;

	@Property
	private String lastName;

	@Property
	private String email;

	@Property
	private String dni;

	@Property
	private long telefono;

	@Property
	private Date ejemploDate;

	@Property
	private String tipoUsuario;

	@Property
	private int numeroPuntos;
	@Property
	private String localidad;
	@Property
	private int cp;
	@Property
	private String provincia;
	@Property
	private String direccion;
	@Property
	private String piso;
	@Property
	private String numero;

	@SessionState(create = false)
	private UserSession userSession;

	@Inject
	private UserService userService;

	@Component
	private Form registrationForm;

	@Component(id = "loginName")
	private TextField loginNameField;

	@Component(id = "password")
	private PasswordField passwordField;

	@Inject
	private Messages messages;

	private Long userProfileId;

	void onValidateFromRegistrationForm() {

		if (!registrationForm.isValid()) {
			return;
		}

		if (!password.equals(retypePassword)) {
			registrationForm.recordError(passwordField, messages.get("error-passwordsDontMatch"));
		} else {

			try {
				UserProfile userProfile = userService.registerUser(loginName, password,
						new UserProfileDetails(firstName, lastName, email, dni, telefono, ejemploDate, tipoUsuario,
								numeroPuntos, localidad, cp, provincia, direccion, piso, numero));
				userProfileId = userProfile.getUserProfileId();
			} catch (DuplicateInstanceException e) {
				registrationForm.recordError(loginNameField, messages.get("error-loginNameAlreadyExists"));
			}

		}

	}

	Object onSuccess() {
		userSession = new UserSession();
		userSession.setUserProfileId(userProfileId);
		userSession.setFirstName(firstName);
		return Index.class;

	}

}
