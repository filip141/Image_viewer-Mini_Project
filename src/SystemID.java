
public class SystemID {
	String system;
	public SystemID() {
		system = System.getProperty("os.name");
	}
	
	public boolean isWindows(){
		Boolean found;
		

		found = system.contains("indows");
		return found;
	}
	public boolean isLinux(){
		Boolean found;
		
		found = system.contains("nux") || system.contains("buntu") || system.contains("nix");
		return found;
	}
	public boolean isMac(){
		Boolean found;
		
		found = system.contains("mac");
		return found;
	}
}
