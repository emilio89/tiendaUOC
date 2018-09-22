package tiendaonline.web.pages.blog;

import java.util.Date;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import tiendaonline.model.administradorservice.AdministradorService;
import tiendaonline.model.entradablog.EntradaBlog;
import tiendaonline.model.util.ListadoIndex;

public class VerBlog {
	@Property
	private EntradaBlog entradablog;

	@Inject
	private AdministradorService administradorService;

	public List<EntradaBlog> getListaEntradasBlog() {
		this.numeroElementos = administradorService.listaEntradasBlog().size();

		return administradorService.listaEntradasBlog();
	}

	public void setListaEntradasBlog(List<EntradaBlog> este) {
		this.listaEntradasBlog2 = este;
	}

	@Property
	private List<EntradaBlog> listaEntradasBlog2;

	private int numeroElementos;

	public boolean getIsMultiPaged() {
		if (this.numeroElementos > 10)
			return true;
		else
			return false;

	}

}
