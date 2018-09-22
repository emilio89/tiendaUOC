/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.pages;

import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import tiendaonline.model.userservice.UserService;
import tiendaonline.web.services.Private;
import tiendaonline.web.util.UserSession;

/**
 *
 * @author Emilio
 */
@Private
public class Administracion {

	@SessionState(create = false)
	private UserSession userSession;

	@Inject
	private UserService userService;

}
