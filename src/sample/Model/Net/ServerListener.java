package sample.Model.Net;

import sample.Controller.Controller;

import javax.sound.midi.ControllerEventListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Anton on 2016-10-11.
 */
public class ServerListener {
    private static final int PORT = 23;
    private ServerSocket socket = null;
    private boolean listening = false;
    private ClientHandler currentClientHandler = null;
    protected Controller controller = null;

    public void start() throws IOException {
        listening = true;
        try {
            socket = new ServerSocket(PORT);
            System.out.println("Start listening on "+socket.getLocalPort());
            while (listening){
                Socket clientSocket = socket.accept();
                if (currentClientHandler == null){
                    currentClientHandler = new ClientHandler(clientSocket,controller);
                    System.out.println(clientSocket.getInetAddress() +" connected.");
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

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
