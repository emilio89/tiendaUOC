/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiendaonline.model.lineapedido;

import tiendaonline.model.pedido.Pedido;
import tiendaonline.model.ropa.Ropa;
import javax.persistence.*;
/**
 *
 * @author emilio
 */
@Entity
public class LineaPedido {
  
    private long idLineaPedido;
    private double precioUnitario;
    private int numeroUnidades;
    
    private long idStockTalla;
    private Pedido pedido;
    private Ropa idRopa;
    
    public LineaPedido() {}

    public LineaPedido(Pedido pedido, long idStockTalla, Ropa idRopa,double precioUnitario, int numeroUnidades) {
        this.pedido = pedido;
        this.idStockTalla = idStockTalla;
        this.idRopa = idRopa;
        this.precioUnitario = precioUnitario;
        this.numeroUnidades = numeroUnidades;
    }
    
    @Column(name = "idLineaPedido")
    @SequenceGenerator( // It only takes effect for
    name = "idLineaPedidoGenerator", // databases providing identifier
    sequenceName = "LineaPedidoSeq")
    // generators.       
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idLineaPedidoGenerator")
    public long getIdLineaPedido() {
        return idLineaPedido;
    }

    public void setIdLineaPedido(long idLineaPedido) {
        this.idLineaPedido = idLineaPedido;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getNumeroUnidades() {
        return numeroUnidades;
    }

    public void setNumeroUnidades(int numeroUnidades) {
        this.numeroUnidades = numeroUnidades;
    }
    
  @ManyToOne(optional=false, fetch=FetchType.LAZY)
  @JoinColumn(name="idPedido")
  public Pedido getPedido() {
    return pedido;
  }

  public void setPedido(Pedido pedido) {
    this.pedido = pedido;
  }
  
  @ManyToOne(optional=false, fetch=FetchType.LAZY)
  @JoinColumn(name="idRopa")
  public Ropa getIdRopa() {
    return idRopa;
  }

  public void setIdRopa(Ropa idRopa) {
    this.idRopa = idRopa;
  }

  public long getIdStockTalla() {
    return idStockTalla;
  }

  public void setIdStockTalla(long idStockTalla) {
    this.idStockTalla = idStockTalla;
  }

 @Override
  public String toString() {
    return String.valueOf(this.getIdLineaPedido()); 
  }


  

 
    
    
    
}
