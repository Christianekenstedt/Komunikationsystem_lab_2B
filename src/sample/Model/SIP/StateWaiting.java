package sample.Model.SIP;

import sample.Model.Net.AudioStreamUDP;
import sample.Model.Net.ClientHandler;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Anton on 2016-10-11.
 */
public class StateWaiting extends SipState {



    public StateWaiting(ClientHandler remote){
        System.out.println("State: Waiting.");
        ActionListener al = e -> {

            if(remote.isConnected()){
                if(remote.getRemoteBusy()==false){
                    System.out.println("No answer!");
                    receivedBye(remote);
                }
            }
            remote.getCallTimeout().stop();
        };
        remote.setCallTimeout(new Timer(10000, al));
        remote.getCallTimeout().start();
    }

    @Override
    SipState receivedBye(ClientHandler remote){
        remote.send("BYE");
        remote.getCallTimeout().stop();
        remote.getController().setStatusLabel("QUITTING");
        return new StateQuitting();
    }

    @Override
    SipState receivedBusy(ClientHandler remote){
        System.out.println("Remote is busy.");
        remote.getController().setStatusLabel("Remote is busy.");
        remote.disconnect();
        remote.getCallTimeout().stop();
        System.out.println("timer stopped");
        remote.setRemoteBusy(true);
        return new StateIdling();
    }

    @Override
    SipState receivedByeReceived(ClientHandler remote){
        remote.getCallTimeout().stop();
        remote.send("BYE_OK");
        remote.disconnect();
        return new StateIdling();
    }

    @Override
    SipState receivedTRO(ClientHandler remote, int port){
        remote.getCallTimeout().stop();
        remote.setRemoteAudioStreamPort(port);
        remote.send("TRO_ACK");

        remote.getController().setStatusLabel("Streaming.");

        AudioStreamUDP as = remote.getAudioStream();

        try {
            as.connectTo(remote.getRemoteAddress(), remote.getRemoteAudioStreamPort());
            as.startStreaming();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new StateStreaming();
    }

    @Override
    String getStateName() {
        return "Waiting";
    }
}
