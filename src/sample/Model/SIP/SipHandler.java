package sample.Model.SIP;

/**
 * Created by Anton on 2016-10-11.
 */
public class SipHandler {
    private SipState currentState;

    public SipHandler(){
        currentState = new StateIdling();
    }
    public String getState(){
        return currentState.getStateName();
    }

    public void processNextState(SipEvent evt){
        switch(evt){
            case INVITE_SENT:
                currentState = currentState.receivedInviteSent();
                break;
            case INVITE_RECEIVED:
                currentState = currentState.receivedInvite();
                break;
            case TRO_ACK_RECEIVED:
                currentState = currentState.receivedTROAck();
                break;
            case TRO_RECEIVED:
                currentState = currentState.receivedTRO();
                break;
            case BYE_RECEIVED:
                currentState = currentState.receivedBye();
                break;
            case BYE_SENT_RECEIVED:
                currentState = currentState.receivedByeSent();
                break;
            case BYE_OK_RECEIVED:
                currentState = currentState.receivedByeOk();
                break;
        }
    }

    public enum SipEvent{
        INVITE_SENT,
        INVITE_RECEIVED,
        TRO_ACK_RECEIVED,
        TRO_RECEIVED,
        BYE_RECEIVED,
        BYE_SENT_RECEIVED,
        BYE_OK_RECEIVED
    }
}
