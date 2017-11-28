package IndexingServer;

import java.net.SocketException;

import static IndexingServer.Constants.UDP_REGISTER;
import static IndexingServer.ServerController.register;
import static IndexingServer.Utils.stringToSocketAddress;

public class RendezvousServer extends UDPServer{
    public final static String TAG="UDP_INDEXING_SERVER>";


    public RendezvousServer(int UDP_Port) throws SocketException {
        super(UDP_Port);
    }

    public String process(String request) {
        Utils.log(TAG, request);
        String process_result="null";
        String commands[] = request.split(" ");

        switch(commands[0]){
            case "-udpr":
                //register
                process_result=register(commands[1], stringToSocketAddress(commands[2]), UDP_REGISTER);
                break;
            default:
                process_result="null";
                break;
        }
        return process_result;
    }

}
