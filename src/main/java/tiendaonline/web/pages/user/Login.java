package tiendaonline.web.pages.user;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;

import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userservice.IncorrectPasswordException;
import tiendaonline.model.userservice.UserService;
import tiendaonline.web.pages.Index;
import tiendaonline.web.services.AuthenticationPolicy;
import tiendaonline.web.services.AuthenticationPolicyType;
import tiendaonline.web.util.CookiesManager;
import tiendaonline.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@AuthenticationPolicy(AuthenticationPolicyType.NON_AUTHENTICATED_USERS)
public class Login {

	@Property
	private String loginName;

	@Property
	private String password;

	@Property
	private boolean rememberMyPassword;

	@SessionState(create = false)
	private UserSession userSession;

	@Inject
	private Cookies cookies;

	@Component
	private Form loginForm;

	@Inject
	private Messages messages;

	@Inject
	private UserService userService;

	private UserProfile userProfile = null;

	void onValidateFromLoginForm() {

		if (!loginForm.isValid()) {
			return;
		}

		try {
			userProfile = userService.login(loginName, password, false);
		} catch (InstanceNotFoundException e) {
			loginForm.recordError(messages.get("error-authenticationFailed"));
		} catch (IncorrectPasswordException e) {
			loginForm.recordError(messages.get("error-authenticationFailed"));
		}

	}

	Object onSuccess() {

		userSession = new UserSession();
		userSession.setUserProfileId(userProfile.getUserProfileId());
		userSession.setFirstName(userProfile.getFirstName());

		if (rememberMyPassword) {
			CookiesManager.leaveCookies(cookies, loginName, userProfile.getEncryptedPassword());
		}
		return Index.class;

	}

}
