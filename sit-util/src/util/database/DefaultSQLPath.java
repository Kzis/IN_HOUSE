package util.database;

public class DefaultSQLPath {

	private Class<?> className;
	private String path;
	
	public DefaultSQLPath(Class<?> className, String path) {
		this.className = className;
		this.path = path;
	}

	public Class<?> getClassName() {
		return className;
	}

	public void setClassName(Class<?> className) {
		this.className = className;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
