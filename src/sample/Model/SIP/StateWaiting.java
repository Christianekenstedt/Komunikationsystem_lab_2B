package sample.Model.SIP;

import sample.Model.Net.ClientHandler;

/**
 * Created by Anton on 2016-10-11.
 */
public class StateWaiting extends SipState {

    public StateWaiting(){
        System.out.println("Waiting.");
    }

    @Override
    SipState receivedTRO(ClientHandler remote){
        remote.send("TRO_ACK");

        remote.getController().setStatusLabel("Streaming.");
        return new StateStreaming();
    }

    @Override
    String getStateName() {
        return "Waiting";
    }
}
