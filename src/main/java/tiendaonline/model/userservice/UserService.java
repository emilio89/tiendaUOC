
package tiendaonline.model.userservice;

import tiendaonline.model.comentario.Comentario;
import tiendaonline.model.ropa.Ropa;
import tiendaonline.model.userprofile.UserProfile;

import java.util.List;

import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public interface UserService {

	public UserProfile registerUser(String loginName, String clearPassword, UserProfileDetails userProfileDetails)
			throws DuplicateInstanceException;

	public UserProfile login(String loginName, String password, boolean passwordIsEncrypted)
			throws InstanceNotFoundException, IncorrectPasswordException;

	public UserProfile findUserProfile(Long userProfileId) throws InstanceNotFoundException;

	public void updateUserProfileDetails(Long userProfileId, UserProfileDetails userProfileDetails)
			throws InstanceNotFoundException;

	public void changePassword(Long userProfileId, String oldClearPassword, String newClearPassword)
			throws IncorrectPasswordException, InstanceNotFoundException;

	public void deleteUser(Long userProfileId) throws InstanceNotFoundException;

	public void setNumeroPuntos(UserProfile user, int numeroPuntos);

	/* COMENTARIO */

	public Comentario registrarComentario(String textoComentario, UserProfile usuario, Ropa Ropa)
			throws InstanceNotFoundException;

	public Comentario findComentario(Long idComentario) throws InstanceNotFoundException;



}
