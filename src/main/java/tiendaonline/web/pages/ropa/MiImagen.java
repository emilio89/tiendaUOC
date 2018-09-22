/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendaonline.web.pages.ropa;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import tiendaonline.model.ropaservice.RopaService;
import tiendaonline.web.pages.ropa.VerRopa;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

/**
 *
 * @author emilio
 */
public class MiImagen {
	@Inject
	private RopaService ropaService;

	public StreamResponse onActivate(final Long parameter) {
		return new StreamResponse() {
			InputStream inputStream;

			@Override
			public void prepareResponse(Response response) {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				try {
					inputStream = ropaService.recuperarAdjunto(parameter).getImagen().getBinaryStream();
				} catch (InstanceNotFoundException ex) {
					Logger.getLogger("FALLA inputStream1" + VerRopa.class.getName()).log(Level.SEVERE, null, ex);
				} catch (SQLException ex) {
					Logger.getLogger("FALLA inputStream1" + VerRopa.class.getName()).log(Level.SEVERE, null, ex);
				}

				try {
					response.setHeader("Content-Length", "" + inputStream.available());
				} catch (IOException e) {
				}
			}

			@Override
			public String getContentType() {
				return "png/Image";
			}

			@Override
			public InputStream getStream() throws IOException {
				return inputStream;
			}
		};
	}

	public static String getPathImagenes(Request request) {
		String url = request.getPath();
		String[] urlSplitted = url.split("/");
		if (urlSplitted.length == 3)
			return "../";
		if (urlSplitted.length == 4)
			return "../../";
		if (urlSplitted.length == 5)
			return "../../../";
		return "";
	}
}
