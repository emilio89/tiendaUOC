package tiendaonline.test.experiments;

import static tiendaonline.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static tiendaonline.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userservice.IncorrectPasswordException;
import tiendaonline.model.userservice.UserProfileDetails;
import tiendaonline.model.userservice.UserService;
import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

import java.util.*;
import java.text.*;

public class UserServiceExperiments {

	public static void main(String[] args) {

		/* Get service object. */
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE });
		UserService userService = ctx.getBean(UserService.class);

		try {

			// Register user.
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateInString = "1989-02-23";
			Date fechaNac = formatter.parse(dateInString);				
			UserProfile userProfile = userService.registerUser(
					"serviceUser", //nombre
					"userPassword", //password
					new UserProfileDetails(
							"name", 		//nombre 
							"lastName", 	//apellidos
							"user@udc.es", 	//email
							"33333333s",	//Dni
							981888888,		//Telefono
							fechaNac,	//F.nacimiento
							"normal",		//Tipo usuario
							0,  			//Puntos
							"Taragoña",		//localidad
							15706, 			//C.P.
							"Coruña",		//Provincia
							"Fachan",		//Direccion
							"1", 			//Piso
							"S/N" 			//Numero
							));
			System.out.println("User with userId '"
					+ userProfile.getUserProfileId() + "' has been created");
			System.out.println(userProfile);

			// Find user.
			userProfile = userService.login("serviceUser", "userPassword",
					false);
			System.out.println("User with userId '"
					+ userProfile.getUserProfileId() + "' has been retrieved");
			System.out.println(userProfile);

			userService.deleteUser(userProfile.getUserProfileId());
			System.out.println("User with userId '"
					+ userProfile.getUserProfileId() + "' has been deleted");

		} catch (IncorrectPasswordException | InstanceNotFoundException
				| DuplicateInstanceException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
