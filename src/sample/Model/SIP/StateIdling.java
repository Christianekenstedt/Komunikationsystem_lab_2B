package sample.Model.SIP;

import sample.Model.Net.ClientHandler;

/**
 * Created by chris on 2016-10-11.
 */
public class StateIdling extends SipState {

    public StateIdling(){
        System.out.println("State: Idling.");
    }

    @Override
    SipState receivedInviteReceived(ClientHandler remote, int port){

        remote.setRemoteAudioStreamPort(port);
        remote.getController().incomingCallStart();
        return this;
    }

    @Override
    SipState receivedInviteAccepted(ClientHandler remote){
        remote.send("TRO " + remote.getAudioStream().getLocalPort());
        remote.getController().setStatusLabel("Ringing.");
        return new StateRinging();
    }

    @Override
    SipState receivedBusy(ClientHandler remote){
        System.out.println("Remote is busy.");
        remote.getController().setStatusLabel("Busy.");
        remote.disconnect();
        remote.setRemoteBusy(true);
        if(remote.getCallTimeout() != null)
            remote.getCallTimeout().stop();
        return new StateIdling();
    }

    @Override
    SipState receivedInvite(ClientHandler remote){
        if(remote.getRemoteBusy() == false){
            //send invite
            remote.send("INVITE " + remote.getAudioStream().getLocalPort());
            //start waiting for a tro
            remote.getController().setStatusLabel("Waiting");
            return new StateWaiting(remote);
        }else{
            return this;
        }

    }

    @Override
    SipState receivedBye(ClientHandler remote){
        remote.send("BYE");
        return new StateQuitting();
    }

    @Override
    SipState receivedByeReceived(ClientHandler remote){
        remote.send("BYE_OK");
        remote.getController().setStatusLabel("Idling");
        return new StateIdling();
    }

    @Override
    String getStateName() {
        return "Idling";
    }
}
