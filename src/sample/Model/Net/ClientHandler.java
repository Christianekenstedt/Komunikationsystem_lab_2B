package sample.Model.Net;

import sample.Controller.Controller;
import sample.Model.SIP.*;

import java.io.*;
import java.net.*;

/**
 * Created by Anton on 2016-10-11.
 */
public class ClientHandler {
    private SipHandler sipHandler;
    private Socket socket;
    private boolean receiving = true;
    private BufferedReader in;
    private PrintWriter out;
    private Controller controller;
    private ServerListener listener;

    public ClientHandler(Socket clientSocket, Controller controller, ServerListener listener) throws IOException {
        this.listener = listener;
        this.controller = controller;
        this.socket = clientSocket;
        this.sipHandler = new SipHandler(this);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        Thread t = new Thread(){
            @Override
            public void run() {
                receive();
            }
        };
        t.start();
    }

    private void receive(){
        System.out.println("Started listening.");
        while(receiving){

            try {
                String msg = in.readLine();

                if(msg!=null){
                    System.out.println("Received: " + msg);
                    sipHandler.processNextState(msg);
                }else{
                    listener.disconnectClient();
                }

            } catch (IOException e) {
                e.printStackTrace();
                listener.disconnectClient();
            }
        }
    }

    public void invokeInvite(){
        sipHandler.invokeInvite();
    }

    public void send(String message){
        System.out.println("Sent: " + message);

        this.out.println(message);
    }

    public Controller getController(){
        return this.controller;
    }

    public void stop(){
        receiving = false;

        try{
            if(socket!=null)
                socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        try{
            if(in!=null)
                in.close();
        }catch(IOException e){
            System.out.println("Socket is closed.");
        }

        if(out!=null)
            out.close();


    }
}
