package IndexingServer;

import java.io.IOException;
import java.net.*;

public class UDPServer implements Runnable, ProcessRequest{
    DatagramSocket serverSocket;
    byte[] receiveData = new byte[1024];
    byte[] sendData = new byte[1024];
    private boolean terminate=false;
    public final static String TAG="UDP_SERVER>";
    private InetSocketAddress remoteAddress;

    public UDPServer(int UDP_Port) throws SocketException {
        this.serverSocket = new DatagramSocket(UDP_Port);

    }

    public void stopServer(){
        terminate=true;
    }

    @Override
    public void run() {

        while(true)
        {
            if(terminate||Thread.currentThread().isInterrupted()){

                Utils.log(TAG, "UDPServer is terminated");
                if(serverSocket!=null){
                    serverSocket.close();
                }
                System.exit(0);
                break;
            }

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePacket);

                String sentence = new String( receivePacket.getData()).trim();
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                remoteAddress=new InetSocketAddress(IPAddress,port);
                String output=process(sentence);
                sendData = output.getBytes();
                Utils.log(TAG,output);
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        terminate=true;

    }

    @Override
    public String process(String read) {
        Utils.log(TAG, "data received " + read);
        return null;
    }

    public InetSocketAddress getRemoteAddress(){
        return remoteAddress;
    }
}
