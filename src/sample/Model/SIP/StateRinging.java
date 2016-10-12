package sample.Model.SIP;

import sample.Model.Net.ClientHandler;

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
        return new StateStreaming();
    }

    @Override
    String getStateName() {
        return "Ringing";
    }
}
