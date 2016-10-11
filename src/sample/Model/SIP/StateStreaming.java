package sample.Model.SIP;

import sample.Model.Net.ClientHandler;

/**
 * Created by chris on 2016-10-11.
 */
public class StateStreaming extends SipState {

    public StateStreaming(){
        System.out.println("Streaming.");
    }

    SipState receivedBye(ClientHandler remote){
        return new StateQuitting();
    }

    SipState receivedByeReceived(ClientHandler remote){
        remote.send("BYE_OK");
        return new StateIdling();
    }

    @Override
    String getStateName() {
        return null;
    }
}
