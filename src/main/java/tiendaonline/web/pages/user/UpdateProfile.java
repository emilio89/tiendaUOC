package tiendaonline.web.pages.user;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userservice.UserProfileDetails;
import tiendaonline.model.userservice.UserService;
import tiendaonline.web.pages.Index;
import tiendaonline.web.services.AuthenticationPolicy;
import tiendaonline.web.services.AuthenticationPolicyType;
import tiendaonline.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import java.util.Date;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)

public class UpdateProfile {

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
	private Date fechaNacimiento;

	@Property
	private String tipoUsuario;

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

	int numeroPuntos;

	@SessionState(create = false)
	private UserSession userSession;

	@Inject
	private UserService userService;

	void onPrepareForRender() throws InstanceNotFoundException {

		UserProfile userProfile;

		userProfile = userService.findUserProfile(userSession.getUserProfileId());
		firstName = userProfile.getFirstName();
		lastName = userProfile.getLastName();
		email = userProfile.getEmail();
		dni = userProfile.getDni();
		telefono = userProfile.getTelefono();
		fechaNacimiento = userProfile.getFechaNacimiento();
		localidad = userProfile.getLocalidad();
		cp = userProfile.getCp();
		provincia = userProfile.getProvincia();
		direccion = userProfile.getDireccion();
		piso = userProfile.getPiso();
		numero = userProfile.getNumero();

	}

	Object onSuccess() throws InstanceNotFoundException {

		userService.updateUserProfileDetails(userSession.getUserProfileId(),
				new UserProfileDetails(firstName, lastName, email, dni, telefono, fechaNacimiento, tipoUsuario,
						numeroPuntos, localidad, cp, provincia, direccion, piso, numero));
		userSession.setFirstName(firstName);
		return Index.class;

	}

	public int getNumeroPuntos() throws InstanceNotFoundException {
		UserProfile userProfile = userService.findUserProfile(userSession.getUserProfileId());
		return userProfile.getNumeroPuntos();
	}

	public void setNumeroPuntos(int numeroPuntos) throws InstanceNotFoundException {
		UserProfile userProfile = userService.findUserProfile(userSession.getUserProfileId());
		this.numeroPuntos = userProfile.getNumeroPuntos();
	}

}