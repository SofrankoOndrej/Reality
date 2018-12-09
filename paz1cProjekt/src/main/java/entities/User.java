package entities;

public class User {

	private Long id;
	private String name;
	private String surname;
	private String username;
	private String password;
	private String email;
	private String lastBoundingBox;
	private int lastZoom;
	private String cacheFolderPath;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastBoundingBox() {
		return lastBoundingBox;
	}

	public void setLastBoundingBox(String lastBoundingBox) {
		this.lastBoundingBox = lastBoundingBox;
	}

	public String getCacheFolderPath() {
		return cacheFolderPath;
	}

	public void setCacheFolderPath(String cacheFolderPath) {
		this.cacheFolderPath = cacheFolderPath;
	}

	public int getLastZoom() {
		return lastZoom;
	}

	public void setLastZoom(int lastZoom) {
		this.lastZoom = lastZoom;
	}

	@Override
	public String toString() {
		return "User: [id: " + getId() + ", name: " + getName() + ", surname: " + getSurname() + ", email: "
				+ getEmail() + ", username: " + getUsername() + " ]";
	}

}
