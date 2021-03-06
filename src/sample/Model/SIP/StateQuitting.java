package sample.Model.SIP;

import sample.Model.Net.ClientHandler;

/**
 * Created by Anton on 2016-10-11.
 */
public class StateQuitting extends SipState {

    public StateQuitting(){
        System.out.println("State: Quitting.");
    }

    @Override
    SipState receivedByeOk(ClientHandler remote){

        remote.getController().setStatusLabel("Idling.");
        remote.disconnect();
        remote.getAudioStream().stopStreaming();
        return new StateIdling();
    }

    @Override
    String getStateName() {
        return "Quitting";
    }
}
