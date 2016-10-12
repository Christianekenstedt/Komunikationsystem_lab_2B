package sample.Model.SIP;

import sample.Model.Net.ClientHandler;

/**
 * Created by Anton on 2016-10-11.
 */
public class StateQuitting extends SipState {

    public StateQuitting(){
        System.out.println("Quitting.");
    }

    @Override
    SipState receivedByeOk(ClientHandler remote){

        remote.getController().setStatusLabel("Idling.");
        return new StateIdling();
    }

    @Override
    String getStateName() {
        return "Quitting";
    }
}
