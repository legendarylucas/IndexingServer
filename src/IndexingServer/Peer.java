package IndexingServer;

import java.util.ArrayList;

public class Peer{
	private String privateAddress;
	private String publicAddress;
	private String peerName;
	private ArrayList<String> files=new ArrayList<String>();
	
	public Peer(String peerName, String privateAddress, String publicAddress){
		this.peerName=peerName;
		this.privateAddress =privateAddress;
		this.publicAddress=publicAddress;
	}

	public String getPeerName(){return peerName;}

	public void updatePeerName(String name){
		this.peerName=name;
	}

	public String getPrivateAddress(){
		return privateAddress;
	}

	public void updatePrivateAddress(String address){
		this.privateAddress=address;
	}

	public String getPublicAddress(){return publicAddress;}

	public void updatePublicAddress(String address){
		this.publicAddress=address;
	}
	
	public void addFile(String fileName){
		files.add(fileName);
	}
	
	public String getFile(String documentName){
		for(String fileName: files){
			if(fileName.contains(documentName)){
				return fileName;
			}
		}
		return "null";
	}

	 public ArrayList<String> getFiles(){
		 return files;
	 }

}
