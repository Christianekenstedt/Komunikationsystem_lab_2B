package sample.Model.SIP;

import sample.Model.Net.ClientHandler;

/**
 * Created by chris on 2016-10-11.
 */
public class StateStreaming extends SipState {

    public StateStreaming(){
        System.out.println("State: Streaming.");
    }

    SipState receivedBye(ClientHandler remote){
        remote.send("BYE");
        remote.getController().setStatusLabel("Quitting.");
        return new StateQuitting();
    }

    SipState receivedByeReceived(ClientHandler remote){
        remote.send("BYE_OK");
        remote.getAudioStream().stopStreaming();
        remote.disconnect();
        remote.getController().setStatusLabel("Idling.");
        return new StateIdling();
    }

    @Override
    String getStateName() {
        return "Quitting";
    }
}
