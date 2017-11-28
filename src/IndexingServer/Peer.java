package IndexingServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class Peer{
	private InetSocketAddress privateAddress;
	private InetSocketAddress publicAddress;
	private String peerName;

	public Peer(String peerName, InetSocketAddress privateAddress, InetSocketAddress publicAddress){
		this.peerName=peerName;
		this.privateAddress =privateAddress;
		this.publicAddress=publicAddress;
	}

	public String getPeerName(){return peerName;}

	public void updatePeerName(String name){
		this.peerName=name;
	}

	public InetSocketAddress getPrivateAddress(){
		return privateAddress;
	}

	public void updatePrivateAddress(InetSocketAddress address){
		if(address!=null) {
			this.privateAddress = address;
		}
	}

	public InetSocketAddress getPublicAddress(){return publicAddress;}

	public void updatePublicAddress(InetSocketAddress address){
		this.publicAddress=address;
	}
	

}
