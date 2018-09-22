package tiendaonline.model.userprofile;

import tiendaonline.model.comentario.Comentario;
import tiendaonline.model.entradablog.EntradaBlog;
import tiendaonline.model.pedido.Pedido;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class UserProfile {

	private Long userProfileId;
	private String loginName;
	private String encryptedPassword;
	private String firstName;
	private String lastName;
	private String email;
	private String dni;
	private long telefono;
	private Date fechaNacimiento;
	private String tipoUsuario;
	private int numeroPuntos;

	private String localidad;
	private int cp;
	private String provincia;
	private String direccion;
	private String piso;
	private String numero;

	private List<Comentario> comentarios;
	private List<Pedido> pedidos;
	private List<EntradaBlog> entradas;
	public UserProfile() {
	}

	public UserProfile(String loginName, String encryptedPassword, String firstName, String lastName, String email,
			String dni, long telefono, Date fechaNacimiento, String tipoUsuario, int numeroPuntos, String localidad,
			int cp, String provincia, String direccion, String piso, String numero) {

		/**
		 * NOTE: "userProfileId" *must* be left as "null" since its value is
		 * automatically generated.
		 */

		this.loginName = loginName;
		this.encryptedPassword = encryptedPassword;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.dni = dni;
		this.telefono = telefono;
		this.fechaNacimiento = fechaNacimiento;
		this.tipoUsuario = tipoUsuario;
		this.numeroPuntos = numeroPuntos;
		this.localidad = localidad;
		this.cp = cp;
		this.provincia = provincia;
		this.direccion = direccion;
		this.piso = piso;
		this.numero = numero;

	}

	@Column(name = "usrId")
	@SequenceGenerator( // It only takes effect for
			name = "UserProfileIdGenerator", // databases providing identifier
			sequenceName = "UserProfileSeq")
	// generators.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UserProfileIdGenerator")
	public Long getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "enPassword")
	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public long getTelefono() {
		return telefono;
	}

	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public int getNumeroPuntos() {
		return numeroPuntos;
	}

	public void setNumeroPuntos(int numeroPuntos) {
		this.numeroPuntos = numeroPuntos;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public int getCp() {
		return cp;
	}

	public void setCp(int cp) {
		this.cp = cp;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "Usuario [UsuarioId=" + userProfileId + ", loginName=" + loginName + ", encryptedPassword="
				+ encryptedPassword + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", dni=" + dni + ", telefono=" + telefono + ", fechaNacimiento=" + fechaNacimiento + "]";
	}

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	@BatchSize(size = 20)
	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	@OneToMany(mappedBy = "usuario")
	@BatchSize(size = 20)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	@OneToMany(mappedBy = "idUser")
	@BatchSize(size = 20)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<EntradaBlog> getEntradas() {
		return entradas;
	}

	public void setEntradas(List<EntradaBlog> entradas) {
		this.entradas = entradas;
	}
	

}
