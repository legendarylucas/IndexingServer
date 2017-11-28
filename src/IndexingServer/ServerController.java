package IndexingServer;

import org.jetbrains.annotations.NotNull;

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


	//accept registration of peer and reply to client
	public static String register(String peerName, InetSocketAddress privateAddress, String mode) {
		//testing is only for 2 peers
		if(Peers.size()>2){
			clearPeers();
		}

		InetSocketAddress publicAddress;
		if(mode.equals(TCP_REGISTER)) {
			publicAddress = (InetSocketAddress) tcp_server.getSocket().getRemoteSocketAddress();
		}else if(mode.equals(UDP_REGISTER)){
			publicAddress =  udp_server.getRemoteAddress();
		}else{
			return "Registration "+Constants.ACTION_Fail;
		}
		String addResult=addPeer(peerName, privateAddress, publicAddress);
		return addResult+";"+searchPeer(publicAddress);

	}

	//add peer to peer list
	private static String addPeer(String peerName, InetSocketAddress privateAddress, InetSocketAddress publicAddress){
		for(Peer p:Peers){
			//if peer is already there
			if(p.getPeerName().equals(peerName)){
				p.updatePrivateAddress(privateAddress);
				p.updatePublicAddress(publicAddress);
				return "Registration "+p.getPeerName()+" "+
						p.getPrivateAddress()+" "+
						p.getPublicAddress()+" "+ Constants.ACTION_SUCCESS;
			}
		}

		//new peer
		Peer mPeer=new Peer(peerName, privateAddress, publicAddress);

		Peers.add(mPeer);
		return "Registration "+mPeer.getPeerName()+" "+
				mPeer.getPrivateAddress()+" "+
				mPeer.getPublicAddress()+" "+Constants.ACTION_SUCCESS;

	}

	//check remote address
	private static InetSocketAddress searchPeer(InetSocketAddress publicAddress){
		for(Peer p: Peers){
			if(p.getPublicAddress()!=publicAddress){
				return p.getPublicAddress();
			}
		}
		return null;
	}

	private static void clearPeers(){
		Utils.log(TAG, "registration cleared!");
		Peers.clear();
	}

	private static int portAllocation(){
		return 0;
	}

}
