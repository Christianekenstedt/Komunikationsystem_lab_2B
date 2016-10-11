package sample.Model.Net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Anton on 2016-10-11.
 */
public class ServerListener {
    private static final int PORT = 12345;
    private ServerSocket socket = null;
    private boolean listening = false;
    private ClientHandler currentClientHandler = null;

    public void start() throws IOException {
        listening = true;
        try {
            socket = new ServerSocket(PORT);
            while (listening){
                Socket clientSocket = socket.accept();
                if (currentClientHandler == null){
                    currentClientHandler = new ClientHandler(clientSocket, null);
                }
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }finally {
            if (socket != null) socket.close();
        }

    }

    public void disconnectClient(){
        //Disconnect the client currentClientHandler
        if (currentClientHandler != null) currentClientHandler.stop();
        //Set currentClientHandler = null;
        currentClientHandler = null;
    }

    public void stopListening(){
        listening = false;
    }

}
