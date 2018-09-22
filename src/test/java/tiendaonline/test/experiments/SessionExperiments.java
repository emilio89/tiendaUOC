package tiendaonline.test.experiments;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userservice.UserProfileDetails;
import tiendaonline.model.userservice.util.PasswordEncrypter;

public class SessionExperiments {

	public static void main(String[] args) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			// Register user.
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateInString = "1989-02-23";
			Date fechaNac = formatter.parse(dateInString);				
			UserProfile userProfile = new UserProfile(
					"serviceUser", //nombre
					"userPassword", //password
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
					);

			session.saveOrUpdate(userProfile);
			Long userId = userProfile.getUserProfileId();
			System.out.println("User with userId '" + userId
					+ "' has been created");
			System.out.println(userProfile);

			// Find user.
			userProfile = (UserProfile) session.get(UserProfile.class, userId);
			if (userProfile != null) {
				System.out.println("User with userId '" + userId
						+ "' has been retrieved");
				System.out.println(userProfile);
			} else {
				System.out.println("User with userId '" + userId
						+ "' has not been found");
			}
			
			session.delete(userProfile);
			System.out.println("User with userId '" + userProfile.getUserProfileId()
					+ "' has been deleted");
			
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}

		HibernateUtil.shutdown();

	}
}
