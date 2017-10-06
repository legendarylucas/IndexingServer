package Interfaces;

public interface IndexingServerProcessRequest {
	
	public String register(String name, String privateAddress, String filePath);

	public String search(String name); 

}
