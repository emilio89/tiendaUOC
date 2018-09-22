package tiendaonline.model.userservice;

import java.util.Date;

public class UserProfileDetails {

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

	public UserProfileDetails(String firstName, String lastName, String email, String dni, long telefono,
			Date fechaNacimiento, String tipoUsuario, int numeroPuntos, String localidad, int cp, String provincia,
			String direccion, String piso, String numero) {
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

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getDni() {
		return dni;
	}

	public long getTelefono() {
		return telefono;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public String getLocalidad() {
		return localidad;
	}

	public int getCp() {
		return cp;
	}

	public String getProvincia() {
		return provincia;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getPiso() {
		return piso;
	}

	public String getNumero() {
		return numero;
	}

	public int getNumeroPuntos() {
		return numeroPuntos;
	}

}
