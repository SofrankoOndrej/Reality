package persistent;

public class UsernameNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3395264073733020901L;
	
	private String username;
	
	public UsernameNotFoundException(String username) {
		this.username = username;
	}
	
	public String getUseranem() {
		return username;
	}
}
