package IndexingServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class RelayServer implements Runnable{
    DatagramSocket serverSocket1, serverSocket2;
    InetSocketAddress remoteAddress1, remoteAddress2;

    RelayServer(int port1, int port2) throws SocketException{
        serverSocket1=new DatagramSocket(port1);
        serverSocket2=new DatagramSocket(port2);
    }


    @Override
    public void run() {
        socket1ReceivceAndRelay();
        socket2ReceivceAndRelay();
    }

    private void socket1ReceivceAndRelay(){
        Thread startPipe= new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()){
                try{
                    byte[] data = new byte[1280];
                    DatagramPacket dp = new DatagramPacket(data,data.length);
                    serverSocket1.receive(dp);
                    remoteAddress1=new InetSocketAddress(dp.getAddress(),dp.getPort());
                    if(remoteAddress2!=null) {
                        //relay to remote address 2
                        DatagramPacket relayPacket = new DatagramPacket(dp.getData(), dp.getData().length, remoteAddress2);
                        socket2send(relayPacket);
                    }
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        });
        Main.getExecutor().submit(startPipe);
    }

    private void socket2ReceivceAndRelay(){
        Thread startPipe= new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()){
                try{
                    byte[] data = new byte[1280];
                    DatagramPacket dp = new DatagramPacket(data,data.length);
                    serverSocket2.receive(dp);
                    remoteAddress2=new InetSocketAddress(dp.getAddress(),dp.getPort());
                    if(remoteAddress1!=null) {
                        //relay to remote address 1
                        DatagramPacket relayPacket = new DatagramPacket(dp.getData(), dp.getData().length, remoteAddress1);
                        socket1send(relayPacket);
                    }
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        });
        Main.getExecutor().submit(startPipe);
    }

    private void socket2send(DatagramPacket dp) throws IOException{
        serverSocket2.send(dp);
    }

    private void socket1send(DatagramPacket dp) throws IOException{
        serverSocket2.send(dp);
    }

}
