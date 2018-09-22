package tiendaonline.model.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tiendaonline.model.comentario.Comentario;
import tiendaonline.model.comentario.ComentarioDao;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.userprofile.UserProfile;
import tiendaonline.model.userprofile.UserProfileDao;
import tiendaonline.model.userservice.util.PasswordEncrypter;
import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import java.util.Date;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserProfileDao userProfileDao;

	public UserProfile registerUser(String loginName, String clearPassword, UserProfileDetails userProfileDetails)
			throws DuplicateInstanceException {

		try {
			userProfileDao.findByLoginName(loginName);
			throw new DuplicateInstanceException(loginName, UserProfile.class.getName());
		} catch (InstanceNotFoundException e) {
			String encryptedPassword = PasswordEncrypter.crypt(clearPassword);
			UserProfile userProfile = new UserProfile(loginName, encryptedPassword, userProfileDetails.getFirstName(),
					userProfileDetails.getLastName(), userProfileDetails.getEmail(), userProfileDetails.getDni(),
					userProfileDetails.getTelefono(), userProfileDetails.getFechaNacimiento(),
					userProfileDetails.getTipoUsuario(), userProfileDetails.getNumeroPuntos(),
					userProfileDetails.getLocalidad(), userProfileDetails.getCp(), userProfileDetails.getProvincia(),
					userProfileDetails.getDireccion(), userProfileDetails.getPiso(), userProfileDetails.getNumero());

			userProfileDao.save(userProfile);
			return userProfile;
		}

	}

	@Transactional(readOnly = true)
	public UserProfile login(String loginName, String password, boolean passwordIsEncrypted)
			throws InstanceNotFoundException, IncorrectPasswordException {

		UserProfile userProfile = userProfileDao.findByLoginName(loginName);
		String storedPassword = userProfile.getEncryptedPassword();

		if (passwordIsEncrypted) {
			if (!password.equals(storedPassword)) {
				throw new IncorrectPasswordException(loginName);
			}
		} else {
			if (!PasswordEncrypter.isClearPasswordCorrect(password, storedPassword)) {
				throw new IncorrectPasswordException(loginName);
			}
		}
		return userProfile;

	}

	@Transactional(readOnly = true)
	public UserProfile findUserProfile(Long userProfileId) throws InstanceNotFoundException {

		return userProfileDao.find(userProfileId);
	}

	public void updateUserProfileDetails(Long userProfileId, UserProfileDetails userProfileDetails)
			throws InstanceNotFoundException {

		UserProfile userProfile = userProfileDao.find(userProfileId);
		userProfile.setFirstName(userProfileDetails.getFirstName());
		userProfile.setLastName(userProfileDetails.getLastName());
		userProfile.setEmail(userProfileDetails.getEmail());
		userProfile.setDni(userProfileDetails.getDni());
		userProfile.setTelefono(userProfileDetails.getTelefono());
		userProfile.setLocalidad(userProfileDetails.getLocalidad());
		userProfile.setCp(userProfileDetails.getCp());
		userProfile.setProvincia(userProfileDetails.getProvincia());
		userProfile.setDireccion(userProfileDetails.getDireccion());
		userProfile.setPiso(userProfileDetails.getPiso());
		userProfile.setNumero(userProfileDetails.getNumero());
	}

	public void changePassword(Long userProfileId, String oldClearPassword, String newClearPassword)
			throws IncorrectPasswordException, InstanceNotFoundException {

		UserProfile userProfile;
		userProfile = userProfileDao.find(userProfileId);

		String storedPassword = userProfile.getEncryptedPassword();

		if (!PasswordEncrypter.isClearPasswordCorrect(oldClearPassword, storedPassword)) {
			throw new IncorrectPasswordException(userProfile.getLoginName());
		}

		userProfile.setEncryptedPassword(PasswordEncrypter.crypt(newClearPassword));

	}

	@Transactional(readOnly = true)
	public void deleteUser(Long userProfileId) throws InstanceNotFoundException {

		userProfileDao.remove(userProfileId);
	}

	/* COMENTARIO */
	@Autowired
	private ComentarioDao comentarioDao;

	public Comentario registrarComentario(String textoComentario, UserProfile usuario, Ropa ropa)
			throws InstanceNotFoundException {
		Comentario comentario = new Comentario(textoComentario, usuario, ropa);
		ropa.addComentarios(comentario);
		comentarioDao.anadirComentario(comentario);
		return comentario;
	}

	@Transactional(readOnly = true)
	public Comentario findComentario(Long idComentario) throws InstanceNotFoundException {
		return comentarioDao.find(idComentario);

	}


	public void setNumeroPuntos(UserProfile user, int numeroPuntos) {
		user.setNumeroPuntos(numeroPuntos);
		userProfileDao.save(user);
	}

}
