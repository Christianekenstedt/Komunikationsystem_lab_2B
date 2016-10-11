package sample.Model.Net;

import sample.Model.SIP.SipHandler;
import java.net.*;

/**
 * Created by Anton on 2016-10-11.
 */
public class ClientHandler {
    private SipHandler sipHandler;
    private Socket socket;
    private boolean receiving = true;

    public ClientHandler(Socket clientSocket){
        this.socket = clientSocket;
        sipHandler = new SipHandler();
    }

    private void receive(){
        while(receiving){
            //code to receive
        }
    }

    public void send(String message){

    }
}
