package sample.Model.SIP;

import sample.Model.Net.ClientHandler;

/**
 * Created by chris on 2016-10-11.
 */
public class StateIdling extends SipState {

    @Override
    SipState receivedInviteReceived(ClientHandler remote){
        //send tro
        remote.send("TRO");
        return new StateRinging();
    }

    SipState receivedInvite(ClientHandler remote){
        //send invite
        remote.send("INVITE");
        //start waiting for a tro
        return new StateWaiting();
    }

    @Override
    String getStateName() {
        return null;
    }
}
