/********************* EMILIO DOMÍNGUEZ SANCHEZ *********************/
package tiendaonline.model.ropa;

import tiendaonline.model.comentario.Comentario;
import tiendaonline.model.lineapedido.LineaPedido;
import tiendaonline.model.stocktalla.StockTalla;
import tiendaonline.model.adjunto.Adjunto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Emilio
 */
@Entity
public class Ropa {
	private long idRopa;
	private String nombre;
	private double precio;
	private String color;
	private String marca;
	private String descripcion;
	private int numPuntos;
	private long idEtiqueta;
	private long idCategoria;
	private Date fechaRegistro;
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	private List<StockTalla> stocktalla = new ArrayList<StockTalla>();
	private List<LineaPedido> lineaPedido = new ArrayList<LineaPedido>();

	private List<Adjunto> adjuntos = new ArrayList<Adjunto>();

	public Ropa() {
	};

	public Ropa(String nombre, double precio, String color, String marca, String descripcion, Long categoria,
			Date fechaRegistro) {

		/**
		 * NOTE: "idRopa" *must* be left as "null" since its value is
		 * automatically generated.
		 */
		this.nombre = nombre;
		this.precio = precio;
		this.color = color;
		this.marca = marca;
		this.descripcion = descripcion;
		this.idCategoria = categoria;
		this.fechaRegistro = fechaRegistro;

	}

	@Column(name = "idRopa")
	@SequenceGenerator( // It only takes effect for
			name = "idRopaGenerator", // databases providing identifier
			sequenceName = "RopaSeq")
	// generators.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "idRopaGenerator")
	public long getIdRopa() {
		return idRopa;
	}

	public void setIdRopa(long idRopa) {
		this.idRopa = idRopa;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getNumPuntos() {
		return numPuntos;
	}

	public void setNumPuntos(int numPuntos) {
		this.numPuntos = numPuntos;
	}

	@OneToMany(mappedBy = "ropa", fetch = FetchType.LAZY)
	@BatchSize(size = 20)
	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	@OneToMany(mappedBy = "ropa", fetch = FetchType.LAZY)
	@BatchSize(size = 20)
	@Fetch(value = FetchMode.SUBSELECT) // añadido para poder tener varios fetch
	public List<StockTalla> getStocktalla() {
		return stocktalla;
	}

	public void setStocktalla(List<StockTalla> stocktalla) {
		this.stocktalla = stocktalla;
	}

	/*
	 * ManyToMany public List<Etiqueta> getEtiquetas() { return etiquetas; }
	 * 
	 * public void setEtiquetas(List<Etiqueta> etiquetas) { this.etiquetas =
	 * etiquetas; }
	 */

	@OneToMany(mappedBy = "ropa")
	@BatchSize(size = 20)
	public List<Adjunto> getAdjuntos() {
		return adjuntos;
	}

	public void setAdjuntos(List<Adjunto> adjuntos) {
		this.adjuntos = adjuntos;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public void addComentarios(Comentario coment) {

		comentarios.add(coment);

	}

	public void addAdjunto(Adjunto a) {

		adjuntos.add(a);
	}

	public void addStockTalla(StockTalla stock) {
		stocktalla.add(stock);

	}

	public long getIdEtiqueta() {
		return idEtiqueta;
	}

	public void setIdEtiqueta(long idEtiqueta) {
		this.idEtiqueta = idEtiqueta;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@OneToMany(mappedBy = "idRopa", fetch = FetchType.LAZY)
	@Fetch(value = FetchMode.SUBSELECT) // añadido para poder tener varios fetch
	public List<LineaPedido> getLineaPedido() {
		return lineaPedido;
	}

	public void setLineaPedido(List<LineaPedido> lineaPedido) {
		this.lineaPedido = lineaPedido;
	}
}
