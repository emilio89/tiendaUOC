package tiendaonline.model.correo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
public class Correo {
    private long idCorreo;
    private String asuntoCorreo;
    private String nombreCorreo;
    private String instruccionesCorreo;
    private String correo;
    
	public Correo(String asuntoCorreo,String nombreCorreo, String instruccionesCorreo, String correo) {
		super();
		this.asuntoCorreo = asuntoCorreo;
		this.nombreCorreo = nombreCorreo;
		this.instruccionesCorreo = instruccionesCorreo;
		this.correo = correo;
	}

	public Correo() {}
    @Column(name = "idCorreo")
    @SequenceGenerator( // It only takes effect for
    name = "idCorreoGenerator", // databases providing identifier
    sequenceName = "CorreoSeq")
    // generators.       
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idCorreoGenerator")
	public long getIdCorreo() {
		return idCorreo;
	}
	public void setIdCorreo(long idCorreo) {
		this.idCorreo = idCorreo;
	}
	public String getInstruccionesCorreo() {
		return instruccionesCorreo;
	}
	public void setInstruccionesCorreo(String instruccionesCorreo) {
		this.instruccionesCorreo = instruccionesCorreo;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getAsuntoCorreo() {
		return asuntoCorreo;
	}

	public void setAsuntoCorreo(String asuntoCorreo) {
		this.asuntoCorreo = asuntoCorreo;
	}

	public String getNombreCorreo() {
		return nombreCorreo;
	}

	public void setNombreCorreo(String nombreCorreo) {
		this.nombreCorreo = nombreCorreo;
	}


}
