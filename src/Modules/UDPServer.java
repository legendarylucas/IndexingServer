package Modules;

import Interfaces.ProcessRequest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer implements Runnable, ProcessRequest{
    DatagramSocket serverSocket;
    byte[] receiveData = new byte[1024];
    byte[] sendData = new byte[1024];
    private boolean terminate=false;
    public final static String TAG="UDP_SERVER>";

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

                String sentence = new String( receivePacket.getData());
                String output=process(sentence);
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                sendData = output.getBytes();
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

    public DatagramSocket getSocket(){
        return serverSocket;
    }
}
