package tiendaonline.web.services;

import java.io.IOException;

import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.Dispatcher;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userservice.UserService;
import tiendaonline.web.util.UserSession;

/**
 * Receive all the services needed as constructor arguments. When we bind this
 * service, T5 IoC will provide all the services !
 */
public class AccessController implements Dispatcher {

	@SessionState(create = false)
	private UserSession userSession;

	@Inject
	private UserService userService;

	private final static String LOGIN_PAGE = "/";

	private ApplicationStateManager asm;
	private final ComponentClassResolver resolver;
	private final ComponentSource componentSource;

	public AccessController(ApplicationStateManager asm, ComponentClassResolver resolver,
			ComponentSource componentSource) {
		this.asm = asm;
		this.resolver = resolver;
		this.componentSource = componentSource;
	}

	public boolean dispatch(Request request, Response response) throws IOException {
		boolean canAccess = false;

		/*
		 * Access control logic goes here. If the user is allowed to access the
		 * resource, canAccess should be set to true.
		 */

		String path = request.getPath();
		if (path.equals(""))
			return false;

		int nextslashx = path.length();
		String pageName;

		while (true) {

			pageName = path.substring(1, nextslashx);
			if (!pageName.endsWith("/") && resolver.isPageName(pageName))
				break;
			nextslashx = path.lastIndexOf('/', nextslashx - 1);
			if (nextslashx <= 1)
				return false;
		}
		try {
			return checkAccess(pageName, request, response);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}

	public boolean checkAccess(String pageName, Request request, Response response)
			throws IOException, InstanceNotFoundException {

		boolean canAccess = true;

		/* Is the requested page private ? */
		Component page = componentSource.getPage(pageName);
		boolean privatePage = page.getClass().getAnnotation(Private.class) != null;
		boolean userAuthenticated = asm.exists(UserSession.class);

		if (privatePage) {

			canAccess = false;
			/* Is the user already authentified ? */
			if (userAuthenticated) {
				UserSession userSes = asm.get(UserSession.class);

				String tipUsuario = userService.findUserProfile(userSes.getUserProfileId()).getTipoUsuario();
				if (tipUsuario != null)
					canAccess = (tipUsuario.equals("Administrador"));
				else
					canAccess = false;
			}
		}

		/*
		 * This page can't be requested by a non authentified user => we
		 * redirect him on the signon page
		 */
		if (!canAccess) {
			response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
			return true; // Make sure to leave the chain
		}

		return false;
	}

}
