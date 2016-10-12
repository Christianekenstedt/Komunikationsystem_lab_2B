package sample.Model.SIP;

import sample.Model.Net.AudioStreamUDP;
import sample.Model.Net.ClientHandler;

import java.io.IOException;

/**
 * Created by Anton on 2016-10-11.
 */
public class StateWaiting extends SipState {

    public StateWaiting(){
        System.out.println("Waiting.");
    }

    @Override
    SipState receivedBye(ClientHandler remote){
        remote.send("BYE");
        remote.disconnect();
        return new StateIdling();
    }

    @Override
    SipState receivedByeReceived(ClientHandler remote){
        remote.send("BYE_OK");
        remote.disconnect();
        return new StateIdling();
    }

    @Override
    SipState receivedTRO(ClientHandler remote){
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
