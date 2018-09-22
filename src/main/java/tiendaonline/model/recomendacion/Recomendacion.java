/********************* EMILIO DOMÍNGUEZ SACHEZ *********************/

package tiendaonline.model.recomendacion;

import tiendaonline.model.ropa.Ropa;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.BatchSize;

/**
 *
 * @author emilio
 */
@Entity
public class Recomendacion {
	private long idRecomendacion;
	private Ropa idRopa1;
	private Ropa idRopa2;
	private int numVeces;

	public Recomendacion() {
	};

	public Recomendacion(Ropa idRopa1, Ropa idRopa2, int numVeces) {
		this.idRopa1 = idRopa1;
		this.idRopa2 = idRopa2;
		this.numVeces = numVeces;
	}

	@Column(name = "idRecomendacion")
	@SequenceGenerator( // It only takes effect for
			name = "idRecomendacionGenerator", // databases providing identifier
			sequenceName = "RecomendacionSeq")
	// generators.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "idRecomendacionGenerator")
	public long getIdRecomendacion() {
		return idRecomendacion;
	}

	public void setIdRecomendacion(long idRecomendacion) {
		this.idRecomendacion = idRecomendacion;
	}

	@OneToOne
	@JoinColumn(name = "idRopa1")
	public Ropa getIdRopa1() {
		return idRopa1;
	}

	public void setIdRopa1(Ropa idRopa1) {
		this.idRopa1 = idRopa1;
	}

	@OneToOne
	@JoinColumn(name = "idRopa2")
	public Ropa getIdRopa2() {
		return idRopa2;
	}

	public void setIdRopa2(Ropa idRopa2) {
		this.idRopa2 = idRopa2;
	}

	public int getNumVeces() {
		return numVeces;
	}

	public void setNumVeces(int numVeces) {
		this.numVeces = numVeces;
	}

}
