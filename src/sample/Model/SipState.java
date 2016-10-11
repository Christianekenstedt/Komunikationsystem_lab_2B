package sample.Model;

/**
 * Created by chris on 2016-10-11.
 */
public abstract class SipState {
    /*
    * Metoder();
    * */
    SipState receivedInviteSent(){return this;}
    SipState receivedInvite(){return this;}
    SipState receivedTRO(){return this;}
    SipState receivedTROAck(){return this;}
    SipState receivedByeSent(){return this;}
    SipState receivedBye(){return this;}
    SipState receivedByeOk(){return this;}
}
