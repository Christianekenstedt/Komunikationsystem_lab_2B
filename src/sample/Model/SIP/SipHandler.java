package sample.Model.SIP;

import sample.Model.Net.ClientHandler;

/**
 * Created by Anton on 2016-10-11.
 */
public class SipHandler {
    private SipState currentState;
    private ClientHandler client;

    public SipHandler(ClientHandler client){
        currentState = new StateIdling();
        this.client = client;
    }
    public String getState(){
        return currentState.getStateName();
    }

    public void processNextState(String message){
        SipEvent evt = parseMessage(message);
        if(evt!=null){
            switch(evt){
                case INVITE_RECEIVED:
                    client.getController().incommingCallStart();
                    //currentState = currentState.receivedInviteReceived(client);
                    break;
                case TRO_ACK_RECEIVED:
                    currentState = currentState.receivedTROAck(client);
                    break;
                case TRO_RECEIVED:
                    currentState = currentState.receivedTRO(client);
                    break;
                case BYE_RECEIVED:
                    currentState = currentState.receivedByeReceived(client);
                    break;
                case BYE_OK_RECEIVED:
                    currentState = currentState.receivedByeOk(client);
                    break;
                default:
                    currentState = currentState.receivedError(client);
                    break;
            }
        }else{
            currentState = currentState.receivedError(client);
        }

    }

    private SipHandler.SipEvent parseMessage(String msg){
        String[] split = msg.split(" ");

        switch(split[0]){
            case "INVITE":
                return SipHandler.SipEvent.INVITE_RECEIVED;
            case "TRO":
                return SipHandler.SipEvent.TRO_RECEIVED;
            case "TRO_ACK":
                return SipHandler.SipEvent.TRO_ACK_RECEIVED;
            case "BYE":
                return SipHandler.SipEvent.BYE_RECEIVED;
            case "BYE_OK":
                return SipHandler.SipEvent.BYE_OK_RECEIVED;
            default:
                return null;
        }
    }

    public void invokeAcceptInvite(){
        currentState = currentState.receivedInviteReceived(client);
    }

    public void invokeInvite(){
        currentState = currentState.receivedInvite(client);
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
