package Modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import IndexingServer.IndexingServer;

public class ServerController implements Runnable{
	private static final String TAG="ServerController>";
	private Server server;
//	private boolean terminate=false;
	
	public ServerController(Server server){
		this.server=server;
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
				Utils.log(TAG, "Server Shutdown");
				server.stopServer();
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

}
