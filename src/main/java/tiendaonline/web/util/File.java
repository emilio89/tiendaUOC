package tiendaonline.web.util;

public final class File {
    private long id;
    private String title;
    private String name;
    private String type;
    private long size;
    private byte[] content;
    private String extension;
   //Getters y setters si no usan lombok
    /**
     * NO CAMBIAR esta parte pues es utilizada en la clase de creacion de StreamResponse (StreamResponseFactory.java)
     */
    @Override
    public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("inline;filename=");
            builder.append(name);
            builder.append(".");
            builder.append(extension);
            return builder.toString();
    }
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
    
}
