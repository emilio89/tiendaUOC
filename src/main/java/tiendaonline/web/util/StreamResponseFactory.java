package tiendaonline.web.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

public final class StreamResponseFactory {
	    /**
	     * Se le pasa un File que contiene la informacion del archivo para generar un streamResponse
	     */
	    public StreamResponse getResponse(final File file) {
	        
	        return new StreamResponse() {
	            @Override
	            public void prepareResponse(Response response) {
	                response.setHeader("Content-Disposition", file.toString());
	            }
	            @Override
	            public InputStream getStream() throws IOException {
	                return new ByteArrayInputStream(file.getContent());
	            }
	            @Override
	            public String getContentType() {
	                return file.getType();
	            }
	        };
	        
	    }

}
