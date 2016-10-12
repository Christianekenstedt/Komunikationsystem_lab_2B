package sample.Model.SIP;

import sample.Model.Net.ClientHandler;

/**
 * Created by chris on 2016-10-11.
 */
public class StateIdling extends SipState {

    public StateIdling(){
        System.out.println("Idling.");
    }

    @Override
    SipState receivedInviteReceived(ClientHandler remote){

        remote.send("TRO " + remote.getAudioStream().getLocalPort());
        remote.getController().setStatusLabel("Ringing.");
        return new StateRinging();
    }

    SipState receivedInvite(ClientHandler remote){
        //send invite
        remote.send("INVITE " + remote.getAudioStream().getLocalPort());
        //start waiting for a tro
        remote.getController().setStatusLabel("Waiting");
        return new StateWaiting(remote);
    }

    @Override
    SipState receivedBye(ClientHandler remote){
        remote.send("BYE");
        return new StateQuitting();
    }

    @Override
    String getStateName() {
        return "Idling";
    }
}
