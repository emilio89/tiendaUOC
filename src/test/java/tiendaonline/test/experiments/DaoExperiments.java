package tiendaonline.test.experiments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Transaction;

import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userprofile.UserProfileDao;
import tiendaonline.model.userprofile.UserProfileDaoHibernate;
import tiendaonline.model.userservice.UserProfileDetails;
import tiendaonline.model.userservice.util.PasswordEncrypter;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class DaoExperiments {

	public static void main(String[] args) {

		UserProfileDaoHibernate userProfileDaoHibernate = new UserProfileDaoHibernate();
		userProfileDaoHibernate.setSessionFactory(HibernateUtil
				.getSessionFactory());
		UserProfileDao userProfileDao = userProfileDaoHibernate;

		Transaction tx = HibernateUtil.getSessionFactory().getCurrentSession()
				.beginTransaction();
		try {

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

			userProfileDao.save(userProfile);
			Long userId = userProfile.getUserProfileId();
			System.out.println("User with userId '" + userId
					+ "' has been created");
			System.out.println(userProfile);

			// Find user.
			userProfile = userProfileDao.find(userId);
			System.out.println("User with userId '" + userId
					+ "' has been retrieved");
			System.out.println(userProfile);
			
			//Delete user
			userProfileDao.remove(userId);
			System.out.println("User with userId '" + userId
					+ "' has been deleted");
			
			tx.commit();

		} catch (RuntimeException e) {
			e.printStackTrace();
			tx.rollback();
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
			tx.commit();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			HibernateUtil.getSessionFactory().getCurrentSession().close();
		}

		HibernateUtil.shutdown();

	}

}
