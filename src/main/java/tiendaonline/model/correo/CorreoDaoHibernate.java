package tiendaonline.model.correo;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@Repository("CorreoDao")
public class CorreoDaoHibernate 
extends GenericDaoHibernate<Correo, Long> implements CorreoDao {
	
	
    @Autowired
    private SessionFactory sessionFactory;
  
  public Correo findByIdCorreo(long idCorreo) throws InstanceNotFoundException {

	  Correo correo = (Correo) getSession().createQuery(
  			"SELECT u FROM Correo u WHERE u.idCorreo = :idCorreo")
  			.setParameter("idCorreo", idCorreo)
  			.uniqueResult();
  	if (correo == null) {
 			throw new InstanceNotFoundException(idCorreo, Correo.class.getName());
  	} else {
  		return correo;
  	}
}
  
  
  public List<Correo> listaCorreo() 
  {
  
      Query query = getSession().createQuery("SELECT u FROM Correo u");
      List<Correo> listaEntradaBlog= query.list();
      return listaEntradaBlog;

   }
   
     
     
   
  public void modificarCorreo(Correo correo)
  {
     sessionFactory.getCurrentSession().save(correo);

    } 
  


}
