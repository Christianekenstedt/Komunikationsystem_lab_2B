package sample.Model;

/**
 * Created by chris on 2016-10-11.
 */
public abstract class SipState {
    abstract String getStateName();
    SipState receivedInviteSent(){return new StateIdling();}
    SipState receivedInvite(){return new StateIdling();}
    SipState receivedTRO(){return new StateIdling();}
    SipState receivedTROAck(){return new StateIdling();}
    SipState receivedByeSent(){return new StateIdling();}
    SipState receivedBye(){return new StateIdling();}
    SipState receivedByeOk(){return new StateIdling();}

}
