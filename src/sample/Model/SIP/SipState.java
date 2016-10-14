package sample.Model.SIP;

import sample.Model.Net.ClientHandler;

/**
 * Created by chris on 2016-10-11.
 */
public abstract class SipState {
    abstract String getStateName();
    SipState receivedBusy(ClientHandler remote){remote.disconnect();return new StateIdling();}
    SipState receivedInviteReceived(ClientHandler remote, int port){remote.disconnect();return new StateIdling();}
    SipState receivedInvite(ClientHandler remote){remote.disconnect();return new StateIdling();}
    SipState receivedTRO(ClientHandler remote, int port){remote.disconnect();return new StateIdling();}
    SipState receivedTROAck(ClientHandler remote){remote.disconnect();return new StateIdling();}
    SipState receivedByeReceived(ClientHandler remote){remote.disconnect();return new StateIdling();}
    SipState receivedBye(ClientHandler remote){remote.disconnect();return new StateIdling();}
    SipState receivedByeOk(ClientHandler remote){remote.disconnect();return new StateIdling();}
    SipState receivedError(ClientHandler remote){remote.disconnect();return new StateIdling();}
    SipState receivedInviteAccepted(ClientHandler remote){remote.disconnect();return new StateIdling();}
}
