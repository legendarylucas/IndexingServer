package IndexingServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Interfaces.Constants;
import Interfaces.IndexingServerProcessRequest;
import Modules.Peer;
import Modules.Server;
import Modules.ServerController;
import Modules.Utils;

public class IndexingServer extends Server implements IndexingServerProcessRequest{
	private final static String TAG="IndexingServer>";
	private ArrayList<Peer> Peers;
	private static ExecutorService executor;
public IndexingServer(int port) {
		super(port);
		Peers=new ArrayList<Peer>();
	}

	public static void main(String[] args) {
		
		Utils.log(TAG, "Starting server");
		System.out.print("Please specify a port for indexing server >");
		try {
			int port=Integer.valueOf(Utils.systemRead());
			Utils.log(TAG, "Waiting for clients..."); 
			ExecutorService executor=Executors.newFixedThreadPool(2);
			IndexingServer server=new IndexingServer(port);
			ServerController controller=new ServerController(server);
			executor.submit(server);
			executor.submit(controller);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		
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
	        case "-r":
	        	//register
	        	process_result=register(commands[1], commands[2]);
	            break;
	        case "-s": 
	        	//search
	        	process_result=search(commands[1]);
	            break;
	        default:
	        	process_result="null";
	            break;
		}
		return process_result;
	}

	@Override
	public String register(String name, String fileName) {
		for(Peer p:Peers){
			//if peer is already there
			if(p.getName().equals(name)){
				p.addFile(fileName);
				return "Registration "+p.getName()+" "+Constants.ACTION_SUCCESS;
			}
		}
		
		//new peer
		Peer mPeer=new Peer(name);
		mPeer.addFile(fileName);

		Peers.add(mPeer);
		return "Registration "+mPeer.getName()+" "+Constants.ACTION_SUCCESS;
	}

	@Override
	public String search(String name) {
		String response="null";
		for(Peer p:Peers){
			for(String file:p.getFiles()){
				if(file.contains(name)){
					Utils.log(TAG, "found file");
					if(response.equals("null")){
						response= p.getName()+" "+file+";";
						Utils.log(TAG, response);
					}else{
						response= response+p.getName()+" "+file+";";
						Utils.log(TAG, "more");
						Utils.log(TAG, response);
					}
				}
			}
		}
		return response;
	
	}

	
}
