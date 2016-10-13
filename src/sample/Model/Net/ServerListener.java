package sample.Model.Net;

import sample.Controller.Controller;
import sample.Model.SIP.SipHandler;

import javax.sound.midi.ControllerEventListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Anton on 2016-10-11.
 */
public class ServerListener {
    private static final int PORT = 5060;
    private ServerSocket socket = null;
    private boolean listening = false;
    private ClientHandler currentClientHandler = null;
    protected Controller controller = null;
    private SipHandler sipHandler;

    public void start() throws IOException {
        this.sipHandler = new SipHandler();
        this.listening = true;
        try {
            socket = new ServerSocket(PORT);
            System.out.println("Start listening on "+socket.getLocalPort());
            while (listening){
                Socket clientSocket = socket.accept();
                if (currentClientHandler == null){

                    currentClientHandler = new ClientHandler(clientSocket, controller, this);
                    currentClientHandler.setRemoteAddress(clientSocket.getInetAddress());
                    System.out.println(clientSocket.getInetAddress() +" connected.");
                }else{
                    PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
                    pw.println("BUSY");
                    pw.flush();
                    pw.close();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    clientSocket.close();
                }
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }finally {
            if (socket != null) socket.close();
        }
    }

    public void processRemoteMessage(String msg){
        sipHandler.processNextState(msg, currentClientHandler);
    }

    public void inviteRemoteClient(String ip, int port){
        Socket socket = new Socket();

        InetAddress serverAddress;
        try{
            serverAddress = InetAddress.getByName(ip);
        }catch(UnknownHostException uhe){
            //controller.addMessage("Could not resolve host.");
            System.out.println("Could not resolve host.");
            return;
        }

        try{
            socket = new Socket(serverAddress, port);
        }catch(Exception e){
            //controller.addMessage("Could not connect to: " + host);
            System.out.println("Could not connect to host.");
            return;
        }

        try {
            if(currentClientHandler == null){
                currentClientHandler = new ClientHandler(socket, controller, this);
                currentClientHandler.setRemoteAddress(socket.getInetAddress());
                sipHandler.invokeInvite(currentClientHandler);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not connect to host.");
        }
    }

    public void disconnectClient(){
        //Disconnect the client currentClientHandler
        if (currentClientHandler != null)
            currentClientHandler.stop();
        //Set currentClientHandler = null;
        currentClientHandler = null;

        System.out.println("Client disconnected");
    }

    public ClientHandler getCurrentClientHandler(){
        return this.currentClientHandler;
    }

    public void stopListening(){
        listening = false;
        try {
            if(socket!=null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void invokeError(){
        sipHandler.invokeError(currentClientHandler);
    }

    public void invokeAcceptInvite(){
        sipHandler.invokeAcceptInvite(currentClientHandler);
    }

    public void invokeBye(){
        if(currentClientHandler!=null)
            sipHandler.invokeBye(currentClientHandler);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
