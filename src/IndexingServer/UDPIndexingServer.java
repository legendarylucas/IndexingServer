package IndexingServer;

import java.net.SocketException;

import static IndexingServer.Constants.TCP_REGISTER;
import static IndexingServer.ServerController.register;
import static IndexingServer.ServerController.search;

public class UDPIndexingServer extends UDPServer{
    public final static String TAG="UDP_INDEXING_SERVER>";


    public UDPIndexingServer(int UDP_Port) throws SocketException {
        super(UDP_Port);
    }

    public String process(String request) {
        Utils.log(TAG, request);
        String process_result="null";
        String commands[] = request.split(" ");

        switch(commands[0]){
            case "-udpr":
                //register
                process_result=register(commands[1], commands[2], commands[3], TCP_REGISTER);
                break;
            case "-udps":
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
