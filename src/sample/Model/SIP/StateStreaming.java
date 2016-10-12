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
        remote.getController().setStatusLabel("Quitting.");
        remote.send("BYE");
        return new StateQuitting();
    }

    SipState receivedByeReceived(ClientHandler remote){
        remote.send("BYE_OK");
        remote.getController().setStatusLabel("Idling.");
        remote.disconnect();
        return new StateIdling();
    }

    @Override
    String getStateName() {
        return "Quitting";
    }
}
