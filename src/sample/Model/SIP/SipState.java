package sample.Model.SIP;

import sample.Model.Net.ClientHandler;

/**
 * Created by chris on 2016-10-11.
 */
public abstract class SipState {
    abstract String getStateName();
    SipState receivedInviteReceived(ClientHandler remote){return new StateIdling();}
    SipState receivedInvite(ClientHandler remote){return new StateIdling();}
    SipState receivedTRO(ClientHandler remote){return new StateIdling();}
    SipState receivedTROAck(ClientHandler remote){return new StateIdling();}
    SipState receivedByeReceived(ClientHandler remote){return new StateIdling();}
    SipState receivedBye(ClientHandler remote){return new StateIdling();}
    SipState receivedByeOk(ClientHandler remote){return new StateIdling();}
    SipState receivedError(ClientHandler remote){return new StateIdling();}
}
