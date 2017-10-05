package Modules;

import java.util.ArrayList;

public class Peer{
	private String peerName;
	private ArrayList<String> files=new ArrayList<String>();
	
	public Peer(String peerName){
		this.peerName=peerName;
	}
	
	public String getName(){
		return peerName;
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
