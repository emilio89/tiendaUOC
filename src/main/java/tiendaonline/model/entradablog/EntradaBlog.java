package tiendaonline.model.entradablog;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import tiendaonline.model.userprofile.UserProfile;


@Entity
public class EntradaBlog {
    private long idEntradaBlog;
    private UserProfile idUser;
    private Date fechaCreacion;  
    private String titulo;
    private String resumen;
    private String texto;
    
    
    
    
	public EntradaBlog(UserProfile idUser, Date fechaCreacion, String titulo, String resumen,
			String texto) {
		super();
		this.idUser = idUser;
		this.fechaCreacion = fechaCreacion;
		this.titulo = titulo;
		this.resumen = resumen;
		this.texto = texto;
	}
	
	public EntradaBlog() {}
    @Column(name = "idEntradaBlog")
    @SequenceGenerator( // It only takes effect for
    name = "idEntradaBlogGenerator", // databases providing identifier
    sequenceName = "EntradaBlogSeq")
    // generators.       
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idEntradaBlogGenerator")
	public long getIdEntradaBlog() {
		return idEntradaBlog;
	}
	public void setIdEntradaBlog(long idEntradaBlog) {
		this.idEntradaBlog = idEntradaBlog;
	}
	@ManyToOne
	@JoinColumn(name="idUser")
	public UserProfile getIdUser() {
		return idUser;
	}
	public void setIdUser(UserProfile idUser) {
		this.idUser = idUser;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getResumen() {
		return resumen;
	}
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}


}
