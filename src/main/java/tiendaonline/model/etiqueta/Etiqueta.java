
package tiendaonline.model.etiqueta;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import tiendaonline.model.ropa.Ropa;

/**
 *
 * @author Emilio
 */
@Entity
public class Etiqueta {

	private long idEtiqueta;
	private String nombreEtiqueta;
	private int porcentajeDescuento;
	private List<Ropa> ropas;

	public Etiqueta() {
	};

	public Etiqueta(String nombreEtiqueta) {
		this.nombreEtiqueta = nombreEtiqueta;
	}

	public Etiqueta(long idEtiqueta, String nombreEtiqueta) {
		this.idEtiqueta = idEtiqueta;
		this.nombreEtiqueta = nombreEtiqueta;
	}

	public Etiqueta(String nombreEtiqueta, int porcentajeDescuento) {
		this.nombreEtiqueta = nombreEtiqueta;
		this.porcentajeDescuento = porcentajeDescuento;
	}

	public Etiqueta(long idEtiqueta, String nombreEtiqueta, int porcentajeDescuento) {
		this.idEtiqueta = idEtiqueta;
		this.nombreEtiqueta = nombreEtiqueta;
		this.porcentajeDescuento = porcentajeDescuento;
	}

	@Column(name = "idEtiqueta")
	@SequenceGenerator( // It only takes effect for
			name = "idEtiquetaGenerator", // databases providing identifier
			sequenceName = "EtiquetaSeq")
	// generators.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "idEtiquetaGenerator")
	public long getIdEtiqueta() {
		return idEtiqueta;
	}

	public void setIdEtiqueta(long idEtiqueta) {
		this.idEtiqueta = idEtiqueta;
	}

	public String getNombreEtiqueta() {
		return nombreEtiqueta;
	}

	public void setNombreEtiqueta(String nombreEtiqueta) {
		this.nombreEtiqueta = nombreEtiqueta;
	}

	public int getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(int porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}

	@Override
	public String toString() {
		return String.valueOf(this.getIdEtiqueta());
	}

	@OneToMany(mappedBy = "idEtiqueta")
	public List<Ropa> getRopas() {
		return ropas;
	}

	public void setRopas(List<Ropa> ropas) {
		this.ropas = ropas;
	}

}
