package IndexingServer;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import Modules.Peer;
import Modules.TCPServer;
import Modules.Utils;

import static Interfaces.Constants.TCP_REGISTER;
import static Modules.ServerController.register;
import static Modules.ServerController.search;

public class TCPIndexingServer extends TCPServer {
	private final static String TAG="TCP_INDEXING_SERVER>";
	private ArrayList<Peer> Peers;
	private static ExecutorService executor;
public TCPIndexingServer(int port) {
		super(port);
		Peers=new ArrayList<Peer>();
	}


	public ExecutorService getExecutor(){
		return executor;
	}
	
	
	@Override
	public String process(String request) {
		Utils.log(TAG, request);
		String process_result="null";
		String commands[] = request.split(" ");
		
		switch(commands[0]){
	        case "-tcpr":
	        	//register
	        	process_result=register(commands[1], commands[2], commands[3], TCP_REGISTER);
	            break;
	        case "-tcps":
	        	//search
	        	process_result=search(commands[1]);
	            break;
	        default:
	        	process_result="null";
	            break;
		}
		return process_result;
	}



	
}
