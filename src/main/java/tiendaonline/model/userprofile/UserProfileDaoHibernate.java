package tiendaonline.model.userprofile;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.pedido.Pedido;

@Repository("userProfileDao")
public class UserProfileDaoHibernate extends GenericDaoHibernate<UserProfile, Long> implements UserProfileDao {

	public UserProfile findByLoginName(String loginName) throws InstanceNotFoundException {

		UserProfile userProfile = (UserProfile) getSession()
				.createQuery("SELECT u FROM UserProfile u WHERE u.loginName = :loginName")
				.setParameter("loginName", loginName).uniqueResult();
		if (userProfile == null) {
			throw new InstanceNotFoundException(loginName, UserProfile.class.getName());
		} else {
			return userProfile;
		}

	}

	public List<UserProfile> getListaUsuarios() throws InstanceNotFoundException {

		Query query = getSession().createQuery("SELECT u FROM UserProfile u");
		List<UserProfile> listaTotosUsuarios = query.list();
		return listaTotosUsuarios;

	}

}