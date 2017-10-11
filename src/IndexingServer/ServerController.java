package IndexingServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import static IndexingServer.Constants.TCP_REGISTER;
import static IndexingServer.Constants.UDP_REGISTER;

public class ServerController implements Runnable{
	private static final String TAG="ServerController>";
	private static TCPServer tcp_server;
	private static UDPServer udp_server;
	private static ArrayList<Peer> Peers;
//	private boolean terminate=false;
	
	public ServerController(TCPServer server1, UDPServer server2){
		this.tcp_server=server1;
		this.udp_server=server2;
		Peers=new ArrayList<Peer>();

	}

	@Override
	public void run() {
		 BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));
//		 while(true){
//			if(terminate||Thread.currentThread().isInterrupted()){
//	                Utils.log(TAG, "Controller is terminated");
//	                server.getExecutor().shutdownNow();
//	                System.exit(0);
//	                break;
//	            }
		System.out.println("Enter exit to shutdown server >");
		try {
			String command = inputStream.readLine();
			switch (command){
			case "exit":
				Utils.log(TAG, "TCPServer Shutdown");
				tcp_server.stopServer();
				Utils.log(TAG, "UDPServer Shutdown");
				udp_server.stopServer();
				System.exit(0);
				break;
			default:
				break;
				
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				 
//		 }
		
	}


	public static String register(String peerName, String privateAddress, String fileName, String mode) {
		InetSocketAddress publicAddress;
		if(mode.equals(TCP_REGISTER)) {
			publicAddress = (InetSocketAddress) tcp_server.getSocket().getRemoteSocketAddress();
		}else if(mode.equals(UDP_REGISTER)){
			publicAddress = (InetSocketAddress) udp_server.getSocket().getRemoteSocketAddress();
		}else{
			return "Registration "+Constants.ACTION_Fail;
		}
		for(Peer p:Peers){
			//if peer is already there
			if(p.getPeerName().equals(peerName)){
				p.addFile(fileName);
				p.updatePrivateAddress(privateAddress);
				p.updatePublicAddress(publicAddress.toString());
				return "Registration "+p.getPeerName()+" "+
						p.getPrivateAddress()+" "+
						p.getPublicAddress()+" "+ Constants.ACTION_SUCCESS;
			}
		}

		//new peer
		Peer mPeer=new Peer(peerName, privateAddress, publicAddress.toString());
		mPeer.addFile(fileName);

		Peers.add(mPeer);
		return "Registration "+mPeer.getPeerName()+" "+
				mPeer.getPrivateAddress()+" "+
				mPeer.getPublicAddress()+" "+Constants.ACTION_SUCCESS;
	}


	public static String search(String name) {
		String response="null";
		for(Peer p:Peers){
			for(String file:p.getFiles()){
				if(file.contains(name)){
					Utils.log(TAG, "found file");
					if(response.equals("null")){
						response= p.getPublicAddress()+" "+p.getPrivateAddress()+" "+file+";";
						Utils.log(TAG, response);
					}else{
						response= response+p.getPublicAddress()+" "+p.getPrivateAddress()+" "+file+";";
						Utils.log(TAG, "more");
						Utils.log(TAG, response);
					}
				}
			}
		}
		return response;
	}
}
