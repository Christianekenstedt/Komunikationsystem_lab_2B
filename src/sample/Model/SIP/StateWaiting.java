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

    private Timer callTimeout;

    public StateWaiting(ClientHandler remote){
        System.out.println("Waiting.");
        ActionListener al = e -> {
            System.out.println("NO ANSWER!");
            receivedBye(remote);
            callTimeout.stop();
        };
        callTimeout = new Timer(10000, al);
        callTimeout.start();
    }

    @Override
    SipState receivedBye(ClientHandler remote){
        remote.send("BYE");
        remote.disconnect();
        callTimeout.stop();
        return new StateIdling();
    }

    @Override
    SipState receivedBusy(ClientHandler remote){
        System.out.println("Remote is busy.");
        remote.disconnect();
        callTimeout.stop();
        return new StateIdling();
    }

    @Override
    SipState receivedByeReceived(ClientHandler remote){
        callTimeout.stop();
        remote.send("BYE_OK");
        remote.disconnect();
        return new StateIdling();
    }

    @Override
    SipState receivedTRO(ClientHandler remote){
        callTimeout.stop();
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
