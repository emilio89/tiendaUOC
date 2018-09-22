package tiendaonline.test.model.userservice;

import static tiendaonline.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static tiendaonline.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.comentario.Comentario;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userservice.IncorrectPasswordException;
import tiendaonline.model.userservice.UserProfileDetails;
import tiendaonline.model.userservice.UserService;
import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class UserServiceTest {

    private final long NON_EXISTENT_USER_PROFILE_ID = -1;

    @Autowired
    private UserService userService;
	@Autowired
	private RopaService ropaService;
	@Autowired
	private AdministradorService administradorService;

    @Test
    public void testRegisterUserAndFindUserProfile()
        throws DuplicateInstanceException, InstanceNotFoundException, ParseException {
        
		// Register user.	
		UserProfile userProfile = registerUser(	"serviceUser","userPassword");

        UserProfile userProfile2 = userService.findUserProfile(
            userProfile.getUserProfileId());

        /* Check data. */
        assertEquals(userProfile, userProfile2);

        //Delete user
		userService.deleteUser(userProfile.getUserProfileId());
        
    }

    @Test(expected = DuplicateInstanceException.class)
    public void testRegisterDuplicatedUser() throws DuplicateInstanceException,
        InstanceNotFoundException {

		// Register user.
		UserProfile userProfile = registerUser(	"serviceUser","userPassword");
		
		UserProfile userProfile2 = registerUser(	"serviceUser","userPassword");
		
        //Delete test user
        userService.deleteUser(userProfile.getUserProfileId());
    }

    @Test
    public void testLoginClearPassword() throws IncorrectPasswordException,
        InstanceNotFoundException, DuplicateInstanceException {

        String clearPassword = "userPassword";
        UserProfile userProfile = registerUser("user", clearPassword);

        UserProfile userProfile2 = userService.login(userProfile.getLoginName(),
            clearPassword, false);

        assertEquals(userProfile, userProfile2);
        
      //Delete test user
        userService.deleteUser(userProfile.getUserProfileId());
    }


    @Test
    public void testLoginEncryptedPassword() throws IncorrectPasswordException,
            InstanceNotFoundException, DuplicateInstanceException {

        UserProfile userProfile = registerUser("user", "clearPassword");

        UserProfile userProfile2 = userService.login(userProfile.getLoginName(),
            userProfile.getEncryptedPassword(), true);

        assertEquals(userProfile, userProfile2);
        
        //Delete test user
        userService.deleteUser(userProfile.getUserProfileId());

    }

    @Test(expected = IncorrectPasswordException.class)
    public void testLoginIncorrectPasword() throws IncorrectPasswordException,
            InstanceNotFoundException, DuplicateInstanceException {

        String clearPassword = "userPassword";
        UserProfile userProfile = registerUser("user", clearPassword);

        userService.login(userProfile.getLoginName(), 'X' + clearPassword,
             false);
        
      //Delete test user
        userService.deleteUser(userProfile.getUserProfileId());        

    }

    @Test(expected = InstanceNotFoundException.class)
    public void testLoginWithNonExistentUser()
            throws IncorrectPasswordException, InstanceNotFoundException {

        userService.login("user", "userPassword", false);

    }

    @Test(expected = InstanceNotFoundException.class)
    public void testFindNonExistentUser() throws InstanceNotFoundException {

        userService.findUserProfile(NON_EXISTENT_USER_PROFILE_ID);

    }

    @Test
    public void testUpdate() throws InstanceNotFoundException,
            IncorrectPasswordException, DuplicateInstanceException {

        /* Update profile. */
        String clearPassword = "userPassword";
        UserProfile userProfile = registerUser("user", clearPassword);

        UserProfileDetails newUserProfileDetails = new UserProfileDetails(
            'X' + userProfile.getFirstName(), 'X' + userProfile.getLastName(),
            'X' + userProfile.getEmail(), userProfile.getDni(),
            + userProfile.getTelefono(),  userProfile.getFechaNacimiento(),
            'X' + userProfile.getTipoUsuario(), userProfile.getNumeroPuntos(),
            'X' + userProfile.getLocalidad(), userProfile.getCp(),
            'X' + userProfile.getProvincia(), 'X' + userProfile.getDireccion(),
            'X' + userProfile.getPiso(), 'X' + userProfile.getNumero());

  
        
        userService.updateUserProfileDetails(userProfile.getUserProfileId(),
            newUserProfileDetails);

        /* Check changes. */
        userService.login(userProfile.getLoginName(), clearPassword, false);
        UserProfile userProfile2 = userService.findUserProfile(
            userProfile.getUserProfileId());

        assertEquals(newUserProfileDetails.getFirstName(),
            userProfile2.getFirstName());
        assertEquals(newUserProfileDetails.getLastName(),
            userProfile2.getLastName());
        assertEquals(newUserProfileDetails.getEmail(),
            userProfile2.getEmail());
        
      //Delete test user
        userService.deleteUser(userProfile.getUserProfileId());

    }

    @Test(expected = InstanceNotFoundException.class)
    public void testUpdateWithNonExistentUser()
            throws InstanceNotFoundException {

        userService.updateUserProfileDetails(NON_EXISTENT_USER_PROFILE_ID,
            new UserProfileDetails("name", "lastName", "user@udc.es","33333333s",981888888,new Date(),"normal",0, "Loc", 15706, "Prov", "Dir", "Piso", "Numero"));

    }

    @Test
    public void testChangePassword() throws InstanceNotFoundException,
            IncorrectPasswordException, DuplicateInstanceException {

        /* Change password. */
        String clearPassword = "userPassword";
        UserProfile userProfile = registerUser("user", clearPassword);
        String newClearPassword = 'X' + clearPassword;

        userService.changePassword(userProfile.getUserProfileId(),
            clearPassword, newClearPassword);

        /* Check new password. */
        userService.login(userProfile.getLoginName(), newClearPassword, false);
        
      //Delete test user
        userService.deleteUser(userProfile.getUserProfileId());

    }

    @Test(expected = IncorrectPasswordException.class)
    public void testChangePasswordWithIncorrectPassword()
            throws InstanceNotFoundException, IncorrectPasswordException, DuplicateInstanceException {

        String clearPassword = "userPassword";
        UserProfile userProfile = registerUser("user", clearPassword);

        userService.changePassword(userProfile.getUserProfileId(),
            'X' + clearPassword, 'Y' + clearPassword);
        
      //Delete test user
        userService.deleteUser(userProfile.getUserProfileId());

    }

    @Test(expected = InstanceNotFoundException.class)
    public void testChangePasswordWithNonExistentUser()
            throws InstanceNotFoundException, IncorrectPasswordException {

        userService.changePassword(NON_EXISTENT_USER_PROFILE_ID,
                "userPassword", "XuserPassword");

    }

    private UserProfile registerUser(String loginName, String clearPassword) throws DuplicateInstanceException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = "1989-02-23";
		Date fechaNac = null;
		try {
			fechaNac = formatter.parse(dateInString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}			
         
    	UserProfileDetails userProfileDetails = new UserProfileDetails(
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
        
        


            return userService.registerUser(
                loginName, clearPassword, userProfileDetails);


    }
    
	@Test
	public void testRegistrarComentario() throws InstanceNotFoundException {
		Random rand = new Random();

		Ropa ropa = ropaService.findRopa(1L);
		UserProfile userProfile2 = userService.findUserProfile(7L);

		String comentarioString = "ComentarioPrueba-" + rand.nextInt(9999);
		Comentario comentario = userService.registrarComentario(comentarioString, userProfile2, ropa);

		Comentario aux = userService.findComentario(comentario.getIdComentario());

		/* Check data. */
		assertEquals(comentario, aux);

	}

	@Test
	public void testFindComentario() throws InstanceNotFoundException {
		Random rand = new Random();
		Ropa ropa = ropaService.findRopa(1L);
		UserProfile userProfile2 = userService.findUserProfile(7L);
		String comentarioString = "ComentarioPrueba-" + rand.nextInt(9999);
		Comentario comentario = userService.registrarComentario(comentarioString, 
				userProfile2, ropa);
		Comentario aux = userService.findComentario(comentario.getIdComentario());
		/* Check data. */
		assertEquals(comentario, aux);
		ropaService.borrarComentario(aux.getIdComentario());
	}


}
