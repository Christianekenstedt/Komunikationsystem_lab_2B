package sample.Model.Net;

import sample.Controller.Controller;
import sample.Model.SIP.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by Anton on 2016-10-11.
 */
public class ClientHandler {
    private SipHandler sipHandler;
    private Socket socket;
    private boolean receiving = true;
    private BufferedReader in;
    private DataOutputStream out;
    private Controller controller;

    public ClientHandler(Socket clientSocket, Controller controller) throws IOException {
        this.controller = controller;
        this.socket = clientSocket;
        this.sipHandler = new SipHandler(this);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new DataOutputStream(socket.getOutputStream());
        Thread t = new Thread(){
            @Override
            public void run() {
                receive();
            }
        };
        t.start();
    }

    private void receive(){
        while(receiving){
            try {
                String msg = in.readLine();

                if(msg!=null){
                    sipHandler.processNextState(msg);
                }

            } catch (IOException e) {
                e.printStackTrace();
                stop();
            }
        }
    }

    public void send(String message){
        try {
            out.writeBytes(message);
        } catch (IOException e) {
            e.printStackTrace();
            stop();
        }
    }

    public void stop(){
        try{
            if(in!=null)
                in.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        try{
            if(out!=null)
                out.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        try{
            if(socket!=null)
                socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        //reference to ServerListener to notify it that this handler has stopped.
    }
}
