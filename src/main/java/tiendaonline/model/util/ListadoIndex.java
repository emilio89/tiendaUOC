/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.model.util;

public class ListadoIndex {

	long idRopa;
	String direccionImagen;
	String nombre;
	String descripcion;
	int cantidad;

	public ListadoIndex(long idRopa, String direccionImagen, String nombre, String descripcion) {
		this.idRopa = idRopa;
		this.direccionImagen = direccionImagen;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public ListadoIndex(long idRopa, String direccionImagen, String nombre, int cantidad) {
		this.idRopa = idRopa;
		this.direccionImagen = direccionImagen;
		this.nombre = nombre;
		this.cantidad = cantidad;
	}

	public long getIdRopa() {
		return idRopa;
	}

	public void setIdRopa(long idRopa) {
		this.idRopa = idRopa;
	}

	public String getDireccionImagen() {
		return direccionImagen;
	}

	public void setDireccionImagen(String direccionImagen) {
		this.direccionImagen = direccionImagen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
