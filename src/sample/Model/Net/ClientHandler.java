package sample.Model.Net;

import sample.Controller.Controller;
import sample.Model.SIP.*;

import java.io.*;
import java.net.*;

/**
 * Created by Anton on 2016-10-11.
 */
public class ClientHandler {
    private Socket socket;
    private boolean receiving = true;
    private BufferedReader in;
    private PrintWriter out;
    private Controller controller;
    private ServerListener listener;

    private AudioStreamUDP audioStream;
    private int remoteAudioStreamPort;
    private InetAddress remoteAddress;

    public InetAddress getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(InetAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public int getRemoteAudioStreamPort() {
        return remoteAudioStreamPort;
    }

    public void setRemoteAudioStreamPort(int remoteAudioStreamPort) {
        this.remoteAudioStreamPort = remoteAudioStreamPort;
    }



    public ClientHandler(Socket clientSocket, Controller controller, ServerListener listener) throws IOException {
        this.audioStream = new AudioStreamUDP();
        this.listener = listener;
        this.controller = controller;
        this.socket = clientSocket;
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
        System.out.println("Started receiving.");
        while(receiving){

            try {
                String msg = in.readLine();

                if(msg!=null){
                    System.out.println("Received: " + msg);
                    listener.processRemoteMessage(msg);
                }else{
                    System.out.println("Message was null.");
                    disconnect();
                }

            } catch (IOException e) {
                System.out.println("Could not receive. Socket closed");
                listener.disconnectClient();
            }
        }
    }

    public void send(String message){
        System.out.println("Sent: " + message);

        this.out.println(message);
    }

    public Controller getController(){
        return this.controller;
    }

    public void disconnect(){
        controller.setStatusLabel("Idling.");
        controller.incomingCallStop();
        closeAudioStream();
        listener.disconnectClient();
    }

    private void closeAudioStream(){
        if (audioStream != null) {
            audioStream.stopStreaming();
        }
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



    public AudioStreamUDP getAudioStream(){
        return this.audioStream;
    }
}

