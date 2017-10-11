package IndexingServer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    public static void main(String[] args) {

        String TAG="MAIN";

        try {
            Utils.log(TAG, "Starting server");
            System.out.print("Please specify a port for TCP indexing server >");
            int TCP_Port=Integer.valueOf(Utils.systemRead());
            System.out.print("Please specify a port for UDP indexing server >");
            int UDP_Port=Integer.valueOf(Utils.systemRead());
            Utils.log(TAG, "Waiting for clients...");

            ExecutorService executor= Executors.newFixedThreadPool(3);
            TCPIndexingServer tcp_server=new TCPIndexingServer(TCP_Port);
            UDPIndexingServer udp_server=new UDPIndexingServer(UDP_Port);
            ServerController controller=new ServerController(tcp_server, udp_server);
            executor.submit(tcp_server);
            executor.submit(udp_server);
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
}
