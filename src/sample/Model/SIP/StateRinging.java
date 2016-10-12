package sample.Model.SIP;

import sample.Model.Net.AudioStreamUDP;
import sample.Model.Net.ClientHandler;

import java.io.IOException;

/**
 * Created by Anton on 2016-10-11.
 */
public class StateRinging extends SipState {

    public StateRinging(){
        System.out.println("Ringing.");
    }

    @Override
    SipState receivedTROAck(ClientHandler remote){

        remote.getController().setStatusLabel("Streaming");

        //code to start audiostream

        AudioStreamUDP as = remote.getAudioStream();

        try {
            as.connectTo(remote.getRemoteAddress(), remote.getRemoteAudioStreamPort());
            as.startStreaming();
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 2016-10-12
        }

        return new StateStreaming();
    }

    @Override
    String getStateName() {
        return "Ringing";
    }
}
